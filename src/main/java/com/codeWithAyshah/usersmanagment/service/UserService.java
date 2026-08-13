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
        return userRepository.findAllByDeletedFalse();
    }

    public void insertUser(User user) {

        userRepository.save(user);

    }

    public User getUserById(int id) {
        return userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void updateUser(int id ,User  user) {
        user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setFullName(user.getFullName());
        user.setAddress(user.getAddress());
        user.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(user);
    }

    public void patchUser(int id, User user) {
        user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));

        if (user.getFullName()!= null) {
            user.setFullName(user.getAddress());
        }
        if (user.getAddress() != null) {
            user.setAddress(user.getAddress());
        }


        User updated = userRepository.save(user);

    }

    public void deleteUser(int id) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setDeleted(true);
        userRepository.save(user);
    }

    public boolean checkIfExist(int id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public void testNPlusOne() {

        List<User> users = userRepository.findAllByDeletedFalse();

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
