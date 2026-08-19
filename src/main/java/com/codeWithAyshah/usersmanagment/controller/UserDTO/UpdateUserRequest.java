package com.codeWithAyshah.usersmanagment.controller.UserDTO;

import com.codeWithAyshah.usersmanagment.controller.addressDTO.UpdateUserAddressRequest;
import com.codeWithAyshah.usersmanagment.model.UserAddress;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateUserRequest {


    //@NotBlank(message = "Full name is required")
    private String full_Name;
    @NotNull(message = "Addresses are required")
    @Valid
    private List<UpdateUserAddressRequest> addresses;
}
