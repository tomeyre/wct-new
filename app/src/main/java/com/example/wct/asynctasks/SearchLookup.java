package com.example.wct.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.wct.MapsActivity;
import com.example.wct.R;
import com.example.wct.pojo.CurrentAddress;
import com.example.wct.pojo.GeocodeLookup;
import com.example.wct.util.HttpGet;
import com.example.wct.util.Print;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchLookup extends AsyncTask<String, String, String> {

    private CurrentAddress currentAddress = CurrentAddress.getInstance();
    private Context context;

    public SearchLookup(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "https://eu1.locationiq.com/v1/search.php?key=" + context.getResources().getString(R.string.geoKey) + "&q=" + strings[0] + "&format=json";
        String json = new HttpGet().getJSONFromUrl(url);
        new Print().printUrl(url);

        ObjectMapper objectMapper= new ObjectMapper();
        List<GeocodeLookup> geocodeLookup;
        try {
            geocodeLookup = objectMapper.readValue(json, new TypeReference<List<GeocodeLookup>>() {});
            currentAddress.setGeocodeLookup(geocodeLookup.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        ((MapsActivity)context).updateMapUsingAddress();
    }
}
