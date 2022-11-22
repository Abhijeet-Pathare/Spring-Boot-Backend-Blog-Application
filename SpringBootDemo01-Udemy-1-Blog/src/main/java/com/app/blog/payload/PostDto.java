package com.app.blog.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDto {

	private long id;

	// title should not be null or empty
	// title should have at least 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least two characters")
	private String title;

	// post should not be null or empty
	// post should have at least 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;
	
	// post content should not be null or empty
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;

	public PostDto() {

	}

	public PostDto(long id, String title, String description, String content) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

//	@Override
//	public String toString() {
//		return "PostDto [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
//				+ ", comments=" + comments + "]";
//	}

}
