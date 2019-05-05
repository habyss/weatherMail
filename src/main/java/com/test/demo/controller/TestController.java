package com.test.demo.controller;

import com.test.demo.annotation.RequestLimit;
import com.test.demo.entity.OrderParam;
import com.test.demo.entity.User;
import com.test.demo.task.TestTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kun.han
 */
@RestController
public class TestController {

    @Autowired
    private TestTaskService testTaskService;

    @RequestLimit
    @PostMapping("test")
    public void testRequestParam(OrderParam orderParam,
                                 @RequestParam("activityId") Integer activityId){

        System.out.println("activityId = " + activityId);
        List<User> users = orderParam.getUsers();
        User user1 = orderParam.getUser();
        System.out.println(user1);
        for (User user : users) {
            System.out.println("list" + user);
        }
    }
    @GetMapping("testTask")
    public void testTask(){
        testTaskService.test();
    }
}
