package com.codeWithAyshah.usersmanagment.controller.addressDTO;

import lombok.Data;

@Data
public class CreateUserAddressRequest {

    private String street;
    private String city;
    private String building_Number;

}
