package com.germeny.pasqualesilvio;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blikoon.qrcodescanner.QrCodeActivity;
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
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private final String LOGTAG = "QRCScanner-MainActivity";

    EditText edDeviceName;
    Button btnScan;
    boolean canClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_device_register);
        edDeviceName = findViewById(R.id.edDeviceName);
        btnScan = findViewById(R.id.btnScan);

        edDeviceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                canClick = !editable.toString().isEmpty();
                btnScan.setEnabled(canClick);
            }
        });

        btnScan.setOnClickListener(v->{
            if(canClick){
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }
                else{
                    Intent i = new Intent(RegisterDeviceActivity.this, QrCodeActivity.class);
                    startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(RegisterDeviceActivity.this, QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Log.d(LOGTAG, "COULD NOT GET A GOOD RESULT.");
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (result != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(RegisterDeviceActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
            return;

        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String[] result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult").split(" ");

            if(result.length == 4){
                ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("loading");
                RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
                Call<BaseResponseList<AddGatewayResponse>> call = service.postGateway(
                        result[0],
                        edDeviceName.getText().toString(),
                        result[1],
                        result[2],
                        Integer.parseInt(result[3])
                );
                pd.show();

                call.enqueue(new Callback<BaseResponseList<AddGatewayResponse>>() {
                    @Override
                    public void onResponse(Call<BaseResponseList<AddGatewayResponse>> call, Response<BaseResponseList<AddGatewayResponse>> response) {
                        pd.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == 200) {
                                Toast.makeText(RegisterDeviceActivity.this, "Success Add", Toast.LENGTH_SHORT).show();
                                finishAffinity();
                                Intent intent = new Intent(RegisterDeviceActivity.this, SystemActivity.class);
                                startActivity(intent);
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
                        pd.dismiss();
                        Toast.makeText(RegisterDeviceActivity.this, "May be network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
