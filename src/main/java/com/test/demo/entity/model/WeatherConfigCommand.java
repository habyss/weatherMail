package com.test.demo.entity.model;

import com.test.demo.entity.WeatherConfig;

/**
 * @author kun.han on 2019/7/4 14:08
 */
public class WeatherConfigCommand extends WeatherConfig {

    private Integer num;

    private Integer pageSize;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
