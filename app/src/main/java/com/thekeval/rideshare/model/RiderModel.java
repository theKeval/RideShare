package com.thekeval.rideshare.model;

public class RiderModel {
    public String name;
    public String password;
    public String email;
    public String contact_no;
    public int ride_owner;

    public RiderModel(String name, String password, String email, String contact_no, int ride_owner) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.contact_no = contact_no;
        this.ride_owner = ride_owner;
    }
}
