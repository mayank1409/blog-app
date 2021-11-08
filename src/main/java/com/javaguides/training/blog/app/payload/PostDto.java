package com.javaguides.training.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotBlank(message = "title must not be blank")
    private String title;
    private String description;
    @NotBlank(message = "content must not be blank")
    private String content;
    private Set<CommentDto> comments;
}
