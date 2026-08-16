package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @EntityGraph(attributePaths = "addresses")
    List<User> findAll();

    //deriver query

    Optional<User> findByIdAndDeletedFalse(Integer id);
    List<User> findAllByDeletedFalse();



    Page<User> findAllByDeletedFalse(Pageable pageable);
}
