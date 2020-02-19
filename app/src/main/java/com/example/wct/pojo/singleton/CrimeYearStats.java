package com.example.wct.pojo.singleton;

import com.example.wct.pojo.CrimeWithDate;
import com.example.wct.pojo.entity.Crime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrimeYearStats {

    private List<CrimeWithDate> crimes = new ArrayList<>();
    private HashMap<String, Integer> totals = new HashMap<>();

    private void reset()
    {
        totals.put("anti-social-behaviour", 0);
        totals.put("public-order", 0);
        totals.put("bicycle-theft", 0);
        totals.put("other-theft", 0);
        totals.put("burglary", 0);
        totals.put("robbery", 0);
        totals.put("shoplifting", 0);
        totals.put("theft-from-the-person", 0);
        totals.put("criminal-damage-arson", 0);
        totals.put("drugs", 0);
        totals.put("possession-of-weapons", 0);
        totals.put("vehicle-crime", 0);
        totals.put("violent-crime", 0);
        totals.put("other-crime", 0);
    }

    private static final CrimeYearStats ourInstance = new CrimeYearStats();

    public static CrimeYearStats getInstance() {
        return ourInstance;
    }

    private CrimeYearStats() {
        reset();
    }

    public List<CrimeWithDate> getCrimes() {
        return crimes;
    }

    public void clear(){
        System.out.println("CLEAR LIST");
        if(crimes != null && !crimes.isEmpty())crimes.clear();
        reset();
    }

    public synchronized void updateCrimes(List<Crime> crimesForMonth, int year, int month) {
        if (crimes.size() == 0) {
            crimes.add(new CrimeWithDate(crimesForMonth, year, month));
        } else {
            for (int i = 0; i < crimes.size(); i++) {
                if (crimes.get(i).getMonth() < month &&
                        crimes.get(i).getYear() == year) {
                    crimes.add(i,new CrimeWithDate(crimesForMonth, year, month));
                    break;
                }
                if (i == crimes.size() - 1) {
                    crimes.add(new CrimeWithDate(crimesForMonth, year, month));
                    break;
                }
            }
        }
    }

    public void updateTotals(String type){
        int total = totals.get(type);
        total++;
        totals.put(type, total);
    }

    public void print(){
        for(CrimeWithDate crimeWithDate : crimes){
            System.out.println("Crimes total = " + crimeWithDate.getCrimeMonth().size() + " for month " + crimeWithDate.getMonth() + " year " + crimeWithDate.getYear());
        }
    }

    public Integer getTotalsForType(String type){
        return totals.get(type);
    }

    public HashMap<String, Integer> getTotals(){
        return totals;
    }
}
