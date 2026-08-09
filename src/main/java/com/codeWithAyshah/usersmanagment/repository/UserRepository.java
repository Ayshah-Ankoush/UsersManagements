package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
