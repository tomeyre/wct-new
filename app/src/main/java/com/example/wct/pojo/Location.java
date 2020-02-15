package com.example.wct.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("street")
    private Street street;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Street getStreet() {
        return street;
    }


}
