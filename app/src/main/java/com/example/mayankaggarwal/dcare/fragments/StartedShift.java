package com.example.mayankaggarwal.dcare.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.Prefs;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartedShift extends Fragment {

    Button endShift;
    Fragment fragment;
    private String latitude;
    private String longitude;
    private static final int REQUEST_PERMISSION = 1;

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
        endShift=(Button)view.findViewById(R.id.nodialog);
        Log.d("tagg",Prefs.getPrefs("vendor_id_selected", getContext()));
        Log.d("tagg",Prefs.getPrefs("wpr_token", getContext()));
        Log.d("tagg",Prefs.getPrefs("vendor_id_selected", getContext()));
        Log.d("tagg",Prefs.getPrefs("activity_list_selected", getContext()));
        Log.d("tagg",Prefs.getPrefs("shift_id", getContext()));
        setListener();
        return view;
    }

    private void setListener() {
        endShift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        endShift.setBackgroundResource(R.drawable.round_shape_solid_invalid);
                        endShift.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    case MotionEvent.ACTION_UP:
                        endShift.setBackgroundResource(R.drawable.round_shape_border_orange);
                        endShift.setTextColor(getResources().getColor(R.color.themered));
                        return false;
                }
                return false;
            }
        });
        endShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vendor_id=Prefs.getPrefs("vendor_id_selected", getContext());
                String checkItems_id=Prefs.getPrefs("activity_list_selected", getContext());
                getCurrentLocation();
                Data.crewShiftStartEnd(getActivity(), vendor_id, checkItems_id, "end", latitude, longitude, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        Prefs.setPrefs("shiftStarted", "0", getContext());
                        fragment = ShiftFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, fragment);
                        transaction.commit();
                    }
                    @Override
                    public void onFailure() {
                        Globals.showFailAlert(getActivity(), "Error Ending Shift!");
                    }
                });
            }
        });
    }

    public void getCurrentLocation() {
        LocationManager locationManager;
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
//                Log.d("tagg","lat:"+latitude);
//                Log.d("tagg","lng:"+longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

}
