package com.codeWithAyshah.usersmanagment.controller.UserDTO;

import com.codeWithAyshah.usersmanagment.model.UserAddress;
import lombok.Data;

import java.util.List;
@Data
public class UserDTO {
        private Integer id;
        private String full_Name;
        private List <UserAddress> addresses;
}
