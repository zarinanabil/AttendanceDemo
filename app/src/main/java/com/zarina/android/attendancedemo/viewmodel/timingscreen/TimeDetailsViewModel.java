package com.zarina.android.attendancedemo.viewmodel.timingscreen;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.zarina.android.attendancedemo.adapter.TimeDetailsAdapter;
import com.zarina.android.attendancedemo.model.timingscreen.TimeDetails;
import com.zarina.android.attendancedemo.repository.timingscreen.TimeDetailsRepository;
import com.zarina.android.attendancedemo.utils.Constants;

import java.util.List;



public class TimeDetailsViewModel extends AndroidViewModel {

    private TimeDetailsRepository repository;
    private LiveData<List<TimeDetails>> listTimeDetails;
    private LiveData<List<TimeDetails>> listTimeDetailsByPin;

    public TimeDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new TimeDetailsRepository(application);
        listTimeDetails = repository.getAllTimeDetails();
    }

    public void insert(TimeDetails timeDetails, ProgressDialog progressDialog) {
        repository.insert(timeDetails,progressDialog);
    }

    private void updatetimeDetail(String date,String timeout,int pin,ProgressDialog progressDialog){

        repository.updateTimeDetail(date,timeout,pin,progressDialog);
    }

    private void updatetimeInDetail(String date,String timein,int pin,ProgressDialog progressDialog){

        repository.updateTimeInDetail(date,timein,pin,progressDialog);
    }


    private void update(TimeDetails timeDetails) {
        repository.update(timeDetails);
    }


    public LiveData<List<TimeDetails>> getAllTimeDetails() {
        return listTimeDetails;
    }

    public LiveData<List<TimeDetails>> getAllTimeDetailsByPin(int pin){

        listTimeDetailsByPin = repository.getAllTimeDetailsByPin(pin);
        return listTimeDetailsByPin;
    }

    private List<TimeDetails> getTimeDetailsByPinList(){
        return  listTimeDetailsByPin.getValue();
    }

    public void setTimeIn(String name,int pin,ProgressDialog progressDialog)
    {
        boolean updated = false;

        if(getTimeDetailsByPinList().size()==0)
        {
            updated=true;
            insert(new TimeDetails(Constants.getCurrentDate()+"_"+pin,Constants.getCurrentDate(), name, Constants.getCurrentTime(), "", pin),progressDialog);
        }
        else {
            List<TimeDetails> timeDetailsList = getTimeDetailsByPinList();

            for (int i=0;i<timeDetailsList.size();i++) {

                if(timeDetailsList.get(i).getDate().equals(Constants.getCurrentDate())){

                    updatetimeInDetail(timeDetailsList.get(i).getDate(), Constants.getCurrentTime(),pin,progressDialog);
                    updated=true;
                    break;

                }

            }
        }

        if(!updated)
        {
            insert(new TimeDetails(Constants.getCurrentDate()+"_"+pin,Constants.getCurrentDate(), name, Constants.getCurrentTime(),"", pin),progressDialog);
        }


    }

    public void setTimeOut(String name,int pin,ProgressDialog progressDialog){

        boolean updated = false;

        if(getTimeDetailsByPinList().size()==0)
        {
            updated=true;
            insert(new TimeDetails(Constants.getCurrentDate()+"_"+pin,Constants.getCurrentDate(), name, "", Constants.getCurrentTime(), pin),progressDialog);
        }
        else {
            List<TimeDetails> timeDetailsList = getTimeDetailsByPinList();

            for (int i=0;i<timeDetailsList.size();i++) {

                if(timeDetailsList.get(i).getDate().equals(Constants.getCurrentDate())) {

                    updatetimeDetail(timeDetailsList.get(i).getDate(), Constants.getCurrentTime(),pin, progressDialog);
                    updated = true;
                    break;
                }

            }
        }

        if(!updated)
            insert(new TimeDetails(Constants.getCurrentDate() + "_" + pin, Constants.getCurrentDate(), name, "", Constants.getCurrentTime(), pin), progressDialog);

    }



}
