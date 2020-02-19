package com.example.wct.util;

import android.content.Context;

import com.example.wct.MapsActivity;
import com.example.wct.pojo.entity.Crime;
import com.example.wct.pojo.singleton.Crimes;

import java.util.ArrayList;
import java.util.List;

public class SortCrimesUtil {

    Crimes crimes = Crimes.getInstance();

    public void sortCrimesIntoStreets(List<Crime> crimes, Context context){
        List<List<Crime>> sortedCrimes = new ArrayList<>();
        for(Crime crime : crimes){
            crime.getLocation().getStreet().setName(crime.getLocation().getStreet().getName().replace("On or near ",""));
            if(sortedCrimes == null || sortedCrimes.isEmpty()){
                List<Crime> crimeList = new ArrayList<>();
                crimeList.add(crime);
                sortedCrimes.add(crimeList);
            }else {
                for (int i = 0; i < sortedCrimes.size(); i++){
                    if(sortedCrimes.get(i).get(0).getLocation().getLatitude().equals(crime.getLocation().getLatitude()) &&
                    sortedCrimes.get(i).get(0).getLocation().getLongitude().equals(crime.getLocation().getLongitude())){
                        sortedCrimes.get(i).add(crime);
                        break;
                    }
                    if(i == sortedCrimes.size() - 1){
                        List<Crime> crimeList = new ArrayList<>();
                        crimeList.add(crime);
                        sortedCrimes.add(crimeList);
                        break;
                    }
                }
            }
        }
        print(sortedCrimes);
        this.crimes.setFullCrimeList(sortedCrimes);
        ((MapsActivity)context).update();
    }
    public void print(List<List<Crime>> sortedCrimes){
        for(List<Crime> crimeList : sortedCrimes){
            for(Crime crime : crimeList){
                System.out.println(crime.getLocation().getStreet().getName() + " :: " + crime.getCategory());
            }
        }
    }
}
