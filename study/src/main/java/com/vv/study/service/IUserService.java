package com.vv.study.service;

import com.vv.study.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findUserById(Integer id);

    User insertUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

}
