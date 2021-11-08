package com.javaguides.training.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorDetails {

    private Date timestamp;
    private String errorMessage;
    private String details;

}
