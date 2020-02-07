package com.example.wct.util;

import android.content.Context;

import com.example.wct.MapsActivity;
import com.example.wct.pojo.Crime;

import java.util.ArrayList;
import java.util.List;

public class SortCrimesUtil {

    public void sortCrimesIntoStreets(List<Crime> crimes, Context context){
        List<List<Crime>> sortedCrimes = new ArrayList<>();
        for(Crime crime : crimes){
            if(sortedCrimes == null || sortedCrimes.isEmpty()){
                List<Crime> crimeList = new ArrayList<>();
                crimeList.add(crime);
                sortedCrimes.add(crimeList);
            }else {
                for (int i = 0; i < sortedCrimes.size(); i++){
                    if(sortedCrimes.get(i).get(0).getLocation().getStreet().getName().equalsIgnoreCase(crime.getLocation().getStreet().getName())){
                        sortedCrimes.get(i).add(crime);
                        break;
                    }
                    if(i == sortedCrimes.size() - 1){
                        List<Crime> crimeList = new ArrayList<>();
                        crimeList.add(crime);
                        sortedCrimes.add(crimeList);
                    }
                }
            }
        }
        print(sortedCrimes);

        ((MapsActivity)context).update(sortedCrimes);
    }
    public void print(List<List<Crime>> sortedCrimes){
        for(List<Crime> crimeList : sortedCrimes){
            for(Crime crime : crimeList){
                System.out.println(crime.getLocation().getStreet().getName() + " :: " + crime.getCategory());
            }
        }
    }
}
