package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.adapter.RVOrders;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.OrderAlerts;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public static RecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclervieworder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getOrder();
        return view;
    }

    public void getOrder() {
        if (!(Prefs.getPrefs("vendor_id_selected", getContext()).equals("notfound")) && !(Prefs.getPrefs("shift_id", getContext()).equals("notfound"))) {
            Data.getAllOrders(getActivity(), Prefs.getPrefs("vendor_id_selected", getContext()), Prefs.getPrefs("shift_id", getContext()), new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
                    Log.d("tagg", "success");
                    try {
                        if (!(Prefs.getPrefs("orderJson", getContext())).equals("notfound")) {
                            JsonParser jsonParser = new JsonParser();
                            JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", getContext())).getAsJsonObject();
                            JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                            recyclerView.setAdapter(new RVOrders(getActivity(), orderArray));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure() {
                    Globals.showFailAlert(getActivity(), "Error fetching orders!");
                }
            });
        }
    }

}
