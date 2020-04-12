package com.example.androidapp;

public class Route {
    private String driver, passenger,place,start,dest,namepassenger,telpassenger,picpassenger;    //สถานะ,บันทึกแล้ว
    boolean isWait;

    public Route(String driver, String passenger, String place, String start, String dest) {
        this.driver = driver;
        this.passenger = passenger;
        this.place = place;
        this.start = start;
        this.dest = dest;
        this.isWait = true;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setWait(boolean wait) {
        isWait = wait;
    }

    public void setNamepassenger(String namepassenger) {
        this.namepassenger = namepassenger;
    }

    public void setTelpassenger(String telpassenger) {
        this.telpassenger = telpassenger;
    }

    public void setPicpassenger(String picpassenger) {
        this.picpassenger = picpassenger;
    }

    public String getDriver() {
        return driver;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getPlace() {
        return place;
    }

    public String getStart() {
        return start;
    }

    public String getDest() {
        return dest;
    }

    public boolean isWait() {
        return isWait;
    }

    public String getNamepassenger() {
        return namepassenger;
    }

    public String getTelpassenger() {
        return telpassenger;
    }

    public String getPicpassenger() {
        return picpassenger;
    }
}
