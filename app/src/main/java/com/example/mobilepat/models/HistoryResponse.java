package com.example.mobilepat.models;

import java.util.List;

public class HistoryResponse {
    private String message;
    private int status;
    private String error;
    private List<LogAbsensi> response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<LogAbsensi> getResponse() {
        return response;
    }

    public void setResponse(List<LogAbsensi> response) {
        this.response = response;
    }

    public class LogAbsensi {
        private String waktu_masuk;
        private String waktu_pulang;
        private String status;

        public String getWaktu_masuk() {
            return waktu_masuk;
        }

        public void setWaktu_masuk(String waktu_masuk) {
            this.waktu_masuk = waktu_masuk;
        }

        public String getWaktu_pulang() {
            return waktu_pulang;
        }

        public void setWaktu_pulang(String waktu_pulang) {
            this.waktu_pulang = waktu_pulang;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

