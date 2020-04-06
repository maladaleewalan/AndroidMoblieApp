package com.example.androidapp;

public class User {
    private String id,email,firstname,lastname,tel,role;

    public User(String id,String email, String firstname, String lastname, String tel, String role) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.tel = tel;
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTel() {
        return tel;
    }

    public String getRole() {
        return role;
    }
}
