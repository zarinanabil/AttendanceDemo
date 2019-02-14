package com.zarina.android.attendancedemo.repository.loginregisterscreen;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.zarina.android.attendancedemo.model.loginregisterscreen.AttendantDetails;
import com.zarina.android.attendancedemo.room.loginregisterscreen.AttendantDetailsDao;
import com.zarina.android.attendancedemo.room.loginregisterscreen.AttendantDetailsDatabase;
import com.zarina.android.attendancedemo.utils.DatabaseCallback;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AttendanceDetailsRepository {

    private AttendantDetailsDao attendantDetailsDao;
    private LiveData<List<AttendantDetails>> listAttendantDetails;

    public AttendanceDetailsRepository(Application application) {
        AttendantDetailsDatabase database = AttendantDetailsDatabase.getInstance(application);
        attendantDetailsDao = database.attendantDetailsDao();
        listAttendantDetails = attendantDetailsDao.getAllAttendantDetails();
    }

    public void insert(final AttendantDetails attendantDetails,final DatabaseCallback databaseCallback) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                attendantDetailsDao.insert(attendantDetails);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                databaseCallback.onUserAdded();
            }

            @Override
            public void onError(Throwable e) {
                //databaseCallback.onDataNotAvailable();
            }
        });
    }

    public LiveData<List<AttendantDetails>> getAllAttendantDetails() {
        return listAttendantDetails;
    }

}

