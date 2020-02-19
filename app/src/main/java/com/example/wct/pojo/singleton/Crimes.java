package com.example.wct.pojo.singleton;

import com.example.wct.pojo.entity.Crime;

import java.util.List;

public class Crimes {

    List<List<Crime>> crimes;
    List<List<Crime>> fullCrimeList;

    private static final Crimes ourInstance = new Crimes();

    public static Crimes getInstance() {
        return ourInstance;
    }

    private Crimes() {
    }

    public List<List<Crime>> getCrimes() {
        return crimes;
    }

    public void setCrimes(List<List<Crime>> crimes) {
        this.crimes = crimes;
    }

    public void setFullCrimeList(List<List<Crime>> crimes) {
        this.crimes = crimes;
        this.fullCrimeList = crimes;
    }

    public void resetCrimes(){
        crimes = fullCrimeList;
    }
}
