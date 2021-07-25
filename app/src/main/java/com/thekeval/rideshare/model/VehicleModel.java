package com.thekeval.rideshare.model;

public class VehicleModel {
    public String vin;
    public String manufacturer;
    public String model;
    public String manufacturing_year;
    public int owner_id;

    public VehicleModel(String vin, String manufacturer, String model, String manufacturing_year, int owner_id) {
        this.vin = vin;
        this.manufacturer = manufacturer;
        this.model = model;
        this.manufacturing_year = manufacturing_year;
        this.owner_id = owner_id;
    }
}
