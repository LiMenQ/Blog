package com.lmq.service;

import com.lmq.dao.IUserRepository;
import com.lmq.pojo.User;
import com.lmq.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/29 20:51
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user =userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
