package com.example.wct.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutcomeStatus {

    @JsonProperty("category")
    private String category;
    @JsonProperty("date")
    private String date;

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }
}
