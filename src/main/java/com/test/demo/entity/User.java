package com.test.demo.entity;

import lombok.Builder;

import java.io.Serializable;

/**
 * @author kun.han on 2019/7/5 16:15
 */
@Builder
public class User implements Serializable {
    private Long id;

    private String name;

    private String password;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}