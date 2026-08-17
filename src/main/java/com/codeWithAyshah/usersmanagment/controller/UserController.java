package com.codeWithAyshah.usersmanagment.controller;

import com.codeWithAyshah.usersmanagment.Mapper.UserMapper;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.*;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import com.codeWithAyshah.usersmanagment.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper ;

    public UserController(UserService userService,UserMapper userMapper) {
        this.userService = userService;
        this.userMapper =userMapper;
    }



    @GetMapping("/count")
    public long  countActiveUsers() {
        long count = userService.countUsers();
        return count;
    }

    @GetMapping("/getUsers")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void creatUser(@RequestBody CreatUserRequest creatUserRequests) {
        User user = userMapper.creatUserRequestToUser(creatUserRequests);
        userService.insertUser(user);
    }

    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable Integer id) {
        User user =userService.getUserById(id);
        return userMapper.userToUserResponse(user);

    }

    @PutMapping("{id}")
    public void update(@RequestBody CreatUserRequest creatUserRequest , @PathVariable Integer id ) {//updateUserReq
        User user = userMapper.creatUserRequestToUser(creatUserRequest);
        userService.updateUser(id ,user);
    }

    @PatchMapping("/{id}")
    public void updateUserPartially(
            @PathVariable("id") Integer id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        User updatedUser = userMapper.updateUserRequestToUser(updateUserRequest);
        userService.updateUserPartially(id ,updatedUser);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
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
    public void addAddress(
            @PathVariable Integer userId,
            @RequestBody UserAddresses address
    ) {
        userService.addAddress(userId, address);
    }

    @PostMapping("/search")
    public List<UserResponse> search(
            @RequestBody UserFilterRequest filter
    ) {
        return userService.searchUsers(filter)
                .stream()
                .map(userMapper::userToUserResponse)
                .toList();
    }
    @RequestMapping("/pagination/simple")
    public Page<UserResponse> simplePaging(
            @RequestParam (defaultValue = "0") Integer page ,
            @RequestParam (defaultValue = "5") Integer size)
    {
       Pageable pageable = PageRequest.of(page ,size , Sort.by("id").ascending());
       Page<User> users= userService.getAllUsersUsingSimplePagination(pageable);
       return users.map(userMapper::userToUserResponse);

    }

    @RequestMapping("/pagination/native")
    public Page<UserResponse> nativePaging(
            @RequestParam (defaultValue = "0") Integer page ,
            @RequestParam (defaultValue = "5") Integer size)
    {
        Pageable pageable = PageRequest.of(page ,size , Sort.by("id").ascending());
        Page<User> users= userService.getAllUsersNativePageable(pageable);
        return users.map(userMapper::userToUserResponse);

    }
    @RequestMapping("/pagination/JPQL")
    public Page<UserResponse> JPQLPaging(
            @RequestParam (defaultValue = "0") Integer page ,
            @RequestParam (defaultValue = "5") Integer size)
    {
        Pageable pageable = PageRequest.of(page ,size , Sort.by("id").ascending());
        Page<User> users= userService.getAllUsersJPQLPageable(pageable);
        return users.map(userMapper::userToUserResponse);

    }
    @PostMapping("/pagination/criteria")
    public Page<UserResponse> criteriaPagination(
            @RequestBody UserFilterRequest filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("id").ascending()
        );

        Page<User> users = userService.criteriaPagination(
                filters,
                pageable
        );

        return users.map(userMapper::userToUserResponse);
    }
    @GetMapping("/pagination/join-fetch")
    public Page<UserResponse> joinFetchPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> users =
                userService.getUsersWithAddresses(pageable);

        return users.map(userMapper::userToUserResponse);
    }


}
