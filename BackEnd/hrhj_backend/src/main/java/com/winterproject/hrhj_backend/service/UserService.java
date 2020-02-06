package com.winterproject.hrhj_backend.service;

import com.winterproject.hrhj_backend.domain.entity.Post;
import com.winterproject.hrhj_backend.domain.entity.User;
import com.winterproject.hrhj_backend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(User user)
    {
        userRepository.save(user);
    }

    public void saveUserInfo(User user)
    {
        userRepository.save(user);
    }

    public User getUserInfo(int uid) {
        return userRepository.findById(uid).orElse(new User());
    }

    public List<Post> getPostList(User user)
    {
        return user.getPostList();
    }

}
