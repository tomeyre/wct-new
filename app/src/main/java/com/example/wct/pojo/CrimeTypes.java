package com.example.wct.pojo;

import java.util.HashMap;

public class CrimeTypes {

    private HashMap<String, String> type = new HashMap<>();

    {
        type.put("asb","anti-social-behaviour");
        type.put("po","public-order");
        type.put("bt","bicycle-theft");
        type.put("ot","other-theft");
        type.put("b","burglary");
        type.put("r","robbery");
        type.put("s","shoplifting");
        type.put("tftp","theft-from-the-person");
        type.put("cda","criminal-damage-arson");
        type.put("d","drugs");
        type.put("pow","possession-of-weapons");
        type.put("vc","vehicle-crime");
        type.put("vic","violent-crime");
        type.put("oc","other-crime");
    }

    public String getCrimeType(String type){
        return this.type.get(type);
    }
}
