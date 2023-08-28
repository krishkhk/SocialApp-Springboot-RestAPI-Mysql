package com.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.user.entity.Comment;
import com.user.entity.Post;

public interface CommentRepo extends JpaRepository<Comment, Long> {

	public List<Comment> findByPost(Post post);

}
