package com.test.demo.controller;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.service.Impl.WeatherServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kun.han on 2019/6/12 17:07
 */
@RestController
public class TestWeatherController {
    @Resource
    WeatherServiceImpl weatherService;

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
}
