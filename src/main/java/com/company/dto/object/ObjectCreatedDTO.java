package com.company.dto.object;

import com.company.dto.CertificateDTO;
import com.company.dto.LicenceDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectCreatedDTO {
    private String id;
    private String name;
    private String ownerSubject;
    private String ownerInfo;
    private String goal;
    private String importance;
    private String damages;
    private CertificateDTO certificateDTO;
    private LicenceDTO licenceDTO;
    private String web_usage;
    private String cyber_security;
    private String threats;
    private String hacked_outcomes;
    private String actions;





}
