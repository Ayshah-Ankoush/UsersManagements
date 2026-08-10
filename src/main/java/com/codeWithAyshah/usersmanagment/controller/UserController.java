package com.codeWithAyshah.usersmanagment.controller;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService  userService;

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
    @GetMapping("/listUsers")
    public List<ResponseUserDto> listUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    public void insert (@RequestBody RequestUserDto userDto) {
        userService.InsertUser(userDto);
    }
    @GetMapping("{id}")
    public RequestUserDto getUser(@PathVariable int id){
       return userService.getUserById(id);
    }

    @PutMapping
    public void update(@RequestBody RequestUserDto userDto){//updateUserReq
        userService.updateUser(userDto);
    }

    @PatchMapping("/{id}")
    public RequestUserDto patch(
            @PathVariable ("id") int id,
            @RequestBody RequestUserDto userDto
    ){
        return userService.patchUser(id,userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.HEAD)
    public ResponseEntity<Void> head(@PathVariable int id){
        if (userService.checkIfExist(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
