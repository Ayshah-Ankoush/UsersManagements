package com.codeWithAyshah.usersmanagment.controller.addressDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserAddressRequest {
    @NotBlank(message = "City is required")
    @Pattern(
            regexp = "Ramallah|Nablus|Jenin|Hebron|Jerusalem|Bethlehem",
            message = "City is not supported"
    )
    private String city;
    private String street;
    private String building_Number;

}
