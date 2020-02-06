package com.winterproject.hrhj_backend.controller;

import com.winterproject.hrhj_backend.domain.entity.Post;
import com.winterproject.hrhj_backend.domain.entity.User;
import com.winterproject.hrhj_backend.service.PostService;
import com.winterproject.hrhj_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public PostController(PostService postService) { this.postService = postService;}

    @PostMapping(path = "/savepost")
    public Post savePost(@RequestBody Post post) {
        User user = userService.getUserInfo(post.getUid());

        user.addPost(post);
        userService.saveUserInfo(user);

        return post;
    }

}
