package com.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

}
