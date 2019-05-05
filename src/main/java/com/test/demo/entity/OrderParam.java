package com.test.demo.entity;

import java.util.List;

/**
 * @author kun.han
 */
public class OrderParam {
    private Integer num;

    private String address;

    private User user;

    private List<User> users;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "OrderParam{" +
                "num=" + num +
                ", address='" + address + '\'' +
                ", users=" + users +
                '}';
    }
}
