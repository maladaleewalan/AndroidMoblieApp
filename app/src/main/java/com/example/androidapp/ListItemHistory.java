package com.example.androidapp;

public class ListItemHistory {

    private String place,start,dest;

    public ListItemHistory(String place, String start, String dest) {
        this.place = place;
        this.start = start;
        this.dest = dest;
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
