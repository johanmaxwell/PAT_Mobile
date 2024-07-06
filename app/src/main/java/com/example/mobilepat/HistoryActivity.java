package com.example.mobilepat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepat.models.HistoryResponse;
import com.example.mobilepat.models.UserResponse;
import com.example.mobilepat.network.ApiService;
import com.example.mobilepat.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AbsensiAdapter absensiAdapter;
    private String nipPegawai;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.backButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        fetchUserInfo();
    }

    private void fetchUserInfo() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<UserResponse> call = apiService.getUserInfo();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        nipPegawai = userResponse.getNip();
                        fetchHistoryAbsensi(nipPegawai); // Fetch history after getting user info
                    } else {
                        Toast.makeText(HistoryActivity.this, "User info not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(HistoryActivity.this, "Failed to retrieve user info", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchHistoryAbsensi(String nip) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<HistoryResponse> call = apiService.getHistoryAbsensi(nip);
        call.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful()) {
                    HistoryResponse historyResponse = response.body();
                    if (historyResponse != null && historyResponse.getResponse() != null) {
                        List<HistoryResponse.LogAbsensi> absensiList = historyResponse.getResponse();
                        absensiAdapter = new AbsensiAdapter(absensiList);
                        recyclerView.setAdapter(absensiAdapter);
                    } else {
                        Toast.makeText(HistoryActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = "Failed to fetch data";
                    try {
                        if (response.errorBody() != null) {
                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                            errorMessage = errorObject.getString("message");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(HistoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
