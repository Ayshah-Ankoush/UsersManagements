package com.codeWithAyshah.usersmanagment.controller.UserDTO;

import lombok.Data;

@Data
public class UserFilterRequest{
        private String fullName;
        private String phoneNumber;
        private String city;

}
