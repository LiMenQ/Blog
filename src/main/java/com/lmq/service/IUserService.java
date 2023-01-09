package com.lmq.service;

import com.lmq.pojo.User;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/29 20:50
 */
public interface IUserService {
    User checkUser(String username,String password);
}
