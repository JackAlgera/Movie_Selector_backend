package com.jack.applications.utils;

import java.util.UUID;

public class IdGenerator {

    private static IdGenerator instance;

    private IdGenerator() {
    }

    public static IdGenerator getIdGenerator() {
        if(instance == null) {
            instance = new IdGenerator();
        }

        return instance;
    }

    public String getRandomId() {
        return UUID.randomUUID().toString();
    }

}
