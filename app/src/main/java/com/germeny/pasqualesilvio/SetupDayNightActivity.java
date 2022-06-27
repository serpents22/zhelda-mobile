package com.germeny.pasqualesilvio;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.adapter.SetupDayNightAdapter;
import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.DayNightResponse;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class SetupDayNightActivity extends AppCompatActivity {
    RecyclerView rvDayNight;
    Button btnSave;
    List<DayNightResponse> listResponse = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_daynight);

        rvDayNight = findViewById(R.id.rvDayNight);
        btnSave = findViewById(R.id.btnSave);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();

        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponseList<DayNightResponse>> call = service.getSetupDayNight(getIntent().getStringExtra("device_id"), getIntent().getStringExtra("type"));

        call.enqueue(new Callback<BaseResponseList<DayNightResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<DayNightResponse>> call, Response<BaseResponseList<DayNightResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        listResponse = response.body().getData();

                        SetupDayNightAdapter adapter = new SetupDayNightAdapter(SetupDayNightActivity.this, listResponse);
                        rvDayNight.setLayoutManager(new LinearLayoutManager(SetupDayNightActivity.this, LinearLayoutManager.VERTICAL, false));
                        rvDayNight.setAdapter(adapter);
                    } else {
                        Toast.makeText(SetupDayNightActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(SetupDayNightActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(SetupDayNightActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<BaseResponseList<DayNightResponse>> call, Throwable t) {
                Log.e("asdx", t.getMessage());
                Toast.makeText(SetupDayNightActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

        btnSave.setOnClickListener(v->{
            String sendData = "";
            for(int i = 0; i < listResponse.size(); i ++){
                DayNightResponse data = listResponse.get(i);
                String startTime = data.getStart();
                String stopTime = data.getStop();

                if(startTime.length() > 5){
                    startTime = startTime.substring(0,5);
                }
                if(stopTime.length() > 5){
                    stopTime = stopTime.substring(0,5);
                }

                sendData += data.getTagName() + "=" + startTime + "," + stopTime + "," + data.getWeekMask();

                if(i < listResponse.size() - 1){
                    sendData += ";";
                }
            }

            Call<BaseResponse> call2 = service.postDataControl(getIntent().getStringExtra("device_id"), sendData);
            call2.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call2, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(SetupDayNightActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SetupDayNightActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                        try {
                            ErrorResponse error = converter.convert(response.errorBody());
                            Toast.makeText(SetupDayNightActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(SetupDayNightActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                        }
                    }
                    pd.hide();
                }

                @Override
                public void onFailure(Call<BaseResponse> call2, Throwable t) {
                    Toast.makeText(SetupDayNightActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        });
    }
}