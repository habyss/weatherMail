package com.test.demo.entity.model;

import com.test.demo.entity.WeatherConfig;

/**
 * @author kun.han on 2019/7/4 14:08
 */
public class WeatherConfigCommand extends WeatherConfig {
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer pageSize;
}
