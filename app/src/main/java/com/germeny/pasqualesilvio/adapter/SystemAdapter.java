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
import com.germeny.pasqualesilvio.R;
import com.germeny.pasqualesilvio.SystemActivity;
import com.germeny.pasqualesilvio.model.SystemModel;

import java.util.List;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.MyViewHolder> {
    private List<SystemModel> cardList;
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

    public SystemAdapter(List<SystemModel> cardList) {
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
        SystemModel model = cardList.get(position);
        holder.imageTitle.setImageResource(model.getImage());
        holder.name.setText(model.getName());

        holder.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(SystemActivity.class, IndiviAcitivity.class);
//                MyViewHolder vholder = (MyViewHolder) v.getTag();
//                int position = vholder.getPosition();
                final Intent intent = new Intent(context, IndiviAcitivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
