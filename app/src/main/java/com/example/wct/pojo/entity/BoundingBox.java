package com.example.wct.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoundingBox {

    @JsonProperty("0")
    private Double latLeft;
    @JsonProperty("0")
    private Double latRight;
    @JsonProperty("0")
    private Double lonLeft;
    @JsonProperty("0")
    private Double lonRight;

    public Double getLatLeft() {
        return latLeft;
    }

    public Double getLatRight() {
        return latRight;
    }

    public Double getLonLeft() {
        return lonLeft;
    }

    public Double getLonRight() {
        return lonRight;
    }
}
