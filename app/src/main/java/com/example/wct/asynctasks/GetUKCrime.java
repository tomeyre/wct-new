package com.example.wct.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.wct.pojo.Crime;
import com.example.wct.pojo.CurrentAddress;
import com.example.wct.util.DateUtil;
import com.example.wct.util.HttpGet;
import com.example.wct.util.LatitudeAndLongitudeUtil;
import com.example.wct.util.Print;
import com.example.wct.util.SortCrimesUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GetUKCrime extends AsyncTask<Boolean, String, Boolean> {

    private List<Crime> crimes = new ArrayList<>();
    private DateUtil dateUtil = DateUtil.getInstance();
    private SortCrimesUtil sortCrimesUtil = new SortCrimesUtil();
    private Context context;
    private CurrentAddress currentAddress = CurrentAddress.getInstance();
    private LatitudeAndLongitudeUtil latitudeAndLongitudeUtil = LatitudeAndLongitudeUtil.getInstance();

    public GetUKCrime(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        HttpGet httpGet = new HttpGet();
        String url;
        if(params[0]) {
            url = "https://data.police.uk/api/crimes-street/all-crime?lat=" + currentAddress.getGeocodeLookup().getLat() + "&lng=" + currentAddress.getGeocodeLookup().getLon() + "&date=" + dateUtil.getYear() + "-" + getMonthAsString();
        }
        else{
            url = "https://data.police.uk/api/crimes-street/all-crime?lat=" + latitudeAndLongitudeUtil.getLatLng().latitude + "&lng=" + latitudeAndLongitudeUtil.getLatLng().longitude + "&date=" + dateUtil.getYear() + "-" + getMonthAsString();
        }
        new Print().printUrl(url);

        String json = httpGet.getJSONFromUrl(url);
        if(json == null)return params[0];

        ObjectMapper mapper = new ObjectMapper();
        try {
            crimes = mapper.readValue(json, new TypeReference<List<Crime>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(Boolean param) {
        if(crimes == null || crimes.size() == 0){
            dateUtil.minusOneMonth();
            new GetUKCrime(context).execute(param);
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
