package com.germeny.pasqualesilvio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

public class DetailAcitivity extends AppCompatActivity {
    Button onbtn, offbtn, editbtn, menubtn, zonebtn1, zonebtn2, planbtn, sanebtn;
    ImageView backbtn, minusBtn, plusbtn;
    TextView nameTxt, temperText;
    LabeledSwitch switchonoff;
    int temper = 27;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        editbtn = (Button)findViewById(R.id.deatilEditBtn);
        menubtn = (Button)findViewById(R.id.detailmemu);
        zonebtn1 = (Button)findViewById(R.id.detailzone1);
        zonebtn2 = (Button)findViewById(R.id.detailzone2);
        planbtn = (Button)findViewById(R.id.detailplanbtn);
        sanebtn = (Button)findViewById(R.id.detailsanebtn);
//        onbtn = (Button) findViewById(R.id.detailonbtn);
//        offbtn = (Button) findViewById(R.id.detailoffbtn);
        backbtn = (ImageView)findViewById(R.id.detailBackBtn);
        nameTxt = (TextView)findViewById(R.id.detailname);
        temperText = (TextView)findViewById(R.id.detailTemperature);
        minusBtn = (ImageView)findViewById(R.id.detailminus);
        plusbtn = (ImageView)findViewById(R.id.detailplus);
        switchonoff = (LabeledSwitch)findViewById(R.id.switch1);

        switchonoff.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    switchonoff.setColorOn(getColor(R.color.teal_200));
                }else {
                    switchonoff.setColorOn(getColor(R.color.falsecolor));
                }
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temper -= 1;
                temperText.setText(String.valueOf(temper));
            }
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temper += 1;
                temperText.setText(String.valueOf(temper));
            }
        });

//        onbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onbtn.setBackground(getResources().getDrawable(R.drawable.leftbuttonback));
//                offbtn.setBackground(getResources().getDrawable(R.drawable.unrightbuttonback));
//            }
//        });
//
//        offbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onbtn.setBackground(getResources().getDrawable(R.drawable.unleftbuttonback));
//                offbtn.setBackground(getResources().getDrawable(R.drawable.rightbuttonback));
//            }
//        });

        planbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailAcitivity.this, PlanActivity.class);
                startActivity(i);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailAcitivity.this, IndiviAcitivity.class);
                startActivity(i);
            }
        });
    }
}
