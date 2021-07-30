package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieShow extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        PieChart pieChart=findViewById(R.id.pieChart);

        ArrayList<PieEntry> pieEntries=new ArrayList<>();

        pieEntries.add(new PieEntry((float) 10.2,"LED"));
        pieEntries.add(new PieEntry((float) 15.7,"CFL"));
        pieEntries.add(new PieEntry((float) 11.3,"Router"));
        pieEntries.add(new PieEntry((float) 24.1,"Fan"));
        pieEntries.add(new PieEntry((float) 8.0,"Other"));

        PieDataSet pieDataSet=new PieDataSet(pieEntries,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);


        PieData pieData=new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Daily usage\n of each device in\nWatts per hour");
        pieChart.setCenterTextSize(18f);
        pieChart.getLegend().setTextSize(16f);
        pieChart.setEntryLabelTextSize(18f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animateY(2000);
    }
}