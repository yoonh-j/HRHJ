package com.winterproject.hrhj_backend.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @Column(nullable = true)
    private int uid;
    private String image;
    private String text;
    private long date;
    private int emotion;
    private int textColor;


}
