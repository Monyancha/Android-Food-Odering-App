package com.example.mayankaggarwal.dcare.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.adapter.RVOrders;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    Activity activity;
    Context context;
    JsonArray orderAssigned = new JsonArray();
    JsonArray orderAcknowledged = new JsonArray();
    JsonArray orderTransit = new JsonArray();
    JsonArray orderOther = new JsonArray();
    GoogleMap map;
    MapView mapView;
    public static RecyclerView recyclerView;
    BottomSheetBehavior bottomSheetBehavior;
    CoordinatorLayout coordinatorLayout;
    LinearLayout bottom_layout;


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_map, container, false);
        context = getContext();
        activity = getActivity();
        recyclerView=(RecyclerView)view.findViewById(R.id.maporederrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        map=mapView.getMap();
        mapView.getMapAsync(this);
        try {
            if (!(Prefs.getPrefs("orderJson", getActivity())).equals("notfound")) {
                JsonParser jsonParser = new JsonParser();
                JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", getActivity())).getAsJsonObject();
                JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                recyclerView.setAdapter(new RVOrders(getActivity(), orderArray,true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottom_layout=(LinearLayout)view.findViewById(R.id.bottom_sheet_item);
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.mapcoordinate);
        bottomSheetBehavior = (BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_item)));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.onLayoutChild(coordinatorLayout, bottom_layout, ViewCompat.LAYOUT_DIRECTION_LTR);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.map = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        getMarkers();
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return true;
            }
        });

        googleMap.addPolyline(getPolylines());

    }

    private void getMarkers() {
        if (!(Prefs.getPrefs("orderJson", getActivity())).equals("notfound")) {
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(Prefs.getPrefs("orderJson", getActivity())).getAsJsonObject();
            JsonArray orderArray = obj.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
            for (int i = 0; i < orderArray.size(); i++) {
                JsonObject ob = orderArray.get(i).getAsJsonObject();
                JsonObject orderObject = orderArray.get(i).getAsJsonObject().get("order").getAsJsonObject();
                String order_code = orderObject.get("order_last_state_code").getAsString();
                if (Integer.parseInt(order_code) == Globals.ORDERSTATE_ASSIGNED) {
                    orderAssigned.add(ob);
                } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_CREW_AKNOLEDGED) {
                    orderAcknowledged.add(ob);
                } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_IN_TRANSIT) {
                    orderTransit.add(ob);
                } else {
                    orderOther.add(ob);
                }
            }
            getLatLng(orderAssigned, BitmapDescriptorFactory.fromResource(R.drawable.smallred));
            getLatLng(orderAcknowledged, BitmapDescriptorFactory.fromResource(R.drawable.smallblue));
            getLatLng(orderTransit, BitmapDescriptorFactory.fromResource(R.drawable.bigblue));
            getLatLng(orderOther, BitmapDescriptorFactory.fromResource(R.drawable.smallgray));
        }
    }

    private void getLatLng(JsonArray array, BitmapDescriptor drawable) {
        for (int i = 0; i < array.size(); i++) {
            String name = "";
            JsonObject order = array.get(i).getAsJsonObject().get("order").getAsJsonObject();
            if (!order.get("order_id").isJsonNull()) {
                name = order.get("order_id").getAsString();
            }
            JsonObject orderObject = array.get(i).getAsJsonObject().get("drop_address").getAsJsonObject();
            Boolean add_location_lat = orderObject.get("add_location_lat").isJsonNull();
            Boolean add_location_long = orderObject.get("add_location_long").isJsonNull();
            if (add_location_lat || add_location_long) {
                return;
            } else {
                String lat = orderObject.get("add_location_lat").getAsString();
                String lng = orderObject.get("add_location_long").getAsString();
                plotMarker(lat, lng, drawable, name);
            }
        }
    }

    private void plotMarker(String lat, String lng, BitmapDescriptor drawable, String name) {
        Log.d("tagg",lat+" "+lng+" "+name);
        final LatLng place = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        MarkerOptions marker = new MarkerOptions().position(place).title(name);
        marker.icon(drawable);
        this.map.addMarker(marker);
        this.map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(place)
                        .zoom(15)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }


    public PolylineOptions getPolylines() {
        List<LatLng> polylines = new ArrayList<LatLng>();
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
        List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

        if (!(Prefs.getPrefs("roadMapJson", context).equals("notfound"))) {
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(Prefs.getPrefs("roadMapJson", context)).getAsJsonObject();
            JsonArray legs = obj.get("legs").getAsJsonArray();
            for (int i = 0; i < legs.size(); i++) {
                JsonArray steps = legs.get(i).getAsJsonObject().get("steps").getAsJsonArray();
                for (int j = 0; j < steps.size(); j++) {
                    List<LatLng> list =decodePoly(steps.get(i).getAsJsonObject().get("polyline").getAsJsonObject().get("points").getAsString());
                    for (int l = 0; l < list.size(); l++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("lat",
                                Double.toString(((LatLng) list.get(l)).latitude));
                        hm.put("lng",
                                Double.toString(((LatLng) list.get(l)).longitude));
                        path.add(hm);
                    }
                }
            }
        }
        routes.add(path);


        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List<HashMap<String, String>> pathk = routes.get(i);

            for (int j = 0; j < pathk.size(); j++) {
                HashMap<String, String> point = pathk.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(15);
            polyLineOptions.color(Color.parseColor("#fe4c13"));
        }

//        googleMap.addPolyline(polyLineOptions);
        return polyLineOptions;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}

