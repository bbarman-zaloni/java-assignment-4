package com.zaloni.training.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaloni.training.dao.UserRepository;
import com.zaloni.training.entity.User;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository repo;

    public User findById(int id) {
        Optional<User> user = repo.findById(new Long(id));
        return user.isPresent() ? user.get() : null;
    }

    public User findByEmail(String email) {
        System.out.println("email:"+email);
        email = "admin@gmail.com";
        List<User> userList = repo.findByEmail(email);
        System.out.println("size:"+userList.size());
        System.out.println(userList);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    public boolean isValidCredential(String email, String password) {
        System.out.println("e:"+email+", p:"+ password);
        int count = repo.findByEmailAndPassword(email, password).size();
        return count == 1;
    }
}
