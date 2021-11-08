package com.javaguides.training.blog.app.service.impl;

import com.javaguides.training.blog.app.entity.Comment;
import com.javaguides.training.blog.app.entity.Post;
import com.javaguides.training.blog.app.exception.BlogApiException;
import com.javaguides.training.blog.app.exception.ResourceNotFoundException;
import com.javaguides.training.blog.app.payload.CommentDto;
import com.javaguides.training.blog.app.repository.CommentRepository;
import com.javaguides.training.blog.app.repository.PostRepository;
import com.javaguides.training.blog.app.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        CommentDto response = mapToDTO(comment);
        return response;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto findById(Long postId, Long id) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id.toString()));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException("Comment does not belong to Post", HttpStatus.BAD_REQUEST);
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, CommentDto commentDto, Long id) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id.toString()));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException("Comment does not belong to this Post", HttpStatus.BAD_REQUEST);
        }
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        comment = commentRepository.save(comment);

        return mapToDTO(comment);
    }

    @Override
    public void deleteCommentById(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId.toString()));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id.toString()));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException("Comment does not belong to this Post", HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
