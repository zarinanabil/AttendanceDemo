package com.zarina.android.attendancedemo.room.loginregisterscreen;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zarina.android.attendancedemo.R;
import com.zarina.android.attendancedemo.model.loginregisterscreen.AttendantDetails;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {AttendantDetails.class}, version = 1, exportSchema = false)
public abstract class AttendantDetailsDatabase extends RoomDatabase {

    private static AttendantDetailsDatabase instance;

    private Context context;

    public abstract AttendantDetailsDao attendantDetailsDao();

    public static synchronized AttendantDetailsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AttendantDetailsDatabase.class, "attendant_details_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        instance.context = context;

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            InsertDetailsOnCreate();

        }
    };

    private static void InsertDetailsOnCreate() {

        Completable.fromAction(new Action() {

            private AttendantDetailsDao attendantDetailsDao;

            @Override
            public void run() throws Exception {
                attendantDetailsDao = instance.attendantDetailsDao();
                attendantDetailsDao.insert(new AttendantDetails(instance.context.getString(R.string.name_inserted),
                        instance.context.getString(R.string.address_inserted),
                        Integer.parseInt(instance.context.getString(R.string.pin_inserted))));
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

}
