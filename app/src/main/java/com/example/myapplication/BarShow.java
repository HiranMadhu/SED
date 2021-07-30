package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarShow extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarChart barChart;
        setContentView(R.layout.activity_bar_chart);
        barChart = (BarChart) findViewById(R.id.barChart1);

        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(0,45f));
        barEntries.add(new BarEntry(1,38f));
        barEntries.add(new BarEntry(2,50f));
        barEntries.add(new BarEntry(3,32f));
        barEntries.add(new BarEntry(4,44f));
        barEntries.add(new BarEntry(5,37f));
        barEntries.add(new BarEntry(6,49f));
        barEntries.add(new BarEntry(7,42f));
        barEntries.add(new BarEntry(8,34f));
        barEntries.add(new BarEntry(9,20f));
        BarDataSet barDataSet=new BarDataSet(barEntries,"");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(Color.BLACK);

        ArrayList<String> theDates =new ArrayList<>();
        theDates.add("Yesterday");
        theDates.add("05/19");
        theDates.add("05/18");
        theDates.add("05/17");
        theDates.add("05/16");
        theDates.add("05/15");
        theDates.add("05/14");
        theDates.add("05/13");
        theDates.add("05/12");
        theDates.add("05/11");

        BarData theData=new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(theData);
        barChart.animateY(2000);
        barChart.getLegend().setTextSize(16f);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(theDates));
        barChart.getXAxis().setTextSize(14f);
        barChart.getXAxis().setLabelCount(10);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setVisibleXRange(3,5);
        barChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Intent intent=new Intent(BarShow.this,PieShow.class);
                startActivity(intent);
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }
}

