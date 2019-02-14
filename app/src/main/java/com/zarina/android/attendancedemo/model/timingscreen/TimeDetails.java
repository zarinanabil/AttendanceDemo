package com.zarina.android.attendancedemo.model.timingscreen;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import android.support.annotation.NonNull;

@Entity(tableName = "time_details_table")
public class TimeDetails {

   /* @PrimaryKey(autoGenerate = true)
    private int id;*/

    @PrimaryKey
    @NonNull private String date_pin;

    private String date;

    private String name;

    private String timein;

    private String timeout;

    private int pin;

    public TimeDetails(String date_pin,String date, String name, String timein, String timeout, int pin) {
        this.date_pin = date_pin;
        this.date = date;
        this.name = name;
        this.timein = timein;
        this.timeout = timeout;
        this.pin = pin;
    }

    public String getDate_pin() {
        return date_pin;
    }

    public void setDate_pin(int date_pin) {
        this.date_pin = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "TimeDetails{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", timein='" + timein + '\'' +
                ", timeout='" + timeout + '\'' +
                ", pin=" + pin +
                '}';
    }
}
