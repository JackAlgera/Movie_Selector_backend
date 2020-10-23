package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {

    @JsonProperty("iso_3166_1")
    private String countryCode;
    @JsonProperty("name")
    private String name;

    public Country() {
    }

    public Country(String countryCode, String name) {
        this.countryCode = countryCode;
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

