
package com.example.mobilepat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.mobilepat.models.LocationCheckResponse;
import com.example.mobilepat.models.LocationRequest;
import com.example.mobilepat.models.UploadResponse;
import com.example.mobilepat.models.UserResponse;
import com.example.mobilepat.network.ApiService;
import com.example.mobilepat.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryKehadiranActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 2;

    private ImageView imageView;
    private Button buttonOpenCamera;
    private Button buttonSubmit;
    private ImageButton backButton;

    private Uri photoURI;
    private File photoFile;
    private double latitude;
    private double longitude;
    private String nipPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_kehadiran);

        imageView = findViewById(R.id.imageView);
        buttonOpenCamera = findViewById(R.id.buttonOpenCamera);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        backButton = findViewById(R.id.backButton);

        buttonOpenCamera.setOnClickListener(v -> openCamera());
        buttonSubmit.setOnClickListener(v -> uploadPhotoAndSubmit());

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EntryKehadiranActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(this, "com.example.mobilepat.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Camera not supported", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoFile != null && photoFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
                // No need to upload here for the example, you can integrate it as needed
            } else {
                Toast.makeText(this, "Error capturing image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Handle location permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission given", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to upload photo and submit attendance
    private void uploadPhotoAndSubmit() {
        if (photoFile == null || !photoFile.exists()) {
            Toast.makeText(this, "Take a photo first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Retrofit instance and API service
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Get location data
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(EntryKehadiranActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    latitude = lastKnownLocation.getLatitude();
                    longitude = lastKnownLocation.getLongitude();
                }
            }
        }

        // Fetch user info to get nipPegawai first
        Call<UserResponse> callUser = apiService.getUserInfo();
        callUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        nipPegawai = userResponse.getNip();

                        // Prepare location data with nipPegawai
                        LocationRequest locationRequest = new LocationRequest(latitude, longitude, nipPegawai);

                        // Make API call to check location
                        Call<LocationCheckResponse> call1 = apiService.checkLocation(locationRequest);
                        call1.enqueue(new Callback<LocationCheckResponse>() {
                            @Override
                            public void onResponse(Call<LocationCheckResponse> call, Response<LocationCheckResponse> response) {
                                if (response.isSuccessful()) {
                                    LocationCheckResponse locationCheckResponse = response.body();
                                    if (locationCheckResponse != null && "Verified".equals(locationCheckResponse.getMessage())) {
                                        // Location verified, proceed to upload photo
                                        uploadPhoto(apiService);
                                    } else {
                                        Toast.makeText(EntryKehadiranActivity.this, "Location verification failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    String errorMessage = "Location verification failed";
                                    try {
                                        if (response.errorBody() != null) {
                                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                                            errorMessage = errorObject.getString("message");
                                        }
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(EntryKehadiranActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LocationCheckResponse> call, Throwable t) {
                                Toast.makeText(EntryKehadiranActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(EntryKehadiranActivity.this, "User info not found", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity if user info is not available
                    }
                } else {
                    String errorMessage = "Failed to retrieve user info";
                    try {
                        if (response.errorBody() != null) {
                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                            errorMessage = errorObject.getString("message");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EntryKehadiranActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    finish(); // Close activity if unauthorized
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(EntryKehadiranActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadPhoto(ApiService apiService) {
        // Prepare photo file for upload
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto_bukti_masuk", photoFile.getName(), requestFile);

        // Prepare form data for photo upload
        RequestBody latitudeRB = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
        RequestBody longitudeRB = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));
        RequestBody nipRB = RequestBody.create(MediaType.parse("text/plain"), nipPegawai);

        // Make API call to upload photo
        Call<UploadResponse> call = apiService.uploadPhotoAndSubmitAttendance(body, latitudeRB, longitudeRB, nipRB);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.isSuccessful()) {
                    UploadResponse uploadResponse = response.body();
                    if (uploadResponse != null) {
                        Toast.makeText(EntryKehadiranActivity.this, uploadResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EntryKehadiranActivity.this, "Unexpected error: Response body is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = "Photo upload failed";
                    try {
                        if (response.errorBody() != null) {
                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                            errorMessage = errorObject.getString("message");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EntryKehadiranActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Toast.makeText(EntryKehadiranActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

