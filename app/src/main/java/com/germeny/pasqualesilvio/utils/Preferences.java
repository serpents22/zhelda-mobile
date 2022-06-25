package com.germeny.pasqualesilvio.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.germeny.pasqualesilvio.model.GatewaysDataItem;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class Preferences {
    public String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token", "");
    }

    public void setToken(String log, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Token", Context.MODE_PRIVATE).edit();
        editor.putString("Token", log);
        editor.apply();
    }

    public List<GatewaysDataItem> getGateway() {
        return Hawk.get("gateway_data");
    }

    public void setGateway(List<GatewaysDataItem> gateway) {
        Hawk.put("gateway_data", gateway);
    }
}
