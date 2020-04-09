package com.example.androidapp;

public class ListItem {

    private String firstname,start,dest,tel;

    public ListItem(String firstname, String start, String dest, String tel) {
        this.firstname = firstname;
        this.start = start;
        this.dest = dest;
        this.tel = tel;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getStart() {
        return start;
    }

    public String getDest() {
        return dest;
    }

    public String getTel() {
        return tel;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
