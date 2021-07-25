package com.thekeval.rideshare.model;

public class RiderModel {
    public String name;
    public String password;
    public String gender;
    public String email;
    public String contact_no;
    // public int ride_owner;

    public RiderModel(String name, String password, String gender, String email, String contact_no) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.contact_no = contact_no;
        // this.ride_owner = ride_owner;
    }
}
