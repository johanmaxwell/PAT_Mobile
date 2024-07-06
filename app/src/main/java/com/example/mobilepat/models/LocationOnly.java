package com.example.mobilepat.models;

public class LocationOnly {
    private double latitude;
    private double longitude;

    public LocationOnly(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
