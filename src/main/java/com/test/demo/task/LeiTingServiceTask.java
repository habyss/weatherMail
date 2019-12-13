package com.test.demo.task;

import com.test.demo.service.LeiTingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/12/13 10:01
 */
@Service
public class LeiTingServiceTask {

    @Resource
    private LeiTingService leiTingService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void getWeather() throws Exception {
        leiTingService.updateByNum(3);
    }
}
