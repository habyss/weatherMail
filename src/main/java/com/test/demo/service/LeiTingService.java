package com.test.demo.service;


import com.github.pagehelper.PageInfo;
import com.test.demo.entity.Title;
import com.test.demo.entity.model.Page;

/**
 * @author kun.han on 2019/12/13 9:13
 */
public interface LeiTingService {
    /**
     * 定时更新雷霆论坛数据
     * @param num
     */
    void updateByNum(Integer num) throws Exception;

    PageInfo<Title> getTitles(Page page, String title);
}
