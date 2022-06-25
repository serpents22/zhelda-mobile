package com.germeny.pasqualesilvio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.adapter.SystemAdapter;
import com.germeny.pasqualesilvio.model.SystemModel;
import com.germeny.pasqualesilvio.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class SystemActivity extends AppCompatActivity {
    private SystemAdapter SAdapter;

    Button okBtn, editBtn, cancelBtn, menuBtn;
    RecyclerView listview;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        getSupportActionBar().hide();
        okBtn = (Button) findViewById(R.id.systemnuovobtn);
        cancelBtn = (Button) findViewById(R.id.systemcancelbtn);
        editBtn = (Button) findViewById(R.id.systemeditbtn);
        menuBtn = (Button) findViewById(R.id.systemMenu);
        listview = (RecyclerView) findViewById(R.id.systemListview);
        backBtn = (ImageView)findViewById(R.id.systemBackBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SystemActivity.this, NuovoActivity.class);
                startActivity(i);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SystemActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        SAdapter = new SystemAdapter(new Preferences().getGateway());
        LinearLayoutManager HLayoutManager = new LinearLayoutManager(this);
        HLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(HLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.setAdapter(SAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Preferences().setSystemActivityActive(true, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Preferences().setSystemActivityActive(false, this);
    }

    //    private void preparecardData() {
//        SystemModel movie = new SystemModel("Casa", R.drawable.checkicon);
//        systemList.add(movie);
//        SystemModel movie1 = new SystemModel("Ufficio", R.drawable.warningicon);
//        systemList.add(movie1);
//        SystemModel movie2 = new SystemModel("Casa montagna", R.drawable.cancelicon);
//        systemList.add(movie2);
//    }
}
