package com.user.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.Comment;
import com.user.entity.Post;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repo.CommentRepo;
import com.user.repo.PostRepo;
import com.user.repo.UserRpo;

@RestController
@RequestMapping("/post")
public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);


	@Autowired
	PostRepo postRepo;

	@Autowired
	UserRpo userRepo;
	
	@Autowired
	CommentRepo commentRepo;


	@GetMapping("/list")
	public List<Post> getAllpost(){
		logger.info("Received a GET request for Fetching All Posts:");
		List<Post> posts=postRepo.findAll();
		for(Post post:posts) {
			List<Comment> comments=commentRepo.findAll();
			post.setComments(comments);
		}
		return posts;
	}

	@PostMapping("createpost")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		logger.info("Received a POST request for creating a Post.");

		try {
			User author = userRepo.findById(post.getAuthor().getId()).orElse(null);
			if (author != null) {
				post.setAuthor(author);
				Post savedPost = postRepo.save(post);
				return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (ResourceNotFoundException ex) {
			logger.error("Error while creating Post: {}", ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Error while creating post: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<Post> getPostId(@PathVariable("id") Long id) {

		logger.info("Received a GET request for Fetching  " +id+ "  Details");
		Optional<Post> postId = postRepo.findById(id);
		if (postId.isPresent()) {
			logger.info("successfully Fetched with User ID: {}"  +postId.get().getId());
			return ResponseEntity.ok(postId.get());
		} else {
			logger.error("Error while fetching Post Id not Found ");
			throw new ResourceNotFoundException("Id not Found");
		}

	}
	
	
	@PutMapping("/updatedPost/{id}")
	public ResponseEntity<Post> updatePostDetails(@PathVariable("id") Long id, @RequestBody Post post) {

		logger.info("Received a PUT request for Updating the Post Details.");
		Optional<Post> updateDetails = postRepo.findById(id);
		if (updateDetails.isPresent()) {
			Post existDetails = updateDetails.get();
			existDetails.setTitle(post.getTitle());
			existDetails.setContent(post.getContent());
			//existDetails.setPostedAt(post.getPostedAt());

			if (post.getAuthor() != null) {
				User updateAuthor = userRepo.findById(post.getAuthor().getId()).orElseThrow(
						() -> new ResourceNotFoundException("User not found with ID:" + post.getAuthor().getId()));
				existDetails.setAuthor(updateAuthor);
			}

			
			Post savedPost = postRepo.save(existDetails);
			
			logger.info("Successfully Updated the Post Details Given Id ." +savedPost.getId());


			return ResponseEntity.ok(savedPost);

		} else {
			throw new ResourceNotFoundException("Post not found with ID: " + post.getId());
		}

	}
	
	//using PATCH method only required fields should be Updated //field that to be updated
	
	@PatchMapping("/updatedRequired/{id}")
	public ResponseEntity<Post> partialUpdatePostDetails(@PathVariable("id") Long id, @RequestBody Map<String, Object> postUpdates) {

		logger.info("Received a PATCH request for Changing Partial Post Details.");

	    Optional<Post> updateDetails = postRepo.findById(id);
	    if (updateDetails.isPresent()) {
	        Post existDetails = updateDetails.get();

	        if (postUpdates.containsKey("title")) {
	            existDetails.setTitle((String) postUpdates.get("title"));
	        }

	        if (postUpdates.containsKey("content")) {
	            existDetails.setContent((String) postUpdates.get("content"));
	        }

	        if (postUpdates.containsKey("postedAt")) {
	            existDetails.setPostedAt((java.util.Date) postUpdates.get("postedAt"));
	        }

	        if (postUpdates.containsKey("author")) {
	            Map<String, Object> authorMap = (Map<String, Object>) postUpdates.get("author");
	            Long authorId = (Long) authorMap.get("id");
	            User updateAuthor = userRepo.findById(authorId)
	                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + authorId));
	            existDetails.setAuthor(updateAuthor);
	        }
	        
	        logger.info("Successfully Updated The Post Details." +existDetails.getId());


	        Post savedPost = postRepo.save(existDetails);
	        return ResponseEntity.ok(savedPost);
	    } else {
	    	logger.error("Error while Updating post Details Id not found: {}");
	        throw new ResourceNotFoundException("Post not found with ID: " + id);
	    }
	}

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteId(@PathVariable("id") Long id){
		logger.info("Received a DELETE request for Deleting the Posts.");
		Optional<Post> deleteId=postRepo.findById(id);
		if(deleteId.isPresent()) {
			postRepo.deleteById(id);
			return ResponseEntity.ok("Deleted Successfully !");
		}else {
			logger.info("Deleting the Post id not found.");
			throw new ResourceNotFoundException("Id not Found !");
		}
		
	}
	
	
	
	
	
	

}
