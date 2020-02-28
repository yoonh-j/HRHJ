package com.example.hrhj.domain.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {


    private int pid;
    private int uid;
    private String image;
    private String text;
    private long date;
    private int emotion;
    private int textColor;



    @Override
    public boolean equals(Object o){
        return ( pid == ((Post)o).pid );
    }



}
