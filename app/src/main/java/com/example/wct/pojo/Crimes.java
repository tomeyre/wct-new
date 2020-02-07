package com.example.wct.pojo;

import java.util.List;

public class Crimes {

    List<List<Crime>> crimes;

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
}
