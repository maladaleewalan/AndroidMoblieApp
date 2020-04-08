package com.example.androidapp;

public class Route {
    private String driver,passsenger,place,start,dest;

    public Route(String driver, String passsenger, String place, String start, String dest) {
        this.driver = driver;
        this.passsenger = passsenger;
        this.place = place;
        this.start = start;
        this.dest = dest;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setPasssenger(String passsenger) {
        this.passsenger = passsenger;
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

    public String getDriver() {
        return driver;
    }

    public String getPasssenger() {
        return passsenger;
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
}
