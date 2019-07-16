package com.test.demo.service.impl;

import com.test.demo.entity.User;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.mapper.test.UserMapper;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.AsyncService;
import com.test.demo.service.TestService;
import com.test.demo.service.TestServiceYes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author kun.han on 2019/6/28 11:15
 */
// 多数据源需要手动指定 事务管理器

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, value = "testTransactionManager")
public class TestServiceYesImpl implements TestServiceYes {
    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    TestService testService;

    @Resource
    AsyncService asyncService;

    @Override
    public void testYes() {
        WeatherConfig weatherConfig = weatherConfigMapper.selectByPrimaryKey(10L);

        weatherConfig.setStatus(1);
        weatherConfigMapper.updateByPrimaryKeySelective(weatherConfig);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testNo() {
        WeatherConfig weatherConfig = weatherConfigMapper.selectByPrimaryKey(10L);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        weatherConfigMapper.updateByPrimaryKeySelective(weatherConfig);
    }

    @Override
    public User getUser(String name) {
        User user = User.builder().name(name).password("111").build();
        userMapper.insert(user);
//        User byName = userMapper.getByName(user);
//        User user1 = getUser1(user);
        User user1 = testService.getUser(user);

        User result = null;
        try {
            Future<User> user2 = asyncService.getUser(user);
            User user3 = user2.get();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User getUser1(User user){
        return userMapper.getByName(user);
    }
}


