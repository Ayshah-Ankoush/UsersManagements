package com.codeWithAyshah.usersmanagment.controller.UserDTO;

import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import lombok.Data;

import java.util.List;

@Data
public class UpdateUserRequest {

    private String fullName;
    private List<UserAddresses> addresses;
}
