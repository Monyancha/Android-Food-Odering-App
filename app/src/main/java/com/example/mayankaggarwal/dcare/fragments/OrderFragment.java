package com.example.mayankaggarwal.dcare.fragments;


import android.Manifest;
import android.app.Activity;
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
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public static RecyclerView recyclerView;
    static SwipeRefreshLayout swipeRefreshLayout;
    public static LinearLayout tripLayout;
    public static ImageView tripImage;
    private static final int REQUEST_PERMISSION = 1;
    int inTransitOrders = 0;
    int deliveredOrdersInTrip = 0;
    Activity activity;
    Context context;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        this.activity = getActivity();
        this.context = getContext();
        getCurrentLocation();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclervieworder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripLayout = (LinearLayout) view.findViewById(R.id.triplayout);
        tripImage = (ImageView) view.findViewById(R.id.tripimage);
        Globals.mapView=false;
        if (Prefs.getPrefs("trip_started", getContext()).equals("0")) {
            tripImage.setImageResource(R.drawable.starttrip);
        } else {
            tripImage.setImageResource(R.drawable.endtrip);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.orderrefresh);
        if (Globals.orderFetch == 0) {
            getOrder(activity, context);
            getReasons(activity);
        } else {
            try {
                if (!(Prefs.getPrefs("orderJson", getActivity())).equals("notfound")) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", getActivity())).getAsJsonObject();
                    JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                    if (checkTransit(orderArray) == 1) {
                        tripLayout.setVisibility(View.VISIBLE);
                    } else {
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
                final Context context = getContext();
                final String operation;
                String operation1;
                if (Prefs.getPrefs("trip_started", getContext()).equals("0")) {
                    operation1 = "start";
                } else {
                    Log.d("tagg",Prefs.getPrefs("order_info", getActivity()));
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(Prefs.getPrefs("order_info", getActivity())).getAsJsonObject().get("order_info").getAsJsonArray();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject ob = jsonArray.get(i).getAsJsonObject();
                        if (ob.get("new_order_state_code").getAsString().equals(String.valueOf(Globals.ORDERSTATE_IN_TRANSIT))) {
                            inTransitOrders += 1;
                        } else {
                            deliveredOrdersInTrip += 1;
                        }
                    }
                    if (inTransitOrders == jsonArray.size()) {
                        operation1 = "cancel";
                    } else if (deliveredOrdersInTrip == jsonArray.size()) {
                        operation1 = "end";
                    } else {
                        operation1 = "partial";
                    }
                }
                operation = operation1;
                Data.crewTrip(activity, operation, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        if (operation.equals("start")) {
                            Prefs.setPrefs("trip_started", "1", context);
                            tripImage.setImageResource(R.drawable.endtrip);
                            callForRoadMap();
                        } else {
                            tripImage.setImageResource(R.drawable.starttrip);
                            Prefs.setPrefs("trip_started", "0", context);
                        }
                        getOrder(activity, context);
                    }

                    @Override
                    public void onFailure() {
                        Globals.showFailAlert(activity, "Error starting trip!");
                    }
                });
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrder(activity, context);
            }
        });
        return view;
    }

    private void callForRoadMap() {
        if (!(Prefs.getPrefs("orderJson", context)).equals("notfound")) {
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(Prefs.getPrefs("orderJson", context)).getAsJsonObject();
            JsonArray orderArray = obj.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
            JsonArray orderTransit=new JsonArray();
            for(int i=0;i<orderArray.size();i++){
                JsonObject ob = orderArray.get(i).getAsJsonObject();
                JsonObject orderObject = orderArray.get(i).getAsJsonObject().get("order").getAsJsonObject();
                String order_code = orderObject.get("order_last_state_code").getAsString();
                if (Integer.parseInt(order_code) == Globals.ORDERSTATE_IN_TRANSIT) {
                    orderTransit.add(ob);
                }
            }
            String address=Globals.getAddressForRoadMap(orderTransit);
            Log.d("tagg",""+address);
            Data.googleRoadMAp(activity, address, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
                    Log.d("tagg","success road map api");
                    //plot path on map
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    public static void getOrder(final Activity activity, final Context context) {
        if (!(Prefs.getPrefs("vendor_id_selected", context).equals("notfound")) && !(Prefs.getPrefs("shift_id", context).equals("notfound"))) {
            Data.getAllOrders(activity, Prefs.getPrefs("vendor_id_selected", context), Prefs.getPrefs("shift_id", context), new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
                    Log.d("tagg", "success");
                    swipeRefreshLayout.setRefreshing(false);
                    try {
                        if (!(Prefs.getPrefs("orderJson", context)).equals("notfound")) {
                            JsonParser jsonParser = new JsonParser();
                            JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", context)).getAsJsonObject();
                            JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                            if (checkTransit(orderArray) == 1) {
                                tripLayout.setVisibility(View.VISIBLE);
                            } else {
                                tripLayout.setVisibility(View.GONE);
                            }
                            checkForNullLatLng(activity, orderArray);
                            recyclerView.setAdapter(new RVOrders(activity, orderArray));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {
                    swipeRefreshLayout.setRefreshing(false);
                    Globals.showFailAlert(activity, "Error fetching orders!");
                }
            });
        }
    }

    private static void checkForNullLatLng(final Activity activity, JsonArray orderArray) {
        for (int i = 0; i < orderArray.size(); i++) {
            JsonObject orderObject = orderArray.get(i).getAsJsonObject().get("drop_address").getAsJsonObject();
            Boolean add_location_lat = orderObject.get("add_location_lat").isJsonNull();
            Boolean add_location_long = orderObject.get("add_location_long").isJsonNull();
            if (add_location_lat || add_location_long) {
                JsonObject dropObject = orderArray.get(i).getAsJsonObject().get("drop_address").getAsJsonObject();
                String google_string = Globals.getDropAddress(dropObject);
                Log.d("tagg", google_string);
                Data.googleLatLngApi(activity, google_string.replace(" ", "+"), new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        Data.updatelatlng(activity, new Data.UpdateCallback() {
                            @Override
                            public void onUpdate() {
                                //success
                            }

                            @Override
                            public void onFailure() {
                                //fail
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        //nothing
                        Globals.googleLat = null;
                        Globals.googleLng = null;
                        Globals.place_id = null;
                    }
                });
            }
        }

    }

    public static void getReasons(final Activity activity) {
        Data.getReasons(activity, new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Log.d("tagg", "success reasons");
                Globals.orderFetch = 1;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Globals.showFailAlert(activity, "Error fetching reasons!");
            }
        });
    }

    public static int checkTransit(JsonArray orderArray) {
        int k = 0;
        for (int i = 0; i < orderArray.size(); i++) {
            final JsonObject orderObject = orderArray.get(i).getAsJsonObject().get("order").getAsJsonObject();
            String order_code = orderObject.get("order_last_state_code").getAsString();
            if (Integer.parseInt(order_code) == Globals.ORDERSTATE_IN_TRANSIT) {
                k = 1;
                break;
            }
        }
        return k;
    }

    public void getCurrentLocation() {
        final LocationManager locationManager;
        LocationListener locationListener = null;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
            return;
        }


        final LocationListener finalLocationListener = locationListener;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location == null) {
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
