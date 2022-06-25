package com.germeny.pasqualesilvio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.model.LoginResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;
import com.germeny.pasqualesilvio.utils.Preferences;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button loginBtn, registerBtn;
    EditText email, password;
    ImageView helpbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        email = (EditText)findViewById(R.id.emailEdit);
        password = (EditText)findViewById(R.id.passwordEdit);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        helpbtn = (ImageView)findViewById(R.id.heleclient);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("loading");

                RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
                Call<BaseResponse<LoginResponse>> call = service.postLogin(
                        email.getText().toString(),
                        password.getText().toString());
                pd.show();

                call.enqueue(new Callback<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<LoginResponse>> call, Response<BaseResponse<LoginResponse>> response) {
                        pd.hide();
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Toast.makeText(MainActivity.this, response.body().getData().getToken().getToken(), Toast.LENGTH_SHORT).show();
                                new Preferences().setToken(response.body().getData().getToken().getToken(), MainActivity.this);
                                new Preferences().setGateway(response.body().getData().getGatewaysData());

                                Intent intent = new Intent(MainActivity.this, SystemActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                                    .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                            try {
                                ErrorResponse error = converter.convert(response.errorBody());
                                Toast.makeText(MainActivity.this, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<LoginResponse>> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(MainActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WhatsappActivity.class);
                startActivity(i);
            }
        });
    }
}