package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankaggarwal.dcare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartedShift extends Fragment {


    public static StartedShift newInstance() {
        StartedShift fragment = new StartedShift();
        return fragment;
    }

    public StartedShift() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_started_shift, container, false);
        return view;
    }

}
