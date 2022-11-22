package com.app.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDto {
	
	private long id;
	//name should not be not null or empty
	@NotEmpty(message="Name should not be null or empty")
	private String name;
	
	//email should not be not null or empty
	// email field validation
	@NotEmpty(message="Email should not be null or empty")
	@Email
	private String email;
	

	//comment body should not be not null or empty
	// comment body must be minimum 10 characters
	@NotEmpty
	@Size(min=10, message="Comment body should contain at least 10 characters")
	private String body;
	
	public CommentDto() {
		
	}

	public CommentDto(long id, String name, String email, String body) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	

//	@Override
//	public String toString() {
//		return "CommentDto [id=" + id + ", name=" + name + ", email=" + email + ", body=" + body";
//	}
	
	
	
}
