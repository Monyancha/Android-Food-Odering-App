package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.utils.OrderAlerts;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order, container, false);
        OrderAlerts.showfeedbackAlert(getActivity());
        return view;
    }

}
