package com.codeWithAyshah.usersmanagment.controller;

import com.codeWithAyshah.usersmanagment.Mapper.UserMapper;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.*;
import com.codeWithAyshah.usersmanagment.model.User;
import com.codeWithAyshah.usersmanagment.model.UserAddress;
import com.codeWithAyshah.usersmanagment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/count")
    public Long countActiveUsers() {
        Long count = userService.countUsers();
        return count;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void creatUser(@RequestBody CreateUserRequest creatUserRequests) {
        User user = userMapper.createUserRequestToUser(creatUserRequests);
        userService.insertUser(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return userMapper.userToUserResponse(user);

    }

    @PutMapping("/{id}")
    public void update( @RequestBody  UpdateUserRequest updateUserRequest, @PathVariable Integer id) {//updateUserReq
        User user = userMapper.updateUserRequestToUser(updateUserRequest);

        userService.updateUser(id, user,updateUserRequest);
    }

    @PatchMapping("/{id}")
    public void updateUserPartially(
            @PathVariable("id") Integer id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        User updatedUser = userMapper.updateUserRequestToUser(updateUserRequest);
        userService.updateUserPartially(id, updatedUser);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkUserExists(@PathVariable Integer id) {
        if (userService.checkIfExist(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/test-n-plus-one")
    public void testNPlusOne() {
        userService.testNPlusOne();
    }

    @PostMapping("/{userId}/addresses")
    public void createUserAddress(
            @PathVariable Integer userId,
            @RequestBody UserAddress address
    ) {
        userService.addAddress(userId, address);
    }

    @PostMapping("/search")
    public List<UserDTO> search(
            @RequestBody UserFilterRequest filter
    ) {
        return userService.searchUsers(filter)
                .stream()
                .map(userMapper::userToUserResponse)
                .toList();
    }

    @RequestMapping("/pagination/simple")
    public Page<UserDTO> simplePaging(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        Page<User> users = userService.getAllUsersUsingSimplePagination(pageable);
        return users.map(userMapper::userToUserResponse);

    }

    @RequestMapping("/pagination/native")
    public Page<UserDTO> nativePaging(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        Page<User> users = userService.getAllUsersNativePageable(pageable);
        return users.map(userMapper::userToUserResponse);

    }

    @RequestMapping("/pagination/JPQL")
    public Page<UserDTO> JPQLPaging(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        Page<User> users = userService.getAllUsersJPQLPageable(pageable);
        return users.map(userMapper::userToUserResponse);

    }

    @PostMapping("/pagination/criteria")
    public Page<UserDTO> criteriaPagination(
            @RequestBody UserFilterRequest filters,
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        Page<User> users = userService.criteriaPagination(
                filters,
                pageable
        );

        return users.map(userMapper::userToUserResponse);
    }

    @GetMapping("/pagination/join-fetch")
    public Page<UserDTO> joinFetchPagination(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        Page<User> users = userService.getUsersWithAddresses(pageable);

        return users.map(userMapper::userToUserResponse);
    }


}
