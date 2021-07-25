package com.thekeval.rideshare.model;

public class RideModel {
    public String from_location;
    public String destination_location;
    public String start_time;
    public int ride_duration_minutes;
    public boolean halt_allowed;
    public int ride_owner_id;
    public int available_seats;
    public boolean is_complete;

    public RideModel(String from_location, String destination_location, String start_time, int ride_duration_minutes,
                     boolean halt_allowed, int ride_owner_id, int available_seats, boolean is_complete) {
        this.from_location = from_location;
        this.destination_location = destination_location;
        this.start_time = start_time;
        this.ride_duration_minutes = ride_duration_minutes;
        this.halt_allowed = halt_allowed;
        this.ride_owner_id = ride_owner_id;
        this.available_seats = available_seats;
        this.is_complete = is_complete;
    }
}
