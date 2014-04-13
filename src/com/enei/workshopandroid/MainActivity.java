package com.enei.workshopandroid;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.enei.workshopandroid.adapter.WeatherListAdapter;
import com.enei.workshopandroid.logic.DailyTemperature;
import com.enei.workshopandroid.network.NetworkCalls;
import com.enei.workshopandroid.utils.Constants;
import com.enei.workshopandroid.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    private ArrayList<DailyTemperature> dailyTemperatures;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView tvAux = (TextView) findViewById(R.id.tv_current_temp);
        Utils.setTextViewTemperature(tvAux, 20, this);

        tvAux = (TextView) findViewById(R.id.tv_current_temp_max);
        Utils.setTextViewTemperature(tvAux, 22, this);

        tvAux = (TextView) findViewById(R.id.tv_current_temp_min);
        Utils.setTextViewTemperature(tvAux, 16, this);

        ImageView iv = (ImageView) findViewById(R.id.iv_current_icon);
        iv.setImageResource(R.drawable.sunny);

        new DownloadTask().execute();
    }


    private class DownloadTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            return NetworkCalls.httpGet(Constants.URL_TEST);
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d(Constants.LOG_TAG, "Result from HTTP call was:" + result);

            if (result == null) {
                return;
            }

            // process results
            Double dayTemp, maxTemp, minTemp;
            Date date;

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                JSONArray jArrayAux;
                int weatherCond;

                dailyTemperatures = new ArrayList<DailyTemperature>(jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    // get data
                    date = new Date();
                    date.setTime(jsonObject.getLong("dt")*1000);

                    // get temperatures
                    jsonObject = jsonObject.getJSONObject("temp");
                    dayTemp = jsonObject.getDouble("day");
                    maxTemp = jsonObject.getDouble("max");
                    minTemp = jsonObject.getDouble("min");

                    // get weather unique id
                    jsonObject = jsonArray.getJSONObject(i);
                    jArrayAux = jsonObject.getJSONArray("weather");
                    if (jsonArray.length() > 0) {
                        weatherCond = jArrayAux.getJSONObject(0).getInt("id");
                    } else {
                        weatherCond = -1; // -1 equals non available
                    }

                    dailyTemperatures.add(new DailyTemperature(date, dayTemp.intValue(), maxTemp.intValue(), minTemp.intValue(), weatherCond));
                }


                // represent temperatures for current day on UI
                TextView tvAux = (TextView) findViewById(R.id.tv_current_temp);
                Utils.setTextViewTemperature(tvAux, dailyTemperatures.get(0).getDayTemp(), MainActivity.this);

                tvAux = (TextView) findViewById(R.id.tv_current_temp_max);
                Utils.setTextViewTemperature(tvAux, dailyTemperatures.get(0).getMaxTemp(), MainActivity.this);

                tvAux = (TextView) findViewById(R.id.tv_current_temp_min);
                Utils.setTextViewTemperature(tvAux, dailyTemperatures.get(0).getMinTemp(), MainActivity.this);

                // remove first item from list
                dailyTemperatures.remove(0);

                // initialize the ListView
                ListView lv = (ListView) findViewById(R.id.lv_weather_items);
                lv.setAdapter(new WeatherListAdapter(dailyTemperatures, MainActivity.this));


            } catch (JSONException e) {
                Log.e(Constants.LOG_TAG, "Error processing JSON. E.: " + e.getMessage());
            }
        }
    }

}
