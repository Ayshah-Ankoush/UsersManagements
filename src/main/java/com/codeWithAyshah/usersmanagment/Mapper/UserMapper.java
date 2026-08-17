package com.codeWithAyshah.usersmanagment.Mapper;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.CreatUserRequest;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserAddress;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.UpdateUserRequest;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.UserResponse;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    //mapping from updateUserRequest -> User
    public User updateUserRequestToUser(UpdateUserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        return user;
    }

    //mapping from CreatUserReques to user
    public User creatUserRequestToUser (CreatUserRequest creatUserRequest) {
        User user = new User();

        user.setFullName(creatUserRequest.getFullName());
        user.setPhoneNumber(creatUserRequest.getPhoneNumber());


        if (creatUserRequest.getAddresses() != null) {

            creatUserRequest.getAddresses().forEach(addressRequest -> {

                        UserAddresses address = new UserAddresses();

                        address.setCity(addressRequest.getCity());

                        address.setStreet(addressRequest.getStreet());

                        address.setBuildingNumber(addressRequest.getBuildingNumber());

                        user.addAddress(address);
                    });
        }


        return user;
    }
    //mapping from UserAddress to responseUserAddress

    private ResponseUserAddress userAddressToAddressResponse(UserAddresses address) {
        ResponseUserAddress responseUserAddress = new ResponseUserAddress();
        responseUserAddress.setId(address.getId());
        responseUserAddress.setCity(address.getCity());
        responseUserAddress.setStreet(address.getStreet());
        responseUserAddress.setBuildingNumber(address.getBuildingNumber());

        return responseUserAddress;
    }

    //mapping from user to userResponse
    public UserResponse userToUserResponse (User user) {
        List<UserAddresses> addresses = user.getAddresses()
                .stream()
                .toList();
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFullName());
        userResponse.setAddresses(addresses);

        return userResponse;
    }



}
