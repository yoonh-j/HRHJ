package com.winterproject.hrhj_backend.domain.repository;

import com.winterproject.hrhj_backend.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByUidAndTextContaining(@Param("uid") int uid, @Param("text") String text);
}
