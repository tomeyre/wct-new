package com.example.wct.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GeocodeLookup {

    @JsonProperty("place_id")
    private long placeId;
    @JsonProperty("licence")
    private String licence;
    @JsonProperty("osm_type")
    private String osmType;
    @JsonProperty("osm_id")
    private Integer osmId;
    @JsonProperty("boundingbox")
    private ArrayList<Double> boundingBox;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lon")
    private Double lon;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("class")
    private String classType;
    @JsonProperty("type")
    private String type;
    @JsonProperty("importance")
    private Double importance;
    @JsonProperty("icon")
    private String icon;

    public long getPlaceId() {
        return placeId;
    }

    public String getLicence() {
        return licence;
    }

    public String getOsmType() {
        return osmType;
    }

    public Integer getOsmId() {
        return osmId;
    }

    public ArrayList<Double> getBoundingBox() {
        return boundingBox;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getClassType() {
        return classType;
    }

    public String getType() {
        return type;
    }

    public Double getImportance() {
        return importance;
    }

    public String getIcon() {
        return icon;
    }
}
