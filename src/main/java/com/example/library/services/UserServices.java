package com.example.library.services;

import com.example.library.model.User;
import com.example.library.model.dto.UserDto;

public interface UserServices {

    UserDto createUser(UserDto user) throws Exception;
    UserDto getUser(Long id);

    Boolean deleteUser(Long id);
}
