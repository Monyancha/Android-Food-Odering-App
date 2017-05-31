package com.example.mayankaggarwal.dcare.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.adapter.RVCountryListAdapter;

public class SearchCountry extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchEdit;
    public RVCountryListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);

        initialize();
    }

    private void initialize() {
        recyclerView=(RecyclerView)findViewById(R.id.countrylist);
        searchEdit=(EditText)findViewById(R.id.searchbox);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter=new RVCountryListAdapter(this);
        recyclerView.setAdapter(adapter);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
