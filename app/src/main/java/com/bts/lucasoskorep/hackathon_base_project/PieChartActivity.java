package com.bts.lucasoskorep.hackathon_base_project;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Category;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;
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

public class PieChartActivity extends AppCompatActivity {
    private static final String TAG = "PieChartActivity";
    @BindView(R.id.pieChart)
    PieChart pieChart;
    private static AppDatabase appDatabase;
    private HashMap<String, Integer> mapCatExp = new HashMap<>();

    private String[] categories = {"Income", "Housing", "Food", "Clothing",
                                   "Entertainment", "Transportation", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        populateData();
        setUpPieChart();
    }

    private void setUpPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (mapCatExp.size() <= 0) {
            entries.add(new PieEntry(18.5f, "Green"));
            entries.add(new PieEntry(26.7f, "Yellow"));
            entries.add(new PieEntry(24.0f, "Red"));
            entries.add(new PieEntry(30.8f, "Blue"));

            colors.add(Color.rgb(0, 255, 0));
            colors.add(Color.rgb(255, 255, 0));
            colors.add(Color.rgb(255, 0, 0));
            colors.add(Color.rgb(0, 0, 255));
        }
        else {
            for(Entries entry: appDatabase.entriesDao().getAll()){
                entries.add(new PieEntry(entry.getAmount(), entry.getUid()));
            }

            for (int i = 0; i < categories.length; i++){
                int red = (int)(Math.random() * 256);
                int green = (int)(Math.random() * 256);
                int blue = (int)(Math.random() * 256);
                colors.add(Color.rgb(red, green, blue));
            }
        }

        PieDataSet set = new PieDataSet(entries, "Test Results");
        set.setColors(colors);

        PieData data = new PieData(set);

        pieChart.setData(data);

        pieChart.invalidate(); // refresh

    }

    private void populateData() {
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mapCatExp.size() != 0)
                    mapCatExp = new HashMap<>();

                for(Entries entry: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entry.getUid() + " " + entry.getAmount());

                    if(mapCatExp.get(entry.getUid()) == null){
                        mapCatExp.put(entry.getUid(), entry.getAmount());
                    }
                    else{
                        mapCatExp.put(entry.getUid(), mapCatExp.get(entry.getUid()) + entry.getAmount());
                    }
                }
            }
        };
        new Thread(runnable).start();
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
