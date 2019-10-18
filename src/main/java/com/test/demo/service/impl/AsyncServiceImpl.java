package com.test.demo.service.impl;

import com.test.demo.entity.User;
import com.test.demo.mapper.test.UserMapper;
import com.test.demo.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author kun.han on 2019/7/8 10:42
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Resource
    UserMapper userMapper;

    /**
     * insert
     *
     * @param user user
     * @return user
     */
    @Override
    public Future<User> insert(User user,int i) {
        userMapper.insert(user);
        System.out.println("------------------------------" + i + "--------------------");
        return new AsyncResult<User>(user);
    }

    @Override
    @Async
    public Future<User> getUser(User user) {
        User byName = getUser1(user);
//        User byName = testServiceYes.getUser1(user);
        return new AsyncResult<>(byName);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "testTransactionManager")
    public User getUser1(User user){
        return userMapper.getByName(user);
    }

}
