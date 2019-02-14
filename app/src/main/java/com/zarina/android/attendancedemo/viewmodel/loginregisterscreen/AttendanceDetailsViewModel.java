package com.zarina.android.attendancedemo.viewmodel.loginregisterscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zarina.android.attendancedemo.R;
import com.zarina.android.attendancedemo.model.loginregisterscreen.AttendantDetails;
import com.zarina.android.attendancedemo.repository.loginregisterscreen.AttendanceDetailsRepository;
import com.zarina.android.attendancedemo.utils.DatabaseCallback;

import java.util.List;

public class AttendanceDetailsViewModel extends AndroidViewModel {

    private AttendanceDetailsRepository repository;
    private LiveData<List<AttendantDetails>> listAttendantDetails;
    public MutableLiveData<Integer> toastMessage = new MutableLiveData<>();
    public String name;

    public AttendanceDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new AttendanceDetailsRepository(application);
        listAttendantDetails = repository.getAllAttendantDetails();
    }

    public void insert(AttendantDetails attendantDetails, DatabaseCallback databaseCallback) {
        repository.insert(attendantDetails,databaseCallback);
    }


    public LiveData<List<AttendantDetails>> getAllAttendantDetails() {
        return listAttendantDetails;
    }

    public boolean checkPinMatch(String enteredPin) {

        List<AttendantDetails> attendantDetails = null;
        if(getAllAttendantDetails().getValue()!=null)
        {
            attendantDetails = (List<AttendantDetails>) getAllAttendantDetails().getValue();
            //checking if AttendanceDetailsDB has the PinEntered
            for (int i = 0; i < attendantDetails.size(); i++) {
                if ((attendantDetails.get(i).getPin()) == (Integer.parseInt(enteredPin.trim()))) {
                    name=attendantDetails.get(i).getName();
                    return true;
                }
            }
        }
        else
        {
            toastMessage.setValue(R.string.pin_match_error);
        }

        return false;
    }


    public LiveData<Integer> getToastMessage() {
        return toastMessage;
    }

}
