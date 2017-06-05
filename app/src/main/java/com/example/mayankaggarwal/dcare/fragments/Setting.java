package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mayankaggarwal.dcare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment {

    public LinearLayout editprofile,editshift;
    public Fragment fragment=null;

    public static Setting newInstance() {
        Setting fragment = new Setting();
        return fragment;
    }

    public Setting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_setting, container, false);
        editprofile=(LinearLayout)view.findViewById(R.id.editprofile);
        editshift=(LinearLayout)view.findViewById(R.id.editshift);
        settingListener();
        return view;
    }

    private void settingListener() {

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = EditProfileFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();
            }
        });

        editshift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = StartShift.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();
            }
        });
    }

}
