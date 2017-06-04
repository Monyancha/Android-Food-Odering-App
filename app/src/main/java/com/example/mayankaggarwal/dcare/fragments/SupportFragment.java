package com.example.mayankaggarwal.dcare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mayankaggarwal.dcare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {

    Button issue,ticket;

    public static SupportFragment newInstance() {
        SupportFragment fragment = new SupportFragment();
        return fragment;
    }


    public SupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_support, container, false);
        issue=(Button)view.findViewById(R.id.issueticket);
        ticket=(Button)view.findViewById(R.id.ticketbutton);

        issue.setBackgroundResource(R.drawable.rect_solid_orange);
        issue.setTextColor(getResources().getColor(R.color.white));

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issue.setBackgroundResource(R.drawable.rect_solid_orange);
                issue.setTextColor(getResources().getColor(R.color.white));
                ticket.setBackgroundResource(R.drawable.rect_border_blue);
                ticket.setTextColor(getResources().getColor(R.color.themeblue));

            }
        });
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issue.setBackgroundResource(R.drawable.rect_border_orange);
                issue.setTextColor(getResources().getColor(R.color.themered));
                ticket.setBackgroundResource(R.drawable.rect_solid_blue);
                ticket.setTextColor(getResources().getColor(R.color.white));
            }
        });
        return view;
    }

}
