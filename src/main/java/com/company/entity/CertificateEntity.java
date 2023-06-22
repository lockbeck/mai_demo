package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "certificate")
@Entity
@Getter
@Setter

public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String info;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private LocalDateTime startDate= LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime endDate= LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;


}
