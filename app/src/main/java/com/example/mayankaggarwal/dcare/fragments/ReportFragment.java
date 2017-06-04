package com.example.mayankaggarwal.dcare.fragments;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import com.example.mayankaggarwal.dcare.R;
import com.example.mayankaggarwal.dcare.activities.Details;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements OnChartValueSelectedListener{


    PieChart mChart;

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        return fragment;
    }

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report, container, false);
        mChart = (PieChart)view.findViewById(R.id.chart1);
        mChart.setUsePercentValues(false);
        mChart.getDescription().setEnabled(false);


        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableText("AVERAGE RATING\n4.3"));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(false);

        mChart.setOnChartValueSelectedListener(this);

        setData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        mChart.getLegend().setEnabled(false);

//        Legend l = mChart.getLegend();
//        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);

        return view;
    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Drawable drawable = DrawableCompat.wrap(getResources().getDrawable(R.drawable.notselectstar));

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
        for (int i = 1; i <= count ; i++) {
            entries.add(new PieEntry(((i*10)/range),drawable,
                    getResources().getDrawable(R.drawable.selectstar)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(0f);
//        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(0f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(getResources().getColor(R.color.themeblue));
        colors.add(getResources().getColor(R.color.themegrey));
        colors.add(getResources().getColor(R.color.dark_grey));
        colors.add(getResources().getColor(R.color.themered));

        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText(String str) {

        SpannableString styledString = new SpannableString(str);
        // make the text twice as large
        styledString.setSpan(new RelativeSizeSpan(6f), 15, str.length(), 0);
        // make text color
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_grey)), 0,15, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.themeblue)), 15, str.length(), 0);
        // make text bold
        styledString.setSpan(new StyleSpan(Typeface.BOLD), 15, str.length(), 0);

        return styledString;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.d("tagg",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.d("tagg", "nothing selected");
    }
}
