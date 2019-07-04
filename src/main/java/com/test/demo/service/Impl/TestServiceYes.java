package com.test.demo.service.Impl;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.mapper.WeatherConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kun.han on 2019/6/28 11:15
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class TestServiceYes {
    @Resource
    private WeatherConfigMapper weatherConfigMapper;

    public void testYes(){
        WeatherConfig weatherConfig = weatherConfigMapper.selectByPrimaryKey(10L);

        weatherConfig.setStatus(1);
        weatherConfigMapper.updateByPrimaryKeySelective(weatherConfig);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testNo(){
        WeatherConfig weatherConfig = weatherConfigMapper.selectByPrimaryKey(10L);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        weatherConfigMapper.updateByPrimaryKeySelective(weatherConfig);
    }
    public List<WeatherConfig> test(){
        WeatherConfig config = weatherConfigMapper.selectByPrimaryKey(1L);
        config.setStatus(4);
        WeatherConfig weatherConfig = weatherConfigMapper.selectByPrimaryKey(80L);
        if (weatherConfig == null){
            weatherConfigMapper.insert(config);
        }
        return weatherConfigMapper.selectByStatus(4);
    }

}
