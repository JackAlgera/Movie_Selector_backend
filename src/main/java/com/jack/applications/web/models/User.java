package com.jack.applications.web.models;

import com.jack.applications.utils.IdGenerator;

public class User {

    private static IdGenerator idGenerator = IdGenerator.getIdGenerator();

    private String userId;
    private String userName;

    public User(String userName) {
        this.userId = idGenerator.getRandomId();
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
