package com.company.dto.object;

import com.company.dto.CertificateDTO;
import com.company.dto.LicenceDTO;
import com.company.enums.ObjectImportance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectUpdateDTO {
    private String name;
    private String ownerSubject;
    private String ownerInfo;
    private String goal;
    private ObjectImportance importance;
    private String damages;
    private CertificateDTO certificateDTO;
    private LicenceDTO licenceDTO;
    private String web_usage;
    private String cyber_security;
    private String threats;
    private String hacked_outcomes;
    private String actions;
}
