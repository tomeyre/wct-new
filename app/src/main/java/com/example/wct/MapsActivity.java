package com.example.wct;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wct.animation.AnimateFilter;
import com.example.wct.asynctasks.GetUKCrime;
import com.example.wct.asynctasks.SearchLookup;
import com.example.wct.broadcastRecievers.Network;
import com.example.wct.broadcastRecievers.NetworkStateReceiver;
import com.example.wct.pojo.Crime;
import com.example.wct.pojo.CrimeTotal;
import com.example.wct.pojo.Crimes;
import com.example.wct.pojo.CurrentAddress;
import com.example.wct.pojo.GeocodeLookup;
import com.example.wct.util.CrimeTotals;
import com.example.wct.util.GpsTrackerUtil;
import com.example.wct.util.HttpGet;
import com.example.wct.util.LatitudeAndLongitudeUtil;
import com.example.wct.util.MapUpdate;
import com.example.wct.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private CameraUpdate cameraUpdate;
    private Crimes crimes = Crimes.getInstance();
    private CurrentAddress currentAddress = CurrentAddress.getInstance();
    private CrimeTotals crimeTotals = CrimeTotals.getInstance();
    private LocationManager lm;

    private LatitudeAndLongitudeUtil latLng = LatitudeAndLongitudeUtil.getInstance();
    private GpsTrackerUtil gpsTracker;

    private CardView streetView;
    private TextView streetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        gpsTracker = new GpsTrackerUtil(this);
        latLng.setLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));

        streetView = findViewById(R.id.streetView);
        streetName = findViewById(R.id.streetName);
        lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //new SearchLookup(this).execute();

        new GetUKCrime(MapsActivity.this).execute(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void update(List<List<Crime>> crimes){
        new MapUpdate().addNewMarkers(crimes, this, mMap);
    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        updateMapUsingLatLon();
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
        List<Crime> markerCrimes = new ArrayList<>();
        for (int i = 0; i < crimes.getCrimes().size(); i++) {
            if (crimes.getCrimes().get(i).get(0).getLocation().getLongitude() == marker.getPosition().longitude &&
                    crimes.getCrimes().get(i).get(0).getLocation().getLatitude() == marker.getPosition().latitude) {
                markerCrimes = crimes.getCrimes().get(i);
                break;
            }
        }

        crimeTotals.calculate(markerCrimes);
        StringBuilder sb = new StringBuilder();
        for (CrimeTotal crimeTotal : crimeTotals.getTotals()) {
            sb.append(crimeTotal.getType() + crimeTotal.getCount() + "\n");
        }


        streetName.setText(markerCrimes.get(0).getLocation().getStreet().getName().trim() + "\n" + sb.toString());
        return false;
    }

    public void updateMapUsingAddress(){
        LatLng newLatLon = new LatLng(currentAddress.getGeocodeLookup().getLat(), currentAddress.getGeocodeLookup().getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLon, 16f));
    }

    public void updateMapUsingLatLon(){
        LatLng newLatLon = new LatLng(latLng.getLatLng().latitude, latLng.getLatLng().longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLon, 16f));
    }

    public void showPosition() {
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && new Network().isNetworkEnabled(MapsActivity.this)) {
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng.getLatLng(), 16);
            mMap.animateCamera(cameraUpdate);
        } else {
            Toast.makeText(getApplicationContext(), "Internet connection required.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
