package com.company.dto.object;

import com.company.dto.CertificateDTO;
import com.company.dto.LicenceDTO;
import com.company.enums.ObjectImportance;
import com.company.enums.ObjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ObjectFullInfoDTO {

//    (id,key,title,description,
//    preview_attach(id,url),attach(id,url,duration),
//    category(id,name),published_date,channel(id,name,photo(url)),shared_count,duration
//    tagList(id,name), view_count,Like(like_count,dislike_count,
//    isUserLiked,IsUserDisliked),)

    private String uuid;
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

    private LocalDateTime createdDate;
    private Boolean visible;
    private ObjectStatus status;




}
