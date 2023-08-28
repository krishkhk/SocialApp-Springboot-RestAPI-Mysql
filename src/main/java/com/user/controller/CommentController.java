package com.user.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.user.entity.Comment;
import com.user.entity.Post;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repo.CommentRepo;
import com.user.repo.PostRepo;
import com.user.repo.UserRpo;
import com.user.view.Views;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	UserRpo userRepo;

	@Autowired
	PostRepo postRepo;

	@Autowired
	CommentRepo commentRepo;

	/*
	 * @GetMapping("/list") public List<Comment> getAllComment(){
	 * logger.info("Listed all comments "); return commentRepo.findAll(); }
	 */

	@GetMapping("/list")
	public List<Comment> getAllComment() {
		logger.info("Received a GET request for Fetching All Comments ");
		List<Comment> comments = commentRepo.findAll();

		for (Comment comment : comments) {
			comment.getAuthor(); // Fetch the author for each comment
			comment.getPost(); // Fetch the post for each comment
		}

		return comments;
	}

	// Find comment by Post id
	@GetMapping("/commentsByPost/{id}")
	public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("id") Long id) {
		logger.info("Received a GET request for Fetching Post  " +id+ "  Id comments");
		try {
			Post post = postRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));

			List<Comment> comments = commentRepo.findByPost(post);
			logger.info("successfully Fetched with Post ID Comments: {}"  +post.getId());
			return ResponseEntity.ok(comments);
		} catch (ResourceNotFoundException ex) {
			logger.error("Error while fetching Post Comment Id not Found " +ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/postComment")
	public ResponseEntity<Comment> postComment(@RequestBody Comment comments) {

		logger.info("Received a POST request for creating a New Comment.");
		try {
			User author = userRepo.findById(comments.getAuthor().getId())
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found !"));

			Post post = postRepo.findById(comments.getPost().getId())
					.orElseThrow(() -> new ResourceNotFoundException(" Post Not Found!"));

			Comment comment = new Comment();
			comment.setText(comments.getText());
			comment.setAuthor(author);
			comment.setPost(post);

			// comment.setCommentedAt(new java.util.Date());

			Comment savedComment = commentRepo.save(comment);
			
			logger.info("Received a PUT request for Succesfully created the comment with the Id ." +savedComment.getId());

			return new ResponseEntity<Comment>(savedComment, HttpStatus.CREATED);
		} catch (ResourceNotFoundException ex) {
			logger.error("Error while creating Comment: {}", ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Error while creating Comment Internal Error: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Delete Comments

	// If You want to performe Comments Delete

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteId(@PathVariable("id") Long id) {
		Optional<Comment> deleteId = commentRepo.findById(id);
		if (deleteId.isPresent()) {
			commentRepo.deleteById(id);
			logger.info("Successfully Deleted User");
			return ResponseEntity.ok("Deleted Successfully ! with the Id " +deleteId.get().getId());
		} else {
			logger.info("Deleting the User Id not found.");
			throw new ResourceNotFoundException("Id not Found !");
		}

	}

}
