package com.winterproject.hrhj_backend.service;

import com.winterproject.hrhj_backend.domain.entity.Post;
import com.winterproject.hrhj_backend.domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getPostInfo(int pid) {
        return postRepository.findById(pid).orElse(new Post());
    }

    public List<Post> searchPost(int uid, String text) {
        return postRepository.findAllByUidAndTextContaining(uid,text);
    }

}
