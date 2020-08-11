package com.test.demo.controller;

import com.test.demo.entity.Test;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.impl.WeatherServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author kun.han on 2019/10/17 16:28
 */
@RestController
public class TestController {

    @Resource
    WeatherConfigMapper weatherConfigMapper;
    @Resource
    WeatherServiceImpl weatherService;

    public Test test(@Validated Test test) {
        System.out.println(test.getBirthday());
        return test;
    }

    @GetMapping("test")
    public String test() throws Exception {
        // 创建大小为5的线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(10));

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 3; i++) {
            TestRun worker = new TestRun("task-" + i);
            // 只执行一次
//          scheduledThreadPool.schedule(worker, 5, TimeUnit.SECONDS);
            // 周期性执行，每5秒执行一次
            scheduledThreadPool.scheduleAtFixedRate(worker, 0, 5, TimeUnit.SECONDS);
        }

        Thread.sleep(10000);

        System.out.println("Shutting down executor...");
        // 关闭线程池
        scheduledThreadPool.shutdown();
        boolean isDone;
        // 等待线程池终止
        do {
            isDone = scheduledThreadPool.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("awaitTermination...");
        } while (!isDone);

        System.out.println("Finished all threads");
        return null;
    }

    @GetMapping("testIn")
    public String testIn(){
        return weatherService.addSubject("test");
    }

}













