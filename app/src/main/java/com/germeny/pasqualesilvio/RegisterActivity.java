package com.germeny.pasqualesilvio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button BackBtn, saveBtn;
    EditText edName, edEmail, edPhone, edPassword, edCPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        BackBtn = (Button) findViewById(R.id.registerbackBtn);
        saveBtn = (Button) findViewById(R.id.registersavebtn);
        edName = (EditText) findViewById(R.id.edName);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPhone = (EditText) findViewById(R.id.edPhone);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edCPassword = (EditText) findViewById(R.id.edCPassword);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        saveBtn.setOnClickListener(v -> {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("loading");

            if(edName.getText() != null && edEmail.getText() != null && edPhone.getText() != null && edPassword.getText() != null && edCPassword.getText() != null){
                RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
                Call<BaseResponse> call = service.postRegister(
                        edName.getText().toString(),
                        edEmail.getText().toString(),
                        edPhone.getText().toString(),
                        edPassword.getText().toString(),
                        edCPassword.getText().toString(),
                        "mobile"
                );
                pd.show();

                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        pd.hide();
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                    .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                            try {
                                ErrorResponse error = converter.convert(response.errorBody());
                                Toast.makeText(RegisterActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(RegisterActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(RegisterActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
