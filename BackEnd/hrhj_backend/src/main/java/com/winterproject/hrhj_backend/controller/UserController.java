package com.winterproject.hrhj_backend.controller;


import com.winterproject.hrhj_backend.domain.entity.Post;
import com.winterproject.hrhj_backend.domain.entity.User;
import com.winterproject.hrhj_backend.service.UserService;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) { this.userService = userService;}

    @PostMapping(path = "/register")
    public User createUser(@RequestBody User user) {
        userService.createUser(user);
        return userService.getUserInfo(user.getUid());
    }

    @PostMapping(path = "/getpostlist")
    public List<Post> getPostList(@RequestBody int uid) {
        List<Post> postList = userService.getPostList(userService.getUserInfo(uid));
        Collections.reverse(postList);
        return postList;
    }

}
