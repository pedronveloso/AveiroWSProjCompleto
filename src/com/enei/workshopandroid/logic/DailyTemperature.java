package com.enei.workshopandroid.logic;


import java.util.Date;

public class DailyTemperature {

    private Date date;
    private int dayTemp, maxTemp, minTemp;
    private int weatherID; // unique weather condition id


    public DailyTemperature(Date date, int dayTemp, int maxTemp, int minTemp, int weatherID) {
        this.date = date;
        this.dayTemp = dayTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.weatherID = weatherID;
    }

    public Date getDate() {
        return date;
    }

    public int getDayTemp() {
        return dayTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getWeatherID() {
        return weatherID;
    }
}
