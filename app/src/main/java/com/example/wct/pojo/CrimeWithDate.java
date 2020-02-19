package com.example.wct.pojo;

import com.example.wct.pojo.entity.Crime;

import java.util.ArrayList;
import java.util.List;

public class CrimeWithDate {

    private List<Crime> crimeMonth = new ArrayList<>();
    private int year;
    private int month;

    public CrimeWithDate(List<Crime> crimeMonth, int year, int month) {
        this.crimeMonth = crimeMonth;
        this.year = year;
        this.month = month;
    }

    public List<Crime> getCrimeMonth() {
        return crimeMonth;
    }

    public void setCrimeMonth(List<Crime> crimeMonth) {
        this.crimeMonth = crimeMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
