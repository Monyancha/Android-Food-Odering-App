package com.example.mayankaggarwal.dcare.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.fragments.OrderFragment;
import com.example.mayankaggarwal.dcare.rest.Data;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.OrderAlerts;
import com.example.mayankaggarwal.dcare.utils.Prefs;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.vision.text.Line;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mayankaggarwal on 08/06/17.
 */

public class RVOrders extends RecyclerView.Adapter<RVOrders.MyViewHolder> {

    Activity context;
    JsonArray orderArray;


    public RVOrders(Activity context, JsonArray orderArray) {
        this.context = context;
        this.orderArray = orderArray;
    }

    @Override
    public RVOrders.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_layout, parent, false);

        return new RVOrders.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RVOrders.MyViewHolder holder, final int position) {
        final JsonObject orderObject = orderArray.get(position).getAsJsonObject().get("order").getAsJsonObject();
        String order_code = orderObject.get("order_last_state_code").getAsString();

        JsonElement jsonElement = orderObject.get("order_id");
        final String order_id = getNullAsEmptyString(jsonElement);


        holder.ordername.setText("ORDR#" + order_id);

        if (Integer.parseInt(order_code) == Globals.ORDERSTATE_ASSIGNED) {
            holder.item.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.VISIBLE);
            holder.ack.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.GONE);
            holder.intrans.setVisibility(View.GONE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themered));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themered));
        } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_CREW_AKNOLEDGED) {
            holder.item.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.GONE);
            holder.ack.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.GONE);
            holder.intrans.setVisibility(View.VISIBLE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themeblue));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themeblue));
        } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_END_STATE_DELIVERED) {
            holder.item.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.GONE);
            holder.ack.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.VISIBLE);
            holder.intrans.setVisibility(View.GONE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themegrey));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themegrey));
        } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_IN_TRANSIT) {
            holder.item.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.GONE);
            holder.ack.setVisibility(View.VISIBLE);
            if(Prefs.getPrefs("trip_started",context).equals("0")){
                holder.ordercart.setVisibility(View.GONE);
            }else{
                holder.ordercart.setVisibility(View.VISIBLE);
            }
            holder.delivered.setVisibility(View.GONE);
            holder.intrans.setVisibility(View.GONE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themeblue));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themeblue));
        } else {
            holder.item.setVisibility(View.GONE);
        }


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.changeOrderState(context, order_id, "5", new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        Log.d("tagg", "success change");
                        holder.pending.setVisibility(View.GONE);
                        holder.ack.setVisibility(View.GONE);
                        holder.delivered.setVisibility(View.GONE);
                        holder.intrans.setVisibility(View.VISIBLE);
                        holder.item.setVisibility(View.VISIBLE);
                        holder.ordername.setTextColor(context.getResources().getColor(R.color.themeblue));
                        holder.line.setBackgroundColor(context.getResources().getColor(R.color.themeblue));
                    }

                    @Override
                    public void onFailure() {
                        Globals.showFailAlert(context, "Error accepting order!");
                    }
                });
            }
        });

//        JsonElement jsonElementO = orderObject.get("order_display_id");
//        String name = getNullAsEmptyString(jsonElementO);
//        if (name != null) {
//            holder.ordername.setText(name);
//        }

        JsonObject dropObject = orderArray.get(position).getAsJsonObject().get("drop_address").getAsJsonObject();
        String address = dropObject.get("house_number").getAsString() + "," + dropObject.get("street_name").getAsString() + "," +
                dropObject.get("complex_name").getAsString() + "," + dropObject.get("city").getAsString() + "," +
                dropObject.get("state").getAsString();
        holder.address.setText(address);

        holder.ordercart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAlerts.showfeedbackAlert(context, order_id);
            }
        });

        holder.cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.item.setVisibility(View.VISIBLE);
                holder.pending.setVisibility(View.GONE);
                holder.ack.setVisibility(View.VISIBLE);
                holder.delivered.setVisibility(View.GONE);
                holder.intrans.setVisibility(View.GONE);
                holder.ordername.setTextColor(context.getResources().getColor(R.color.themeblue));
                holder.line.setBackgroundColor(context.getResources().getColor(R.color.themeblue));
                holder.ordercart.setVisibility(View.GONE);
                OrderFragment.tripLayout.setVisibility(View.VISIBLE);

                if (!(Prefs.getPrefs("orderJson", context)).equals("notfound")) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject ob = jsonParser.parse(Prefs.getPrefs("orderJson", context)).getAsJsonObject();

                    JsonArray orderArray = ob.get("payload").getAsJsonObject().get("orders").getAsJsonObject().get("orders").getAsJsonArray();
                    JsonObject orderObject = orderArray.get(position).getAsJsonObject().get("order").getAsJsonObject();
                    String order_code = orderObject.get("order_last_state_code").getAsString();

                    orderObject.remove("order_last_state_code");
                    orderObject.addProperty("order_last_state_code", "7");
                    Prefs.setPrefs("orderJson", ob.toString(), context);
                }

            }
        });

//        holder.ack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Data.changeOrderState(context, order_id, String.valueOf(Globals.ORDERSTATE_IN_TRANSIT), new Data.UpdateCallback() {
//                    @Override
//                    public void onUpdate() {
//                        OrderFragment.tripImage.setImageResource(R.drawable.endtrip);
//                        Prefs.setPrefs("trip_started","1",context);
//                        Log.d("tagg","success change");
//                        holder.item.setVisibility(View.VISIBLE);
//                        holder.pending.setVisibility(View.GONE);
//                        holder.ack.setVisibility(View.VISIBLE);
//                        holder.ordercart.setVisibility(View.VISIBLE);
//                        holder.delivered.setVisibility(View.GONE);
//                        holder.intrans.setVisibility(View.GONE);
//                        holder.ordername.setTextColor(context.getResources().getColor(R.color.themeblue));
//                        holder.line.setBackgroundColor(context.getResources().getColor(R.color.themeblue));
//                    }
//                    @Override
//                    public void onFailure() {
//                        Globals.showFailAlert(context, "Error starting trip!");
//                    }
//                });
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (orderArray != null) {
            if (orderArray.size() != 0) {
                return orderArray.size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ordername, address, accept;
        ImageView cancel, ordercart, call, cycle;
        LinearLayout ack, pending, delivered, line, item, intrans;

        public MyViewHolder(View itemView) {
            super(itemView);
            ordername = (TextView) itemView.findViewById(R.id.ordername);
            address = (TextView) itemView.findViewById(R.id.orderaddress);
            accept = (TextView) itemView.findViewById(R.id.accepttext);
            cancel = (ImageView) itemView.findViewById(R.id.canceltext);
            ordercart = (ImageView) itemView.findViewById(R.id.ordercart);
            call = (ImageView) itemView.findViewById(R.id.call);
            ack = (LinearLayout) itemView.findViewById(R.id.acklayout);
            pending = (LinearLayout) itemView.findViewById(R.id.pendinglayout);
            delivered = (LinearLayout) itemView.findViewById(R.id.confirmlayout);
            line = (LinearLayout) itemView.findViewById(R.id.line);
            item = (LinearLayout) itemView.findViewById(R.id.itemlinearlayout);
            intrans = (LinearLayout) itemView.findViewById(R.id.transitlayout);
            cycle = (ImageView) itemView.findViewById(R.id.transitimage);
        }
    }

    private String getNullAsEmptyString(JsonElement jsonElement) {
        return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
    }
}
