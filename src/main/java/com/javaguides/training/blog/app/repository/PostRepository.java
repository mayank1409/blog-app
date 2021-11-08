package com.javaguides.training.blog.app.repository;

import com.javaguides.training.blog.app.entity.Comment;
import com.javaguides.training.blog.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByTitle(String title);


}
