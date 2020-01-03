package com.test.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.entity.model.Sudoku;
import com.test.demo.utils.BaseAO;
import com.test.demo.utils.Constant;
import com.test.demo.utils.JsonResult;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kun.han on 2020/1/2 14:16
 */
@RestController
public class SudokuController {

    @Resource
    ObjectMapper mapper;

    @SneakyThrows
    @PostMapping("solveSudo")
    public BaseAO solveSudo(@RequestBody int[][] sudo) {
        // 号称世界上最难数独
        Sudoku sudoku = new Sudoku();
        sudoku.setMatrix(sudo);
        int[][] result = sudoku.backTrace(0, 0);
        return JsonResult.successMap(Constant.SUCCESS_FIND, result);
    }
}
