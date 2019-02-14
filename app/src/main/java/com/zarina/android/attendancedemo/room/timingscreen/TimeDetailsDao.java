package com.zarina.android.attendancedemo.room.timingscreen;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.zarina.android.attendancedemo.model.timingscreen.TimeDetails;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface TimeDetailsDao {

    @Insert
    void insert(TimeDetails timeDetails);

    @Update
    public void update(TimeDetails timeDetails);

    @Query("UPDATE time_details_table SET timeout = :timeout WHERE date_pin = :date_pin")
    int updateTimeDetail(String date_pin, String timeout);

    @Query("UPDATE time_details_table SET timein = :timein WHERE date_pin = :date_pin")
    int updateTimeInDetail(String date_pin, String timein);

    @Query("SELECT * FROM time_details_table ORDER BY pin")
    LiveData<List<TimeDetails>> getAllTimeDetails();

    @Query("SELECT * FROM time_details_table where pin = :pin")
    Maybe<List<TimeDetails>> getAllTimeDetailsByPin(int pin);

}

