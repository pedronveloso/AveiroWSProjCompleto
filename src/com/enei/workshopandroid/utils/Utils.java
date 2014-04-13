package com.enei.workshopandroid.utils;


import android.content.Context;
import android.widget.TextView;
import com.enei.workshopandroid.R;

public final class Utils {

    private Utils() {
    }

    public static void setTextViewTemperature(TextView tv, int value, Context ctx) {
        tv.setText(value + ctx.getString(R.string.celsius_symbol));
    }
}
