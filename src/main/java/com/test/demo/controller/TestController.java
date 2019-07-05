package com.test.demo.controller;

import com.test.demo.entity.User;
import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.WeatherConfigCommand;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.Impl.TestService;
import com.test.demo.service.Impl.TestServiceNo;
import com.test.demo.service.Impl.TestServiceYes;
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
    TestServiceYes testServiceYes;

    @Resource
    TestService testService;

    @GetMapping("testNo")
    public void testNo(){
        testServiceNo.testNo();
    }

    @GetMapping("testYes")
    public void testYes(){
        testServiceYes.testYes();
    }

    @GetMapping("testYesNo")
    public void testYesNo(){
        testServiceYes.testNo();
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
        return testService.testInsert();
    }
}
