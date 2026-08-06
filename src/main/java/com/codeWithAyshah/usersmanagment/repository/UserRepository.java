package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
}
