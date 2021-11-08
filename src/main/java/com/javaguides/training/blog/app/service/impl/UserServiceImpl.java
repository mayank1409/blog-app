package com.javaguides.training.blog.app.service.impl;

import com.javaguides.training.blog.app.entity.User;
import com.javaguides.training.blog.app.exception.ResourceAlreadyExistsException;
import com.javaguides.training.blog.app.payload.UserDto;
import com.javaguides.training.blog.app.repository.UserRepository;
import com.javaguides.training.blog.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto signUp(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user != null) {
            throw new ResourceAlreadyExistsException("User", "username", userDto.getUsername());
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        user = modelMapper.map(userDto, User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }
}
