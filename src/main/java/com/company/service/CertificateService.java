package com.company.service;

import com.company.dto.CertificateDTO;
import com.company.entity.CertificateEntity;
import com.company.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;
    public CertificateEntity create(CertificateDTO dto){
        CertificateEntity entity = new CertificateEntity();
        entity.setInfo(dto.getInfo());
        entity.setUrl(dto.getUrl());
//        entity.setStartDate(dto.getStartDate());
//        entity.setEndDate(dto.getEndDate());
        certificateRepository.save(entity);
        return entity;

    }
    public CertificateEntity saveNew(CertificateEntity certificate,CertificateDTO dto){
//        certificateRepository.delete(certificate);
        CertificateEntity entity = new CertificateEntity();
        entity.setInfo(dto.getInfo());
        entity.setUrl(dto.getUrl());
//        entity.setStartDate(dto.getStartDate());
//        entity.setEndDate(dto.getEndDate());
        certificateRepository.save(entity);
        return entity;

    }

    public CertificateDTO getDTO(CertificateEntity certificate) {
        CertificateDTO dto= new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setInfo(certificate.getInfo());
        dto.setUrl(certificate.getUrl());
        dto.setEndDate(certificate.getEndDate());
        dto.setStartDate(certificate.getStartDate());
        return dto;
    }
}
