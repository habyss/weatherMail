package com.test.demo.service.impl;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/6/28 11:15
 */
@Service
public class TestServiceNo {

    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    public void testNo(){
        WeatherConfig weatherConfig = weatherConfigMapper.selectByPrimaryKey(10L);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        weatherConfigMapper.updateByPrimaryKeySelective(weatherConfig);
    }

}
