package com.company.repository;

import com.company.entity.ObjectEntity;
import com.company.enums.ObjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ObjectRepository extends JpaRepository<ObjectEntity, String> {


    @Modifying
    @Transactional
    @Query("update ObjectEntity as o set o.status =?2 where o.uuid =?1")
    void changeStatus(String id, ObjectStatus status);
    @Modifying
    @Transactional
    @Query("update ObjectEntity as o set o.visible =?2, o.status =?3 where o.uuid =?1")
    void delete(String id, Boolean visible,ObjectStatus status);


    List<ObjectEntity> findByStatus(ObjectStatus status);


}
