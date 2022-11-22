package com.app.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.payload.PostDto;
import com.app.blog.payload.PostResponse;
import com.app.blog.service.PostService;
import com.app.blog.utils.AppConstants;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//@Autowired  //can be omited because of only one constructor
	public PostController(PostService postService) {
		this.postService = postService;
	}

	//update a post by id rest api
	@PutMapping("posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){
		PostDto postResponse = postService.updatePost(postDto, id);
		
		return new ResponseEntity<PostDto>(postResponse,HttpStatus.OK);
	}
 	
	
	//get all posts
	@GetMapping("posts")
	public ResponseEntity<PostResponse> getPosts(
			@RequestParam(value= "pageNo", defaultValue= AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
			@RequestParam(value= "pageSize", defaultValue= AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_PAGE_SORT_BY,required=false) String sortBy
			){
		System.out.println(postService.getAllPosts(pageNo, pageSize, sortBy));
		return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy));
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	//create blog post 
	@PostMapping(path ="posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id){
		return new ResponseEntity<String>(postService.deletePost(id),HttpStatus.OK);
	}
	
}
