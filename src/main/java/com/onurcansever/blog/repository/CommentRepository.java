package com.onurcansever.blog.repository;

import com.onurcansever.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Retrieve the list of comments by post id
    List<Comment> findByPostId(Long postId);
}
