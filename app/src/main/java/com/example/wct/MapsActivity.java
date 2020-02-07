package com.example.wct;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.example.wct.animation.AnimateFilter;
import com.example.wct.asynctasks.GetUKCrime;
import com.example.wct.network.NetworkStateReceiver;
import com.example.wct.pojo.Crime;
import com.example.wct.pojo.Crimes;
import com.example.wct.util.MapUpdate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private CameraUpdate cameraUpdate;
    private Crimes crimes = Crimes.getInstance();


    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        new GetUKCrime(MapsActivity.this).execute();

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void update(List<List<Crime>> crimes){
        new MapUpdate().addNewMarkers(crimes, this, mMap);
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }

    //-----------------------------------------------------------code for marker selected
    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (marker.getTitle().equalsIgnoreCase("Search Location") || marker.getTitle().equalsIgnoreCase("Your Location")) {
//            hideSoftKeyboard();
//            new AnimateFilter().shrinkFilter(filterBtn, filterImage, MainActivity.this, filterHeight, filterList.getFilterList(), dateRow, btnRow,
//                    monthSpinner, yearSpinner, filterSearchBtn);
//            hidePopUpView();
//        } else {
//            hideSoftKeyboard();
//            new AnimateFilter().shrinkFilter(filterBtn, filterImage, MainActivity.this, filterHeight, filterList.getFilterList(), dateRow, btnRow,
//                    monthSpinner, yearSpinner, filterSearchBtn);
//            crimeCount.resetStreetCount();
//            if (layoutTitle.getY() == getScreenHeight(MainActivity.this) + convertDpToPixel(100, MainActivity.this)) {
//                layoutTitle.animate()
//                        .y(getScreenHeight(MainActivity.this) - layoutTitle.getHeight() - statusBarHeight)
//                        .setDuration(animationTime)
//                        .setStartDelay(0)
//                        .start();
//                layoutBody.animate()
//                        .y(getScreenHeight(MainActivity.this) - layoutTitle.getHeight() - statusBarHeight)
//                        .setDuration(animationTime)
//                        .setStartDelay(0)
//                        .start();
//
//            }
            String location = "";
//            if (filterByCrime) {
//                temp = filteredCrimes;
//            } else {
//                temp = crimeList;
//            }
            crimes.getCrimes();
            ArrayList<Marker> markerCrimes = new ArrayList<>();
            ArrayList<Counter> counts = new ArrayList<>();
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).get(0).getLongitude() == marker.getPosition().longitude &&
                        temp.get(i).get(0).getLatitude() == marker.getPosition().latitude) {
                    location = new CapitalizeString().getString(temp.get(i).get(0).getStreetName());
                    for (int j = 0; j < temp.get(i).size(); j++) {

                        markerCrimes.add(new Crimes(
                                temp.get(i).get(j).getCrimeType(),
                                temp.get(i).get(j).getDateOccur(),
                                temp.get(i).get(j).getDateReport(),
                                temp.get(i).get(j).getOutcome(), location,
                                temp.get(i).get(j).getLatitude(),
                                temp.get(i).get(j).getLongitude(),
                                temp.get(i).get(j).getWeapon(),
                                temp.get(i).get(j).getDescription(),
                                temp.get(i).get(j).getTimeOccur(),
                                temp.get(i).get(j).getTimeReport(),
                                temp.get(i).get(j).getId()));

                        if (counts.isEmpty()) {
                            counts.add(new Counter(temp.get(i).get(j).getCrimeType(), 1));
                            continue;
                        }
                        for (int k = 0; k < counts.size(); k++) {
                            if (counts.get(k).getName().equalsIgnoreCase(temp.get(i).get(j).getCrimeType())) {
                                int tempCount = counts.get(k).getCount();
                                counts.set(k, new Counter(temp.get(i).get(j).getCrimeType(), ++tempCount));
                                break;
                            }
                            if (k == counts.size() - 1) {
                                counts.add(new Counter(temp.get(i).get(j).getCrimeType(), 1));
                                break;
                            }
                        }
                    }
                    break;
                }
            }


            streetName.setText(location.trim());
            areaTotalsTitle.setText(location.trim());
            new CrimeCountList(this).sortCrimesCountStreet(counts, false, (filterByCrime || filterByMonth), MainActivity.this, true);
//        }
        return false;
    }

    private void hideSoftKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(search.getWindowToken(),
//                InputMethodManager.RESULT_UNCHANGED_SHOWN);
//        search.clearFocus();
//        System.out.println("SHOW ALLk");

    }
}
