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
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
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

    public String createObject(ObjectCreatedDTO dto) {
        ProfileEntity profile = profileService.getProfile();
        if (!profile.getRole().equals(ProfileRole.ROLE_USER)) {
            throw  new NoPermissionException("no access");
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");
        }

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
        entity.setStatus(ObjectStatus.WAITING);

        objectRepository.save(entity);

        return "Object successfully created";
    }

    public String update(ObjectUpdateDTO dto, String objectId) {

        ProfileEntity profile = profileService.getProfile();

        ObjectEntity entity = get(objectId);

        if (!profile.getRole().equals(ProfileRole.ROLE_MANAGER)) {
            throw  new NoPermissionException("no access");

        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");

        }
        if(entity.getVisible().equals(Boolean.FALSE)){
            throw new NoPermissionException("OBJECT HAS BEEN DELETED");
        }

        entity.setName(dto.getName());
        entity.setOwnerSubject(dto.getOwnerSubject());
        entity.setOwnerInfo(dto.getOwnerInfo());
        entity.setGoal(dto.getGoal());
        entity.setImportance(dto.getImportance());
        entity.setDamages(dto.getDamages());

        entity.setCertificate(certificateService.saveNew(entity.getCertificate(),dto.getCertificateDTO()));
        entity.setLicence(licenceService.saveNew(entity.getLicence(),dto.getLicenceDTO()));

        entity.setWeb_usage(dto.getWeb_usage());
        entity.setCyber_security(dto.getCyber_security());
        entity.setThreats(dto.getThreats());
        entity.setHacked_outcomes(dto.getHacked_outcomes());
        entity.setActions(dto.getActions());

        objectRepository.save(entity);

        return "Object successfully updated";
    }
    public String reject(String objectId){
        ProfileEntity profile = profileService.getProfile();

        ObjectEntity entity = get(objectId);

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw  new NoPermissionException("no access");

        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");

        }
        if(entity.getVisible().equals(Boolean.FALSE)){
            throw new NoPermissionException("OBJECT HAS BEEN DELETED");
        }
        if(entity.getStatus().equals(ObjectStatus.REJECTED)){
            throw new BadRequestException("OBJECT ALREADY REJECTED");
        }
        objectRepository.changeStatus(entity.getUuid(), ObjectStatus.REJECTED);

        return "OBJECT REJECTED";
    }

    public String success(String objectId){
        ProfileEntity profile = profileService.getProfile();

        ObjectEntity entity = get(objectId);

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw  new NoPermissionException("no access");

        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");

        }
        if(entity.getVisible().equals(Boolean.FALSE)){
            throw new NoPermissionException("OBJECT HAS BEEN DELETED");
        }
        if(entity.getStatus().equals(ObjectStatus.SUCCESS)){
            throw new BadRequestException("OBJECT ALREADY SUCCESSFUL");
        }
        objectRepository.changeStatus(entity.getUuid(), ObjectStatus.SUCCESS);

        return "OBJECT  SUCCESS";
    }

    private ObjectEntity get(String objectId) {

        return objectRepository.findById(objectId).orElseThrow(() -> {
            throw new ItemNotFoundException("OBJECT not fount");
        });
    }

    public ObjectFullInfoDTO getById(String videoId) {

        ProfileEntity profile = profileService.getProfile();


        if (profile.getRole().equals(ProfileRole.ROLE_USER)) {
            throw new NotPermissionException("no access");
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw new NotPermissionException("no access");
        }

        ObjectEntity entity = get(videoId);
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
        if(entity.getStatus().equals(ObjectStatus.WAITING)){
            objectRepository.changeStatus(entity.getUuid(),ObjectStatus.PROCESSING);
        }
        return dto;
    }

    public List<ObjectShortInfoDTO> pagination() {

        ProfileEntity profile = profileService.getProfile();

        if (profile.getRole().equals(ProfileRole.ROLE_USER)) {
            throw new NotPermissionException("no access");
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw new NotPermissionException("no access");
        }

        List<ObjectShortInfoDTO> infoDTOList = new ArrayList<>();
        List<ObjectEntity> all = objectRepository.findAll();
        all.forEach(objectEntity -> {
            if(objectEntity.getVisible().equals(Boolean.TRUE)) {
                if(objectEntity.getStatus().equals(ObjectStatus.WAITING)){
                    objectRepository.changeStatus(objectEntity.getUuid(),ObjectStatus.PROCESSING);
                }
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

    public String delete(String objectId){
        ProfileEntity profile = profileService.getProfile();

        if (!profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw  new NoPermissionException("no access");

        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access");


        }

        objectRepository.delete(objectId,Boolean.FALSE,ObjectStatus.DELETED);
        return "Object successfully deleted";
    }

    public List<ObjectShortInfoDTO> getByStatus(ObjectStatus status) {
        ProfileEntity profile = profileService.getProfile();

        if (profile.getStatus().equals(ProfileStatus.BLOCKED)||(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE))) {
            throw  new NoPermissionException("no access to see objects by status");
        }
        List<ObjectShortInfoDTO> infoDTOList = new ArrayList<>();
        List<ObjectEntity> all = objectRepository.findByStatus(status);
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

}
