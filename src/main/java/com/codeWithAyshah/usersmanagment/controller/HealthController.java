package com.codeWithAyshah.usersmanagment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
//check the health of the AP
@GetMapping("/health")
public String test() {
    return ("the project is healthy ..");
}
}
