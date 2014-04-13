package com.enei.workshopandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.enei.workshopandroid.R;
import com.enei.workshopandroid.logic.DailyTemperature;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeatherListAdapter extends BaseAdapter {

    private ArrayList<DailyTemperature> mDailyTemperatures;
    private LayoutInflater mInflater;

    public WeatherListAdapter(ArrayList<DailyTemperature> mDailyTemperatures, Context ctx) {
        this.mDailyTemperatures = mDailyTemperatures;
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return mDailyTemperatures.size();
    }

    @Override
    public Object getItem(int position) {
        return mDailyTemperatures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherViewHolder holder;

        // there was no previous view stored
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.temp_elem, parent, false);
            holder = new WeatherViewHolder();
            holder.tvDayTemp = (TextView) convertView.findViewById(R.id.tv_current_temp);
            holder.tvDayName = (TextView) convertView.findViewById(R.id.tv_day_name);
            holder.tvMaxTemp = (TextView) convertView.findViewById(R.id.tv_current_temp_max);
            holder.tvMinTemp = (TextView) convertView.findViewById(R.id.tv_current_temp_min);
            holder.ivWeatherIcon = (ImageView) convertView.findViewById(R.id.iv_current_icon);

            // store view for later use
            convertView.setTag(holder);
        } else {
            // there was a previous view in use, reuse it
            holder = (WeatherViewHolder) convertView.getTag();
        }

        // assign values to UI


        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(mDailyTemperatures.get(position).getDate());
      //  holder.tvDayName.setText(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+" - ");
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                holder.tvDayName.setText("Segunda-Feira");
                break;

            case Calendar.TUESDAY:
                holder.tvDayName.setText("Terça-Feira");
                break;


            case Calendar.WEDNESDAY:
                holder.tvDayName.setText("Quarta-Feira");
                break;

            case Calendar.THURSDAY:
                holder.tvDayName.setText("Quinta-Feira");
                break;

            case Calendar.FRIDAY:
                holder.tvDayName.setText("Sexta-Feira");
                break;

            case Calendar.SATURDAY:
                holder.tvDayName.setText("Sábado");
                break;

            case Calendar.SUNDAY:
                holder.tvDayName.setText("Domingo");
                break;
        }

        holder.tvDayTemp.setText(mDailyTemperatures.get(position).getDayTemp() + "º");
        holder.tvMaxTemp.setText(mDailyTemperatures.get(position).getMaxTemp() + "º");
        holder.tvMinTemp.setText(mDailyTemperatures.get(position).getMinTemp() + "º");

        Drawable conditionDrawable;
        switch (mDailyTemperatures.get(position).getWeatherID()) {

            case 500:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.shower1);
                break;

            case 501:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.shower2);
                break;

            case 502:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.shower3);
                break;

            case 800:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.sunny);
                break;

            case 801:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.cloudy1);
                break;

            case 802:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.cloudy2);
                break;

            case 803:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.cloudy3);
                break;

            case 804:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.cloudy4);
                break;

            default:
                conditionDrawable = convertView.getResources().getDrawable(R.drawable.dunno);
        }
        holder.ivWeatherIcon.setImageDrawable(conditionDrawable);

        return convertView;
    }


    class WeatherViewHolder {
        TextView tvDayName;
        TextView tvDayTemp;
        TextView tvMaxTemp;
        TextView tvMinTemp;
        ImageView ivWeatherIcon;
    }
}
