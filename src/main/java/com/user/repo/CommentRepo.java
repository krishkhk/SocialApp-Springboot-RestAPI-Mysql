package com.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Comment;
import com.user.entity.Post;

public interface CommentRepo extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

}
