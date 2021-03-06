package com.example.wct.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Street {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
