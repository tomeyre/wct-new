package com.example.wct.util;

import com.example.wct.pojo.entity.Crime;
import com.example.wct.pojo.CrimeTotal;

import java.util.ArrayList;
import java.util.List;

public class CrimeTotals {

    List<CrimeTotal> totals = new ArrayList<>();

    private static final CrimeTotals ourInstance = new CrimeTotals();

    public static CrimeTotals getInstance() {
        return ourInstance;
    }

    private CrimeTotals() {
    }

    public void calculate(List<Crime> crimes){
        totals.clear();
        for(Crime crime : crimes){
            if(totals.isEmpty()){
                totals.add(new CrimeTotal(crime.getCategory(), 1));
            }else{
                for(int i = 0; i < totals.size(); i++){
                    if(totals.get(i).getType().equalsIgnoreCase(crime.getCategory())){
                        int temp = totals.get(i).getCount();
                        temp++;
                        totals.get(i).setCount(temp);
                    }else if(i == totals.size() - 1){
                        totals.add(new CrimeTotal(crime.getCategory(), 1));
                        break;
                    }
                }
            }
        }
    }

    public List<CrimeTotal> getTotals() {
        return totals;
    }
}
