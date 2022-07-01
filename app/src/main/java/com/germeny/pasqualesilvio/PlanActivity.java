package com.germeny.pasqualesilvio;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PlanActivity extends AppCompatActivity {
    TimePickerDialog picker;
    Button savebtn;
    ImageView backbtn;
    EditText onedaplan, oneAplan, twoDaplan, twoAplan, threeDaplan, threeAplan, fourDaplan, fourAplan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        getSupportActionBar().hide();

        savebtn = (Button) findViewById(R.id.plansavebtn);
        backbtn = (ImageView) findViewById(R.id.planBackBtn);
        onedaplan = (EditText)findViewById(R.id.onedaplan);
        oneAplan = (EditText)findViewById(R.id.oneAplan);
        twoAplan = (EditText)findViewById(R.id.twoAplan);
        twoDaplan = (EditText)findViewById(R.id.twodaplan);
        threeDaplan = (EditText)findViewById(R.id.threedaplan);
        threeAplan = (EditText)findViewById(R.id.threeAplan);
        fourDaplan = (EditText)findViewById(R.id.fourdaplan);
        fourAplan = (EditText)findViewById(R.id.foruAplan);

        onedaplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              timedrop(onedaplan);
            }
        });

        oneAplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(oneAplan);
            }
        });

        twoDaplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(twoDaplan);
            }
        });

        twoAplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(twoAplan);
            }
        });

        threeAplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(threeAplan);
            }
        });

        threeDaplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(threeDaplan);
            }
        });

        fourAplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(fourAplan);
            }
        });

        fourDaplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedrop(fourDaplan);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlanActivity.this, IndiviAcitivity.class);
                startActivity(i);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlanActivity.this, DetailAcitivity.class);
                startActivity(i);
            }
        });
    }
    public void timedrop(EditText editText){
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(PlanActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        editText.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
        picker.show();
    }
}
