package com.onurcansever.blog.service.impl;

import com.onurcansever.blog.entity.Comment;
import com.onurcansever.blog.entity.Post;
import com.onurcansever.blog.exception.BlogApiException;
import com.onurcansever.blog.exception.ResourceNotFoundException;
import com.onurcansever.blog.payload.CommentDto;
import com.onurcansever.blog.repository.CommentRepository;
import com.onurcansever.blog.repository.PostRepository;
import com.onurcansever.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = this.mapToEntity(commentDto);

        // Retrieve Post Entity by Post id
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set Post Entity to Comment Entity
        comment.setPost(post);

        // Save Comment Entity to Database
        Comment newComment = this.commentRepository.save(comment);

        return this.mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        // Retrieve all Comments by Post id
        List<Comment> comments = this.commentRepository.findByPostId(postId);

        // Convert List of Comment Entities to CommentDto
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // Retrieve Post Entity by Post id
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment Entity by Comment id
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if Comment Entity belongs to Post Entity
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        // Convert Comment Entity to CommentDto
        return this.mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        // Retrieve Post Entity by Post id
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment Entity by Comment id
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if Comment Entity belongs to Post Entity
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        // Update Comment Entity
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        // Save Comment Entity to Database
        Comment updatedComment = this.commentRepository.save(comment);

        // Convert Comment Entity to CommentDto
        return this.mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // Retrieve Post Entity by Post id
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Retrieve Comment Entity by Comment id
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if Comment Entity belongs to Post Entity
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        // Delete Comment Entity
        this.commentRepository.delete(comment);
    }

    // Convert Comment Entity to CommentDto
    private CommentDto mapToDTO(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }

    // Convert CommentDto to Comment Entity
    private Comment mapToEntity(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }
}
