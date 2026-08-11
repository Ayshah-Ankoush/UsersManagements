package com.codeWithAyshah.usersmanagment.service;

import com.codeWithAyshah.usersmanagment.Mapper.ReqMapper;
import com.codeWithAyshah.usersmanagment.Mapper.ResMapper;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.entity.User;
import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

   // User users = Arrays.asList();

    private UserRepository  userRepository;
    private ReqMapper reqMapper;
    private ResMapper resMapper;

    public UserService(UserRepository userRepository , ReqMapper reqMapper, ResMapper resMapper) {
        this.userRepository = userRepository;
        this.reqMapper = reqMapper;
        this.resMapper = resMapper;
    }


    public long countUsers() {
        return userRepository.count();
    }

    public List<ResponseUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(resMapper)
                .collect(Collectors.toList());
    }

    public void InsertUser(RequestUserDto userDto){
        User user  =reqMapper.toEntity(userDto);
        userRepository.save(user);

    }
    public RequestUserDto getUserById(int id){
        return userRepository.findById(id)
                .stream()
                .map(reqMapper)
                .findFirst().get();
    }

    public void updateUser(RequestUserDto userDto){
        User user  =reqMapper.toEntity(userDto);
        userRepository.save(user);
    }
    public RequestUserDto patchUser(int id , RequestUserDto userDto){
        User user = userRepository.findById(id) .orElseThrow();

        if (userDto.fullName() != null){
            user.setFullName(userDto.fullName());
        }
        if (userDto.address() != null){
            user.setAddress(userDto.address());
        }


         User updated =userRepository.save(user);
        return reqMapper.apply(updated);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public boolean checkIfExist(int id){
        return userRepository.existsById(id);
    }
}
