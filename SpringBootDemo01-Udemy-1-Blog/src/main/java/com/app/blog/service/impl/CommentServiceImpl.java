package com.app.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.app.blog.exceptions.BlogAPIException;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.model.Comments;
import com.app.blog.model.Post;
import com.app.blog.payload.CommentDto;
import com.app.blog.repository.CommentRepository;
import com.app.blog.repository.PostRepository;
import com.app.blog.service.CommentService;
import com.sun.tools.javac.parser.Tokens.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository, ModelMapper mapper ) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comments comment = mapToEntity(commentDto);
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		//set post to comment entity
		comment.setPost(post);
		
		// comment entity to DB
		Comments newComment = commentRepository.save(comment);
		
		
		return mapToDto(newComment);
	}

	
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		//retrieve comments by post id
		List<Comments> comments = commentRepository.findByPostId(postId);
		
		//convert List of comment entities to list of dto's
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		//retrieve post by post id
		Post post = postRepository.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post", "id", postId));
		
		//retrieve comment by id
		Comments comment = commentRepository.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!(comment.getPost().getId() == post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		return mapToDto(comment);
	}
	
	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		Comments comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		
		
		
		if(!(comment.getPost().getId() == post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}else {
			comment.setName(commentDto.getName());
			comment.setEmail(commentDto.getEmail());
			comment.setBody(commentDto.getBody());
			
			Comments updatedComment = commentRepository.save(comment);
			
			return mapToDto(updatedComment);
		}
			
	}
	
	
	@Override
	public void deleteComment(long postId, long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		
		Comments comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!(comment.getPost().getId() == post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment not present for the post.");
		}
		commentRepository.deleteById(commentId);
		
	}
	
	private CommentDto mapToDto (Comments comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setName(comment.getName());
//		commentDto.setBody(comment.getBody());
		
		return commentDto;
	}
	
	private Comments mapToEntity (CommentDto commentDto) {
		
		Comments comment = mapper.map(commentDto, Comments.class);
		
//		Comments comment = new Comments();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
	
		return comment;
	}

	

	
}
