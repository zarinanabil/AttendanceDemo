package com.zarina.android.attendancedemo;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zarina.android.attendancedemo.adapter.TimeDetailsAdapter;
import com.zarina.android.attendancedemo.model.timingscreen.TimeDetails;
import com.zarina.android.attendancedemo.utils.Constants;
import com.zarina.android.attendancedemo.viewmodel.timingscreen.TimeDetailsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeDetailsActivity extends AppCompatActivity {

    TimeDetailsViewModel timeDetailsViewModel;
    @BindView(R.id.tv_welcomename)
    TextView mTextViewWelcomeName;
    @BindView(R.id.btn_timein)
    Button mButtonTimeIn;
    @BindView(R.id.btn_timeout)
    Button mButtonTimeOut;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    TimeDetailsAdapter mTimeDetailsAdapter;
    int pin;
    String name;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_details);

        Intent intent = getIntent();
        pin = intent.getIntExtra("pin", -1);
        name = intent.getStringExtra("name");

        initViews();

        setObserver();

        setListeners();

        setWelcomeName();

    }

    private void setListeners() {

        mButtonTimeIn.setOnClickListener(v -> setTimeIn(v));

        mButtonTimeOut.setOnClickListener(v -> setTimeOut(v));
    }

    private void initViews() {

        timeDetailsViewModel = ViewModelProviders.of(this).get(TimeDetailsViewModel.class);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(TimeDetailsActivity.this));
        mTimeDetailsAdapter = new TimeDetailsAdapter(TimeDetailsActivity.this);
        mRecyclerView.setAdapter(mTimeDetailsAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog = Constants.getProgressDialog(this,R.string.pleasewait);

    }

    private void setObserver() {

        timeDetailsViewModel.getAllTimeDetailsByPin(pin).observe(this, this::timeDetailByPin);

    }

    private void setWelcomeName() {

        mTextViewWelcomeName.setText(getResources().getString(R.string.welcome) + " , " + name);
    }


    private void timeDetailByPin(List<TimeDetails> timeDetailsList) {

        progressDialog.show();

        if (timeDetailsList != null && !timeDetailsList.isEmpty()) {

            populateList(timeDetailsList);

        } else {
            Toast.makeText(TimeDetailsActivity.this, R.string.noentryyet, Toast.LENGTH_LONG).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.hide();
                }
            }, 5000);
        }
    }


    private void populateList(List<TimeDetails> timeDetailsList) {

        mTimeDetailsAdapter.displayTimeList(timeDetailsList,progressDialog);
    }



    public void setTimeIn(View view) {

        timeDetailsViewModel.setTimeIn(name,pin,progressDialog);

    }

    public void setTimeOut(View view) {

        timeDetailsViewModel.setTimeOut(name,pin,progressDialog);

    }

}
