package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclervieworder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.orderrefresh);
        if(Globals.orderFetch==0){
            getOrder();
        }else {
            try {
                if (!(Prefs.getPrefs("orderJson", getActivity())).equals("notfound")) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", getActivity())).getAsJsonObject();
                    JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                    recyclerView.setAdapter(new RVOrders(getActivity(), orderArray));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

}
