package com.codeWithAyshah.usersmanagment.repository;

import com.codeWithAyshah.usersmanagment.controller.UserDTO.UserFilterRequest;
import com.codeWithAyshah.usersmanagment.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserCriteriaRepositoryImpl implements UserCriteriaRepository{

    private final EntityManager entityManager ;

    UserCriteriaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager ;
    }
    @Override
    public Page<User> findUsers(UserFilterRequest filter , Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userQuery = builder.createQuery(User.class);
        Root<User> user = userQuery.from(User.class);

        Predicate[] predicates =creatPredicates(builder ,user,filter);
        userQuery.select(user);
        userQuery.where(predicates);
       userQuery.orderBy(builder.asc(user.get("id")));


       TypedQuery<User> query = entityManager.createQuery(userQuery);
       //after we have an executable query now we will apply the pagination
       query.setFirstResult(Math.toIntExact(pageable.getOffset()));
       query.setMaxResults(Math.toIntExact(pageable.getPageSize()));
        //now send it to the database
       List<User> list = query.getResultList();


       //now let's do count query
        CriteriaBuilder countCriteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countCriteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);

        Predicate[] countPredicates =creatPredicates(countCriteriaBuilder,countRoot,filter);
        countQuery.select(countCriteriaBuilder.count(countRoot));
        countQuery.where(countPredicates);
        Long totalElement = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(list,pageable,totalElement);




    }

    private Predicate[] creatPredicates(CriteriaBuilder criteriaBuilder, Root<User> user, UserFilterRequest filter)
    {
        List<Predicate> predicates = new ArrayList<>();
        //for the soft deletion
        predicates.add(criteriaBuilder.isFalse(user.get("deleted")));

        if (filter==null){
            return predicates.toArray(new Predicate[0]);

        }
        if (filter.getFullName()!=null && !filter.getFullName().isBlank()){
            predicates.add(criteriaBuilder.like(user.get("fullName"), "%"+filter.getFullName()+"%"));
        }
        if (filter.getPhoneNumber()!=null && !filter.getPhoneNumber().isBlank()){
            predicates.add(criteriaBuilder.equal(user.get("phoneNumber"), filter.getPhoneNumber()));
        }

        return predicates.toArray(new Predicate[0]);


    }








}
