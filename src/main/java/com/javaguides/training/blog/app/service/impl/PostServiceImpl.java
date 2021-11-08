package com.javaguides.training.blog.app.service.impl;

import com.javaguides.training.blog.app.entity.Post;
import com.javaguides.training.blog.app.exception.ResourceAlreadyExistsException;
import com.javaguides.training.blog.app.exception.ResourceNotFoundException;
import com.javaguides.training.blog.app.payload.PostDto;
import com.javaguides.training.blog.app.payload.PostResponse;
import com.javaguides.training.blog.app.repository.PostRepository;
import com.javaguides.training.blog.app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = postRepository.findByTitle(postDto.getTitle());
        if (post != null) {
            throw new ResourceAlreadyExistsException("Post", "title", postDto.getTitle());
        }
        post = mapToEntity(postDto);
        post = postRepository.save(post);
        return mapToDTO(post);
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    @Override
    public PostResponse list(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDto> postDtos = posts.getContent().stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = PostResponse.builder().content(postDtos).page(pageNumber).
                size(pageSize).totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements()).last(posts.isLast()).build();
        return postResponse;
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public PostDto findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new ResourceNotFoundException("Post", "id", id.toString());
        }
        Post post = optionalPost.get();
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new ResourceNotFoundException("Post", "id", id.toString());
        }
        Post post = optionalPost.get();
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post = postRepository.save(post);
        return mapToDTO(post);
    }

    @Override
    public void deletePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new ResourceNotFoundException("Post", "id", id.toString());
        }
        postRepository.delete(optionalPost.get());
    }
}
