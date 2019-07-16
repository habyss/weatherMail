package com.test.demo.service;

import com.test.demo.entity.User;

/**
 * @author kun.han on 2019/7/8 18:09
 */
public interface TestServiceYes {
    void testYes();

    void testNo();

    User getUser(String name) ;

    User getUser1(User user);
}
