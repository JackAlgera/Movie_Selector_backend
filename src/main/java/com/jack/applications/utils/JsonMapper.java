package com.jack.applications.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private static JsonMapper instance;
    private ObjectMapper objectMapper;

    private JsonMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public static JsonMapper getJsonMapper() {
        if(instance == null) {
            instance = new JsonMapper();
        }

        return instance;
    }

    public <T> T readValue(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return this.objectMapper.readValue(jsonString, valueType);
    }
}
