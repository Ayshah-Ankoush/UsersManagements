package com.codeWithAyshah.usersmanagment.Mapper;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class ResMapper implements Function<User, ResponseUserDto> {

    @Override
    public ResponseUserDto apply(User user) {
        return new ResponseUserDto(
                user.getId(), user.getFullName(), user.getAddress(), user.getPhoneNumber());
    }

    public User toEntity(ResponseUserDto userDto) {
        User user = new User();
        user.setId(userDto.id());
        user.setFullName(userDto.fullName());
        user.setAddress(userDto.address());
        user.setPhoneNumber(userDto.phoneNumber());
        return user;
    }
}
