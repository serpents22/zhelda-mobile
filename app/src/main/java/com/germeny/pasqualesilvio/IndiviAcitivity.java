package com.germeny.pasqualesilvio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.adapter.IndiviAdapter;
import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseDevConfig;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.DevChronaResponse;
import com.germeny.pasqualesilvio.model.DevStatResponse;
import com.germeny.pasqualesilvio.model.IndiviDataDevStatResponse;
import com.germeny.pasqualesilvio.model.IndiviDataListResponse;
import com.germeny.pasqualesilvio.model.IndiviDataResponse;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.model.IndiviDataSetResponse;
import com.germeny.pasqualesilvio.model.IndiviDataStatResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class IndiviAcitivity extends AppCompatActivity {
    SwitchMaterial indiviSwitch;
    Button menuBtn, indiviSettingBtn;
    ImageView backBtn, stateimage, btnDay, btnNight;
    RecyclerView indiviListview;
    IndiviDataResponse data;
    TextView tvTitle;
    List<IndiviDataListResponse> indiviDataListResponse;
    List<IndiviDataStatResponse> indiviDataStatResponse;
    List<IndiviDataSetResponse> indiviDataSetResponses;
    List<IndiviDataDevStatResponse> indiviDataDevStatResponses;
    ProgressDialog pd2;
    SwitchCompat swDay, swNight;
    DevChronaResponse dayData, nightData;
    private IndiviAdapter IAdapter;

    boolean realtimeData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indivi);

        pd2 = new ProgressDialog(this);
        pd2.setMessage("loading");

        getSupportActionBar().hide();

        indiviListview = findViewById(R.id.indiviListview);
        menuBtn = findViewById(R.id.indiviMenu);
        indiviSettingBtn = findViewById(R.id.indivisettingBtn);
        backBtn = findViewById(R.id.indiviBackBtn);
        indiviSwitch = findViewById(R.id.indiviSwitch);
        stateimage = findViewById(R.id.indiviFire);
        btnDay = findViewById(R.id.btnDay);
        btnNight = findViewById(R.id.btnNight);
        tvTitle = findViewById(R.id.tvTitle);

        swDay = findViewById(R.id.swDay);
        swNight = findViewById(R.id.swNight);

        tvTitle.setText(getIntent().getStringExtra("title"));

        indiviSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                stateimage.setImageResource(R.drawable.fireimage);
            }else {
                stateimage.setImageResource(R.drawable.snow);
            }
        });

        backBtn.setOnClickListener(v -> {
            finish();
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
                            data= response.body().getData().get(0);
                            if(data.isIsHeater()){
                                stateimage.setImageResource(R.drawable.ic_sun);
                                indiviSwitch.setChecked(true);
                            }
                            else {
                                stateimage.setImageResource(R.drawable.snow);
                                indiviSwitch.setChecked(false);
                            }
                        }

                        /*if(!response.body().getChronaForceData().isEmpty()){
                            for(int i = 0; i < response.body().getChronaForceData().size(); i++){
                                if(response.body().getChronaForceData().get(i).getTagName().equals("CHRONAFORCE")){
                                    dayData = response.body().getChronaForceData().get(i);
                                    swDay.setChecked(dayData.getValue().equals("ON"));
                                }

                                if(response.body().getChronaForceData().get(i).getTagName().equals("CHRONBFORCE")){
                                    nightData = response.body().getChronaForceData().get(i);
                                    swNight.setChecked(nightData.getValue().equals("ON"));
                                }
                            }

                            swDay.setOnCheckedChangeListener((compoundButton, b) -> {
                                if(dayData != null){
                                    if(b){
                                        postDataChron("A","ON");
                                    }
                                    else{
                                        postDataChron("A","OFF");
                                    }
                                }
                            });

                            swNight.setOnCheckedChangeListener((compoundButton, b) -> {
                                if(nightData != null){
                                    if(b){
                                        postDataChron("B","ON");
                                    }
                                    else{
                                        postDataChron("B","OFF");
                                    }
                                }
                            });
                        }*/
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<BaseResponseDevConfig<IndiviDataResponse>> call, Throwable t) {
                Log.e("asdx", t.getMessage());
                Toast.makeText(IndiviAcitivity.this, "May be network error 1", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

        Call<BaseResponseList<DevStatResponse>> calle = service.getDeviceStat(getIntent().getStringExtra("device_id"));

        calle.enqueue(new Callback<BaseResponseList<DevStatResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<DevStatResponse>> calle, Response<BaseResponseList<DevStatResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                    //------------------------------------------------------------------
                        /*if(!response.body().getData().isEmpty()){
                            if(response.body().getData().get(0).isIsAlarmed()){
                                holder.imageTitle.setImageResource(R.drawable.cancelicon);
                            }
                            else {
                                if(response.body().getData().get(0).getIsConnected().equals("ON")){
                                    holder.imageTitle.setImageResource(R.drawable.checkicon);
                                }
                                else{
                                    holder.imageTitle.setImageResource(R.drawable.ic_warning);
                                }
                            }
                        }
                        else {
                            holder.imageTitle.setImageResource(R.drawable.warningicon);
                        }*/

                        if(!response.body().getData().isEmpty()){
                            //for(int i = 0; i < response.body().getChronaForceData().size(); i++){
                                if(response.body().getData().get(0).getChrono1Stat() == 2 || response.body().getData().get(0).getChrono1Stat() == 1){
                                    //dayData = response.body().getChronaForceData().get(i);
                                    swDay.setChecked(true);
                                    btnDay.setImageResource(R.drawable.ic_day);
                                } else if (response.body().getData().get(0).getChrono1Stat() == 4 || response.body().getData().get(0).getChrono1Stat() == 3)
                                {
                                    swDay.setChecked(false);
                                    btnDay.setImageResource(R.drawable.ic_day);
                                }

                                if(response.body().getData().get(0).getChrono2Stat() == 2 || response.body().getData().get(0).getChrono2Stat() == 1){
                                    //nightData = response.body().getChronaForceData().get(i);
                                    swNight.setChecked(true);
                                    btnNight.setImageResource(R.drawable.ic_night);
                                }  else if (response.body().getData().get(0).getChrono2Stat() == 4 || response.body().getData().get(0).getChrono2Stat() == 3)
                                {
                                    swNight.setChecked(false);
                                    btnNight.setImageResource(R.drawable.ic_night);
                                }
                            //}

                            swDay.setOnCheckedChangeListener((compoundButton, b) -> {
                                //if(dayData != null){
                                    if(b){
                                        postDataChron("A","ON");
                                    }
                                    else{
                                        postDataChron("A","OFF");
                                    }
                                //}
                            });

                            swNight.setOnCheckedChangeListener((compoundButton, b) -> {
                                //if(nightData != null){
                                    if(b){
                                        postDataChron("B","ON");
                                    }
                                    else{
                                        postDataChron("B","OFF");
                                    }
                                //}
                            });
                        }


                    //------------------------------------------------------------------
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<DevStatResponse>> call, Throwable t) {
                Toast.makeText(IndiviAcitivity.this, "May be network error", Toast.LENGTH_SHORT).show();
            }
        });




        indiviSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(data != null){
                String isHeater = data.isIsHeater() ? "1" : "0";
                String isOn = data.isIsOn() ? "1" : "0";
                String isChrono = data.isIsChrono()  ? "1" : "0";
                String sendData = isHeater + "," + isOn + "," + data.getNSectors() + "," + isChrono + ",5";

                char[] myNameChars = sendData.toCharArray();
                if(b){
                    myNameChars[0] = '1';
                    stateimage.setImageResource(R.drawable.ic_sun);
                }
                else {
                    myNameChars[0] = '0';
                    stateimage.setImageResource(R.drawable.snow);
                }
                sendData = String.valueOf(myNameChars);

                Toast.makeText(this, sendData, Toast.LENGTH_SHORT).show();

                Call<BaseResponse> call2 = service.postDataControl(getIntent().getStringExtra("device_id"), "DEVCONFIG=" + sendData);

                call2.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call2, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Toast.makeText(IndiviAcitivity.this, "Success Post Data", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                    .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                            try {
                                ErrorResponse error = converter.convert(response.errorBody());
                                Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                            }
                        }
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call2, Throwable t) {
                        Log.e("asdx", t.getMessage());
                        Toast.makeText(IndiviAcitivity.this, "May be network error 2", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }
        });

        pd2.show();
    }

    private void postDataChron(String site, String ON){
        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponse> call2 = service.postDataControl(data.getDeviceId(), "CHRON" + site + "FORCE=" + ON);

        call2.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call2, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        Toast.makeText(IndiviAcitivity.this, "Success Post Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call2, Throwable t) {
                Toast.makeText(IndiviAcitivity.this, "May be network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        realtimeData = true;

        getDataList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        realtimeData = false;
    }

    private void runRealtimeData(){
        if(realtimeData){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> getDataList(), 3000);
        }
    }

    public void getDataList(){
        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);

        Call<BaseResponseList<DevStatResponse>> calle = service.getDeviceStat(getIntent().getStringExtra("device_id"));

        calle.enqueue(new Callback<BaseResponseList<DevStatResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<DevStatResponse>> calle, Response<BaseResponseList<DevStatResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        if(!response.body().getData().isEmpty()){
                            if(response.body().getData().get(0).getChrono1Stat() == 2 || response.body().getData().get(0).getChrono1Stat() == 1){
                                btnDay.setImageResource(R.drawable.ic_day);
                            } else if (response.body().getData().get(0).getChrono1Stat() == 4 || response.body().getData().get(0).getChrono1Stat() == 3)
                            {
                                btnDay.setImageResource(R.drawable.ic_day);
                            }

                            if(response.body().getData().get(0).getChrono2Stat() == 2 || response.body().getData().get(0).getChrono2Stat() == 1){
                                btnNight.setImageResource(R.drawable.ic_night);
                            }  else if (response.body().getData().get(0).getChrono2Stat() == 4 || response.body().getData().get(0).getChrono2Stat() == 3)
                            {
                                btnNight.setImageResource(R.drawable.ic_night);
                            }
                        }


                        //------------------------------------------------------------------
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<DevStatResponse>> call, Throwable t) {
                Toast.makeText(IndiviAcitivity.this, "May be network error", Toast.LENGTH_SHORT).show();
            }
        });

        Call<BaseResponseList<IndiviDataListResponse>> call3 = service.getIndiviDataList(getIntent().getStringExtra("device_id"));
        call3.enqueue(new Callback<BaseResponseList<IndiviDataListResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<IndiviDataListResponse>> call3, Response<BaseResponseList<IndiviDataListResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        if(!response.body().getData().isEmpty()){
                            indiviDataListResponse = response.body().getData();
                            getDataStat();
                        }
                        else {
                            runRealtimeData();
                        }
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        runRealtimeData();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                    runRealtimeData();
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<IndiviDataListResponse>> call3, Throwable t) {
                Log.e("asdx", t.getMessage());
                Toast.makeText(IndiviAcitivity.this, "May be network error 3", Toast.LENGTH_SHORT).show();
                pd2.dismiss();
                runRealtimeData();
            }
        });
    }

    public void getDataStat(){
        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponseList<IndiviDataStatResponse>> call = service.getIndiviDataStatList(getIntent().getStringExtra("device_id"));

        call.enqueue(new Callback<BaseResponseList<IndiviDataStatResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<IndiviDataStatResponse>> call, Response<BaseResponseList<IndiviDataStatResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        indiviDataStatResponse = response.body().getData();
                        getDataSet();
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        runRealtimeData();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                    runRealtimeData();
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<IndiviDataStatResponse>> call, Throwable t) {
                Toast.makeText(IndiviAcitivity.this, "May be network error 4", Toast.LENGTH_SHORT).show();
                pd2.dismiss();
                runRealtimeData();
            }
        });
    }

    public void getDataSet(){
        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponseList<IndiviDataSetResponse>> call = service.getIndiviDataSetList(getIntent().getStringExtra("device_id"));

        call.enqueue(new Callback<BaseResponseList<IndiviDataSetResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<IndiviDataSetResponse>> call, Response<BaseResponseList<IndiviDataSetResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        indiviDataSetResponses = response.body().getData();
                        getDataDev();
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        runRealtimeData();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                    runRealtimeData();
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<IndiviDataSetResponse>> call, Throwable t) {
                Toast.makeText(IndiviAcitivity.this, "May be network error 5", Toast.LENGTH_SHORT).show();
                pd2.dismiss();
                runRealtimeData();
            }
        });
    }

    public void getDataDev(){
        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponseList<IndiviDataDevStatResponse>> call = service.getIndiviDataDevStatList(getIntent().getStringExtra("device_id"));

        call.enqueue(new Callback<BaseResponseList<IndiviDataDevStatResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<IndiviDataDevStatResponse>> call, Response<BaseResponseList<IndiviDataDevStatResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        indiviDataDevStatResponses = response.body().getData();

                        for(int i=0; i< indiviDataListResponse.size();i++) {
                            if(indiviDataListResponse.get(i).getDeviceModel().equals("NONE")){
                                indiviDataListResponse.remove(indiviDataListResponse.get(i));
                                i--;
                            }
                        }

                        for(int i=0; i< indiviDataListResponse.size();i++){

                            //append data stat
                            for(int a=0; a<indiviDataStatResponse.size(); a++){
                                if(indiviDataStatResponse.get(a).getThInfo().equals(indiviDataListResponse.get(i).getTagName())){
                                    indiviDataListResponse.get(i).setIndiviDataStatResponse(indiviDataStatResponse.get(a));
                                }
                            }

                            //append data set
                            for(int b=0; b<indiviDataSetResponses.size(); b++){
                                if(indiviDataSetResponses.get(b).getThInfo().equals(indiviDataListResponse.get(i).getTagName())){
                                    Log.e("eeee", indiviDataSetResponses.get(b).getThInfo());
                                    indiviDataListResponse.get(i).setIndiviDataSetResponse(indiviDataSetResponses.get(b));
                                }
                            }

                            //append data dev
                            if(indiviDataDevStatResponses != null && !indiviDataDevStatResponses.isEmpty()){
                                indiviDataListResponse.get(i).setIndiviDataDevStatResponse(indiviDataDevStatResponses.get(0));
                            }
                        }

                        IAdapter = new IndiviAdapter(IndiviAcitivity.this, indiviDataListResponse);
                        LinearLayoutManager HLayoutManager = new LinearLayoutManager(IndiviAcitivity.this);
                        HLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        indiviListview.setLayoutManager(HLayoutManager);
                        indiviListview.setItemAnimator(new DefaultItemAnimator());
                        indiviListview.setAdapter(IAdapter);
                    } else {
                        Toast.makeText(IndiviAcitivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(IndiviAcitivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(IndiviAcitivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
                pd2.dismiss();
                runRealtimeData();
            }

            @Override
            public void onFailure(Call<BaseResponseList<IndiviDataDevStatResponse>> call, Throwable t) {
                Toast.makeText(IndiviAcitivity.this, "May be network error 6", Toast.LENGTH_SHORT).show();
                pd2.dismiss();
                runRealtimeData();
            }
        });
    }
}
