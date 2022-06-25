package com.germeny.pasqualesilvio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.germeny.pasqualesilvio.utils.TcpClient;

public class NuovoActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG = "QRCScanner-MainActivity";

    ImageView backbtn, scanBtn;
    Button saveBtn, btnRegisterDevice;
    EditText edSSID, edPassword;

    TcpClient mTcpClient;

    AsyncTask<String, String, TcpClient> ct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_nuovo);

        getSupportActionBar().hide();

        backbtn = findViewById(R.id.nuovoBackBtn);
        scanBtn = findViewById(R.id.scanBtn);
        saveBtn = findViewById(R.id.saveBtn);
        edSSID = findViewById(R.id.edSSID);
        edPassword = findViewById(R.id.edPassword);
        btnRegisterDevice = findViewById(R.id.btnRegisterDevice);

        backbtn.setOnClickListener(v -> {
            Intent i = new Intent(NuovoActivity.this, SystemActivity.class);
            startActivity(i);
        });

        scanBtn.setOnClickListener(v -> {
            Intent i = new Intent(NuovoActivity.this,QrCodeActivity.class);
            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
        });

        saveBtn.setOnClickListener(v-> {
            new ConnectTask().execute("");

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mTcpClient != null) {

                        mTcpClient.sendMessage("$SSID=" + edSSID.getText().toString() + "," + edPassword.getText().toString() + "\r\n") ;

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mTcpClient.sendMessage("$REBOOT" + "\r\n");
                            }
                        }, 10000);
                    }
                }
            }, 2000);
        });

        btnRegisterDevice.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterDeviceActivity.class));
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTcpClient != null) {
            mTcpClient.stopClient();
        }
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {
            //we create a TCPClient object
            //here the messageReceived method is implemented
            //this method calls the onProgressUpdate
            mTcpClient = new TcpClient(this::publishProgress);
            mTcpClient.run();

            mTcpClient.sendMessage("$SSID=" + edSSID.getText().toString() + "," + edPassword.getText().toString() + "\r\n");

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> mTcpClient.sendMessage("$REBOOT"+ "\r\n"), 10000);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....
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
                AlertDialog alertDialog = new AlertDialog.Builder(NuovoActivity.this).create();
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
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d(LOGTAG, "Have scan result in your app activity :" + result);
            AlertDialog alertDialog = new AlertDialog.Builder(NuovoActivity.this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        }
    }
}
