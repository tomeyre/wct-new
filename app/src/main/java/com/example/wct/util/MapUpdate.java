package com.example.wct.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.wct.MapsActivity;
import com.example.wct.R;
import com.example.wct.pojo.singleton.Crimes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.List;

public class MapUpdate {

    private DateUtil dateUtil = DateUtil.getInstance();
    private ArrayList<WeightedLatLng> weightedLatLngs;
    private Crimes crimes = Crimes.getInstance();

    public void addNewMarkers(Context context, GoogleMap mMap) {
        List<Marker> markers = new ArrayList<>();
        try {
            mMap.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            markers.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        weightedLatLngs = new ArrayList<>();

        for (int i = 0; i < crimes.getCrimes().size(); i++) {
            try {
                int mapColour = crimes.getCrimes().get(i).size();
                String streetName = new StringUtil().getString(crimes.getCrimes().get(i).get(0).getLocation().getStreet().getName());

                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(((MapsActivity) context).LAYOUT_INFLATER_SERVICE);

                if (mapColour < 100) {
                    View v = inflater.inflate(R.layout.custom_map_marker, null);
                    weightedLatLngs.add(new WeightedLatLng(new LatLng(crimes.getCrimes().get(i).get(0).getLocation().getLatitude(), crimes.getCrimes().get(i).get(0).getLocation().getLongitude()), crimes.getCrimes().get(i).size()));
                    markers.add(mMap.addMarker(new MarkerOptions()
                            .title(streetName)
                            .position(new LatLng(crimes.getCrimes().get(i).get(0).getLocation().getLatitude(), crimes.getCrimes().get(i).get(0).getLocation().getLongitude()))
                            .icon(BitmapDescriptorFactory.fromBitmap(new BitmapGenerator().getMarkerBitmapFromView(mapColour, v, context)))));
                    mMap.setOnMarkerClickListener(((MapsActivity) context));
                } else {
                    View bigV = inflater.inflate(R.layout.custom_map_marker_big, null);
                    weightedLatLngs.add(new WeightedLatLng(new LatLng(crimes.getCrimes().get(i).get(0).getLocation().getLatitude(), crimes.getCrimes().get(i).get(0).getLocation().getLongitude()), 100));
                    markers.add(mMap.addMarker(new MarkerOptions()
                            .title(streetName)
                            .position(new LatLng(crimes.getCrimes().get(i).get(0).getLocation().getLatitude(), crimes.getCrimes().get(i).get(0).getLocation().getLongitude()))
                            .icon(BitmapDescriptorFactory.fromBitmap(new BitmapGenerator().getMarkerBitmapFromView(mapColour, bigV, context)))));
                    mMap.setOnMarkerClickListener(((MapsActivity) context));
                }
                Log.i("MAP COLOR ", "" + mapColour + " / MAP LOCATION " + crimes.getCrimes().get(i).get(0).getLocation().getStreet().getName() + " / " + crimes.getCrimes().get(i).get(0).getLocation().getLatitude() + crimes.getCrimes().get(i).get(0).getLocation().getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (crimes.getCrimes().isEmpty()) {
            Toast.makeText(context.getApplicationContext(), "No crimes found...",
                    Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context.getApplicationContext(), "Crime statistics for " + dateUtil.getMonthAsString() + " " + dateUtil.getYear(),
                    Toast.LENGTH_LONG).show();

        int[] colors = {
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);

//        if(weightedLatLngs != null && !weightedLatLngs.isEmpty()) {
//            // Create a heat map tile provider, passing it the latlngs of the police stations.
//            mProvider = new HeatmapTileProvider.Builder()
//                    .weightedData(weightedLatLngs)
//                    .gradient(gradient)
//                    .radius(50)
//                    .build();
//        }
//        if (dateTxt.getVisibility() == INVISIBLE) {
//            dateTxt.setVisibility(VISIBLE);
//        }
//        if (null != crimeList && !crimeList.isEmpty()) {
//            dateTxt.setText(dateUtil.getMonthAsString() + " " + dateUtil.getYear());
//        }
//        if (filterBtn.getVisibility() == INVISIBLE) {
//            filterBtn.setVisibility(VISIBLE);
//        }
//        screenEnabled=tr();
    }

}
