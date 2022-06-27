package com.germeny.pasqualesilvio;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.orhanobut.hawk.Hawk;

public class MyApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Hawk.init(this).build();
    }

    public static Context getAppContext() {
        return MyApp.context;
    }
}
