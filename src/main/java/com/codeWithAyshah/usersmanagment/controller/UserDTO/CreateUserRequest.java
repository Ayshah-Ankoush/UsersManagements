package com.codeWithAyshah.usersmanagment.controller.UserDTO;


import com.codeWithAyshah.usersmanagment.controller.addressDTO.CreateUserAddressRequest;
import com.codeWithAyshah.usersmanagment.model.UserAddress;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {

        private String full_Name;
        private String phone_Number;
        private List<CreateUserAddressRequest> addresses;

}
