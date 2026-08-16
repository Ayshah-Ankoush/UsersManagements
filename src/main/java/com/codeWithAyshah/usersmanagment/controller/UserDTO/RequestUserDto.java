package com.codeWithAyshah.usersmanagment.controller.UserDTO;


import com.codeWithAyshah.usersmanagment.models.UserAddresses;

import java.util.List;

public record RequestUserDto(

        String fullName,
        String phoneNumber
) {
}
