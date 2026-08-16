package com.codeWithAyshah.usersmanagment.controller.UserDTO;

import com.codeWithAyshah.usersmanagment.models.UserAddresses;

import java.util.List;

public record ResponseUserDto(
        int id,
        String fullName,
        List<UserAddresses> addresses) {
}
