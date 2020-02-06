package com.winterproject.hrhj_backend.service;

import com.winterproject.hrhj_backend.domain.repository.DeviceCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceCodeService {

    @Autowired
    private DeviceCodeRepository deviceCodeRepository;
}
