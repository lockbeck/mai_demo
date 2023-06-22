package com.company.service;

import com.company.dto.LicenceDTO;
import com.company.entity.LicenceEntity;
import com.company.repository.LicenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenceService {
    @Autowired
    private LicenceRepository licenceRepository;
    public LicenceEntity create(LicenceDTO dto){
        LicenceEntity entity = new LicenceEntity();
        entity.setInfo(dto.getInfo());
        entity.setUrl(dto.getUrl());
//        entity.setStartDate(dto.getStartDate());
//        entity.setEndDate(dto.getEndDate());
        licenceRepository.save(entity);
        return entity;

    }
    public LicenceEntity saveNew(LicenceEntity licence,LicenceDTO dto){
//        licenceRepository.delete(licence);
        LicenceEntity entity = new LicenceEntity();
        entity.setInfo(dto.getInfo());
        entity.setUrl(dto.getUrl());
//        entity.setStartDate(dto.getStartDate());
//        entity.setEndDate(dto.getEndDate());
        licenceRepository.save(entity);
        return entity;

    }

    public LicenceDTO getDTO(LicenceEntity licence) {
        LicenceDTO dto= new LicenceDTO();
        dto.setId(licence.getId());
        dto.setInfo(licence.getInfo());
        dto.setUrl(licence.getUrl());
        dto.setEndDate(licence.getEndDate());
        dto.setStartDate(licence.getStartDate());
        return dto;
    }
}
