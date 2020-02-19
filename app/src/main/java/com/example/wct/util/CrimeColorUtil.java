package com.example.wct.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.example.wct.R;
import com.example.wct.pojo.CrimeTypes;

import java.util.HashMap;

public class CrimeColorUtil {

    private Activity activity;

    public CrimeColorUtil(Activity activity){
        this.activity = activity;
        initializeColor();
    }

    private HashMap<String, Integer> colorHashMap = new HashMap<>();
    private CrimeTypes crimeTypes = new CrimeTypes();

    private void initializeColor() {
        colorHashMap.put(crimeTypes.getCrimeType("asb"), ContextCompat.getColor(activity, R.color.AntiSocialBehaviour));
        colorHashMap.put(crimeTypes.getCrimeType("po"), ContextCompat.getColor(activity, R.color.PublicOrder));
        colorHashMap.put(crimeTypes.getCrimeType("bt"), ContextCompat.getColor(activity, R.color.BicycleTheft));
        colorHashMap.put(crimeTypes.getCrimeType("ot"), ContextCompat.getColor(activity, R.color.OtherTheft));
        colorHashMap.put(crimeTypes.getCrimeType("b"), ContextCompat.getColor(activity, R.color.Burglary));
        colorHashMap.put(crimeTypes.getCrimeType("r"), ContextCompat.getColor(activity, R.color.Robbery));
        colorHashMap.put(crimeTypes.getCrimeType("s"), ContextCompat.getColor(activity, R.color.Shoplifting));
        colorHashMap.put(crimeTypes.getCrimeType("tftp"), ContextCompat.getColor(activity, R.color.TheftFromThePerson));
        colorHashMap.put(crimeTypes.getCrimeType("cda"), ContextCompat.getColor(activity, R.color.CriminalDamageArson));
        colorHashMap.put(crimeTypes.getCrimeType("d"), ContextCompat.getColor(activity, R.color.Drugs));
        colorHashMap.put(crimeTypes.getCrimeType("pow"), ContextCompat.getColor(activity, R.color.PossessionOfWeapons));
        colorHashMap.put(crimeTypes.getCrimeType("vc"), ContextCompat.getColor(activity, R.color.VehicleCrime));
        colorHashMap.put(crimeTypes.getCrimeType("vic"), ContextCompat.getColor(activity, R.color.ViolentCrime));
        colorHashMap.put(crimeTypes.getCrimeType("oc"), ContextCompat.getColor(activity, R.color.OtherCrime));
    }

    public Integer getCrimeColor(String type) {
        return colorHashMap.get(type);
    }
}
