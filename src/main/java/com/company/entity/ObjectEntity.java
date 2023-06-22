package com.company.entity;

import com.company.enums.ObjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "object")
@NoArgsConstructor
public class ObjectEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String ownerSubject;
    @Column(nullable = false)
    private String ownerInfo;
    @Column(nullable = false)
    private String goal;
    @Column(nullable = false)
    private String importance;
    @Column(nullable = false)
    private String damages;

    @Column(nullable = false)
    private String web_usage;
    @Column(nullable = false)
    private String cyber_security;
    @Column(nullable = false)
    private String threats;
    @Column(nullable = false)
    private String hacked_outcomes;
    @Column(nullable = false)
    private String actions;

    @JoinColumn(name = "certificate_id")
    @OneToOne(targetEntity = com.company.entity.CertificateEntity.class,fetch = FetchType.LAZY)
    private CertificateEntity certificate;
    @JoinColumn(name = "licence_id")
    @OneToOne(targetEntity = com.company.entity.LicenceEntity.class,fetch = FetchType.LAZY)
    private LicenceEntity licence;


    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, insertable = false, updatable = false)
    private ProfileEntity profile;


    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ObjectStatus status = ObjectStatus.NOT_PUBLISHED;

    public ObjectEntity(String uuid) {
        this.uuid = uuid;
    }
}
