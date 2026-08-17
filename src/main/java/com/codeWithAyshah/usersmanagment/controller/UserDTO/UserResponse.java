package com.codeWithAyshah.usersmanagment.controller.UserDTO;

import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import lombok.Data;

import java.util.List;
@Data
public class UserResponse{
        private Integer id;
        private String fullName;
        private List <UserAddresses> addresses;
}
