package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Language {

    @JsonProperty("iso_639_1")
    private String languageCode;
    @JsonProperty("name")
    private String name;

    public Language() {
    }

    public Language(String languageCode, String name) {
        this.languageCode = languageCode;
        this.name = name;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
