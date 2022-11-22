package com.app.blog.service;

import java.util.List;

import com.app.blog.payload.PostDto;
import com.app.blog.payload.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

	PostDto getPostById(long id);

	PostDto updatePost(PostDto postDto, long id);
	
	String deletePost(long id);
}
