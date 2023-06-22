package com.company.repository;

import com.company.entity.CertificateEntity;
import com.company.entity.LicenceEntity;
import org.springframework.data.repository.CrudRepository;

public interface LicenceRepository extends CrudRepository<LicenceEntity,Integer> {
}
