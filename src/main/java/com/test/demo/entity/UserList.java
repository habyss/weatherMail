package com.test.demo.entity;

import java.util.List;

/**
 * @author kun.han on 2019/5/13 17:45
 */
public class UserList {
    private List<User> usersL;

    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<User> getUsersL() {
        return usersL;
    }

    public void setUsersL(List<User> usersL) {
        this.usersL = usersL;
    }

}
