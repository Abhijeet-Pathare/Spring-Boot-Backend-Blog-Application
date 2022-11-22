package com.app.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.blog.model.Comments;

@Repository //no need to add @Repository annotation as it extends JpaRepository which has parent interface as Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
	
	List<Comments> findByPostId(long postId);
}
