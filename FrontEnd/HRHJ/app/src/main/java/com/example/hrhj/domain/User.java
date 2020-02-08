package com.example.hrhj.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int uid;

    private List<Post> postList = new ArrayList<>();

    public void addPost(Post post)
    {
        this.postList.add(post);
    }

    public void deletePost(Post post)
    {
        this.postList.remove(post);
    }
}
