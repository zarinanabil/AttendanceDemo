package com.zarina.android.attendancedemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.format.Time;
import android.widget.Toast;

import com.zarina.android.attendancedemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {


    public static ProgressDialog getProgressDialog(Context context, int msg) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.ProgressDialogStyle);
        progressDialog.setMessage(context.getResources().getString(msg));
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static String getCurrentDate(){

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }

    public static String getCurrentTime(){

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        return today.format("%k:%M:%S");
    }
}
