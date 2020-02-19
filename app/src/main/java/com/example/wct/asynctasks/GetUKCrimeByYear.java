package com.example.wct.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.wct.YearStats;
import com.example.wct.pojo.entity.Crime;
import com.example.wct.pojo.singleton.CrimeYearStats;
import com.example.wct.util.DateUtil;
import com.example.wct.util.HttpGet;
import com.example.wct.util.Print;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class GetUKCrimeByYear extends AsyncTask<Integer, Integer, String> {

    private CrimeYearStats crimeYearStats = CrimeYearStats.getInstance();
    private DateUtil dateUtil = DateUtil.getInstance();
    private Context context;

    public GetUKCrimeByYear(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Integer... params) {

        int year = dateUtil.getYear();
        int month = dateUtil.getMonth() + 1;
        if(month < 1){month = 12;
        year--;}

        try {
            int maxThreads = 12;

            for (int i = 0; i < maxThreads; i++) {
                final int innerMonth = month;
                final int innerYear = year;
                final String url = "https://data.police.uk/api/crimes-at-location?date=" + innerYear + "-" + getMonthAsString(innerMonth) + "&location_id=" + params[0];
                month--;
                if(month < 1){ month = 12;
                year--;}
                new Print().printUrl("YEAR STATS ::: " + url);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            HttpGet jParser = new HttpGet();

                            String json = jParser.getJSONFromUrl(url);

                            int count = 0;
                            while(json == null && count < 3){
                                json = jParser.getJSONFromUrl(url);
                                count++;
                            }

                            if(new JSONArray(json).length() == 0){
                                System.out.println("Crimes for month : " + innerMonth + " ammount of : " + new JSONArray(json).length());
                                crimeYearStats.updateCrimes(new ArrayList(), innerYear, innerMonth);
                            } else {
                                System.out.println("Crimes for month : " + innerMonth + " ammount of : " + new JSONArray(json).length());
                                ObjectMapper mapper = new ObjectMapper();

                                List<Crime> monthCrimes = new ArrayList<>();
                                try {
                                    monthCrimes = mapper.readValue(json, new TypeReference<List<Crime>>() {
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                crimeYearStats.updateCrimes(monthCrimes, innerYear, innerMonth);
                            }

                        }catch (Exception e){e.printStackTrace();
                            crimeYearStats.updateCrimes(new ArrayList(), innerYear, innerMonth);}
                    }
                };
                thread.start();
            }

            while (crimeYearStats.getCrimes().size() != 12){}
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        ((YearStats)context).updateBarChat();
    }

    private String getMonthAsString(Integer month){
        String monthString = month.toString();
        if(monthString.length() == 1)monthString = "0" + month;
        return monthString;
    }
}