package com.jack.applications.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class IdGenerator {

    private static IdGenerator instance;

    private static final SecureRandom random = new SecureRandom();

    private static final String ID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String getRandomId() {
        StringBuilder newId = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            newId.append(ID_CHARS.charAt(random.nextInt(ID_CHARS.length())));
        }

        return newId.toString();
    }

    public UUID getRandomUUID() {
        return UUID.randomUUID();
    }

}
