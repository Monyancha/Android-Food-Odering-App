package com.example.mayankaggarwal.dcare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.OtpActivity;
import com.example.mayankaggarwal.dcare.utils.Countries;
import com.example.mayankaggarwal.dcare.utils.Globals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 31/05/17.
 */

public class RVCountryListAdapter extends RecyclerView.Adapter<RVCountryListAdapter.MyViewHolder> {

    private Context context;
    public List<Countries.Country> country;
    public List<Countries.Country> countryCopy;


    public RVCountryListAdapter(Activity context) {
        this.context = context;
        Countries countries=new Countries();
        country=new ArrayList<Countries.Country>();
        country=countries.makeCountryArray();
        countryCopy=country;
    }

    @Override
    public RVCountryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countryitem, parent, false);

        return new RVCountryListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVCountryListAdapter.MyViewHolder holder, final int position) {

        final Countries.Country ob= this.country.get(position);

        holder.number.setText(ob.getPhoneExtension());
        holder.code.setText(ob.getCountryCode());
        holder.flag.setText(Globals.getCountryFlag(ob.getCountryCode()));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OtpActivity.class);
                intent.putExtra("number",ob.getPhoneExtension());
                intent.putExtra("code",ob.getCountryCode());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return country.size();
    }

    public void filter(String text) {
        if(countryCopy.size()>0){
            country=new ArrayList<Countries.Country>();
            if(text.isEmpty()){
                country=countryCopy;
            }else{
                text=text.toLowerCase();
                for(int i=0;i<countryCopy.size();i++){
                    if(countryCopy.get(i).getCountryCode().toLowerCase().contains(text) || countryCopy.get(i).getPhoneExtension().toLowerCase().contains(text)){
                        country.add(countryCopy.get(i));
                    }
                }
            }
              notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView code,flag,number;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.itemlinearlayout);
            number=(TextView)itemView.findViewById(R.id.number);
            code=(TextView)itemView.findViewById(R.id.code);
            flag=(TextView)itemView.findViewById(R.id.flag);
        }
    }

}
