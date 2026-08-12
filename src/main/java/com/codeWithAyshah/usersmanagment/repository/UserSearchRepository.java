package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.RequestUserDto;
import com.codeWithAyshah.usersmanagment.controller.UserDTO.ResponseUserDto;
import com.codeWithAyshah.usersmanagment.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor  // to inject the entityManager to the consructor
public class UserSearchRepository {
    private final EntityManager entityManager;


    public List<User> findAllBySimpleQuery(String fullName, String address ,String phoneNumber) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        //SELECT * FROM user

        Root<User> root = criteriaQuery.from(User.class);

        //WHERE clauses

        Predicate namePredicate = criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%");
        Predicate addressPredicate = criteriaBuilder.like(root.get("address"), "%" + address + "%");
        Predicate phonePredicate = criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");


        Predicate orPredicate = criteriaBuilder.or(addressPredicate, namePredicate, phonePredicate);
        criteriaQuery.where(orPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();

    }


    public List<User>finaAllByCriteria(RequestUserDto requestUserDto) {

        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (requestUserDto.fullName() !=null){
            Predicate namePredicate = criteriaBuilder.like(root.get("fullName"), "%" + requestUserDto.fullName() + "%");
            predicates.add(namePredicate);
        }
        if (requestUserDto.address() !=null){
            Predicate addressPredicate =  criteriaBuilder.like(root.get("address"), "%" + requestUserDto.address() + "%");
            predicates.add(addressPredicate);
        }
        if (requestUserDto.phoneNumber() !=null){
            Predicate phonePredicate = criteriaBuilder.like(root.get("phoneNumber"), "%" + requestUserDto.phoneNumber() + "%");
            predicates.add(phonePredicate);
        }


        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();



    }

}
