package com.example.wct.pojo.singleton;

import java.util.HashMap;

public class Filter {

    private static final Filter ourInstance = new Filter();

    public static Filter getInstance() {
        return ourInstance;
    }

    private HashMap<String, Boolean> crimeType = new HashMap<>();

    private Filter() {
    }


    {
        crimeType.put("anti-social-behaviour", true);
        crimeType.put("public-order", true);
        crimeType.put("bicycle-theft", true);
        crimeType.put("other-theft", true);
        crimeType.put("burglary", true);
        crimeType.put("robbery", true);
        crimeType.put("shoplifting", true);
        crimeType.put("theft-from-the-person", true);
        crimeType.put("criminal-damage-arson", true);
        crimeType.put("drugs", true);
        crimeType.put("possession-of-weapons", true);
        crimeType.put("vehicle-crime", true);
        crimeType.put("violent-crime", true);
        crimeType.put("other-crime", true);
    }

    public Boolean showType(String type){
        return crimeType.get(type);
    }

    public void setType(String type, Boolean result){
        crimeType.put(type, result);
    }

    public void resetFilter(){
        crimeType.put("anti-social-behaviour", true);
        crimeType.put("public-order", true);
        crimeType.put("bicycle-theft", true);
        crimeType.put("other-theft", true);
        crimeType.put("burglary", true);
        crimeType.put("robbery", true);
        crimeType.put("shoplifting", true);
        crimeType.put("theft-from-the-person", true);
        crimeType.put("criminal-damage-arson", true);
        crimeType.put("drugs", true);
        crimeType.put("possession-of-weapons", true);
        crimeType.put("vehicle-crime", true);
        crimeType.put("violent-crime", true);
        crimeType.put("other-crime", true);
    }
}
