package com.test.demo.controller;

import com.github.pagehelper.PageInfo;
import com.test.demo.entity.Title;
import com.test.demo.entity.model.Page;
import com.test.demo.service.LeiTingService;
import com.test.demo.utils.BaseAO;
import com.test.demo.utils.Constant;
import com.test.demo.utils.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author kun.han on 2019/12/13 16:15
 */
@RestController
public class LeiTingController {

    @Resource
    private LeiTingService leiTingService;

    @GetMapping("getTitles")
    public BaseAO getTitles(Page page, String title){
        if (Objects.isNull(page.getPageNum()) || Objects.isNull(page.getPageSize())){
            return JsonResult.failureMap(Constant.NO_PARAM);
        }
        PageInfo<Title> info =  leiTingService.getTitles(page, title);
        return JsonResult.successMap(Constant.SUCCESS_FIND, info);
    }
}
