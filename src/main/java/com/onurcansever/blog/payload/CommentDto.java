package com.onurcansever.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty.")
    @Min(value = 3, message = "Name should have at least 3 characters.")
    private String name;

    @NotEmpty(message = "Email should not be empty.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotEmpty(message = "Body should not be empty.")
    @Min(value = 10, message = "Body should have at least 10 characters.")
    private String body;
}
