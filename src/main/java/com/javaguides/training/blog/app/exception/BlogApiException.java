package com.javaguides.training.blog.app.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public BlogApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BlogApiException(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
