package com.bts.lucasoskorep.hackathon_base_project;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;
import com.bts.lucasoskorep.hackathon_base_project.Entity.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphActivity extends AppCompatActivity {
    private static final String TAG = "GraphActivity";
    private HashMap<String, Integer> mapMonthExp = new HashMap<>();

    private String[] months = {"January", "February" , "March", "April", "May",
                               "June", "July","August", "September", "October", "November", "December"};

    private static AppDatabase appDatabase;

    @BindView(R.id.barChart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ButterKnife.bind(this);
        populateData();
        setUpBarChart();
    }


    private void setUpBarChart() {
        List<BarEntry> entries = new ArrayList<>();

        if(mapMonthExp.size() <= 0) {
            entries.add(new BarEntry(0f, 30f));
            entries.add(new BarEntry(1f, 80f));
            entries.add(new BarEntry(2f, 60f));
            entries.add(new BarEntry(3f, 50f));
            // gap of 2f
            entries.add(new BarEntry(5f, 70f));
            entries.add(new BarEntry(6f, 60f));
        }
        else{
            int count = 0;
            for(Entries entry: appDatabase.entriesDao().getAll()){
                entries.add(new BarEntry(count, entry.getAmount()));
                count += 1;
            }
        }

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
    }

    private void populateData() {
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                    if (mapMonthExp.size() != 0)
                        mapMonthExp = new HashMap<>();

                for(Entries entry: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entry.getMonth() + " " + entry.getAmount());

                    if(mapMonthExp.get(months[entry.getMonth() - 1]) == null)
                        mapMonthExp.put(months[entry.getMonth() - 1], entry.getAmount());
                    else{
                        mapMonthExp.put(months[entry.getMonth() - 1], mapMonthExp.get(months[entry.getMonth() - 1]) + entry.getAmount());
                    }
                }
            }
        };
        new Thread(runnable).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

