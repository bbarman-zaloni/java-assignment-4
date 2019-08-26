package com.zaloni.training.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zaloni.training.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    public List<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    public List<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}
