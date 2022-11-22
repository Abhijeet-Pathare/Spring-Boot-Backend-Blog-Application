package com.app.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.model.Post;
import com.app.blog.payload.PostDto;
import com.app.blog.payload.PostResponse;
import com.app.blog.repository.PostRepository;
import com.app.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	//@Autowired //if class has only one constructor then we can omit the autowired annotation
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		//convert DTO to enitity
		Post post = mapToEntity(postDto);	
		Post newPost = postRepository.save(post);
		//convert entity to dto
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {
		
		//create a pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		// get content from page object
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		PostDto postDto = mapToDto(post);
		return postDto;
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// get post by id from the db and if the post is not found throw exception
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post update = postRepository.save(post);
		
		return mapToDto(update);
	}
	
	@Override
	public String deletePost( long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		return "Post Deleted Successfully";
	}
	
	
	
	
	//common logic
	// we created private method to convert entity to dto
	private PostDto mapToDto(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
		
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		return postDto;
	}
	
	//common logic
	// we created private method to convert dto to entity
	private Post mapToEntity(PostDto postDto) {

		Post post = mapper.map(postDto, Post.class);
		
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}

	

	

	

	
	
}
