package com.zarina.android.attendancedemo.repository.timingscreen;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import com.zarina.android.attendancedemo.model.timingscreen.TimeDetails;
import com.zarina.android.attendancedemo.room.timingscreen.TimeDetailsDao;
import com.zarina.android.attendancedemo.room.timingscreen.TimeDetailsDatabase;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class TimeDetailsRepository {

    private TimeDetailsDao timeDetailsDao;
    private LiveData<List<TimeDetails>> listTimeDetails;
    private MutableLiveData<List<TimeDetails>> listTimeDetailsByPin=new MutableLiveData<>();
    private int updated;


    public TimeDetailsRepository(Application application) {
        TimeDetailsDatabase database = TimeDetailsDatabase.getInstance(application);
        timeDetailsDao = database.timeDetailsDao();
        listTimeDetails = timeDetailsDao.getAllTimeDetails();
    }



    public void insert(final TimeDetails timeDetails,ProgressDialog progressDialog) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                timeDetailsDao.insert(timeDetails);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

                if(progressDialog!=null)
                {
                    progressDialog.hide();
                }

                getAllTimeDetailsByPin(timeDetails.getPin());

            }

            @Override
            public void onError(Throwable e) {

                if(progressDialog!=null)
                {
                    progressDialog.hide();
                }
            }
        });
    }


    public int updateTimeDetail(final String date, String timeout, int pin, ProgressDialog progressDialog){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                updated=timeDetailsDao.updateTimeDetail(date+"_"+pin,timeout);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

                //Toast-updated
                if(progressDialog!=null)
                {
                    progressDialog.hide();
                }

                getAllTimeDetailsByPin(pin);
            }

            @Override
            public void onError(Throwable e) {

                if(progressDialog!=null)
                {
                    progressDialog.hide();
                }

            }
        });

        return updated;
    }

    public void updateTimeInDetail(final String date, String timein,int pin, ProgressDialog progressDialog){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                updated=timeDetailsDao.updateTimeInDetail(date+"_"+pin,timein);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

                //Toast-updated
                if(progressDialog!=null)
                {
                    progressDialog.hide();
                }

                getAllTimeDetailsByPin(pin);
            }

            @Override
            public void onError(Throwable e) {

                if(progressDialog!=null)
                {
                    progressDialog.hide();
                }
            }
        });

    }


    public void update(final TimeDetails timeDetails) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                timeDetailsDao.update(timeDetails);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public LiveData<List<TimeDetails>> getAllTimeDetailsByPin(int pin) {
        //AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DB_NAME).build();
        timeDetailsDao.getAllTimeDetailsByPin(pin)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()
        ).subscribe(new MaybeObserver<List<TimeDetails>>() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.e("onSubscribe","ingetAllTimeDetailsByPin: ");

            }

            @Override
            public void onSuccess(List<TimeDetails> timeDetailsList) {

                Log.e("inAllTimeDetailsByPin","ingetAllTimeDetailsByPin: "+timeDetailsList.size());
                listTimeDetailsByPin.setValue(timeDetailsList);

            }

            @Override
            public void onError(Throwable e) {

                Log.e("onError","ingetAllTimeDetailsByPin: ");

            }

            @Override
            public void onComplete() {

                Log.e("onComplete","ingetAllTimeDetailsByPin: ");
            }
        });

        return listTimeDetailsByPin;

    }


    public LiveData<List<TimeDetails>> getAllTimeDetails() {
        return listTimeDetails;
    }


}
