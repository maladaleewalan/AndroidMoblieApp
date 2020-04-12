package com.example.androidapp;

public class Car {
    String regis,place,carPic,user_id;
//    boolean isService;

    public Car(String regis, String place, String carPic, String user_id) {
        this.regis = regis;
        this.place = place;
        this.carPic = carPic;
        this.user_id = user_id;
//        this.isService = false;
    }

    public void setRegis(String regis) {
        this.regis = regis;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setCarPic(String carPic) {
        this.carPic = carPic;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

//    public void setService(boolean service) {
//        isService = service;
//    }

    public String getRegis() {
        return regis;
    }

    public String getPlace() {
        return place;
    }

    public String getCarPic() {
        return carPic;
    }

    public String getUser_id() {
        return user_id;
    }

//    public boolean isService() {
//        return isService;
//    }
}
