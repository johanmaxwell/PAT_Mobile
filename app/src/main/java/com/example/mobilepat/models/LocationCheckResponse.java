package com.example.mobilepat.models;

public class LocationCheckResponse {
    private String message;
    private int status;
    private String error;
    private ResponseData response;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public ResponseData getResponse() {
        return response;
    }

    public class ResponseData {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
