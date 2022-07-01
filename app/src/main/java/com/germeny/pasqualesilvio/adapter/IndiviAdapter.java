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

import com.germeny.pasqualesilvio.DetailAcitivity;
import com.germeny.pasqualesilvio.R;
import com.germeny.pasqualesilvio.model.IndiviDataListResponse;
import com.germeny.pasqualesilvio.model.IndiviDataSetResponse;
import com.google.gson.Gson;

import java.util.List;

public class IndiviAdapter extends RecyclerView.Adapter<IndiviAdapter.MyViewHolder> {
    private List<IndiviDataListResponse> cardList;
    Context context;

    public IndiviAdapter(Context context, List<IndiviDataListResponse> cardList)
    {
        this.context=context;
        this.cardList = cardList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAlarm, imgFan, imgDay, imgHand, imgOnOff;
        TextView name, number, temper;
        Button settingBtn;
        MyViewHolder(View view) {
            super(view);
            imgAlarm = view.findViewById(R.id.imgAlarm);
            imgFan = view.findViewById(R.id.imgFan);
            imgDay = view.findViewById(R.id.imgDay);
            imgHand = view.findViewById(R.id.imgHand);
            imgOnOff = view.findViewById(R.id.imgOnOff);

            number = view.findViewById(R.id.indiviNumber);
            temper = view.findViewById(R.id.indivithermber);
            name = view.findViewById(R.id.indiviname);
            settingBtn = view.findViewById(R.id.indiviItemBtn);
        }
    }

    @NonNull
    @Override
    public IndiviAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_indivi, parent, false);
        context = parent.getContext();
        return new IndiviAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IndiviAdapter.MyViewHolder holder, int position) {

        IndiviDataListResponse model = cardList.get(position);
        if(model.getThName() == null || model.getThName().isEmpty()){
            holder.name.setText("No Name");
        }
        else {
            holder.name.setText(model.getThName());
        }

        if(model.getIndiviDataStatResponse() != null){
            if(model.getIndiviDataStatResponse().isIsAlarm()){
                holder.imgAlarm.setImageResource(R.drawable.cancelicon);
            }
            else {
                holder.imgAlarm.setImageResource(R.drawable.checkicon);
            }
            holder.imgAlarm.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgAlarm.setVisibility(View.GONE);
        }

        if(model.getIndiviDataStatResponse() != null && model.getIndiviDataStatResponse().isIsFan()){
            holder.imgFan.setImageResource(R.drawable.ic_fan);
            holder.imgFan.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgFan.setVisibility(View.GONE);
        }

        if(model.getIndiviDataSetResponse() != null){
            if(model.getIndiviDataSetResponse().isIsOn()){
                holder.imgOnOff.setImageResource(R.drawable.uc_on);
            } else {
                holder.imgOnOff.setImageResource(R.drawable.uc_off);
            }
            holder.imgOnOff.setVisibility(View.VISIBLE);
        } else {
            holder.imgOnOff.setVisibility(View.GONE);
        }

        if(model.getIndiviDataSetResponse() != null)
        {
            char[] maskData = model.getIndiviDataSetResponse().getGroupMask().toCharArray();
            IndiviDataSetResponse setData = model.getIndiviDataSetResponse();

            if(maskData[0] == '0'){
                if(maskData[1] == '0'){
                    if(setData.isIsOn()){
                        holder.imgHand.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.imgHand.setVisibility(View.GONE);
                    }
                }
                else {
                    switch (model.getIndiviDataDevStatResponse().getChrono2Stat()){
                        case 1 :{
                            holder.imgDay.setImageResource(R.drawable.ic_night);
                            holder.imgDay.setVisibility(View.VISIBLE);
                            holder.imgHand.setVisibility(View.GONE);
                            break;
                        }
                        case 2 :{
                            holder.imgDay.setImageResource(R.drawable.ic_night_off);
                            holder.imgDay.setVisibility(View.VISIBLE);
                            holder.imgHand.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 3 :{
                            holder.imgDay.setImageResource(R.drawable.ic_night_off);
                            holder.imgDay.setVisibility(View.VISIBLE);
                            holder.imgHand.setVisibility(View.GONE);
                            break;
                        }
                        case 4 :{
                            holder.imgDay.setImageResource(R.drawable.ic_night);
                            holder.imgDay.setVisibility(View.VISIBLE);
                            holder.imgHand.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
            }
            else {
                switch (model.getIndiviDataDevStatResponse().getChrono1Stat()){
                    case 1 :{
                        holder.imgDay.setImageResource(R.drawable.ic_day);
                        holder.imgDay.setVisibility(View.VISIBLE);
                        holder.imgHand.setVisibility(View.GONE);
                        break;
                    }
                    case 2 :{
                        holder.imgDay.setImageResource(R.drawable.ic_day_off);
                        holder.imgDay.setVisibility(View.VISIBLE);
                        holder.imgHand.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 3 :{
                        holder.imgDay.setImageResource(R.drawable.ic_day_off);
                        holder.imgDay.setVisibility(View.VISIBLE);
                        holder.imgHand.setVisibility(View.GONE);
                        break;
                    }
                    case 4 :{
                        holder.imgDay.setImageResource(R.drawable.ic_day);
                        holder.imgDay.setVisibility(View.VISIBLE);
                        holder.imgHand.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }

        if(model.getIndiviDataStatResponse() != null){
            holder.temper.setText(String.valueOf(model.getIndiviDataStatResponse().getTemperature()));
        }

        holder.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SystemAdapter.MyViewHolder vholder = (SystemAdapter.MyViewHolder) v.getTag();
//                int position = vholder.getPosition();
                if(model.getIndiviDataSetResponse() == null){
                    Toast.makeText(context, "TH Set is NULL", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Intent intent = new Intent(context, DetailAcitivity.class).putExtra("indiviData", new Gson().toJson(model));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
