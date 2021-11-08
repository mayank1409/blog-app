package com.javaguides.training.blog.app.service;

import com.javaguides.training.blog.app.payload.PostDto;
import com.javaguides.training.blog.app.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse list(int pageNumber, int pageSize, String sortBy, String sortDir);
    PostDto findById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
}

