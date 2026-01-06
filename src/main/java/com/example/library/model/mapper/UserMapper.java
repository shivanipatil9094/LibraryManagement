package com.example.library.model.mapper;

import com.example.library.model.User;
import com.example.library.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "user")
public interface UserMapper {

    UserDto userResponse(User user);
    User userRequest(UserDto userDto);


}
