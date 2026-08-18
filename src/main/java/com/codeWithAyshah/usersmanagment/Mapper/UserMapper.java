package com.codeWithAyshah.usersmanagment.Mapper;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.CreateUserRequest;
import com.codeWithAyshah.usersmanagment.controller.addressDTO.CreateUserAddressRequest;
import com.codeWithAyshah.usersmanagment.controller.addressDTO.UpdateUserAddressRequest;
import com.codeWithAyshah.usersmanagment.controller.addressDTO.UserAddressDTO;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.UpdateUserRequest;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.UserDTO;
import com.codeWithAyshah.usersmanagment.model.User;
import com.codeWithAyshah.usersmanagment.model.UserAddress;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final AddressMapper addressMapper;
    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    //mapping from updateUserRequest -> User
    public User updateUserRequestToUser(
            UpdateUserRequest request
    ) {
        User user = new User();

        user.setFullName(request.getFull_Name());


        if (request.getAddresses() == null) {
            // Addresses were not included in PATCH
            user.setAddresses(null);
        } else {
            for (UpdateUserAddressRequest addressRequest
                    : request.getAddresses()) {

                UserAddress address =
                        addressMapper
                                .updateRequestToUserAddress(
                                        addressRequest
                                );

                user.addAddress(address);
            }
        }

        return user;
    }

    //mapping from CreateUserReques to user
    public User createUserRequestToUser(CreateUserRequest request) {
        User user = new User();

        user.setFullName(request.getFull_Name());
        user.setPhoneNumber(request.getPhone_Number());

        if (request.getAddresses() != null) {
            for (CreateUserAddressRequest addressRequest : request.getAddresses()) {

                UserAddress address = addressMapper.creatUserAddressRequest(addressRequest);

                        user.addAddress(address);
                    }
        }


        return user;
    }
    //mapping from UserAddress to responseUserAddress

    private UserAddressDTO userAddressToAddressResponse(UserAddress address) {
        UserAddressDTO responseUserAddress = new UserAddressDTO();
        responseUserAddress.setId(address.getId());
        responseUserAddress.setCity(address.getCity());
        responseUserAddress.setStreet(address.getStreet());
        responseUserAddress.setBuilding_Number(address.getBuildingNumber());

        return responseUserAddress;
    }

    //mapping from user to userResponse
    public UserDTO userToUserResponse (User user) {
        List<UserAddress> addresses = user.getAddresses()
                .stream()
                //.map(addressMapper::toUserAddress)
                .toList();
        UserDTO userResponse = new UserDTO();

        userResponse.setId(user.getId());
        userResponse.setFull_Name(user.getFullName());
        userResponse.setAddresses(addresses);

        return userResponse;
    }



}
