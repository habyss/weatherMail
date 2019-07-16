package com.test.demo.service;

import com.test.demo.entity.User;

import java.util.concurrent.Future;

/**
 * @author kun.han on 2019/7/8 10:41
 */
public interface AsyncService {
    /**
     * insert
     *
     * @param user user
     * @return user
     */
    Future<User> insert(User user,int i);

    Future<User> getUser(User user);
}
