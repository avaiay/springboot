package com.vv.study.service.impl;

import com.vv.study.dao.UserDao;
import com.vv.study.entity.User;
import com.vv.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public User insertUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.save(user);
    }


    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
