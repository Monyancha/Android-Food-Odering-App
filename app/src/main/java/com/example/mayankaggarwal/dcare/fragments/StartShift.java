package com.example.mayankaggarwal.dcare.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.Details;
import com.example.mayankaggarwal.dcare.adapter.RVCheckItems;
import com.example.mayankaggarwal.dcare.adapter.RVStartShift;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.android.gms.games.internal.GamesLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartShift extends Fragment {

    Button proceed;
    CheckBox rememberBox;
    Fragment fragment;
    public static RecyclerView recyclerView;
    private String latitude;
    private String longitude;
    private static final int REQUEST_PERMISSION = 1;


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
        View view = inflater.inflate(R.layout.fragment_start_shift, container, false);
        getCurrentLocation();
        proceed = (Button) view.findViewById(R.id.proceedbutton);
        rememberBox = (CheckBox) view.findViewById(R.id.remember);
        recyclerView = (RecyclerView) view.findViewById(R.id.shiftrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchVendor();
        proceed.setEnabled(true);
        rememberBox.setEnabled(true);
        setListener();
        return view;
    }

    private void fetchVendor() {
        Data.fetchStartShift(getActivity(), new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Log.d("tagg", "success");
                recyclerView.setAdapter(new RVStartShift(getActivity()));
            }

            @Override
            public void onFailure() {
                Globals.showFailAlert(getActivity(), "Error fetching");
            }
        });
    }

    private void setListener() {
        proceed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
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
                final String vendor_id;
                String checkItems_id = null;
                vendor_id = RVStartShift.vendor_id;
                for (int i = 0; i < RVCheckItems.checkedItems.size(); i++) {
                    if (i == 0) {
                        checkItems_id = RVCheckItems.checkedItems.get(i);
                    } else {
                        checkItems_id = checkItems_id + "," + RVCheckItems.checkedItems.get(i);
                    }
                }

                Log.d("tagg", Prefs.getPrefs("wpr_token", getActivity()));
                Log.d("tagg", Prefs.getPrefs("crewid", getActivity()));
                Log.d("tagg", vendor_id);
                Log.d("tagg", checkItems_id);

                if (vendor_id != null && checkItems_id != null) {
                    final String finalCheckItems_id = checkItems_id;
                    if(Globals.lat!=null && Globals.lng!=null){
                        Data.crewShiftStartEnd(getActivity(), vendor_id, checkItems_id, "start", Globals.lat, Globals.lng, new Data.UpdateCallback() {
                            @Override
                            public void onUpdate() {
                                Prefs.setPrefs("shiftStarted", "1", getActivity());
                                Prefs.setPrefs("vendor_id_selected", vendor_id, getActivity());
                                Prefs.setPrefs("vendor_id_name", RVStartShift.vendor_name, getActivity());
                                Prefs.setPrefs("activity_list_selected", finalCheckItems_id, getActivity());
                                fragment = StartedShift.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, fragment);
                                transaction.commit();
                            }
                            @Override
                            public void onFailure() {
                                Globals.showFailAlert(getActivity(), "Error Starting Shift!");
                            }
                        });
                    }else {
                        Globals.errorRes="Not able to fetch location";
                        Globals.showFailAlert(getActivity(), "Error Starting Shift!");
                    }
                }


            }
        });

        rememberBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Prefs.setPrefs("remember_default","1",getActivity());
                }else {
                    Prefs.setPrefs("remember_default","0",getActivity());
                }
            }
        });

    }

//    public void getCurrentLocation() {
//        LocationManager locationManager;
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
////                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                latitude = String.valueOf(location.getLatitude());
//                longitude = String.valueOf(location.getLongitude());
////                Log.d("tagg","lat:"+latitude);
////                Log.d("tagg","lng:"+longitude);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSION);
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//    }

    public void getCurrentLocation() {
        final LocationManager locationManager;
        LocationListener locationListener = null;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
            return;
        }


        final LocationListener finalLocationListener = locationListener;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location == null) {
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, finalLocationListener);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, finalLocationListener);
                    }
                } else {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                    Globals.lat = String.valueOf(location.getLatitude());
                    Globals.lng = String.valueOf(location.getLongitude());
                }
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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

}
