package com.germeny.pasqualesilvio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.germeny.pasqualesilvio.model.AddGatewayResponse;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class RegisterDeviceActivity extends AppCompatActivity {
    EditText edDeviceId, edDeviceName, edPassword, edType, edMax;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_device_register);
        edDeviceId = findViewById(R.id.edDeviceId);
        edDeviceName = findViewById(R.id.edDeviceName);
        edPassword = findViewById(R.id.edPassword);
        edType = findViewById(R.id.edType);
        edMax = findViewById(R.id.edMax);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v->{
            ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("loading");
            if(edDeviceId.getText() != null && edDeviceName.getText() != null && edPassword.getText() != null && edType.getText() != null && edMax.getText() != null){
                RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
                Call<BaseResponseList<AddGatewayResponse>> call = service.postGateway(
                        edDeviceId.getText().toString(),
                        edDeviceName.getText().toString(),
                        edPassword.getText().toString(),
                        edType.getText().toString(),
                        Integer.parseInt(edMax.getText().toString())
                );
                pd.show();

                call.enqueue(new Callback<BaseResponseList<AddGatewayResponse>>() {
                    @Override
                    public void onResponse(Call<BaseResponseList<AddGatewayResponse>> call, Response<BaseResponseList<AddGatewayResponse>> response) {
                        pd.hide();
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Toast.makeText(RegisterDeviceActivity.this, "Success Add", Toast.LENGTH_SHORT).show();
                                edDeviceId.setText("");
                                edDeviceName.setText("");
                                edPassword.setText("");
                                edType.setText("");
                                edMax.setText("");
                            } else {
                                Toast.makeText(RegisterDeviceActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                    .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                            try {
                                ErrorResponse error = converter.convert(response.errorBody());
                                Toast.makeText(RegisterDeviceActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(RegisterDeviceActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponseList<AddGatewayResponse>> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(RegisterDeviceActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
