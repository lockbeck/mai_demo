package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.profile.ChangePasswordDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.exp.NoPermissionException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import com.company.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;


    public String encode(String password) {

        BCryptPasswordEncoder b = new BCryptPasswordEncoder();

        return b.encode(password);
    }

    // admin
    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity profile = getProfile();

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)&&!profile.getVisible()) {
            throw  new NoPermissionException("no access");
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");
        }

        // name; surname; login; password;
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setUsername(dto.getUsername());

        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setJwt(JwtUtil.encode(entity.getId()));
        return dto;
    }





    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile Not found");
        });
    }

    public ProfileDTO getProfileDTOById(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("This user not fount");
        }

        ProfileEntity profileEntity = optional.get();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setCreatedDate(profileEntity.getCreatedDate());
        profileDTO.setUsername(profileEntity.getUsername());
        profileDTO.setId(profileEntity.getId());

        return profileDTO;
    }

    public List<ProfileDTO> getAllProfileDTO() {

        Iterable<ProfileEntity> iterable = profileRepository.findAll();

        List<ProfileDTO> profileDTOS = new ArrayList<>();
        iterable.forEach(profileEntity -> {

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setEmail(profileEntity.getEmail());
            profileDTO.setCreatedDate(profileEntity.getCreatedDate());
            profileDTO.setUsername(profileEntity.getUsername());
            profileDTO.setId(profileEntity.getId());
            profileDTO.setRole(profileEntity.getRole());
            profileDTO.setStatus(profileEntity.getStatus());

            profileDTOS.add(profileDTO);
        });

        return profileDTOS;
    }

    public ProfileDTO changeVisible(Integer profileId) {
        ProfileEntity profile =  getProfile();

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)&&!profile.getVisible()) {
            throw  new NoPermissionException("no access");
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");
        }
        ProfileEntity entity = get(profileId);
        entity.setVisible(!entity.getVisible());
        if(entity.getStatus().equals(ProfileStatus.ACTIVE)){
            entity.setStatus(ProfileStatus.BLOCKED);
        }
        else {
            entity.setStatus(ProfileStatus.ACTIVE);
        }

        profileRepository.save(entity);

        return getProfileDTOById(entity.getId());
    }






    public ProfileEntity getProfile() {

        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public String changePassword(ChangePasswordDTO dto) {

        ProfileEntity profile = getProfile();
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");

        }
        profile.setPassword(MD5Util.getMd5(dto.getPassword()));
        profileRepository.save(profile);

        return "PASSWORD CHANGED";

    }




}
