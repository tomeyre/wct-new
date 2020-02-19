package com.example.wct;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wct.asynctasks.GetUKCrimeByYear;
import com.example.wct.fragments.BarChartFragment;
import com.example.wct.fragments.StreetTotalFragmentWithColor;
import com.example.wct.pojo.singleton.CrimeYearStats;

/**
 * Created by thomaseyre on 19/07/2018.
 */

public class YearStats extends AppCompatActivity {

    private Integer id;
    private CrimeYearStats crimeYearStats = CrimeYearStats.getInstance();

    private StreetTotalFragmentWithColor streetTotalFragmentWithColor;
    private BarChartFragment barChartFragment;
    public TextView streetName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        setContentView(R.layout.yearly_crime_stats);
        crimeYearStats.clear();

        final FragmentManager fm = getSupportFragmentManager();
        streetTotalFragmentWithColor = new StreetTotalFragmentWithColor();
        fm.beginTransaction().add(R.id.streetYearTotalsFrag, streetTotalFragmentWithColor).commit();

        new GetUKCrimeByYear(YearStats.this).execute(id);

        final FragmentManager fm2 = getSupportFragmentManager();
        barChartFragment = new BarChartFragment();
        fm2.beginTransaction().add(R.id.barChartFragYear, barChartFragment).commit();

        streetName = findViewById(R.id.streetName);
    }

    public void updateBarChat(){
        barChartFragment.customBarChartForTheYear();
    }

    public void updateStreetName(String name){
        streetName.setText(name);
    }

    public void updateCrimeTotals(){
        streetTotalFragmentWithColor.updateCrimeTotals();
    }
}