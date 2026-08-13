package com.codeWithAyshah.usersmanagment.Mapper;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.models.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReqMapper implements Function<User, RequestUserDto> {
    @Override
    public RequestUserDto apply(User user) {
        return new RequestUserDto(
                user.getFullName(), user.getAddress(), user.getPhoneNumber());
    }

    public User toEntity(RequestUserDto userDto) {
        User user = new User();
        //user.setId(userDto.Id());
        user.setFullName(userDto.fullName());
        user.setAddress(userDto.address());
       // user.setPhoneNumber(userDto.phoneNumber());
        return user;
    }
}
