package com.germeny.pasqualesilvio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseDevConfig;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.model.IndiviDataResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;
import com.germeny.pasqualesilvio.utils.TcpClient;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class DeviceConfigActivity extends AppCompatActivity {

    Button btnSave;
    LabeledSwitch swSystem, swSector, swChrono;
    EditText edPolling;
    TextView tvTitle;
    ImageView backBtn;

    TcpClient mTcpClient;
    boolean isHeater;

    AsyncTask<String, String, TcpClient> ct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_config);

        getSupportActionBar().hide();

        tvTitle = findViewById(R.id.tvTitle);
        swSystem = findViewById(R.id.swSystem);
        swSector = findViewById(R.id.swSector);
        swChrono = findViewById(R.id.swChrono);
        edPolling = findViewById(R.id.edPolling);
        btnSave = findViewById(R.id.btnSave);
        backBtn = findViewById(R.id.backBtn);

        tvTitle.setText(getIntent().getStringExtra("title"));

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();

        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponseDevConfig<IndiviDataResponse>> call = service.getIndiviData(getIntent().getStringExtra("device_id"));

        call.enqueue(new Callback<BaseResponseDevConfig<IndiviDataResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseDevConfig<IndiviDataResponse>> call, Response<BaseResponseDevConfig<IndiviDataResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        if(!response.body().getData().isEmpty()){
                           IndiviDataResponse data = response.body().getData().get(0);
                           isHeater = data.isIsHeater();
                            swSystem.setOn(data.isIsOn());
                            swSector.setOn(data.getNSectors() == 2);
                            swChrono.setOn(data.isIsChrono());
                            edPolling.setText(String.valueOf(data.getPollingTime()));
                        }
                    } else {
                        Toast.makeText(DeviceConfigActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(DeviceConfigActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(DeviceConfigActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<BaseResponseDevConfig<IndiviDataResponse>> call, Throwable t) {
                Log.e("asdx", t.getMessage());
                Toast.makeText(DeviceConfigActivity.this, "May be network error 1", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

        backBtn.setOnClickListener(v -> {
            finish();
        });

        swSystem.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn){
                swSystem.setColorOn(getColor(R.color.truecolor));
            }else {
                swSystem.setColorOn(getColor(R.color.falsecolor));
            }
        });

        swSector.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn){
                swSector.setColorOn(getColor(R.color.truecolor));
            }else {
                swSector.setColorOn(getColor(R.color.blue));
            }
        });

        swChrono.setOnToggledListener((toggleableView, isOn) -> {
            if(isOn){
                swChrono.setColorOn(getColor(R.color.truecolor));
            }else {
                swChrono.setColorOn(getColor(R.color.falsecolor));
            }
        });

        btnSave.setOnClickListener(v->{
            String message = "DEVCONFIG=" + (isHeater ? "1" : "0") + "," +
                    (swSystem.isOn() ? "1" : "0") + "," +
                    (swSector.isOn() ? "2" : "1") + "," +
                    (swChrono.isOn() ? "1" : "0") + "," +
                    edPolling.getText().toString();
            pd.show();
            Call<BaseResponse> call2 = service.postDataControl(getIntent().getStringExtra("device_id"), message);

            call2.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call2, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(DeviceConfigActivity.this, "Success Post Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeviceConfigActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                        try {
                            ErrorResponse error = converter.convert(response.errorBody());
                            Toast.makeText(DeviceConfigActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(DeviceConfigActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                        }
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<BaseResponse> call2, Throwable t) {
                    Log.e("asdx", t.getMessage());
                    Toast.makeText(DeviceConfigActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTcpClient != null) {
            mTcpClient.stopClient();
        }
    }
}
