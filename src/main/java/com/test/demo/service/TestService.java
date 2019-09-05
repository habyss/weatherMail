package com.test.demo.service;

import com.test.demo.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author kun.han on 2019/7/9 10:23
 */
public interface TestService {
    User getUser(User user);

    List<Map<String, Object>> getList();
}
