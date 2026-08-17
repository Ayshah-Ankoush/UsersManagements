package com.codeWithAyshah.usersmanagment.service;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.UserFilterRequest;
import com.codeWithAyshah.usersmanagment.exception.ResourceNotFoundException;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.CreatUserRequest;
import com.codeWithAyshah.usersmanagment.models.User;
import com.codeWithAyshah.usersmanagment.models.UserAddresses;
import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import com.codeWithAyshah.usersmanagment.repository.UserSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public User getUserById(Integer id) {
        return userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("User",id));
    }

    public void updateUser(Integer id ,User  newuser) {
        User existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("User",id));
        existingUser.setFullName(newuser.getFullName());
        //user.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(existingUser);
    }

    public User updateUserPartially(Integer id, User newuser) {
       User  existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("User",id));

        if (newuser.getFullName()!= null) {
            existingUser.setFullName(newuser.getFullName());
        }


        User updated = userRepository.save(existingUser);
        return updated;

    }

    public void deleteUser(Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("User",id));
        user.setDeleted(true);
        userRepository.save(user);
    }

    public boolean checkIfExist(Integer id) {
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
    public void addAddress(Integer userId, UserAddresses address) {

        User existingUser = userRepository
                .findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User",userId));

        existingUser.addAddress(address);

        userRepository.save(existingUser);
    }


    public List<User> searchUsers(UserFilterRequest userFilterRequest ) {
      List<User> users =userSearchRepository.finaAllByCriteria(userFilterRequest);
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
    public Page<User> criteriaPagination(
            UserFilterRequest filters,
            Pageable pageable
    ) {
        return userRepository.findUsers(filters, pageable);
    }


}
