package com.javaguides.training.blog.app.controller;

import com.javaguides.training.blog.app.payload.CommentDto;
import com.javaguides.training.blog.app.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/api/v1")
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId, @PathVariable("id") Long id) {
        return new ResponseEntity<>(commentService.findById(postId, id), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Boolean> deleteCommentById(@PathVariable("postId") Long postId, @PathVariable("id") Long id) {
        commentService.deleteCommentById(postId, id);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
