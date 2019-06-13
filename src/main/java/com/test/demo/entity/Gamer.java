package com.test.demo.entity;

import java.io.Serializable;

/**
 * @author kun.han on 2019/5/28 10:18
 */
public class Gamer implements Serializable {
    private Integer id;

    private String name;

    private Integer sex;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}