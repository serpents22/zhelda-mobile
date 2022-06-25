package com.germeny.pasqualesilvio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.adapter.IndiviAdapter;
import com.germeny.pasqualesilvio.adapter.SystemAdapter;
import com.germeny.pasqualesilvio.model.IndiviModel;
import com.germeny.pasqualesilvio.model.SystemModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class IndiviAcitivity extends AppCompatActivity {
    SwitchMaterial indiviSwitch;
    Button menuBtn, indiviSettingBtn;
    ImageView backBtn, stateimage, btnDay, btnNight;
    RecyclerView indiviListview;

    private final List<IndiviModel> IndiviList = new ArrayList<>();
    private IndiviAdapter IAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indivi);

        getSupportActionBar().hide();

        indiviListview = findViewById(R.id.indiviListview);
        menuBtn = findViewById(R.id.indiviMenu);
        indiviSettingBtn = findViewById(R.id.indivisettingBtn);
        backBtn = findViewById(R.id.indiviBackBtn);
        indiviSwitch = findViewById(R.id.indiviSwitch);
        stateimage = findViewById(R.id.indiviFire);
        btnDay = findViewById(R.id.btnDay);
        btnNight = findViewById(R.id.btnNight);

        indiviSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                stateimage.setImageResource(R.drawable.fireimage);
            }else {
                stateimage.setImageResource(R.drawable.snow);
            }
        });

        IAdapter = new IndiviAdapter(IndiviList);
        LinearLayoutManager HLayoutManager = new LinearLayoutManager(this);
        HLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        indiviListview.setLayoutManager(HLayoutManager);
        indiviListview.setItemAnimator(new DefaultItemAnimator());
        indiviListview.setAdapter(IAdapter);
        preparecardData();

        backBtn.setOnClickListener(v -> {
            Intent i = new Intent(IndiviAcitivity.this, SystemActivity.class);
            startActivity(i);
        });

        btnDay.setOnClickListener(v-> {
            startActivity(
                    new Intent(IndiviAcitivity.this, SetupDayNightActivity.class)
                            .putExtra("device_id", getIntent().getStringExtra("device_id"))
                            .putExtra("type", "A")
            );
        });

        btnNight.setOnClickListener(v-> {
            startActivity(
                    new Intent(IndiviAcitivity.this, SetupDayNightActivity.class)
                            .putExtra("device_id", getIntent().getStringExtra("device_id"))
                            .putExtra("type", "B")
            );
        });
    }

    private void preparecardData() {
        IndiviModel movie = new IndiviModel(R.drawable.fanimg, R.drawable.clockimg, "Salone", "1", "22.3");
        IndiviList.add(movie);
        IndiviModel movie1 = new IndiviModel(R.drawable.checkicon, R.drawable.clockimg, "Camera da pranzo", "2", "21.5");
        IndiviList.add(movie1);
        IndiviModel movie2 = new IndiviModel(R.drawable.clocktouch, R.drawable.night, "Camera letto", "3", "19.7");
        IndiviList.add(movie2);
        IndiviModel movie3 = new IndiviModel(R.drawable.cancelicon, R.drawable.day, "Cucina", "4", "22.7");
        IndiviList.add(movie3);
    }
}
