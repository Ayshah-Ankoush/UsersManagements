package com.codeWithAyshah.usersmanagment.Mapper;

import com.codeWithAyshah.usersmanagment.controller.addressDTO.CreateUserAddressRequest;
import com.codeWithAyshah.usersmanagment.controller.addressDTO.UpdateUserAddressRequest;
import com.codeWithAyshah.usersmanagment.model.UserAddress;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    //mapping from UserAddress to CreateUserAddressRequest
    public CreateUserAddressRequest toCreateUserAddressRequest(UserAddress address) {
        CreateUserAddressRequest request = new CreateUserAddressRequest();
       request.setCity(address.getCity());
       request.setStreet(address.getStreet());
       request.setBuilding_Number(address.getBuildingNumber());
       return request;
    }

    //mapping from creatUserAddressRequest to userAddress

    public UserAddress creatUserAddressRequest(CreateUserAddressRequest createUserAddressRequest) {
        UserAddress address = new UserAddress();
        address.setCity(createUserAddressRequest.getCity());
        address.setStreet(createUserAddressRequest.getStreet());
        address.setBuildingNumber(createUserAddressRequest.getBuilding_Number());
        return address;
    }

    //mapping from updateUserAddressRequest to UserAddress

    public UserAddress updateRequestToUserAddress(
            UpdateUserAddressRequest request
    ) {
        UserAddress address = new UserAddress();

        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setBuildingNumber(request.getBuilding_Number());

        return address;
    }



}
