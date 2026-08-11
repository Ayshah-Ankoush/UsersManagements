package com.codeWithAyshah.usersmanagment.controller;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.countDto;
import com.codeWithAyshah.usersmanagment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/count")
    public countDto count() {
        long count = userService.countUsers();
        return new countDto(count);
    }

    @GetMapping("/list")
    public List<ResponseUserDto> listUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void insert(@RequestBody RequestUserDto userDto) {
        userService.insertUser(userDto);
    }

    @GetMapping("{id}")
    public RequestUserDto getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public void update(@RequestBody RequestUserDto userDto ,@PathVariable int id ) {//updateUserReq
        userService.updateUser(id ,userDto);
    }

    @PatchMapping("/{id}")
    public RequestUserDto patch(
            @PathVariable("id") int id,
            @RequestBody RequestUserDto userDto
    ) {
        return userService.patchUser(id, userDto);
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


}
