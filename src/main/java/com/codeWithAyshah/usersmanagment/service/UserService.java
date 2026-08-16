package com.codeWithAyshah.usersmanagment.service;

import com.codeWithAyshah.usersmanagment.exception.UserNotFoundException;
import com.codeWithAyshah.usersmanagment.Mapper.ReqMapper;
import com.codeWithAyshah.usersmanagment.Mapper.ResMapper;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import com.codeWithAyshah.usersmanagment.repository.UserSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public void updateUser(int id ,User  newuser) {
        User existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));
        existingUser.setFullName(newuser.getFullName());
        //user.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(existingUser);
    }

    public void patchUser(int id, User newuser) {
       User  existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));

        if (newuser.getFullName()!= null) {
            existingUser.setFullName(newuser.getFullName());
        }


        User updated = userRepository.save(existingUser);

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
    public void addAddress(int userId, UserAddresses address) {

        User existingUser = userRepository
                .findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        existingUser.addAddress(address);

        userRepository.save(existingUser);
    }


    public List<User> searchUsers(RequestUserDto userDto ) {
      List<User> users =userSearchRepository.finaAllByCriteria(userDto);
      return users;

    }

    public Page<User> getAllUsersUsingSimplePagination(Pageable  pageable) {
        return userRepository.findAllByDeletedFalse(pageable);
    }

    public Page<User> getAllUsersNativePageable(Pageable pageable) {
        return userRepository.findActiveByDeletedFalseNative(pageable);
    }

    public Page<User> getAllUsersJPQLPageable(Pageable pageable) {
        return userRepository.findAllActiveByDeletedFalseJPQL(pageable);
    }


}
