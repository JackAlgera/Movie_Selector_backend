package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Genre extends BaseModel {

    @JsonProperty("name")
    private String name;

    public Genre() {
    }

    public Genre(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
