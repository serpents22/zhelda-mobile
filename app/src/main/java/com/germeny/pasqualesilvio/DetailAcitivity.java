package com.germeny.pasqualesilvio;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.model.IndiviDataListResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class DetailAcitivity extends AppCompatActivity {
    Button onbtn, offbtn, editbtn, menubtn, zonebtn1, zonebtn2, sanebtn;
    ImageView backbtn, minusBtn, plusbtn;
    TextView nameTxt, temperText;
    LabeledSwitch switchonoff;
    IndiviDataListResponse data;
    ProgressDialog pd2;
    double temper = 27;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        pd2 = new ProgressDialog(this);
        pd2.setMessage("loading");

        data = new Gson().fromJson(getIntent().getStringExtra("indiviData"), IndiviDataListResponse.class);

        editbtn = findViewById(R.id.deatilEditBtn);
        menubtn = findViewById(R.id.detailmemu);
        zonebtn1 = findViewById(R.id.detailzone1);
        zonebtn2 = findViewById(R.id.detailzone2);
        sanebtn = findViewById(R.id.detailsanebtn);
        backbtn = findViewById(R.id.detailBackBtn);
        nameTxt = findViewById(R.id.detailname);
        temperText = findViewById(R.id.detailTemperature);
        minusBtn = findViewById(R.id.detailminus);
        plusbtn = findViewById(R.id.detailplus);
        switchonoff = findViewById(R.id.switch1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        temperText.setText(String.valueOf(data.getIndiviDataSetResponse().getTemperature()));
        temper = data.getIndiviDataSetResponse().getTemperature();
        if(data.getIndiviDataSetResponse().getTagName() == null || data.getIndiviDataSetResponse().getTagName().isEmpty()){
            nameTxt.setText("No Name");
        }
        else {
            nameTxt.setText(data.getThName());
        }

        editbtn.setOnClickListener(v->{
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_change_name);

            EditText edName = dialog.findViewById(R.id.edName);
            Button btnSave = dialog.findViewById(R.id.btnSave);

            if(data.getThName() != null){
                edName.setText(data.getThName());
            }

            btnSave.setOnClickListener(vv->{
                if(edName.getText().toString().isEmpty()){
                    Toast.makeText(this, "Title Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd2.show();
                    RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
                    Call<BaseResponse> call3 = service.postInvidiName(data.getDeviceId(), data.getTagName(), edName.getText().toString());
                    call3.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call3, Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getCode() == 200) {
                                    nameTxt.setText(edName.getText().toString());
                                    dialog.dismiss();
                                    Toast.makeText(DetailAcitivity.this, "Success Change Name", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetailAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                        .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                                try {
                                    ErrorResponse error = converter.convert(response.errorBody());
                                    Toast.makeText(DetailAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    Toast.makeText(DetailAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                                }
                            }
                            pd2.dismiss();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call3, Throwable t) {
                            Log.e("asdx", t.getMessage());
                            Toast.makeText(DetailAcitivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                            pd2.dismiss();
                        }
                    });
                }
            });

            dialog.show();
        });

        switchonoff.setOn(data.getIndiviDataSetResponse().isIsOn());

        switchonoff.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn){
                switchonoff.setColorOn(getColor(R.color.teal_200));
            }else {
                switchonoff.setColorOn(getColor(R.color.falsecolor));
            }
        });

        minusBtn.setOnClickListener(v -> {
            temper -= 1;
            temperText.setText(String.valueOf(temper));
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temper += 1;
                temperText.setText(String.valueOf(temper));
            }
        });

        backbtn.setOnClickListener(v -> finish());

        CheckBox cbDay = findViewById(R.id.cbDay);
        CheckBox cbNight =findViewById(R.id.cbNight);

        char[] myNameChars = data.getIndiviDataSetResponse().getGroupMask().toCharArray();

        if(myNameChars.length > 0){
            cbDay.setChecked(myNameChars[0] == '1');
        }

        if(myNameChars.length > 1){
            cbNight.setChecked(myNameChars[1] == '1');
        }

        cbDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbNight.isChecked() && cbDay.isChecked()) {
                cbNight.setChecked(false);
            }
        });
        cbNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbDay.isChecked() && cbNight.isChecked()) {
                cbDay.setChecked(false);
            }
        });

        sanebtn.setOnClickListener(v->{
            pd2.show();
            RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
            String swStatus = switchonoff.isOn() ? "1" : "0";
            String day = cbDay.isChecked() ? "1" : "0";
            String night = cbNight.isChecked() ? "1" : "0";

            Call<BaseResponse> call2 = service.postDataControl(data.getDeviceId(), data.getIndiviDataSetResponse().getTagName() + "=" + swStatus + "," + temperText.getText().toString() + "," + day + night);

            call2.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call2, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(DetailAcitivity.this, "Success Post Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                        try {
                            ErrorResponse error = converter.convert(response.errorBody());
                            Toast.makeText(DetailAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(DetailAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                        }
                    }
                    pd2.dismiss();
                }

                @Override
                public void onFailure(Call<BaseResponse> call2, Throwable t) {
                    Log.e("asdx", t.getMessage());
                    Toast.makeText(DetailAcitivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    pd2.dismiss();
                }
            });
        });
    }
}
