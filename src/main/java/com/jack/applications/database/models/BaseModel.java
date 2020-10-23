package com.jack.applications.database.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseModel {

    @JsonProperty("id")
    protected int id;

    public BaseModel() {
    }

    public BaseModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                '}';
    }
}
