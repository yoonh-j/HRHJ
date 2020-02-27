package com.winterproject.hrhj_backend.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int uid;

    @OneToMany(targetEntity = Post.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    public void addPost(Post post) { this.postList.add(post); }
    public void deletePost(Post post) { this.postList.remove(post); }
}
