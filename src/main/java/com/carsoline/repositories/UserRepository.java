package com.carsoline.repositories;

import com.carsoline.rest.daos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Dominik on 2017-03-22.
 */
public interface UserRepository extends JpaRepository<User, String> {

    //@Query("select u from User u where u.id = ?1")
    //User findUserById(@Param("id") String id);

    User findOneById(String id);

    @Query("select u from user u where u.id = :id")
    User findUserById(@Param("id") String id);

    User findOneByEmail(String email);

    User findOneByNameAndPassword(String name, String password);
}