package com.company;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.security.MessageDigest;

@SpringBootTest
class MaiDemoApplicationTests {
    @Autowired
    private ProfileRepository repository;

    @Test
    void contextLoads() {
        ProfileEntity entity = new ProfileEntity();
        entity.setEmail("t.lochin.98@gmail.com");
        entity.setRole(ProfileRole.ROLE_SUPER_ADMIN);
        entity.setUsername("lockbeck");
        entity.setPassword(MD5Util.getMd5("1111"));
        entity.setStatus(ProfileStatus.ACTIVE);
        repository.save(entity);
    }

    @Test
    public static String sha256(String password) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(password.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }
    @Test
    public static void main(String[] args) {
        System.out.println(sha256("1111"));
        System.out.println(sha256("111"));
    }

}
