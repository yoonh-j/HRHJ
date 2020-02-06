package com.winterproject.hrhj_backend.controller;

import com.winterproject.hrhj_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService) { this.postService = postService;}

}
