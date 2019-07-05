package com.test.demo.service.Impl;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
