package com.test.demo.controller;

import com.test.demo.entity.User;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.WeatherConfigCommand;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.impl.TestServiceImpl;
import com.test.demo.service.impl.TestServiceNo;
import com.test.demo.service.impl.TestServiceYesImpl;
import com.test.demo.service.TestServiceYes;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kun.han on 2019/6/14 11:12
 */
@RestController
public class TestController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    @Resource
    TestServiceNo testServiceNo;

    @Resource
    TestServiceYesImpl testServiceYesImpl;

    @Resource
    TestServiceImpl testServiceImpl;

    @Resource
    TestServiceYes testServiceYes;

    @GetMapping("testNo")
    public void testNo(){
        testServiceNo.testNo();
    }

    @GetMapping("testYes")
    public void testYes(){
        testServiceYesImpl.testYes();
    }

    @GetMapping("testYesNo")
    public void testYesNo(){
        testServiceYesImpl.testNo();
    }

    @GetMapping("getAllTest")
    public List<WeatherConfigCommand> getAllTest(){
        List<WeatherConfigCommand> test = weatherConfigMapper.getAllTest();
        for (WeatherConfigCommand command : test) {
            System.out.println(command);
        }
        return test;
    }

    @GetMapping("getAllByType")
    public List<WeatherConfig> getAllByType(@RequestParam("type")String type){
        return weatherConfigMapper.getAllByType(type);
    }

    @GetMapping("testInsert")
    public User testInsert(){
        return testServiceImpl.testInsert();
    }

    @GetMapping("getUser")
    public User getUser(@RequestParam("name")String name){
        return testServiceYes.getUser(name);

    }
}
