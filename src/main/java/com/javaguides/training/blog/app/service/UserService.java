package com.javaguides.training.blog.app.service;

import com.javaguides.training.blog.app.payload.UserDto;

public interface UserService {
    UserDto signUp(UserDto userDto);
}
