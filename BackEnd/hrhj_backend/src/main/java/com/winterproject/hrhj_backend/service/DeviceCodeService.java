package com.winterproject.hrhj_backend.service;

import com.winterproject.hrhj_backend.domain.entity.DeviceCode;
import com.winterproject.hrhj_backend.domain.repository.DeviceCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class DeviceCodeService {

    @Autowired
    private DeviceCodeRepository deviceCodeRepository;

    public String createCode()
    {
        char[] charaters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                '0','1','2','3','4','5','6','7','8','9'};

        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for( int i = 0 ; i < 15 ; i++ ){
            sb.append( charaters[ random.nextInt( charaters.length ) ] );
        }

        return sb.toString();
    }

    @Transactional
    public void saveDeviceCode(DeviceCode deviceCode)
    {
        deviceCodeRepository.deleteByUid(deviceCode.getUid());
        deviceCodeRepository.save(deviceCode);
    }

    public int checkDeviceCode(DeviceCode deviceCode)
    {
        DeviceCode newDevice = deviceCodeRepository.findByCode(deviceCode.getCode());

        if(newDevice == null)
        {
            return deviceCode.getUid();
        }
        else
        {
            deviceCodeRepository.deleteById(newDevice.getCid());
            return newDevice.getUid();
        }
    }

    public DeviceCode getDeviceCodeInfo(int cid)
    {
        return deviceCodeRepository.findById(cid).orElse(new DeviceCode());
    }
}
