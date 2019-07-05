package com.test.demo.service.Impl;

import com.test.demo.entity.User;
import com.test.demo.mapper.test.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/7/5 16:18
 */
@Service
public class TestService {

    @Resource
    UserMapper userMapper;

    public User testInsert(){
        User user = User.builder().name("hank").password("test").build();
        userMapper.insert(user);
        return user;
    }
}
