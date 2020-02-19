package com.example.wct;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wct.asynctasks.GetUKCrime;
import com.example.wct.broadcastRecievers.Network;
import com.example.wct.broadcastRecievers.NetworkStateReceiver;
import com.example.wct.fragments.BarChartFragment;
import com.example.wct.fragments.FilterFragment;
import com.example.wct.fragments.StreetTotalFragmentWithColor;
import com.example.wct.pojo.entity.Crime;
import com.example.wct.pojo.singleton.CrimeYearStats;
import com.example.wct.pojo.singleton.Crimes;
import com.example.wct.pojo.singleton.CurrentAddress;
import com.example.wct.pojo.singleton.Filter;
import com.example.wct.util.AnimationUtil;
import com.example.wct.util.CrimeTotals;
import com.example.wct.util.DateUtil;
import com.example.wct.util.GpsTrackerUtil;
import com.example.wct.util.LatitudeAndLongitudeUtil;
import com.example.wct.util.MapUpdate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import static com.example.wct.util.ScreenUtils.convertDpToPixel;
import static com.example.wct.util.ScreenUtils.convertPixelsToDp;
import static com.example.wct.util.ScreenUtils.getScreenHeight;
import static com.example.wct.util.ScreenUtils.getStatusBarHeight;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int STREET_NAME_HEIGHT = 85;
    private GoogleMap mMap;
    private CameraUpdate cameraUpdate;
    private Crimes crimes = Crimes.getInstance();
    private CrimeYearStats crimeYearStats = CrimeYearStats.getInstance();
    private CurrentAddress currentAddress = CurrentAddress.getInstance();
    private CrimeTotals crimeTotals = CrimeTotals.getInstance();
    private LocationManager lm;

    private int id;

    private LatitudeAndLongitudeUtil latLng = LatitudeAndLongitudeUtil.getInstance();
    private GpsTrackerUtil gpsTracker;

    private CardView streetView;
    private CardView crimeDetailsView;
    private CardView burgerMenu;
    private TextView streetName;
    private FilterFragment filterFragment;
    private StreetTotalFragmentWithColor streetMonthTotalsFrag;
    private BarChartFragment barChartFragment;

    private Button yearlyStatsBtn;

    private AnimationUtil animationUtil = new AnimationUtil();

    private Filter filter = Filter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final FragmentManager fm = getSupportFragmentManager();
        filterFragment = new FilterFragment();
        fm.beginTransaction().add(R.id.filterFragment, filterFragment).commit();

        final FragmentManager fm2 = getSupportFragmentManager();
        streetMonthTotalsFrag = new StreetTotalFragmentWithColor();
        fm2.beginTransaction().add(R.id.streetMonthTotalsFrag, streetMonthTotalsFrag).commit();

        final FragmentManager fm3 = getSupportFragmentManager();
        barChartFragment = new BarChartFragment();
        fm3.beginTransaction().add(R.id.barChartFragMonth, barChartFragment).commit();

        gpsTracker = new GpsTrackerUtil(this);
        latLng.setLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));

        streetView = findViewById(R.id.streetView);
        crimeDetailsView = findViewById(R.id.crimeDetailsView);
        burgerMenu = findViewById(R.id.burgerMenu);

        yearlyStatsBtn = findViewById(R.id.yearlyStatsBtn);

        yearlyStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ID :::::::::::::::::: " + id);
                Intent intent = new Intent(MapsActivity.this, YearStats.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        burgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterFragment.isFilterR1Visible()) {
                    animationUtil.shrinkFilter(burgerMenu, MapsActivity.this);
                    filterFragment.setFilterR1Invisible();
                } else {
                    animationUtil.expandFilter(burgerMenu, MapsActivity.this);
                    filterFragment.getFilterR1().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterFragment.setFilterR1Visible();
                        }
                    }, 300);
                    if (streetView.getHeight() > 0) {
                        animationUtil.animate(streetView, streetView.getHeight(), 0);
                    }
                }
            }
        });

        streetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (convertPixelsToDp(view.getHeight(), MapsActivity.this) == STREET_NAME_HEIGHT) {
                    animationUtil.animate(streetView, (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this), getScreenHeight(MapsActivity.this));
                    animationUtil.animate(crimeDetailsView, 0, getScreenHeight(MapsActivity.this) - (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this) -
                            getStatusBarHeight(MapsActivity.this));
                } else {
                    animationUtil.animate(streetView, getScreenHeight(MapsActivity.this), (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this));
                    animationUtil.animate(crimeDetailsView, getScreenHeight(MapsActivity.this) - (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this) -
                            getStatusBarHeight(MapsActivity.this), 0);
                }
            }
        });

        streetName = findViewById(R.id.streetName);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.getLatLng().latitude, latLng.getLatLng().longitude), 15f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (streetView.getHeight() > 0) {
                    animationUtil.animate(streetView, streetView.getHeight(), 0);
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        crimeYearStats.clear();
        List<Crime> markerCrimes = new ArrayList<>();
        for (int i = 0; i < crimes.getCrimes().size(); i++) {
            if (crimes.getCrimes().get(i).get(0).getLocation().getLongitude() == marker.getPosition().longitude &&
                    crimes.getCrimes().get(i).get(0).getLocation().getLatitude() == marker.getPosition().latitude) {
                markerCrimes = crimes.getCrimes().get(i);
                crimeYearStats.updateCrimes(crimes.getCrimes().get(i), DateUtil.getInstance().getMonth(), DateUtil.getInstance().getYear());
                id = crimes.getCrimes().get(i).get(0).getLocation().getStreet().getId();
                for (int j = 0; j < crimes.getCrimes().get(i).size(); j++) {
                    crimeYearStats.updateTotals(crimes.getCrimes().get(i).get(j).getCategory());
                }
                break;
            }
        }
        barChartFragment.customBarChartForTheMonth();
        crimeTotals.calculate(markerCrimes);


        streetName.setText(markerCrimes.get(0).getLocation().getStreet().getName().trim());
        if (streetView.getHeight() == 0) {
            animationUtil.animate(streetView, 0, (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this));
        }
        streetMonthTotalsFrag.updateCrimeTotals();
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

    @Override
    public void onBackPressed() {
        if (convertPixelsToDp(streetView.getHeight(), MapsActivity.this) > STREET_NAME_HEIGHT) {
            animationUtil.animate(streetView, getScreenHeight(MapsActivity.this), (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this));
            animationUtil.animate(crimeDetailsView, getScreenHeight(MapsActivity.this) - (int) convertDpToPixel(STREET_NAME_HEIGHT, MapsActivity.this) -
                    getStatusBarHeight(MapsActivity.this), 0);
        } else if (convertPixelsToDp(streetView.getHeight(), MapsActivity.this) == STREET_NAME_HEIGHT) {
            animationUtil.animate(streetView, streetView.getHeight(), 0);
        } else if (filterFragment.isFilterR1Visible()) {
            animationUtil.shrinkFilter(burgerMenu, MapsActivity.this);
            filterFragment.setFilterR1Invisible();
        } else {
            super.onBackPressed();
        }
    }

}
