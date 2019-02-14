package com.zarina.android.attendancedemo.room.loginregisterscreen;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zarina.android.attendancedemo.model.loginregisterscreen.AttendantDetails;

import java.util.List;

@Dao
public interface AttendantDetailsDao {

    @Insert
    void insert(AttendantDetails attendantDetails);

    @Query("SELECT * FROM attendant_details_table ORDER BY id DESC")
    LiveData<List<AttendantDetails>> getAllAttendantDetails();
}
