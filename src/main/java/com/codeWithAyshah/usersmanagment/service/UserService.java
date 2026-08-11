package com.codeWithAyshah.usersmanagment.service;

import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository  userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long countUsers() {
        return userRepository.count();
    }
}
