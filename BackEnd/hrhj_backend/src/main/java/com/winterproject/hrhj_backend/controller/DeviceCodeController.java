package com.winterproject.hrhj_backend.controller;


import com.winterproject.hrhj_backend.domain.entity.DeviceCode;
import com.winterproject.hrhj_backend.service.DeviceCodeService;
import com.winterproject.hrhj_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class DeviceCodeController {

    @Autowired
    DeviceCodeService deviceCodeService;

    public DeviceCodeController(DeviceCodeService deviceCodeService){ this.deviceCodeService = deviceCodeService;}

    @PostMapping(path = "/createdevicecode")
    public DeviceCode savePost(@RequestBody int uid) {
        DeviceCode deviceCode = new DeviceCode();
        deviceCode.setUid(uid);
        deviceCode.setCode(deviceCodeService.createCode());
        deviceCodeService.saveDeviceCode(deviceCode);

        return deviceCodeService.getDeviceCodeInfo(deviceCode.getCid());
    }

    @PostMapping(path = "/changedevice")
    public int changeDevice(@RequestBody DeviceCode deviceCode) {

        return deviceCodeService.checkDeviceCode(deviceCode);
    }
}
