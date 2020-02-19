package com.example.wct.pojo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Crime {

    @JsonProperty("category")
    private String category;
    @JsonProperty("location_type")
    private String locationType;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("context")
    private String context;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("outcome_status")
    private OutcomeStatus outcomeStatus;
    @JsonProperty("persistent_id")
    private String persistentId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("location_subtype")
    private String locationSubtype;
    @JsonProperty("month")
    private String month;

    public String getCategory() {
        return category;
    }

    public String getLocationType() {
        return locationType;
    }

    public Location getLocation() {
        return location;
    }

    public String getContext() {
        return context;
    }

    public OutcomeStatus getOutcomeStatus() {
        return outcomeStatus;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public Integer getId() {
        return id;
    }

    public String getLocationSubtype() {
        return locationSubtype;
    }

    public String getMonth() {
        return month;
    }

    public void print(){
        System.out.println("cat: " + category);
        System.out.println("loc: " + locationType);
        System.out.println("context: " + context);
        System.out.println("pId: " + persistentId);
        System.out.println("id: " + id);
        System.out.println("locSubType: " + locationSubtype);
        System.out.println("month: " + month);
    }
}
