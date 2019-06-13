package com.test.demo.controller;

import com.test.demo.service.SaveListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/6/5 9:46
 */
@RestController
public class ListController {

    @Resource
    private SaveListService saveListService;

    @GetMapping("saveList")
    public void saveList(){
        saveListService.testSaveList();
    }
}
