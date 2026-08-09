package com.codeWithAyshah.usersmanagment.controller;

import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import com.codeWithAyshah.usersmanagment.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService  userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //check the health of the AP
    @GetMapping("/health")
    public String test(){
         return ("the project is healthy ..");
    }

    @GetMapping("/count")
    public long count()
    {
        return userService.countUsers();
    }
}
