package com.user.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Post")
public class Post {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="title")
	private String title;

	@Column(name="content")
	private String Content;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date postedAt;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User author;

	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Comment> comments;

	public Post() {

	}

	public Post(Long id, String title, String content, Date postedAt, User author, List<Comment> comments) {

		this.id = id;
		this.title = title;
		Content = content;
		this.postedAt = postedAt;
		this.author = author;
		this.comments = comments;
	}
	
	  @PrePersist
	    public void prePersist() {
		  ZoneId zoneId = ZoneId.systemDefault();
	        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
	        
	        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
	        this.postedAt = Date.from(zonedDateTime.toInstant());

	  }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
