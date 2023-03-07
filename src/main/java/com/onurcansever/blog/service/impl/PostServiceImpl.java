package com.onurcansever.blog.service.impl;

import com.onurcansever.blog.entity.Post;
import com.onurcansever.blog.exception.ResourceNotFoundException;
import com.onurcansever.blog.payload.PostDto;
import com.onurcansever.blog.payload.PostResponse;
import com.onurcansever.blog.repository.PostRepository;
import com.onurcansever.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Convert DTO to Entity
        Post post = this.mapToEntity(postDto);

        // Save the post to the database.
        Post newPost = this.postRepository.save(post);

        // To return PostDto back to the controller, we need to convert Entity to DTO.
        return this.mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // Creating a pagination and sorting.
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = this.postRepository.findAll(pageable);

        // Get content from the page object.
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(this::mapToDTO).collect(Collectors.toList());

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
    public PostDto getPostById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return this.mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // Get Post entity by id from the database.
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Update the post-entity with the data from the postDto.
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // Save the updated post-entity to the database.
        Post updatedPost = this.postRepository.save(post);

        // Convert Entity to DTO and return it.
        return this.mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        this.postRepository.delete(post);
    }

    // Convert Entity to DTO
    private PostDto mapToDTO(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }

    // Convert DTO to Entity
    private Post mapToEntity(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }
}
