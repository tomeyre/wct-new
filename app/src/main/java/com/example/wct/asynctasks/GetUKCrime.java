package com.example.wct.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.wct.pojo.Crime;
import com.example.wct.util.DateUtil;
import com.example.wct.util.HttpGet;
import com.example.wct.util.SortCrimesUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GetUKCrime extends AsyncTask<String, String, String> {

    private List<Crime> crimes = new ArrayList<>();
    private DateUtil dateUtil = DateUtil.getInstance();
    private SortCrimesUtil sortCrimesUtil = new SortCrimesUtil();
    private Context context;

    public GetUKCrime(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpGet httpGet = new HttpGet();
        String url = "https://data.police.uk/api/crimes-street/all-crime?lat=52.629729&lng=-1.131592&date=" + dateUtil.getYear() + "-" + getMonthAsString();
        System.out.println(url);
        String json = httpGet.getJSONFromUrl(url);
        if(json == null)return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            crimes = mapper.readValue(json, new TypeReference<List<Crime>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(crimes == null){
            System.out.println("CRIMES:::::::0");
            return null;
        }
        System.out.println("CRIMES:::::::" + crimes.size());
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(crimes == null || crimes.size() == 0){
            dateUtil.minusOneMonth();
            new GetUKCrime(context).execute();
        }else {
            sortCrimesUtil.sortCrimesIntoStreets(crimes, context);
        }
    }

    private String getMonthAsString(){
        Integer month = dateUtil.getMonth();
        month++;
        String monthString = month.toString();
        if(monthString.length() == 1)monthString = "0" + month;
        return monthString;
    }
}
