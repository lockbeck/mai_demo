package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CertificateDTO {
    private Integer id;
    private String info;
    private String url;
    private LocalDateTime startDate;
    private LocalDateTime endDate;



}
