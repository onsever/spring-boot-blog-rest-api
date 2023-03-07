package com.onurcansever.blog.controller;

import com.onurcansever.blog.payload.PostDto;
import com.onurcansever.blog.payload.PostResponse;
import com.onurcansever.blog.service.PostService;
import com.onurcansever.blog.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a new blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(this.postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all blog posts
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    // Get a single blog post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostById(id));
    }

    // Update a blog post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePost(postDto, id));
    }

    // Delete a blog post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        this.postService.deletePostById(id);

        return new ResponseEntity<>(String.format("Post with the id of %d deleted successfully!", id), HttpStatus.OK);
    }
}
