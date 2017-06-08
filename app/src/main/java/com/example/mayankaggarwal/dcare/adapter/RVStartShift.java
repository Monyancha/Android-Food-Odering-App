package com.example.mayankaggarwal.dcare.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.MainActivity;
import com.example.mayankaggarwal.dcare.fragments.StartShift;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mayankaggarwal on 07/06/17.
 */

public class RVStartShift extends RecyclerView.Adapter<RVStartShift.MyViewHolder> {

    Activity context;
    JsonParser jsonParser;
    JsonObject jsonObject;
    JsonArray vendorArray;
    public static String vendor_id = null;
    public static String vendor_name = null;


    public RVStartShift(Activity context) {
        this.context = context;
        if ((!Prefs.getPrefs("vendors", context).equals("notfound"))) {
            jsonParser = new JsonParser();
            jsonObject = jsonParser.parse(Prefs.getPrefs("vendors", context)).getAsJsonObject();
            vendorArray = jsonObject.get("payload").getAsJsonObject().get("vendor").getAsJsonArray();
        }
    }

    @Override
    public RVStartShift.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_item_layout, parent, false);

        return new RVStartShift.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RVStartShift.MyViewHolder holder, final int position) {
        final JsonObject ob = vendorArray.get(position).getAsJsonObject();
        holder.vendor.setText(ob.get("vendor_display_name").getAsString());
        holder.beforeclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.recyclerView.setAdapter(new RVCheckItems(context, ob.get("activities").getAsJsonArray()));
                closeAll();
                holder.visibility = true;
                holder.vendorafter.setText(ob.get("vendor_display_name").getAsString());
                holder.beforeclick.setVisibility(View.GONE);
                holder.afterclick.setVisibility(View.VISIBLE);
                vendor_id = ob.get("vendor_id").getAsString();
                vendor_name=ob.get("vendor_display_name").getAsString();
                RVCheckItems.checkedItems.clear();
//                Log.d("tagg", "selected: " + vendor_id);
            }
        });

        holder.afterclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.visibility = false;
                holder.vendor.setText(ob.get("vendor_display_name").getAsString());
                holder.beforeclick.setVisibility(View.VISIBLE);
                holder.afterclick.setVisibility(View.GONE);
            }
        });
    }

    public void closeAll() {
        for (int i = 0; i < vendorArray.size(); i++) {
            RVStartShift.MyViewHolder holder = (MyViewHolder) StartShift.recyclerView.findViewHolderForLayoutPosition(i);
            holder.beforeclick.setVisibility(View.VISIBLE);
            holder.afterclick.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (vendorArray != null) {
            if (vendorArray.size() != 0) {
                return vendorArray.size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vendor, vendorafter;
        LinearLayout beforeclick, afterclick;
        Boolean visibility = false;
        RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            vendor = (TextView) itemView.findViewById(R.id.shiftname);
            vendorafter = (TextView) itemView.findViewById(R.id.aftershiftname);
            beforeclick = (LinearLayout) itemView.findViewById(R.id.normallayout);
            afterclick = (LinearLayout) itemView.findViewById(R.id.onclicklayout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.checkitems);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            afterclick.setVisibility(View.GONE);
        }
    }

}
