package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mayankaggarwal.dcare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomNavigationDrawer extends Fragment {


    public static LinearLayout nav_layout,orderlayout,reportlayout,notificationlayout,shiftlayout,settinglayout,supportlayout,logout;

    public CustomNavigationDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_custom_navigation_drawer, container, false);
        nav_layout=(LinearLayout)view.findViewById(R.id.nav_layout);
        orderlayout=(LinearLayout)view.findViewById(R.id.orderlayout);
        reportlayout=(LinearLayout)view.findViewById(R.id.reportlayout);
        notificationlayout=(LinearLayout)view.findViewById(R.id.notificationlayout);
        shiftlayout=(LinearLayout)view.findViewById(R.id.shiftlayout);
        settinglayout=(LinearLayout)view.findViewById(R.id.settinglayout);
        supportlayout=(LinearLayout)view.findViewById(R.id.supportlayout);
        logout=(LinearLayout)view.findViewById(R.id.logout);

        settingListener();

        return view;
    }

    private void settingListener() {

    }

}
