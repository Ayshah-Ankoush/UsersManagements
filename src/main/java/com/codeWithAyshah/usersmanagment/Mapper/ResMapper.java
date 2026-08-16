package com.codeWithAyshah.usersmanagment.Mapper;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseAddressDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class ResMapper implements Function<User, ResponseUserDto> {

    @Override
    public ResponseUserDto apply(User user) {
        List<ResponseAddressDto> addresses = user.getAddresses()
                .stream()
                .map(address -> new ResponseAddressDto(
                        address.getId(),
                        address.getCity(),
                        address.getStreet(),
                        address.getBuildingNumber()
                ))
                .toList();
        return new ResponseUserDto(
                user.getId(), user.getFullName(),user.getAddresses());
    }

    public User toEntity(ResponseUserDto userDto) {
        User user = new User();
        user.setId(userDto.id());
        user.setFullName(userDto.fullName());

       // user.setPhoneNumber(userDto.phoneNumber());
        return user;
    }
}
