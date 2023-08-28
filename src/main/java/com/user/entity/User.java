package com.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="userName")
	private String userName;

	@Column(name="Email")
	private String Email;

	@Column(name="contactNumber")
	private String contactNumber;

	@Column(name="dateOfBirth")
	private String dateOfBirth;

	@Column(name="address")
	private String address;
	
	public User() {
		
	}

	public User(Long id, String userName, String email, String contactNumber, String dateOfBirth, String address) {
		super();
		this.id = id;
		this.userName = userName;
		Email = email;
		this.contactNumber = contactNumber;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
	
	
	
	
	

}
