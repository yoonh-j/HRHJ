package com.winterproject.hrhj_backend.controller;

import com.winterproject.hrhj_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) { this.userService = userService;}


}
