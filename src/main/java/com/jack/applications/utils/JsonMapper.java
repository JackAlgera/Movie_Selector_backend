package com.jack.applications.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonMapper {

    private final ObjectMapper objectMapper;

    public JsonMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T readValue(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return this.objectMapper.readValue(jsonString, valueType);
    }

    public <T> T readValue(InputStream jsonString, Class<T> valueType) throws IOException {
        return this.objectMapper.readValue(jsonString, valueType);
    }
}
