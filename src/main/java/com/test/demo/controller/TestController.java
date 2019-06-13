package com.test.demo.controller;

import com.test.demo.annotation.RequestLimit;
import com.test.demo.entity.Answer;
import com.test.demo.entity.OrderParam;
import com.test.demo.entity.User;
import com.test.demo.entity.UserList;
import com.test.demo.service.TestService;
import com.test.demo.service.TestServiceNoTranOnClass;
import com.test.demo.task.TestTaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kun.han
 */
@RestController
public class TestController {

    @Resource
    private TestTaskService testTaskService;

    @Resource
    private TestService testService;

    @Resource
    private TestServiceNoTranOnClass testServiceNoTranOnClass;


    @RequestLimit
    @PostMapping("test")
    public OrderParam testRequestParam(OrderParam orderParam,
                                       @RequestParam("activityId") Integer activityId) {

        System.out.println("activityId = " + activityId);
        List<User> users = orderParam.getUsers();
        User user1 = orderParam.getUser();
        System.out.println(user1);
        for (User user : users) {
            System.out.println("list" + user);
        }
        return orderParam;
    }


    @GetMapping("testTask")
    public void testTask() {
        testTaskService.test();
    }

    @PostMapping("ttt")
    public UserList ttt(UserList users,
                        @RequestParam("dd") Integer dd) {
        for (User param : users.getUsersL()) {
            System.out.println(param);
        }
        return users;
    }

    @PostMapping("hk")
    public void hk(UserList users,
                   @RequestParam("dd") Integer dd) {
        for (User user : users.getUsersL()) {
            System.out.println(user);
        }
        for (Answer answer : users.getAnswers()) {
            System.out.println(answer);
        }
        System.out.println(dd);
    }

    @GetMapping("tran")
    public void tran() {
        // 同一个事务类中的方法
        testService.test();
    }

    @GetMapping("tran1")
    public void tran1() {
        // 同一个类中的 非事务方法调用 事务方法和非事务方法
        testServiceNoTranOnClass.test();
    }

    @GetMapping("tran2")
    public void tran2() {
        // 同一个类中的 非事务方法调用 非事务方法和事务方法
        testServiceNoTranOnClass.test2();
    }

    @GetMapping("tran3")
    public void tran3() {
        // 同一个类中的 事务方法调用 事务方法
        testServiceNoTranOnClass.testTranGamer();
    }

}













