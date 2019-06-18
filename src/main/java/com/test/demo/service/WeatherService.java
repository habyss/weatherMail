package com.test.demo.service;

import com.test.demo.entity.WeatherConfig;

import java.util.List;

/**
 * @author kun.han on 2019/6/14 11:01
 */
public interface WeatherService {

    /**
     * 发送邮件
     *
     * @return String
     */
    String sendWeatherMail();

    /**
     * 增加subject
     *
     * @param subject subject
     * @return string
     */
    String addSubject(String subject);

    /**
     * 删除subject
     *
     * @param id id
     * @return string
     */
    String deleteSubject(Long id);

    /**
     * 获取所有的subject
     *
     * @return list
     */
    List<WeatherConfig> getAllSubject();
}
