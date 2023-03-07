package com.onurcansever.blog.controller;

import com.onurcansever.blog.payload.CommentDto;
import com.onurcansever.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(this.commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return new ResponseEntity<>(this.commentService.getAllCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId) {
        return new ResponseEntity<>(this.commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(this.commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId) {
        this.commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>(String.format("Comment with the id of %d deleted successfully!", commentId), HttpStatus.OK);
    }
}
