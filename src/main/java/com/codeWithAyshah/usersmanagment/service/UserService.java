package com.codeWithAyshah.usersmanagment.service;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.UpdateUserRequest;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.UserFilterRequest;
import com.codeWithAyshah.usersmanagment.exception.ResourceNotFoundException;
import com.codeWithAyshah.usersmanagment.model.User;
import com.codeWithAyshah.usersmanagment.model.UserAddress;
import com.codeWithAyshah.usersmanagment.repository.UserRepository;
import com.codeWithAyshah.usersmanagment.repository.UserSearchRepository;
import jakarta.validation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserService {


    private UserRepository userRepository;
    private UserSearchRepository userSearchRepository;
    private Validator validator;

    public UserService(UserRepository userRepository,  UserSearchRepository userSearchRepository) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
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

    @Transactional
    public void updateUser(Integer id, User newUser, UpdateUserRequest request) {
        User existingUser = userRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", id)
                );

        existingUser.setFullName(newUser.getFullName());
        userRepository.saveAndFlush(existingUser);

        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);
        violations.forEach(violation ->
                System.out.println(
                        violation.getPropertyPath()
                                + ": "
                                + violation.getMessage()
                )
        );
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations); // failure happens after the write
        }

        existingUser.getAddresses().clear();
        for (UserAddress address : newUser.getAddresses()) {
            address.setId(null);
            existingUser.addAddress(address);
        }


    }

    @Transactional
    public User updateUserPartially(Integer id, User newUser) {
        User existingUser = userRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", id)
                );

        if (newUser.getFullName() != null) {
            existingUser.setFullName(newUser.getFullName());
        }

        if (newUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(newUser.getPhoneNumber());
        }

        if (newUser.getAddresses() != null) {
            existingUser.getAddresses().clear();

            for (UserAddress address : newUser.getAddresses()) {
                address.setId(null);
                existingUser.addAddress(address);
            }
        }

        return userRepository.save(existingUser);
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
    public void addAddress(Integer userId, UserAddress address) {

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


    public Page<User> getUsersWithAddresses(
            Pageable pageable
    ) {
        return userRepository.findAllActiveWithAddresses(pageable);
    }


}
