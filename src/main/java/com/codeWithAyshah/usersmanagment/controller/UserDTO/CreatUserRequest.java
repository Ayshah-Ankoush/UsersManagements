package com.codeWithAyshah.usersmanagment.controller.UserDTO;


import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import lombok.Data;

import java.util.List;

@Data
public class CreatUserRequest{

        private String fullName;
        private String phoneNumber;
        private List<UserAddresses> addresses;

}
