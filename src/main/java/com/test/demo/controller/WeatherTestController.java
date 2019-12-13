package com.test.demo.controller;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.service.WeatherService;
import com.test.demo.service.impl.WeatherServiceImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author kun.han on 2019/6/12 17:07
 */
@RestController
public class WeatherTestController {
    @Resource
    private WeatherService weatherService;

    @GetMapping("sendWeatherMail")
    public String sendWeatherMail(){
        return weatherService.sendWeatherMail();
    }

    @GetMapping("addSubject")
    public String addSubject(@RequestParam("subject")String subject){
        return weatherService.addSubject(subject);
    }

    @GetMapping("deleteSubject")
    public String deleteSubject(@RequestParam("id")Long id){
        return weatherService.deleteSubject(id);
    }

    @GetMapping("getAllSubject")
    public List<WeatherConfig> getAllSubject(){
        return weatherService.getAllSubject();
    }
    @GetMapping("getAllTest")
    public List<Map> getAllTest(){
        return weatherService.test();
    }

    @DeleteMapping("clear")
    public String clear(){
        return weatherService.clear();
    }

}
