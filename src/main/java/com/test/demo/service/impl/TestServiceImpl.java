package com.test.demo.service.impl;

import com.test.demo.entity.User;
import com.test.demo.mapper.test.UserMapper;
import com.test.demo.service.AsyncService;
import com.test.demo.service.TestService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author kun.han on 2019/7/5 16:18
 */
@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "testTransactionManager")
public class TestServiceImpl implements TestService {

    @Resource
    UserMapper userMapper;

    @Resource
    AsyncService asyncService;

    @Resource
    ThreadPoolTaskExecutor executor;
    public User testInsert() {
        for (int i = 0; i < 10; i++) {
            User user = User.builder().name("hank").password("test").build();
            Future<User> insert = asyncService.insert(user, i);
        }
        System.out.println(executor);
        User user = User.builder().name("hank").password("test").build();
        Future<User> insert = asyncService.insert(user, 20);
        try {
            return insert.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(User user){
        return userMapper.getByName(user);
    }
}
