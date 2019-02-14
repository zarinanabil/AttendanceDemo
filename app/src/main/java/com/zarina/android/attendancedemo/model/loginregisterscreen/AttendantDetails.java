package com.zarina.android.attendancedemo.model.loginregisterscreen;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "attendant_details_table")
public class AttendantDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String address;

    private int pin;


    public AttendantDetails(String name, String address, int pin) {
        this.name = name;
        this.address = address;
        this.pin = pin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "AttendantDetails{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pin=" + pin +
                '}';
    }
}
