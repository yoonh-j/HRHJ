package com.winterproject.hrhj_backend.domain.repository;

import com.winterproject.hrhj_backend.domain.entity.DeviceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeviceCodeRepository extends JpaRepository<DeviceCode, Integer> {

    @Modifying
    @Query("delete from DeviceCode where uid = :uid")
    void deleteByUid(@Param("uid") int uid);

    DeviceCode findByCode(@Param("code") String code);




}
