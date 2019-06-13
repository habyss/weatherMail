package com.test.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * test定时任务
 * @author kun.han
 */
@Service
public class TestTaskService {
    // @Scheduled(cron = "0 * * * * ?")
    public void test(){
        System.out.println(LocalDateTime.now() + " TestTaskService ... ");
    }
}
