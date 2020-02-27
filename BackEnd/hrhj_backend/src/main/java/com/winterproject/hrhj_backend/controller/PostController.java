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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @PostMapping(path = "/deletepost")
    public void deletePost(@RequestBody Post post) {
        User user = userService.getUserInfo(post.getUid());
        File image = new File("c:/hrhj_image/"+post.getImage());
        user.deletePost(post);
        userService.saveUserInfo(user);

        if(image.exists())
        {
            image.delete();
        }

    }

    @PostMapping(path = "/updatepost")
    public Post updatePost(@RequestBody Post post) {

        User user = userService.getUserInfo(post.getUid());
        Post originPost = postService.getPostInfo(post.getPid());

        user.updatePost(originPost,post.getText());
        
        userService.saveUserInfo(user);



        return post;
    }

    @PostMapping(path = "/saveimage")
    public void saveImage(@RequestBody MultipartFile image) {

        String imagePath = "c:/hrhj_image/"+image.getOriginalFilename();

        //image파일을 서버에 저장
        try{
            image.transferTo(new File(imagePath));
        }catch (IllegalStateException | IOException e)
        {
            e.printStackTrace();
        }
    }

}
