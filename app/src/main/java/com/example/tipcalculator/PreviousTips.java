package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class PreviousTips extends AppCompatActivity {

    private RecyclerView rvPreviousTips;
    private ArrayList<TipsList> tipsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_tips);



        rvPreviousTips = findViewById(R.id.rvPreviousTips);


        createTips(100);


        TipsAdapter adapter = new TipsAdapter(tipsList);
        rvPreviousTips.setAdapter(adapter);

        rvPreviousTips.setLayoutManager(new LinearLayoutManager(this));

    }

    public void createTips(int tips){



        for(int i=0;i<tips;i++){
            tipsList.add(new TipsList((int)(Math.random()*100),(int)(Math.random()*10)));
        }


    }


}