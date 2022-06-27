package com.germeny.pasqualesilvio.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.germeny.pasqualesilvio.R;
import com.germeny.pasqualesilvio.model.DayNightResponse;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.util.Calendar;
import java.util.List;

public class SetupDayNightAdapter extends RecyclerView.Adapter<SetupDayNightAdapter.MyViewHolder> {
    private List<DayNightResponse> cardList;
    Context context;

    public SetupDayNightAdapter(Context context, List<DayNightResponse> cardList)
    {
        this.context=context;
        this.cardList = cardList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageTitle;
        TextView edStart, edStop;
        CheckBox cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday;
        MyViewHolder(View view) {
            super(view);
            edStart = view.findViewById(R.id.edStart);
            edStop = view.findViewById(R.id.edStop);
            cbMonday = view.findViewById(R.id.cbMonday);
            cbTuesday = view.findViewById(R.id.cbTuesday);
            cbWednesday = view.findViewById(R.id.cbWednesday);
            cbThursday = view.findViewById(R.id.cbThursday);
            cbFriday = view.findViewById(R.id.cbFriday);
            cbSaturday = view.findViewById(R.id.cbSaturday);
            cbSunday = view.findViewById(R.id.cbSunday);
//            imageTitle = view.findViewById(R.id.systemItemImg);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setup_daynight, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DayNightResponse model = cardList.get(position);
        if(model.getStart().length() > 5){
            holder.edStart.setText(model.getStart().substring(0,5));
        } else {
            holder.edStart.setText(model.getStart());
        }

        holder.edStart.setOnClickListener(v->{
            showTimeDialog(holder.edStart, model, true);
        });

        holder.edStop.setOnClickListener(v->{
            showTimeDialog(holder.edStop, model, false);
        });

        if(model.getStop().length() > 5){
            holder.edStop.setText(model.getStop().substring(0,5));
        } else {
            holder.edStop.setText(model.getStop());
        }

        holder.cbMonday.setChecked(
                model.getWeekMask().substring(0,1).equals("1")
        );
        holder.cbTuesday.setChecked(
                model.getWeekMask().substring(1,2).equals("1")
        );
        holder.cbWednesday.setChecked(
                model.getWeekMask().substring(2,3).equals("1")
        );
        holder.cbThursday.setChecked(
                model.getWeekMask().substring(3,4).equals("1")
        );
        holder.cbFriday.setChecked(
                model.getWeekMask().substring(4,5).equals("1")
        );
        holder.cbSaturday.setChecked(
                model.getWeekMask().substring(5,6).equals("1")
        );
        holder.cbSunday.setChecked(
                model.getWeekMask().substring(6,7).equals("1")
        );

        holder.cbMonday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[0] = '1';
            }
            else {
                myNameChars[0] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });

        holder.cbTuesday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[1] = '1';
            }
            else {
                myNameChars[1] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });

        holder.cbWednesday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[2] = '1';
            }
            else {
                myNameChars[2] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });

        holder.cbThursday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[3] = '1';
            }
            else {
                myNameChars[3] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });

        holder.cbFriday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[4] = '1';
            }
            else {
                myNameChars[4] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });

        holder.cbSaturday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[5] = '1';
            }
            else {
                myNameChars[5] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });

        holder.cbSunday.setOnCheckedChangeListener((compoundButton, b) -> {
            String myName = model.getWeekMask();
            char[] myNameChars = myName.toCharArray();
            if(b){
                myNameChars[6] = '1';
            }
            else {
                myNameChars[6] = '0';
            }
            model.setWeekMask(String.valueOf(myNameChars));
        });
    }

    private void showTimeDialog(TextView tv, DayNightResponse model, boolean isStart) {

        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (TimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute) -> {
            tv.setText(hourOfDay+":"+minute);

            if(isStart){
                model.setStart(hourOfDay+":"+minute);
            }
            else{
                model.setStop(hourOfDay+":"+minute);
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context));

        timePickerDialog.show();
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
