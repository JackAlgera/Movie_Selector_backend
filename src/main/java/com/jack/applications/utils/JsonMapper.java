package com.jack.applications.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

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

    public <T> T readValue(InputStream jsonString, Class<T> valueType) throws IOException {
        return this.objectMapper.readValue(jsonString, valueType);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
