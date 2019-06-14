package com.test.demo.task;

import com.test.demo.service.Impl.WeatherServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/6/12 16:58
 */
@Service
public class WeatherServiceTask {

    @Resource
    private WeatherServiceImpl weatherService;

    @Scheduled(cron = "0 30 7 * * ?")
    public void getWeather() {
        weatherService.sendWeatherMail();
    }
}
