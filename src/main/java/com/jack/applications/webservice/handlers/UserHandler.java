package com.jack.applications.webservice.handlers;

import com.jack.applications.utils.IdGenerator;
import com.jack.applications.webservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserHandler {

    @Autowired
    private IdGenerator idGenerator;

    private final Map<String, User> userMap;

    public UserHandler() {
        this.userMap = new HashMap<>();
    }

    public User generateNewUser(String userName) {
        return new User(idGenerator.getRandomId(), userName);
    }
}
