package com.zarina.android.attendancedemo.room.timingscreen;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zarina.android.attendancedemo.model.timingscreen.TimeDetails;

@Database(entities = {TimeDetails.class}, version = 1, exportSchema = false)
public abstract class TimeDetailsDatabase extends RoomDatabase {

    private static TimeDetailsDatabase instance;

    private Context context;

    public abstract TimeDetailsDao timeDetailsDao();

    public static synchronized TimeDetailsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TimeDetailsDatabase.class, "time_details_table")
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
        }
    };

}
