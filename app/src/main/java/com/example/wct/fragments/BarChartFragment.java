package com.example.wct.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wct.R;
import com.example.wct.YearStats;
import com.example.wct.pojo.singleton.CrimeYearStats;
import com.example.wct.util.CrimeColorUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static com.example.wct.util.ScreenUtils.convertDpToPixel;

public class BarChartFragment extends Fragment {

    private CrimeYearStats crimeYearStats = CrimeYearStats.getInstance();
    private CrimeColorUtil crimeColorUtil;

    private ArrayList<LinearLayout> barChartMonthLayouts = new ArrayList<>();
    private LinearLayout llOne;
    private LinearLayout llTwo;
    private LinearLayout llThree;
    private LinearLayout llFour;
    private LinearLayout llFive;
    private LinearLayout llSix;
    private LinearLayout llSeven;
    private LinearLayout llEight;
    private LinearLayout llNine;
    private LinearLayout llTen;
    private LinearLayout llEleven;
    private LinearLayout llTwelve;
    private LinearLayout llThirteen;
    private LinearLayout llFourteen;
    private CardView barChartStreet;
    private TextView barChartTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_layout, viewGroup, false);

        crimeColorUtil = new CrimeColorUtil(getActivity());

        llOne = view.findViewById(R.id.llOne);
        llTwo = view.findViewById(R.id.llTwo);
        llThree = view.findViewById(R.id.llThree);
        llFour = view.findViewById(R.id.llFour);
        llFive = view.findViewById(R.id.llFive);
        llSix = view.findViewById(R.id.llSix);
        llSeven = view.findViewById(R.id.llSeven);
        llEight = view.findViewById(R.id.llEight);
        llNine = view.findViewById(R.id.llNine);
        llTen = view.findViewById(R.id.llTen);
        llEleven = view.findViewById(R.id.llEleven);
        llTwelve = view.findViewById(R.id.llTwelve);
        llThirteen = view.findViewById(R.id.llThirteen);
        llFourteen = view.findViewById(R.id.llFourteen);
        barChartMonthLayouts.add(llOne);
        barChartMonthLayouts.add(llTwo);
        barChartMonthLayouts.add(llThree);
        barChartMonthLayouts.add(llFour);
        barChartMonthLayouts.add(llFive);
        barChartMonthLayouts.add(llSix);
        barChartMonthLayouts.add(llSeven);
        barChartMonthLayouts.add(llEight);
        barChartMonthLayouts.add(llNine);
        barChartMonthLayouts.add(llTen);
        barChartMonthLayouts.add(llEleven);
        barChartMonthLayouts.add(llTwelve);
        barChartMonthLayouts.add(llThirteen);
        barChartMonthLayouts.add(llFourteen);

        barChartTitle = view.findViewById(R.id.barChartTitle);

        barChartStreet = view.findViewById(R.id.barChartStreet);

        return view;
    }

    public void customBarChartForTheYear() {
            ViewGroup.LayoutParams layoutParamsBarChart = barChartStreet.getLayoutParams();
        layoutParamsBarChart.width = (int) convertDpToPixel(300, getActivity());
            barChartStreet.setLayoutParams(layoutParamsBarChart);
            barChartTitle.setText("Crime Totals By Month And Colour");

        float base = 0;
        for (int i = 0; i < crimeYearStats.getCrimes().size(); i++) {
            if (crimeYearStats.getCrimes().get(i).getCrimeMonth() != null && !crimeYearStats.getCrimes().get(i).getCrimeMonth().isEmpty() &&
                    crimeYearStats.getCrimes().get(i).getCrimeMonth().size() > base) {
                base = crimeYearStats.getCrimes().get(i).getCrimeMonth().size();
                ((YearStats)getActivity()).updateStreetName(crimeYearStats.getCrimes().get(i).getCrimeMonth().get(0).getLocation().getStreet().getName().replace("On or near ",""));
            }
        }
        base = 200 / base;
        for (int i = 0; i < crimeYearStats.getCrimes().size(); i++) {
            TextView textView = new TextView(getActivity());
            RelativeLayout.LayoutParams layoutParamsTv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (crimeYearStats.getCrimes().get(i).getCrimeMonth() == null || crimeYearStats.getCrimes().get(i).getCrimeMonth().isEmpty()) {
                textView.setText("0");
            } else {
                textView.setText("" + crimeYearStats.getCrimes().get(i).getCrimeMonth().size());
            }
            textView.setTextSize(10);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setLayoutParams(layoutParamsTv);
            barChartMonthLayouts.get(i).addView(textView);
            for (int j = 0; j < crimeYearStats.getCrimes().get(i).getCrimeMonth().size(); j++) {
                crimeYearStats.updateTotals(crimeYearStats.getCrimes().get(i).getCrimeMonth().get(j).getCategory());
                CardView cardView = new CardView(getActivity());
                int size = (int) convertDpToPixel(base, getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) convertDpToPixel(25, getActivity()),
                        size);
                layoutParams.setMargins(0, 2, 0, 0);
                cardView.setBackgroundColor(crimeColorUtil.getCrimeColor(crimeYearStats.getCrimes().get(i).getCrimeMonth().get(j).getCategory()));
                cardView.setLayoutParams(layoutParams);
                barChartMonthLayouts.get(i).addView(cardView);

            }
            textView = new TextView(getActivity());
            layoutParamsTv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsTv.setMargins(0, 2, 0, 0);
            textView.setText("" + crimeYearStats.getCrimes().get(i).getMonth());
            textView.setTextSize(8);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setLayoutParams(layoutParamsTv);
            barChartMonthLayouts.get(i).addView(textView);
            textView = new TextView(getActivity());
            layoutParamsTv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setText("" + crimeYearStats.getCrimes().get(i).getYear());
            textView.setTextSize(8);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setLayoutParams(layoutParamsTv);
            barChartMonthLayouts.get(i).addView(textView);
        }

        ((YearStats) getActivity()).updateCrimeTotals();
    }

    public void customBarChartForTheMonth() {
        barChartTitle.setText("Crime Totals");
        float base = 0;
        Integer baseDivision = 0;
        for (int i = 0; i < crimeYearStats.getCrimes().get(0).getCrimeMonth().size(); i++) {
            Iterator hmIterator = crimeYearStats.getTotals().entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                if((Integer) mapElement.getValue() > baseDivision) {
                    baseDivision = (Integer) mapElement.getValue();
                }
            }
        }
        base = 200 / baseDivision;
        Iterator hmIterator = crimeYearStats.getTotals().entrySet().iterator();
        for (int i = 0; i < crimeYearStats.getTotals().size(); i++) {
            TextView textView = new TextView(getActivity());
            RelativeLayout.LayoutParams layoutParamsTv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Map.Entry mapElement = (Map.Entry) hmIterator.next();

            textView.setText("" + mapElement.getValue());
            textView.setTextSize(10);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setLayoutParams(layoutParamsTv);
            barChartMonthLayouts.get(i).removeAllViews();
            barChartMonthLayouts.get(i).addView(textView);
            for (int j = 0; j < (Integer) mapElement.getValue(); j++) {
                CardView cardView = new CardView(getActivity());
                int size = (int) convertDpToPixel(base, getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) convertDpToPixel(25, getActivity()),
                        size);
                layoutParams.setMargins(0, 2, 0, 0);
                cardView.setBackgroundColor(crimeColorUtil.getCrimeColor(mapElement.getKey().toString()));
                cardView.setLayoutParams(layoutParams);
                barChartMonthLayouts.get(i).addView(cardView);
            }

            CardView cardView = new CardView(getActivity());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) convertDpToPixel(25, getActivity()),
                    10);
            layoutParams.setMargins(0, 0, 0, 0);
            cardView.setBackgroundColor(crimeColorUtil.getCrimeColor(mapElement.getKey().toString()));
            cardView.setLayoutParams(layoutParams);
            barChartMonthLayouts.get(i).addView(cardView);
        }
    }
}
