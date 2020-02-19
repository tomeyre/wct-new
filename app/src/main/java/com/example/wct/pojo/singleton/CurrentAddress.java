package com.example.wct.pojo.singleton;

import com.example.wct.pojo.entity.GeocodeLookup;

public class CurrentAddress {

    GeocodeLookup geocodeLookup = new GeocodeLookup();

    private static final CurrentAddress ourInstance = new CurrentAddress();

    public static CurrentAddress getInstance() {
        return ourInstance;
    }

    private CurrentAddress() {
    }

    public GeocodeLookup getGeocodeLookup() {
        return geocodeLookup;
    }

    public void setGeocodeLookup(GeocodeLookup geocodeLookup) {
        this.geocodeLookup = geocodeLookup;
    }
}
