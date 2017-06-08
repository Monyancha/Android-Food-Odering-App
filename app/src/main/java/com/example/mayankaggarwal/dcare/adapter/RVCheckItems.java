package com.example.mayankaggarwal.dcare.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.mayankaggarwal.dcare.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 07/06/17.
 */

public class RVCheckItems extends RecyclerView.Adapter<RVCheckItems.MyViewHolder> {

    private Context context;
    private JsonArray checkList;
    public static List<String> checkedItems=new ArrayList<>();

    public RVCheckItems(Activity context, JsonArray checkList) {
        this.context = context;
        this.checkList=checkList;
    }

    @Override
    public RVCheckItems.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_item_layout, parent, false);

        return new RVCheckItems.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RVCheckItems.MyViewHolder holder, final int position) {

        final JsonObject ob= this.checkList.get(position).getAsJsonObject();

        holder.checkBox.setText(ob.get("activity_name").getAsString());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedItems.add(ob.get("activity_id").getAsString());
                }else {
                    if(checkedItems!=null){
                        for(int i=0;i<checkedItems.size();i++){
                            if(checkedItems.get(i).equals(ob.get("activity_id").getAsString())){
                                checkedItems.remove(i);
                            }
                        }
                    }
                }
//                Log.d("tagg",checkedItems+"");
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkbox);
        }
    }

}
