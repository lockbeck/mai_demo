package com.company.service;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.object.ObjectCreatedDTO;
import com.company.dto.object.ObjectFullInfoDTO;
import com.company.dto.object.ObjectShortInfoDTO;
import com.company.dto.object.ObjectUpdateDTO;
import com.company.entity.ObjectEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ObjectStatus;
import com.company.enums.ProfileRole;
import com.company.exp.ItemNotFoundException;
import com.company.exp.NoPermissionException;
import com.company.exp.NotPermissionException;
import com.company.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private LicenceService licenceService;

    public ResponseInfoDTO createObject(ObjectCreatedDTO dto) {


        ObjectEntity entity = new ObjectEntity();
        entity.setName(dto.getName());
        entity.setOwnerSubject(dto.getOwnerSubject());
        entity.setOwnerInfo(dto.getOwnerInfo());
        entity.setGoal(dto.getGoal());
        entity.setImportance(dto.getImportance());
        entity.setDamages(dto.getDamages());
        entity.setCertificate(certificateService.create(dto.getCertificateDTO()));
        entity.setLicence(licenceService.create(dto.getLicenceDTO()));
        entity.setProfileId(profileService.getProfile().getId());
        entity.setWeb_usage(dto.getWeb_usage());
        entity.setCyber_security(dto.getCyber_security());
        entity.setThreats(dto.getThreats());
        entity.setHacked_outcomes(dto.getHacked_outcomes());
        entity.setActions(dto.getActions());


        objectRepository.save(entity);


        return new ResponseInfoDTO(1, "Object successfully created");
    }

    public ResponseInfoDTO update(ObjectUpdateDTO dto, String objectId) {

        ProfileEntity profile = profileService.getProfile();

        ObjectEntity entity = get(objectId);

        if (!profile.getRole().equals(ProfileRole.ROLE_MANAGER)) {
            return new ResponseInfoDTO(-1, "no access");
        }
        System.out.println("noaccess");

        entity.setName(dto.getName());
        entity.setOwnerSubject(dto.getOwnerSubject());
        entity.setOwnerInfo(dto.getOwnerInfo());
        entity.setGoal(dto.getGoal());
        entity.setImportance(dto.getImportance());
        entity.setDamages(dto.getDamages());
        System.out.println("damages");

        entity.setCertificate(certificateService.saveNew(entity.getCertificate(),dto.getCertificateDTO()));
        entity.setLicence(licenceService.saveNew(entity.getLicence(),dto.getLicenceDTO()));
        System.out.println("certlicen");

        entity.setWeb_usage(dto.getWeb_usage());
        entity.setCyber_security(dto.getCyber_security());
        entity.setThreats(dto.getThreats());
        entity.setHacked_outcomes(dto.getHacked_outcomes());
        entity.setActions(dto.getActions());

        objectRepository.save(entity);



        return new ResponseInfoDTO(1, "Object successfully updated");

    }
    public ResponseInfoDTO publish(String objectId){
        ProfileEntity profile = profileService.getProfile();

        ObjectEntity entity = get(objectId);

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            return new ResponseInfoDTO(-1, "no access");
        }
        if(entity.getStatus().equals(ObjectStatus.PUBLISHED)){
            objectRepository.publish(entity.getUuid(), ObjectStatus.NOT_PUBLISHED);
        }
        else {
            objectRepository.publish(entity.getUuid(), ObjectStatus.PUBLISHED);

        }
        return new ResponseInfoDTO(1, "OBJECT STATUS SUCCESSFULLY UPDATED");
    }



    private ObjectEntity get(String objectId) {

        return objectRepository.findById(objectId).orElseThrow(() -> {
            throw new ItemNotFoundException("OBJECT not fount");
        });
    }







    public ObjectFullInfoDTO getById(String videoId) {

        ProfileEntity profile = profileService.getProfile();

        ObjectEntity entity = get(videoId);

        if (profile.getRole().equals(ProfileRole.ROLE_USER)) {
            throw new NotPermissionException("no access");
        }

        ObjectFullInfoDTO dto = new ObjectFullInfoDTO();
        if(entity.getVisible().equals(Boolean.FALSE)){
            throw new NoPermissionException("OBJECT HAS BEEN DELETED");
        }
        dto.setUuid(entity.getUuid());
        dto.setName(entity.getName());
        dto.setOwnerSubject(entity.getOwnerSubject());
        dto.setOwnerInfo(entity.getOwnerInfo());
        dto.setGoal(entity.getGoal());
        dto.setImportance(entity.getImportance());
        dto.setDamages(entity.getDamages());
        dto.setWeb_usage(entity.getWeb_usage());
        dto.setCyber_security(entity.getCyber_security());
        dto.setThreats(entity.getThreats());
        dto.setHacked_outcomes(entity.getHacked_outcomes());
        dto.setActions(entity.getActions());
        dto.setCertificateDTO(certificateService.getDTO(entity.getCertificate()));
        dto.setLicenceDTO(licenceService.getDTO(entity.getLicence()));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setStatus(entity.getStatus());



        return dto;

    }

    public List<ObjectShortInfoDTO> pagination() {

        ProfileEntity profile = profileService.getProfile();

        if (profile.getRole().equals(ProfileRole.ROLE_USER)) {
            throw new NotPermissionException("no access");
        }

        List<ObjectShortInfoDTO> infoDTOList = new ArrayList<>();
        List<ObjectEntity> all = objectRepository.findAll();
        all.forEach(objectEntity -> {
            if(objectEntity.getVisible().equals(Boolean.TRUE)) {
                ObjectShortInfoDTO shortInfoDTO = new ObjectShortInfoDTO();
                shortInfoDTO.setCertificateDTO(certificateService.getDTO(objectEntity.getCertificate()));
                shortInfoDTO.setLicenceDTO(licenceService.getDTO(objectEntity.getLicence()));
                shortInfoDTO.setName(objectEntity.getName());
                shortInfoDTO.setGoal(objectEntity.getGoal());
                shortInfoDTO.setOwnerInfo(objectEntity.getOwnerInfo());
                shortInfoDTO.setOwnerSubject(objectEntity.getOwnerSubject());
                shortInfoDTO.setDamages(objectEntity.getDamages());
                shortInfoDTO.setCyber_security(objectEntity.getCyber_security());
                shortInfoDTO.setHacked_outcomes(objectEntity.getHacked_outcomes());
                shortInfoDTO.setActions(objectEntity.getActions());
                shortInfoDTO.setThreats(objectEntity.getThreats());
                shortInfoDTO.setImportance(objectEntity.getImportance());
                shortInfoDTO.setWeb_usage(objectEntity.getWeb_usage());
                infoDTOList.add(shortInfoDTO);
            }

        });



        return infoDTOList;
    }
    public ResponseInfoDTO delete(String objectId){
        ProfileEntity profile = profileService.getProfile();

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new NotPermissionException("no access");
        }
        objectRepository.delete(objectId,Boolean.FALSE,ObjectStatus.NOT_PUBLISHED);
        return new ResponseInfoDTO(1,"Object successfully deleted");
    }


}
