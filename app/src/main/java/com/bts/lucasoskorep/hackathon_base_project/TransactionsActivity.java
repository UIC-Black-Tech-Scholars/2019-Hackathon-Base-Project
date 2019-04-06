package com.bts.lucasoskorep.hackathon_base_project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsActivity extends AppCompatActivity {

    ArrayList<String> transactionsarray;
    ArrayAdapter<String> adapter;
    private static AppDatabase appDatabase;

    @BindView(R.id.ListView)
    ListView list;

    @BindView(R.id.button3)
    Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        ButterKnife.bind(this);

        transactionsarray = new ArrayList<String>();


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TransactionsActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        setup();
        populate();

    }

    public void setup(){

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                transactionsarray);
        list.setAdapter(adapter);

    }

    public void populate(){
        appDatabase = AppDatabase.getAppDatabase(this);

        for(Entries entry: appDatabase.entriesDao().getAll()){
            transactionsarray.add("ID: " + entry.getID() + " - " + entry.getAmount());
            adapter.notifyDataSetChanged();
        }

        transactionsarray.add("Test string");
        adapter.notifyDataSetChanged();

    }
}
