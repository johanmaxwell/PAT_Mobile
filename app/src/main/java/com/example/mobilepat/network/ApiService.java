package com.example.mobilepat.network;

import com.example.mobilepat.models.Email;
import com.example.mobilepat.models.GenericResponse;
import com.example.mobilepat.models.HistoryResponse;
import com.example.mobilepat.models.LocationCheckResponse;
import com.example.mobilepat.models.LocationOnly;
import com.example.mobilepat.models.LocationRequest;
import com.example.mobilepat.models.LoginRequest;
import com.example.mobilepat.models.LoginResponse;
import com.example.mobilepat.models.SignUpRequest;
import com.example.mobilepat.models.SignUpResponse;
import com.example.mobilepat.models.UploadResponse;
import com.example.mobilepat.models.UserResponse;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/signup_pegawai")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/user")
    Call<UserResponse> getUserInfo();

    @Multipart
    @POST("/entry_kehadiran")
    Call<UploadResponse> uploadPhotoAndSubmitAttendance(
            @Part MultipartBody.Part file,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("nip") RequestBody nip
    );

    @Multipart
    @POST("/entry_pulang")
    Call<UploadResponse> uploadPhotoAndSubmitLeaving(
            @Part MultipartBody.Part file,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("nip") RequestBody nip
    );

    @GET("/history_absensi")
    Call<HistoryResponse> getHistoryAbsensi(@Query("nip") String nip);

    @POST("/cek_lokasi")
    Call<LocationCheckResponse> checkLocation(@Body LocationRequest locationRequest);

    @POST("/cek_lokasi_only")
    Call<LocationCheckResponse> checkLocationOnly(@Body LocationOnly locationOnly);

    @POST("/send_reset_password")
    Call<GenericResponse> sendResetPasswordLink(@Body Email email);

    @POST("/logout")
    Call<GenericResponse> logout();

}