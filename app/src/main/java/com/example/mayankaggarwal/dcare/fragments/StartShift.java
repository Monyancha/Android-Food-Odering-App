package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.mayankaggarwal.dcare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartShift extends Fragment {

    Button proceed;
    CheckBox rememberBox;
    Fragment fragment;


    public static StartShift newInstance() {
        StartShift fragment = new StartShift();
        return fragment;
    }


    public StartShift() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_start_shift, container, false);
        proceed=(Button)view.findViewById(R.id.proceedbutton);
        rememberBox=(CheckBox)view.findViewById(R.id.remember);
        proceed.setEnabled(false);
        rememberBox.setEnabled(false);
        setListener();
        return view;
    }

    private void setListener() {

        proceed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        proceed.setBackgroundResource(R.drawable.round_solid_blue);
                        proceed.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        proceed.setBackgroundResource(R.drawable.round_shape_border_grey);
                        proceed.setTextColor(getResources().getColor(R.color.themegrey));
                        return false;
                }
                return false;
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment= StartedShift.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();
            }
        });

        rememberBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    proceed.setEnabled(true);
                }else {
                    proceed.setEnabled(false);
                }
            }
        });

    }

}
