package com.codeWithAyshah.usersmanagment.controller;

import com.codeWithAyshah.usersmanagment.Mapper.ReqMapper;
import com.codeWithAyshah.usersmanagment.Mapper.ResMapper;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.countDto;
import com.codeWithAyshah.usersmanagment.exception.UserNotFoundException;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import com.codeWithAyshah.usersmanagment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private ReqMapper reqMapper =  new ReqMapper();
    private ResMapper resMapper =   new ResMapper();

    public UserController(UserService userService, ReqMapper reqMapper, ResMapper resMapper) {
        this.userService = userService;
        this.reqMapper = reqMapper;
        this.resMapper = resMapper;
    }



    @GetMapping("/count")
    public countDto count() {
        long count = userService.countUsers();
        return new countDto(count);
    }

    @GetMapping("/list")
    public List<ResponseUserDto> listUsers() {
        return userService.getAllUsers()
                .stream()
                .map(resMapper)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void insert(@RequestBody RequestUserDto userDto) {
        User user = reqMapper.toEntity(userDto);
        userService.insertUser(user);
    }

    @GetMapping("{id}")
    public ResponseUserDto getUser(@PathVariable int id) {
        User user =userService.getUserById(id);
        return resMapper.apply(user);

    }

    @PutMapping("{id}")
    public void update(@RequestBody RequestUserDto userDto ,@PathVariable int id ) {//updateUserReq
        User user = reqMapper.toEntity(userDto);
        userService.updateUser(id ,user);
    }

    @PatchMapping("/{id}")
    public void patch(
            @PathVariable("id") int id,
            @RequestBody RequestUserDto userDto
    ) {
        User user = reqMapper.toEntity(userDto);
        userService.patchUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> head(@PathVariable int id) {
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
            @PathVariable int userId,
            @RequestBody UserAddresses address
    ) {
        userService.addAddress(userId, address);
    }

    @PostMapping("/search")
    public List<RequestUserDto> search(@RequestBody RequestUserDto userDto) {

       List<User> users =  userService.searchUsers(userDto);
       return users
               .stream()
               .map(reqMapper)
               .collect(Collectors.toList());
    }


}
