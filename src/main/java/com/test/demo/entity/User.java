package com.test.demo.entity;

import lombok.Builder;

import java.io.Serializable;

/**
 * @author kun.han on 2019/7/5 16:15
 */
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

    User(final Long id, final String name, final String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String name;
        private String password;

        UserBuilder() {
        }

        public User.UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public User.UserBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public User.UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this.id, this.name, this.password);
        }

        @Override
        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", name=" + this.name + ", password=" + this.password + ")";
        }
    }
}