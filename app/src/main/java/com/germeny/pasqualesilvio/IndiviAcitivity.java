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
    ImageView backBtn, stateimage;
    RecyclerView indiviListview;

    private List<IndiviModel> IndiviList = new ArrayList<>();
    private IndiviAdapter IAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indivi);

        getSupportActionBar().hide();

        indiviListview = (RecyclerView) findViewById(R.id.indiviListview);
        menuBtn = (Button) findViewById(R.id.indiviMenu);
        indiviSettingBtn = (Button) findViewById(R.id.indivisettingBtn);
        backBtn = (ImageView) findViewById(R.id.indiviBackBtn);
        indiviSwitch = (SwitchMaterial) findViewById(R.id.indiviSwitch);
        stateimage = (ImageView)findViewById(R.id.indiviFire);

        indiviSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    stateimage.setImageResource(R.drawable.fireimage);
                }else {
                    stateimage.setImageResource(R.drawable.snow);
                }
            }
        });

        IAdapter = new IndiviAdapter(IndiviList);
        LinearLayoutManager HLayoutManager = new LinearLayoutManager(this);
        HLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        indiviListview.setLayoutManager(HLayoutManager);
        indiviListview.setItemAnimator(new DefaultItemAnimator());
        indiviListview.setAdapter(IAdapter);
        preparecardData();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IndiviAcitivity.this, SystemActivity.class);
                startActivity(i);
            }
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
