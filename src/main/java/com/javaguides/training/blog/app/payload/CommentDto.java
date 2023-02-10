package com.javaguides.training.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String name;
    @NotBlank(message = "email must not be blank")
    @Email
    private String email;
    @NotBlank(message = "comment body must not be blank")
    private String body;
}
