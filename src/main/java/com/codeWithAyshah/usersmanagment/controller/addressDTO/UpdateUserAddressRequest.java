package com.codeWithAyshah.usersmanagment.controller.addressDTO;

import lombok.Data;

@Data
public class UpdateUserAddressRequest {

    private String city;
    private String street;
    private String building_Number;

}
