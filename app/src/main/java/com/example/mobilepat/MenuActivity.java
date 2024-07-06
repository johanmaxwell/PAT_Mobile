package com.example.mobilepat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilepat.models.GenericResponse;
import com.example.mobilepat.models.UserResponse;
import com.example.mobilepat.network.ApiService;
import com.example.mobilepat.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private TextView textViewHello;
    private LinearLayout buttonHadir;
    private LinearLayout buttonPulang;
    private LinearLayout buttonHistory;
    private LinearLayout buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textViewHello = findViewById(R.id.helloUsername);
        buttonHadir = findViewById(R.id.kehadiranButton);
        buttonPulang = findViewById(R.id.kepulanganButton);
        buttonHistory = findViewById(R.id.historyButton);
        buttonLogout = findViewById(R.id.logoutButton);

        fetchUsernameFromServer();

        buttonHadir.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, EntryKehadiranActivity.class);
            startActivity(intent);
            finish();
        });

        buttonPulang.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, EntryKepulanganActivity.class);
            startActivity(intent);
            finish();
        });

        buttonHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HistoryActivity.class);
            startActivity(intent);
            finish();
        });

        buttonLogout.setOnClickListener(v -> {
            logout();
        });
    }

    private void logout() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<GenericResponse> call = apiService.logout();
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MenuActivity.this, "Logout Succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MenuActivity.this, "Logout Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Logout Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUsernameFromServer() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<UserResponse> call = apiService.getUserInfo();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        textViewHello.setText("Hello, " + userResponse.getUsername());
                    } else {
                        Toast.makeText(MenuActivity.this, "User info not found", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity if user info is not available
                    }
                } else {
                    Toast.makeText(MenuActivity.this, "Failed to retrieve user info", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity if unauthorized
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
