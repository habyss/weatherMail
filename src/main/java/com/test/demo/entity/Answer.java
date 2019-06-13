package com.test.demo.entity;

/**
 * @author kun.han on 2019/5/22 9:40
 */
public class Answer {
    private String name;

    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
