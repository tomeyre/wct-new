package com.example.wct.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private Date date = new Date();

    private static final DateUtil ourInstance = new DateUtil();

    public static DateUtil getInstance() {
        return ourInstance;
    }

    private DateUtil() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public Integer getMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public Integer getDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void minusOneMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month--;
        if(month < 0){
            month = 11;
            year--;

        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        date.setTime(calendar.getTimeInMillis());
    }

    public String getMonthAsString(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        month++;
        String monthString;

        if(month == 1){
            monthString = "January";
        }else if(month == 2){
            monthString = "February";
        }else if(month == 3){
            monthString = "March";
        }else if(month == 4){
            monthString = "April";
        }else if(month == 5){
            monthString = "May";
        }else if(month == 6){
            monthString = "June";
        }else if(month == 7){
            monthString = "July";
        }else if(month == 8){
            monthString = "August";
        }else if(month == 9){
            monthString = "September";
        }else if(month == 10){
            monthString = "October";
        }else if(month == 11){
            monthString = "November";
        }else {
            monthString = "December";
        }

        return monthString;
    }

}
