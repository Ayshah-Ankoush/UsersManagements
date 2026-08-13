package com.codeWithAyshah.usersmanagment.service;

import com.codeWithAyshah.usersmanagment.exception.UserNotFoundException;
import com.codeWithAyshah.usersmanagment.Mapper.ReqMapper;
import com.codeWithAyshah.usersmanagment.Mapper.ResMapper;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import com.codeWithAyshah.usersmanagment.repository.UserSearchRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {


    private UserRepository userRepository;
    private UserSearchRepository userSearchRepository;

    public UserService(UserRepository userRepository,  UserSearchRepository userSearchRepository) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
    }


    public long countUsers() {
        return userRepository.count();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void insertUser(User user) {

        userRepository.save(user);

    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void updateUser(int id ,User  user) {
        user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setFullName(user.getFullName());
        user.setAddress(user.getAddress());
        user.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(user);
    }

    public void patchUser(int id, User user) {
        user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (user.getFullName()!= null) {
            user.setFullName(user.getAddress());
        }
        if (user.getAddress() != null) {
            user.setAddress(user.getAddress());
        }


        User updated = userRepository.save(user);

    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public boolean checkIfExist(int id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public void testNPlusOne() {

        List<User> users = userRepository.findAll();

        for (User user : users) {
            System.out.println(
                    user.getFullName()
                            + " has "
                            + user.getAddresses().size()
                            + " addresses"
            );
        }
    }


    public List<User> searchUsers(RequestUserDto userDto ) {
      List<User> users =userSearchRepository.finaAllByCriteria(userDto);
      return users;
              //.stream()
             // .map(reqMapper)
              //.collect(Collectors.toList());
    }
}
