package com.example.wct.fragments;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wct.MapsActivity;
import com.example.wct.R;
import com.example.wct.asynctasks.SearchLookup;
import com.example.wct.pojo.Filter;
import com.example.wct.util.AnimationUtil;

import static com.example.wct.util.ScreenUtils.convertDpToPixel;
import static com.example.wct.util.ScreenUtils.getScreenWidth;

public class FilterFragment extends Fragment implements Switch.OnCheckedChangeListener {

    private Switch switchASB;
    private Switch switchPO;
    private Switch switchBT;
    private Switch switchOT;
    private Switch switchB;
    private Switch switchR;
    private Switch switchS;
    private Switch switchTFTP;
    private Switch switchCDA;
    private Switch switchD;
    private Switch switchPOW;
    private Switch switchVC;
    private Switch switchVIC;
    private Switch switchOC;
    private Button filterBtn;
    private Button resetBtn;
    private Button searchBtn;
    private Filter filter = Filter.getInstance();
    private AnimationUtil animationUtil;
    private RelativeLayout filterRl;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment, viewGroup, false);

        switchASB = view.findViewById(R.id.switchASB);
        switchPO = view.findViewById(R.id.switchPO);
        switchBT = view.findViewById(R.id.switchBT);
        switchOT = view.findViewById(R.id.switchOT);
        switchB = view.findViewById(R.id.switchB);
        switchR = view.findViewById(R.id.switchR);
        switchS = view.findViewById(R.id.switchS);
        switchTFTP = view.findViewById(R.id.switchTFTP);
        switchCDA = view.findViewById(R.id.switchCDA);
        switchD = view.findViewById(R.id.switchD);
        switchPOW = view.findViewById(R.id.switchPOW);
        switchVC = view.findViewById(R.id.switchVC);
        switchVIC = view.findViewById(R.id.switchVIC);
        switchOC = view.findViewById(R.id.switchOC);

        filterRl = view.findViewById(R.id.filterRl);

        filterBtn = view.findViewById(R.id.filterBtn);
        resetBtn = view.findViewById(R.id.resetBtn);
        searchBtn = view.findViewById(R.id.searchBtn);

        animationUtil = new AnimationUtil();

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MapsActivity)getActivity()).update();
                shrinkMenu();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                ((MapsActivity)getActivity()).update();
                shrinkMenu();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SearchLookup(getActivity()).execute("london");
                shrinkMenu();
            }
        });

        switchASB.setOnCheckedChangeListener(this);
        switchPO.setOnCheckedChangeListener(this);
        switchBT.setOnCheckedChangeListener(this);
        switchOT.setOnCheckedChangeListener(this);
        switchB.setOnCheckedChangeListener(this);
        switchR.setOnCheckedChangeListener(this);
        switchS.setOnCheckedChangeListener(this);
        switchTFTP.setOnCheckedChangeListener(this);
        switchCDA.setOnCheckedChangeListener(this);
        switchD.setOnCheckedChangeListener(this);
        switchPOW.setOnCheckedChangeListener(this);
        switchVC.setOnCheckedChangeListener(this);
        switchVIC.setOnCheckedChangeListener(this);
        switchOC.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switchASB:
                filter.setType("anti-social-behaviour",b);
                break;
            case R.id.switchPO:
                filter.setType("public-order",b);
                break;
            case R.id.switchBT:
                filter.setType("bicycle-theft",b);
                break;
            case R.id.switchOT:
                filter.setType("other-theft",b);
                break;
            case R.id.switchB:
                filter.setType("burglary",b);
                break;
            case R.id.switchR:
                filter.setType("robbery",b);
                break;
            case R.id.switchS:
                filter.setType("shoplifting",b);
                break;
            case R.id.switchTFTP:
                filter.setType("theft-from-the-person",b);
                break;
            case R.id.switchCDA:
                filter.setType("criminal-damage-arson",b);
                break;
            case R.id.switchD:
                filter.setType("drugs",b);
                break;
            case R.id.switchPOW:
                filter.setType("possession-of-weapons",b);
                break;
            case R.id.switchVC:
                filter.setType("vehicle-crime",b);
                break;
            case R.id.switchVIC:
                filter.setType("violent-crime",b);
                break;
            case R.id.switchOC:
                filter.setType("other-crime",b);
                break;
        }
    }

    private void reset(){
        switchASB.setChecked(true);
        switchPO.setChecked(true);
        switchBT.setChecked(true);
        switchOT.setChecked(true);
        switchB.setChecked(true);
        switchR.setChecked(true);
        switchS.setChecked(true);
        switchTFTP.setChecked(true);
        switchCDA.setChecked(true);
        switchD.setChecked(true);
        switchPOW.setChecked(true);
        switchVC.setChecked(true);
        switchVIC.setChecked(true);
        switchOC.setChecked(true);
        filter.resetFilter();
    }

    public RelativeLayout getFilterR1(){
        return filterRl;
    }

    public void setFilterR1Visible(){
        filterRl.setVisibility(View.VISIBLE);
    }

    public void setFilterR1Invisible(){
        filterRl.setVisibility(View.GONE);
    }

    public Boolean isFilterR1Visible(){
        return filterRl.getVisibility() == View.VISIBLE;
    }

    private void shrinkMenu(){
        animationUtil.shrink(((MapsActivity) getActivity()).getBurgerMenu(), getActivity());
        filterRl.setVisibility(View.GONE);
    }
}
