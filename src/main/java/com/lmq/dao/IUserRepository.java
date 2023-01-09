package com.lmq.dao;

import com.lmq.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/29 20:54
 */
public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
