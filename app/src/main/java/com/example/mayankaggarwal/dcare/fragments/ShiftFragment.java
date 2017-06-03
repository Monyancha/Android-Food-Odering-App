package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mayankaggarwal.dcare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShiftFragment extends Fragment {

    Button startShift,laterShift;

    public static ShiftFragment newInstance() {
        ShiftFragment fragment = new ShiftFragment();
        return fragment;
    }


    public ShiftFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shift, container, false);
        startShift=(Button)view.findViewById(R.id.startshift);
        laterShift=(Button)view.findViewById(R.id.noshift);
        setListener();
        return view;
    }

    private void setListener() {
        startShift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startShift.setBackgroundResource(R.drawable.round_solid_blue);
                        startShift.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        startShift.setBackgroundResource(R.drawable.round_shape_border_blue);
                        startShift.setTextColor(getResources().getColor(R.color.themeblue));
                        return false;
                }
                return false;
            }
        });

        laterShift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        laterShift.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        laterShift.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        laterShift.setBackgroundResource(R.drawable.round_shape_border_orange);
                        laterShift.setTextColor(getResources().getColor(R.color.themered));
                        return false;
                }
                return false;
            }
        });

        startShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        laterShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
