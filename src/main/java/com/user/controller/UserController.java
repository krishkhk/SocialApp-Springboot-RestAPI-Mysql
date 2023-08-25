package com.user.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repo.UserRpo;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRpo userRepo;

	@GetMapping("list")
	public List<User> getUsers() {
		return userRepo.findAll();

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserId(@PathVariable("id") Long id) {

		Optional<User> userId = userRepo.findById(id);
		if (userId.isPresent()) {
			return ResponseEntity.ok(userId.get());
		} else {
			throw new ResourceNotFoundException("User Id Not Found");
		}
	}

	@PostMapping("/create")
	public User createUser(@RequestBody User user) {
		return userRepo.save(user);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {

		try {
			User existingUser = userRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

			existingUser.setUserName(user.getUserName());
			existingUser.setEmail(user.getEmail());
			existingUser.setContactNumber(user.getContactNumber());
			existingUser.setDateOfBirth(user.getDateOfBirth());
			existingUser.setAddress(user.getAddress());

			User savedUser = userRepo.save(existingUser);
			return ResponseEntity.ok(savedUser);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteId(@PathVariable("id") Long id){
		Optional<User> deleteId=userRepo.findById(id);
		if(deleteId.isPresent()) {
			userRepo.deleteById(id);
			return ResponseEntity.ok("Deleted Successfully !");
		}else {
			throw new ResourceNotFoundException("Id not Found !");
		}
		
	}
	
	//using PATCH method only required fields should be Updated //field that to be updated
	
	@PatchMapping("/updateRequired/{id}")
	public ResponseEntity<User> partialUpdateUser(@PathVariable("id") Long id,@RequestBody Map<String,Object> user){
		
		System.out.println("  user +=================="  +user.toString());
		
		try {
			User existingUser = userRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

			if(user.containsKey("userName")) {
				existingUser.setUserName((String) user.get("userName"));
			}
			
			if(user.containsKey("email")) {
				existingUser.setEmail((String) user.get("email"));
			}
			
			if(user.containsKey("contactNumber")) {
				existingUser.setContactNumber((String) user.get("contactNumber"));
			}
			
			if (user.containsKey("dateOfBirth")) {
			    existingUser.setDateOfBirth((String) user.get("dateOfBirth"));
			}
			if (user.containsKey("address")) {
			    existingUser.setAddress((String) user.get("address"));
			}
			
			User savedUser=userRepo.save(existingUser);
			return ResponseEntity.ok(savedUser);
		} catch (ResourceNotFoundException e) {
		    return ResponseEntity.notFound().build();
		}
	}

}
