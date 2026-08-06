package com.codeWithAyshah.usersmanagment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //check the health of the API

    @RequestMapping("/health")
    public String test(){
         return ("the project is healthy ..");
    }
}
