package com.winterproject.hrhj_backend.domain.repository;

import com.winterproject.hrhj_backend.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
