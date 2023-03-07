package com.onurcansever.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    // Title should not be null or empty and have at least 12 characters.
    @NotEmpty(message = "Title should not be empty.")
    @Size(min = 12, message = "Title should have at least 12 characters.")
    private String title;

    // Description should not be null or empty and have at least 20 characters.
    @NotEmpty(message = "Description should not be empty.")
    @Size(min = 20, message = "Description should have at least 20 characters.")
    private String description;

    // Content should not be null or empty and have at least 30 characters.
    @NotEmpty(message = "Content should not be empty.")
    @Size(min = 30, message = "Content should have at least 30 characters.")
    private String content;
    private Set<CommentDto> comments;
}
