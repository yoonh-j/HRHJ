package com.winterproject.hrhj_backend.domain.repository;

import com.winterproject.hrhj_backend.domain.entity.DeviceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceCodeRepository extends JpaRepository<DeviceCode, Integer> {
}
