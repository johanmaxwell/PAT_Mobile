package com.example.mobilepat.models;

public class LocationRequest {
    private double latitude;
    private double longitude;
    private String nipPegawai;


    public LocationRequest(double latitude, double longitude, String nipPegawai) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nipPegawai = nipPegawai;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNipPegawai() {
        return nipPegawai;
    }
}
