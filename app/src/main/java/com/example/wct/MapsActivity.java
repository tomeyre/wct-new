package com.example.wct;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wct.asynctasks.GetUKCrime;
import com.example.wct.asynctasks.SearchLookup;
import com.example.wct.broadcastRecievers.Network;
import com.example.wct.broadcastRecievers.NetworkStateReceiver;
import com.example.wct.fragments.FilterFragment;
import com.example.wct.pojo.Crime;
import com.example.wct.pojo.CrimeTotal;
import com.example.wct.pojo.Crimes;
import com.example.wct.pojo.CurrentAddress;
import com.example.wct.pojo.Filter;
import com.example.wct.util.AnimationUtil;
import com.example.wct.util.CrimeTotals;
import com.example.wct.util.GpsTrackerUtil;
import com.example.wct.util.LatitudeAndLongitudeUtil;
import com.example.wct.util.MapUpdate;
import com.example.wct.util.ScreenUtils;
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

import static com.example.wct.util.ScreenUtils.convertDpToPixel;
import static com.example.wct.util.ScreenUtils.convertPixelsToDp;
import static com.example.wct.util.ScreenUtils.getScreenHeight;
import static com.example.wct.util.ScreenUtils.getScreenWidth;
import static com.example.wct.util.ScreenUtils.getStatusBarHeight;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int STREET_NAME_HEIGHT = 85;
    private GoogleMap mMap;
    private CameraUpdate cameraUpdate;
    private Crimes crimes = Crimes.getInstance();
    private CurrentAddress currentAddress = CurrentAddress.getInstance();
    private CrimeTotals crimeTotals = CrimeTotals.getInstance();
    private LocationManager lm;

    private LatitudeAndLongitudeUtil latLng = LatitudeAndLongitudeUtil.getInstance();
    private GpsTrackerUtil gpsTracker;

    private CardView streetView;
    private CardView crimeDetailsView;
    private CardView burgerMenu;
    private TextView streetName;
    private TextView crimeDetails;
    public View filterFragment;
    private FilterFragment newFragment;

    private AnimationUtil animationUtil = new AnimationUtil();

    private Filter filter = Filter.getInstance();

    private boolean mapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final FragmentManager fm = getSupportFragmentManager();
        newFragment = new FilterFragment();
        fm.beginTransaction().add(R.id.filterFragment, newFragment).commit();


        gpsTracker = new GpsTrackerUtil(this);
        latLng.setLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));

        streetView = findViewById(R.id.streetView);
        crimeDetailsView = findViewById(R.id.crimeDetailsView);
        burgerMenu = findViewById(R.id.burgerMenu);
        filterFragment = findViewById(R.id.filterFragment);

        burgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newFragment.isFilterR1Visible()){
                    animationUtil.shrink(burgerMenu, MapsActivity.this);
                    newFragment.setFilterR1Invisible();
                }else {
                    animationUtil.expand(burgerMenu, MapsActivity.this);
                    newFragment.getFilterR1().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newFragment.setFilterR1Visible();
                        }
                    }, 300);
                }
            }
        });

        streetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (convertPixelsToDp(view.getHeight(), MapsActivity.this) == STREET_NAME_HEIGHT) {
                    animationUtil.expandHeight(streetView, (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this), getScreenHeight(MapsActivity.this));
                    animationUtil.expandHeight(crimeDetailsView, 0, getScreenHeight(MapsActivity.this) - (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this) -
                            getStatusBarHeight(MapsActivity.this));
                } else {
                    animationUtil.shrinkHeight(streetView, getScreenHeight(MapsActivity.this), (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this));
                    animationUtil.expandHeight(crimeDetailsView, getScreenHeight(MapsActivity.this) - (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this) -
                            getStatusBarHeight(MapsActivity.this), 0);
                }
            }
        });

        streetName = findViewById(R.id.streetName);
        crimeDetails = findViewById(R.id.crimeDetails);
        lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void update() {
        crimes.resetCrimes();
        List<List<Crime>> filteredList = new ArrayList<>();
        for (List<Crime> crimeList : crimes.getCrimes()) {
            List<Crime> filteredInnerList = new ArrayList<>();
            for (Crime crime : crimeList) {
                if (filter.showType(crime.getCategory())) {
                    filteredInnerList.add(crime);
                }
            }
            if (filteredInnerList != null && !filteredInnerList.isEmpty())
                filteredList.add(filteredInnerList);
        }
        crimes.setCrimes(filteredList);
        new MapUpdate().addNewMarkers(this, mMap);
    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.getLatLng().latitude, latLng.getLatLng().longitude), 15f));
        //updateMapUsingLatLon();
        mapReady = true;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (streetView.getHeight() > 0) {
                    animationUtil.shrinkHeight(streetView, streetView.getHeight(), 0);
                }
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng newLatLng) {
                latLng.setLatLng(newLatLng);
                updateMapUsingLatLon();
            }
        });
        new GetUKCrime(MapsActivity.this).execute(false);
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


        streetName.setText(markerCrimes.get(0).getLocation().getStreet().getName().trim());
        crimeDetails.setText(sb.toString());
        if (streetView.getHeight() == 0) {
            animationUtil.expandHeight(streetView, 0, (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this));
        }
        return false;
    }

    public void updateMapUsingAddress() {
        LatLng newLatLon = new LatLng(currentAddress.getGeocodeLookup().getLat(), currentAddress.getGeocodeLookup().getLon());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLon, 16f));
        new GetUKCrime(MapsActivity.this).execute(true);
    }

    public void updateMapUsingLatLon() {
        LatLng newLatLon = new LatLng(latLng.getLatLng().latitude, latLng.getLatLng().longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLon, 16f));
        new GetUKCrime(MapsActivity.this).execute(false);
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

    public CardView getBurgerMenu() {
        return burgerMenu;
    }
}
