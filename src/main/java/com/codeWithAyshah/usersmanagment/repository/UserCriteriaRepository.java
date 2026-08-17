package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.UserFilterRequest;
import com.codeWithAyshah.usersmanagment.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCriteriaRepository {
    Page<User> findUsers(UserFilterRequest filter, Pageable pageable) ;
}
