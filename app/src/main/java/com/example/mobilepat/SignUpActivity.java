package com.example.mobilepat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilepat.models.SignUpRequest;
import com.example.mobilepat.models.SignUpResponse;
import com.example.mobilepat.network.ApiService;
import com.example.mobilepat.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword, editTextEmail, editTextNIP, editTextPhone;
    private Button buttonSignUp;
    private TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.signupUsername);
        editTextPassword = findViewById(R.id.signupPassword);
        editTextEmail = findViewById(R.id.signupEmail);
        editTextNIP = findViewById(R.id.signupNIP);
        editTextPhone = findViewById(R.id.signupPhone);
        buttonSignUp = findViewById(R.id.signupButton);
        goToLogin = findViewById(R.id.loginLink);

        buttonSignUp.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String NIP = editTextNIP.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !NIP.isEmpty() && !phone.isEmpty()) {
                signUp(username, password, email,  NIP, phone);
            } else {
                Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

        goToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void signUp(String username, String password, String email, String NIP, String phone) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SignUpRequest signUpRequest = new SignUpRequest(username, password, email, NIP, phone);

        Call<SignUpResponse> call = apiService.signUp(signUpRequest);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Sign Up Failed";
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        errorMessage = errorObject.getString("message");
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
