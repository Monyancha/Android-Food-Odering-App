package com.example.mayankaggarwal.dcare.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.utils.Globals;
import com.example.mayankaggarwal.dcare.utils.OrderAlerts;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
        JsonObject orderObject = orderArray.get(position).getAsJsonObject().get("order").getAsJsonObject();
        String order_code = orderObject.get("order_last_state_code").getAsString();
        if (Integer.parseInt(order_code) == Globals.ORDERSTATE_ASSIGNED) {
            holder.pending.setVisibility(View.VISIBLE);
            holder.ack.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.GONE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themered));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themered));
        } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_ACCEPTED) {
            holder.pending.setVisibility(View.GONE);
            holder.ack.setVisibility(View.VISIBLE);
            holder.delivered.setVisibility(View.GONE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themeblue));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themeblue));
        } else if (Integer.parseInt(order_code) == Globals.ORDERSTATE_END_STATE_DELIVERED) {
            holder.pending.setVisibility(View.GONE);
            holder.ack.setVisibility(View.GONE);
            holder.delivered.setVisibility(View.VISIBLE);
            holder.ordername.setTextColor(context.getResources().getColor(R.color.themegrey));
            holder.line.setBackgroundColor(context.getResources().getColor(R.color.themegrey));
        }

        String name = orderObject.get("order_display_id").getAsString();
        holder.ordername.setText(name);

        JsonObject dropObject = orderArray.get(position).getAsJsonObject().get("drop_address").getAsJsonObject();
        String address = dropObject.get("house_number").getAsString() + "," + dropObject.get("street_name").getAsString() + "," +
                dropObject.get("complex_name").getAsString() + "," + dropObject.get("city").getAsString() + "," +
                dropObject.get("state").getAsString();
        holder.address.setText(address);

        holder.ordercart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAlerts.showfeedbackAlert(context);
            }
        });

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
        ImageView cancel, ordercart, call;
        LinearLayout ack, pending, delivered, line;
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
            delivered = (LinearLayout) itemView.findViewById(R.id.deliverlayout);
            line = (LinearLayout) itemView.findViewById(R.id.line);
        }
    }
}
