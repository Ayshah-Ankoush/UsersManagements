package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> ,UserCriteriaRepository{

    @Override
    @EntityGraph(attributePaths = "addresses")
    List<User> findAll();

    //deriver query

    Optional<User> findByIdAndDeletedFalse(Integer id);
    List<User> findAllByDeletedFalse();


//simple pagenation
    Page<User> findAllByDeletedFalse(Pageable pageable);


    //native pagenation
    @Query(value = """
                    SELECT * FROM "user"
                    WHERE deleted = false 
                    ORDER BY id  Asc
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM  "user"
                    WHERE deleted =false
                    """,
            nativeQuery = true
    )

    Page<User> findActiveByDeletedFalseNative(Pageable pageable);

    //JPQL
    @Query (value =
            """
       SELECT u 
       FROM User u
       WHERE u.deleted = false
       ORDER BY id ASC
        """)
    Page<User> findAllActiveByDeletedFalseJPQL(Pageable pageable);

    //join fetch

    @Query(value = """
         SELECT DISTINCT  u
         FROM User u
         LEFT JOIN FETCH u.addresses
         WHERE u.deleted = false
         ORDER BY u.id
                """,
         countQuery = """
            SELECT COUNT (u)
            FROM User u
            WHERE u.deleted= false
                
"""
    )
    Page<User> findAllActiveWithAddresses(Pageable pageable);


}
