package com.germeny.pasqualesilvio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.DetailAcitivity;
import com.germeny.pasqualesilvio.IndiviAcitivity;
import com.germeny.pasqualesilvio.R;
import com.germeny.pasqualesilvio.model.IndiviModel;
import com.germeny.pasqualesilvio.model.SystemModel;

import java.util.List;

public class IndiviAdapter extends RecyclerView.Adapter<IndiviAdapter.MyViewHolder> {
    private List<IndiviModel> cardList;
    Context context;

    public IndiviAdapter(Context context)
    {
        this.context=context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView deviceimg, methodimg;
        TextView name, number, temper;
        Button settingBtn;
        MyViewHolder(View view) {
            super(view);

            deviceimg = view.findViewById(R.id.individevice);
            methodimg = view.findViewById(R.id.indivimethod);
            number = view.findViewById(R.id.indiviNumber);
            temper = view.findViewById(R.id.indivithermber);
            name = view.findViewById(R.id.indiviname);
            settingBtn = view.findViewById(R.id.indiviItemBtn);
        }
    }

    public IndiviAdapter(List<IndiviModel> cardList) {
        this.cardList = cardList;
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

        IndiviModel model = cardList.get(position);

        holder.deviceimg.setImageResource(model.getDeviceimage());
        holder.methodimg.setImageResource(model.getMethodimage());
        holder.number.setText(model.getIndivinumber());
        holder.name.setText(model.getIndiviname());
        holder.temper.setText(model.getIndivitemperature());
        holder.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SystemAdapter.MyViewHolder vholder = (SystemAdapter.MyViewHolder) v.getTag();
//                int position = vholder.getPosition();
                final Intent intent = new Intent(context, DetailAcitivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
