package com.example.wct.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wct.R;
import com.example.wct.pojo.CrimeTypes;
import com.example.wct.pojo.singleton.CrimeYearStats;
import com.example.wct.util.CrimeColorUtil;

public class StreetTotalFragmentWithColor extends Fragment {

    private CrimeYearStats crimeYearStats = CrimeYearStats.getInstance();
    private CrimeColorUtil crimeColorUtil;

    private TextView antiSocialBehaviour;
    private TextView publicOrder;
    private TextView bicycleTheft;
    private TextView otherTheft;
    private TextView burglary;
    private TextView robbery;
    private TextView shoplifting;
    private TextView theftFromThePerson;
    private TextView criminalDamageArson;
    private TextView drugs;
    private TextView possessionOfWeapons;
    private TextView vehicleCrime;
    private TextView violentCrime;
    private TextView otherCrime;

    private CardView colorOne;
    private CardView colorTwo;
    private CardView colorThree;
    private CardView colorFour;
    private CardView colorFive;
    private CardView colorSix;
    private CardView colorSeven;
    private CardView colorEight;
    private CardView colorNine;
    private CardView colorTen;
    private CardView colorEleven;
    private CardView colorTwelve;
    private CardView colorThirteen;
    private CardView colorFourteen;

    private CrimeTypes crimeTypes = new CrimeTypes();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.street_totals_layout_with_colors, container, false);

        crimeColorUtil = new CrimeColorUtil(getActivity());

        antiSocialBehaviour = view.findViewById(R.id.one);
        publicOrder = view.findViewById(R.id.two);
        bicycleTheft = view.findViewById(R.id.three);
        theftFromThePerson = view.findViewById(R.id.four);
        vehicleCrime = view.findViewById(R.id.five);
        otherTheft = view.findViewById(R.id.six);
        otherCrime = view.findViewById(R.id.seven);
        burglary = view.findViewById(R.id.eight);
        shoplifting = view.findViewById(R.id.nine);
        criminalDamageArson = view.findViewById(R.id.ten);
        drugs = view.findViewById(R.id.eleven);
        robbery = view.findViewById(R.id.twelve);
        possessionOfWeapons = view.findViewById(R.id.thirteen);
        violentCrime = view.findViewById(R.id.fourteen);

        colorOne = view.findViewById(R.id.colorOne);
        colorTwo = view.findViewById(R.id.colorTwo);
        colorThree = view.findViewById(R.id.colorThree);
        colorFour = view.findViewById(R.id.colorFour);
        colorFive = view.findViewById(R.id.colorFive);
        colorSix = view.findViewById(R.id.colorSix);
        colorSeven = view.findViewById(R.id.colorSeven);
        colorEight = view.findViewById(R.id.colorEight);
        colorNine = view.findViewById(R.id.colorNine);
        colorTen = view.findViewById(R.id.colorTen);
        colorEleven = view.findViewById(R.id.colorEleven);
        colorTwelve = view.findViewById(R.id.colorTwelve);
        colorThirteen = view.findViewById(R.id.colorThirteen);
        colorFourteen = view.findViewById(R.id.colorFourteen);

        return view;
    }

    public void updateCrimeTotals() {
        antiSocialBehaviour.setText("Anti Social Behaviour: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("asb")));
        colorOne.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("asb")));
        publicOrder.setText("Public Order: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("po")));
        colorTwo.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("po")));
        bicycleTheft.setText("Bicycle Theft: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("bt")));
        colorThree.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("bt")));
        theftFromThePerson.setText("Theft From The Person: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("tftp")));
        colorFour.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("tftp")));
        vehicleCrime.setText("Vehicle Crime: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("vc")));
        colorFive.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("vc")));
        otherTheft.setText("Other Theft: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("ot")));
        colorSix.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("ot")));
        otherCrime.setText("Other Crime: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("oc")));
        colorSeven.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("oc")));
        burglary.setText("Burglary: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("b")));
        colorEight.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("b")));
        shoplifting.setText("Shoplifting: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("s")));
        colorNine.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("s")));
        criminalDamageArson.setText("Criminal Damage Arson: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("cda")));
        colorTen.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("cda")));
        drugs.setText("Drugs: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("d")));
        colorEleven.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("d")));
        robbery.setText("Robbery: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("r")));
        colorTwelve.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("r")));
        possessionOfWeapons.setText("Possession Of Weapons: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("pow")));
        colorThirteen.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("pow")));
        violentCrime.setText("violent Crime: " + crimeYearStats.getTotalsForType(crimeTypes.getCrimeType("vic")));
        colorFourteen.setCardBackgroundColor(crimeColorUtil.getCrimeColor(crimeTypes.getCrimeType("vic")));

        antiSocialBehaviour.setVisibility(View.VISIBLE);
        publicOrder.setVisibility(View.VISIBLE);
        bicycleTheft.setVisibility(View.VISIBLE);
        otherTheft.setVisibility(View.VISIBLE);
        burglary.setVisibility(View.VISIBLE);
        robbery.setVisibility(View.VISIBLE);
        shoplifting.setVisibility(View.VISIBLE);
        theftFromThePerson.setVisibility(View.VISIBLE);
        criminalDamageArson.setVisibility(View.VISIBLE);
        drugs.setVisibility(View.VISIBLE);
        possessionOfWeapons.setVisibility(View.VISIBLE);
        vehicleCrime.setVisibility(View.VISIBLE);
        violentCrime.setVisibility(View.VISIBLE);
        otherCrime.setVisibility(View.VISIBLE);
        colorOne.setVisibility(View.VISIBLE);
        colorTwo.setVisibility(View.VISIBLE);
        colorThree.setVisibility(View.VISIBLE);
        colorFour.setVisibility(View.VISIBLE);
        colorFive.setVisibility(View.VISIBLE);
        colorSix.setVisibility(View.VISIBLE);
        colorSeven.setVisibility(View.VISIBLE);
        colorEight.setVisibility(View.VISIBLE);
        colorNine.setVisibility(View.VISIBLE);
        colorTen.setVisibility(View.VISIBLE);
        colorEleven.setVisibility(View.VISIBLE);
        colorTwelve.setVisibility(View.VISIBLE);
        colorThirteen.setVisibility(View.VISIBLE);
        colorFourteen.setVisibility(View.VISIBLE);
    }
}
