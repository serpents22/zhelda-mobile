package com.germeny.pasqualesilvio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.IndiviAcitivity;
import com.germeny.pasqualesilvio.MainActivity;
import com.germeny.pasqualesilvio.R;
import com.germeny.pasqualesilvio.SystemActivity;
import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.DevStatResponse;
import com.germeny.pasqualesilvio.model.ErrorResponse;
import com.germeny.pasqualesilvio.model.GatewaysDataItem;
import com.germeny.pasqualesilvio.model.LoginResponse;
import com.germeny.pasqualesilvio.network.RestService;
import com.germeny.pasqualesilvio.network.RetrofitInstance;
import com.germeny.pasqualesilvio.utils.Preferences;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.MyViewHolder> {
    private List<GatewaysDataItem> cardList;
    Context context;

    public SystemAdapter(Context context)
    {
        this.context=context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageTitle;
        TextView name;
        Button settingBtn;
        MyViewHolder(View view) {
            super(view);

            imageTitle = view.findViewById(R.id.systemItemImg);
            name = view.findViewById(R.id.systemItemName);
            settingBtn = view.findViewById(R.id.systemItemBtn);
        }
    }

    public SystemAdapter(List<GatewaysDataItem> cardList) {
        this.cardList = cardList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_system, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GatewaysDataItem model = cardList.get(position);
        holder.imageTitle.setImageResource(R.drawable.warningicon);
        holder.name.setText(model.getDeviceName());
        updateData(model.getDeviceId(), holder);

        holder.settingBtn.setOnClickListener(v -> {
            final Intent intent = new Intent(context, IndiviAcitivity.class);
            context.startActivity(intent);
        });
    }

    private void updateData(String deviceId, MyViewHolder holder){

        RestService service = RetrofitInstance.getRetrofitInstance().create(RestService.class);
        Call<BaseResponseList<DevStatResponse>> call = service.getDeviceStat(deviceId);

        call.enqueue(new Callback<BaseResponseList<DevStatResponse>>() {
            @Override
            public void onResponse(Call<BaseResponseList<DevStatResponse>> call, Response<BaseResponseList<DevStatResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        if(!response.body().getData().isEmpty()){
                            if(response.body().getData().get(0).isIsAlarmed()){
                                holder.imageTitle.setImageResource(R.drawable.cancelicon);
                            }
                            else {
                                holder.imageTitle.setImageResource(R.drawable.checkicon);
                            }
                        }
                        else {
                            holder.imageTitle.setImageResource(R.drawable.warningicon);
                        }
                    } else {
                        Toast.makeText(context, "Failed " + response.body().getStatus() + " : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = RetrofitInstance.getRetrofitInstance()
                            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        ErrorResponse error = converter.convert(response.errorBody());
                        Toast.makeText(context, "Failed : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(context, "Failed : Object Response Is Inappropriate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponseList<DevStatResponse>> call, Throwable t) {
                Toast.makeText(context, "May be network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
