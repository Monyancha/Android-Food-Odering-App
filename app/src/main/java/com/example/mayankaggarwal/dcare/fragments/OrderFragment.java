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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.adapter.RVOrders;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.OrderAlerts;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.android.gms.games.internal.GamesLog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public static RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    public static LinearLayout tripLayout;
    public static ImageView tripImage;
    private static final int REQUEST_PERMISSION = 1;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        getCurrentLocation();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclervieworder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripLayout=(LinearLayout)view.findViewById(R.id.triplayout);
        tripImage=(ImageView) view.findViewById(R.id.tripimage);
        if(Prefs.getPrefs("trip_started",getContext()).equals("0")){
            tripImage.setImageResource(R.drawable.starttrip);
        }else{
            tripImage.setImageResource(R.drawable.endtrip);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.orderrefresh);
        if(Globals.orderFetch==0){
            getOrder();
        }else {
            try {
                if (!(Prefs.getPrefs("orderJson", getActivity())).equals("notfound")) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", getActivity())).getAsJsonObject();
                    JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                   if(checkTransit(orderArray)==1){
                       tripLayout.setVisibility(View.VISIBLE);
                   }else {
                       tripLayout.setVisibility(View.GONE);
                   }
                    recyclerView.setAdapter(new RVOrders(getActivity(), orderArray));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tripImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context=getContext();
                String operation;
                if(Prefs.getPrefs("trip_started",getContext()).equals("0")){
                    operation="start";
                }else{
                    operation="end";
                }
                Data.crewTrip(getActivity(),operation, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        Prefs.setPrefs("trip_started","1",context);
                        tripImage.setImageResource(R.drawable.endtrip);
                    }

                    @Override
                    public void onFailure() {
                        Globals.showFailAlert(getActivity(), "Error starting trip!");
                    }
                });
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrder();
            }
        });
        return view;
    }

    public void getOrder() {
        if (!(Prefs.getPrefs("vendor_id_selected", getActivity()).equals("notfound")) && !(Prefs.getPrefs("shift_id", getActivity()).equals("notfound"))) {
            Data.getAllOrders(getActivity(), Prefs.getPrefs("vendor_id_selected", getActivity()), Prefs.getPrefs("shift_id", getActivity()), new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
                    Log.d("tagg", "success");
                    try {
                        if (!(Prefs.getPrefs("orderJson", getActivity())).equals("notfound")) {
                            JsonParser jsonParser = new JsonParser();
                            JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", getActivity())).getAsJsonObject();
                            JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                            if(checkTransit(orderArray)==1) {
                                tripLayout.setVisibility(View.VISIBLE);
                            }else {
                                tripLayout.setVisibility(View.GONE);
                            }
                            recyclerView.setAdapter(new RVOrders(getActivity(), orderArray));
                            getReasons();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure() {
                    swipeRefreshLayout.setRefreshing(false);
                    Globals.showFailAlert(getActivity(), "Error fetching orders!");
                }
            });
        }
    }

    public void getReasons() {
            Data.getReasons(getActivity(),new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
                    Log.d("tagg", "success reasons");
                    Globals.orderFetch=1;
                    swipeRefreshLayout.setRefreshing(false);
                }
                @Override
                public void onFailure() {
                    swipeRefreshLayout.setRefreshing(false);
                    Globals.showFailAlert(getActivity(), "Error fetching reasons!");
                }
            });
    }

    public int checkTransit(JsonArray orderArray){
        int k=0;
        for(int i=0;i<orderArray.size();i++){
            final JsonObject orderObject = orderArray.get(i).getAsJsonObject().get("order").getAsJsonObject();
            String order_code = orderObject.get("order_last_state_code").getAsString();
            if(Integer.parseInt(order_code)==Globals.ORDERSTATE_IN_TRANSIT){
                k=1;
                break;
            }
        }
        return k;
    }

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
                    Globals.lat = String.valueOf(location.getLatitude());
                    Globals.lng = String.valueOf(location.getLongitude());
                }
//                Log.d("tagg", "lat:" + latitude);
//                Log.d("tagg", "lng:" + longitude);
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
