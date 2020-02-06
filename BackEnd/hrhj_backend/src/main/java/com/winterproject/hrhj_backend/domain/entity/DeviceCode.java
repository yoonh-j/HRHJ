package com.winterproject.hrhj_backend.domain.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "device_code")
public class DeviceCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code_id")
    private int cid;

    @Column(nullable = true)
    private int uid;
    private String code;

}
