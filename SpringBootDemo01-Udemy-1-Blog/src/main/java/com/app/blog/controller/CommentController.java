package com.app.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.payload.CommentDto;
import com.app.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired 
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId")long postId,
													@RequestBody CommentDto commentDto){
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
		
		
		return commentService.getCommentsByPostId(postId);
	}
	
	@GetMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postid" )long postId,
														@PathVariable(value = "id") long commentId) {
							
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto,HttpStatus.OK);
		
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") long postId,@PathVariable(value="id") long id,@RequestBody CommentDto commentDto){
		
		CommentDto updatedCommentDto = commentService.updateComment(postId, id, commentDto);
		return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);	
	}
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable long postId,@PathVariable long id){
		commentService.deleteComment(postId, id);
		return new ResponseEntity<>("Comment deleted succesfully",HttpStatus.OK);
	}
	
}
