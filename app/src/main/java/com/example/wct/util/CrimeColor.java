package com.example.wct.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.example.wct.R;
import com.example.wct.YearStats;

import java.util.HashMap;

public class CrimeColor {

    private Context context;
    private HashMap<String, Integer> colorHashMap = new HashMap<>();

    public CrimeColor(Context context){
        this.context = context;
    }


    {
        colorHashMap.put("anti-social-behaviour", ContextCompat.getColor(context, R.color.AntiSocialBehaviour));
        colorHashMap.put("public-order", ContextCompat.getColor(context, R.color.PublicOrder));
        colorHashMap.put("bicycle-theft", ContextCompat.getColor(context, R.color.BicycleTheft));
        colorHashMap.put("other-theft", ContextCompat.getColor(context, R.color.OtherTheft));
        colorHashMap.put("burglary", ContextCompat.getColor(context, R.color.Burglary));
        colorHashMap.put("robbery", ContextCompat.getColor(context, R.color.Robbery));
        colorHashMap.put("shoplifting", ContextCompat.getColor(context, R.color.Shoplifting));
        colorHashMap.put("theft-from-the-person", ContextCompat.getColor(context, R.color.TheftFromThePerson));
        colorHashMap.put("criminal-damage-arson", ContextCompat.getColor(context, R.color.CriminalDamageArson));
        colorHashMap.put("drugs", ContextCompat.getColor(context, R.color.Drugs));
        colorHashMap.put("possession-of-weapons", ContextCompat.getColor(context, R.color.PossessionOfWeapons));
        colorHashMap.put("vehicle-crime", ContextCompat.getColor(context, R.color.VehicleCrime));
        colorHashMap.put("violent-crime", ContextCompat.getColor(context, R.color.ViolentCrime));
        colorHashMap.put("other-crime", ContextCompat.getColor(context, R.color.OtherCrime));
    }

    public Integer getCrimeColor(String type){
        return colorHashMap.get(type);
    }
}
