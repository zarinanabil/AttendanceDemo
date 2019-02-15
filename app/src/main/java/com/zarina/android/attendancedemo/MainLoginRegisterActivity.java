package com.zarina.android.attendancedemo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zarina.android.attendancedemo.model.loginregisterscreen.AttendantDetails;
import com.zarina.android.attendancedemo.utils.DatabaseCallback;
import com.zarina.android.attendancedemo.utils.Screen;
import com.zarina.android.attendancedemo.viewmodel.loginregisterscreen.AttendanceDetailsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainLoginRegisterActivity extends AppCompatActivity implements DatabaseCallback {

    AttendanceDetailsViewModel attendantDetailsViewModel;

    @BindView(R.id.tv_heading)
    TextView textViewHeading;
    @BindView(R.id.ll_login)
    LinearLayout loginLayout;
    @BindView(R.id.et_login_pin)
    EditText editTextLoginEnterPin;
    @BindView(R.id.btn_login)
    Button buttonlogin;
    @BindView(R.id.btn_register)
    Button buttonToRegister;
    @BindView(R.id.ll_register)
    LinearLayout registerLayout;
    @BindView(R.id.et_register_name)
    EditText editTextName;
    @BindView(R.id.et_register_address)
    EditText editTextAddress;
    @BindView(R.id.et_register_pin)
    EditText editTextRegisterPin;
    @BindView(R.id.btn_register_main)
    Button buttonRegister;
    @BindView(R.id.btn_login_back)
    Button buttonAlreadyRegistered;
    Boolean isLoginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_register);

        prepareViews();

        setObserver();

        toggleScreen(Screen.LOGIN_SCREEN);
    }

    private void setObserver() {

        attendantDetailsViewModel.getAllAttendantDetails().observe(this, this::checkAttendantPresent);
        attendantDetailsViewModel.getToastMessage().observe(this, this::showToast);
    }


    private void prepareViews() {

        ButterKnife.bind(this);
        attendantDetailsViewModel = ViewModelProviders.of(this).get(AttendanceDetailsViewModel.class);

    }

    private void showToast(int toastmessage) {

        Toast.makeText(MainLoginRegisterActivity.this, toastmessage, Toast.LENGTH_LONG).show();
    }

    public void toggleScreen(Screen which)
    {
        switch (which)
        {
            case LOGIN_SCREEN:
                textViewHeading.setText(R.string.login);
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
                isLoginScreen=true;
                break;

            case REGISTER_SCREEN:
                textViewHeading.setText(R.string.register);
                registerLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
                isLoginScreen=false;
                break;
        }
    }

    private void checkAttendantPresent(List<AttendantDetails> attendantDetails) {

        //showToast(R.string.database_updated);
        //write code if any initial code needs to be written
    }

    public void onLoginPress(View view) {

        String pin = editTextLoginEnterPin.getText().toString().trim();
        boolean islogininputvalid=false;
        if(pin!=null && !pin.isEmpty())
        {
           islogininputvalid = attendantDetailsViewModel.checkPinMatch(pin);

            if (islogininputvalid) {

                showToast(R.string.match_found);
                //Go To  NextScreen
                gotToTimeDetailScreen(Integer.parseInt(pin),attendantDetailsViewModel.name);
            }
            else
            {
                showToast(R.string.pin_not_registered);
            }

        }
        else
        {
            showToast(R.string.enter_pin);
        }

    }

    public void showRegisterScreen(View view) {

        clearLoginEdit();
        toggleScreen(Screen.REGISTER_SCREEN);
    }


    public void showLoginScreen(View view) {

        clearRegisterEdits();
        toggleScreen(Screen.LOGIN_SCREEN);
    }

    private void clearRegisterEdits(){

        editTextAddress.getText().clear();
        editTextName.getText().clear();
        editTextRegisterPin.getText().clear();

    }

    private void clearLoginEdit(){

        editTextLoginEnterPin.getText().clear();
    }

    public void registerUserDetails(View view) {

        //Check edits are not empty
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        int pin=-1;
        if(editTextRegisterPin.getText().toString().trim()!=null && !editTextRegisterPin.getText().toString().trim().isEmpty() )
        {
            pin = Integer.parseInt(editTextRegisterPin.getText().toString().trim());
        }

        /*
         *
         * Check Pin exists in DB---  PIN should be unique as its used to login
         *
         * */

        if((name==null || name.isEmpty())||(address==null||address.isEmpty())|| pin==-1)
        {
            showToast(R.string.enter_all_details);
        }
        else {
            if (attendantDetailsViewModel.checkPinMatch(editTextRegisterPin.getText().toString().trim())) {
                showToast(R.string.pin_already_exists);
            } else {
                attendantDetailsViewModel.insert(new AttendantDetails(editTextName.getText().toString(),
                        editTextAddress.getText().toString(),
                        Integer.parseInt(editTextRegisterPin.getText().toString())), MainLoginRegisterActivity.this);
                //Enter Main Screen
                gotToTimeDetailScreen(Integer.parseInt(editTextRegisterPin.getText().toString()),
                        editTextName.getText().toString() );
                clearRegisterEdits();

            }
        }

    }

    public void gotToTimeDetailScreen(int pin,String name)
    {
        Intent timeDetailIntent = new Intent(MainLoginRegisterActivity.this, TimeDetailsActivity.class);
        timeDetailIntent.putExtra("pin", pin);
        timeDetailIntent.putExtra("name", name);
        this.startActivity(timeDetailIntent);
        MainLoginRegisterActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        if(!isLoginScreen)
        {
            clearRegisterEdits();
            toggleScreen(Screen.LOGIN_SCREEN);
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onUserAdded() {
        showToast(R.string.register_successful);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
