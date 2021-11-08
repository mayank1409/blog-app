package com.javaguides.training.blog.app.service;

import com.javaguides.training.blog.app.entity.Comment;
import com.javaguides.training.blog.app.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto findById(Long postId, Long id);

    CommentDto updateCommentById(Long postId, CommentDto commentDto, Long id);

    void deleteCommentById(Long postId, Long id);

}
