package com.example.pat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new getJSON().execute();
    }

    private class getJSON extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HTTPHandler h = new HTTPHandler();
            String u = "http://jsonplaceholder.typicode.com/users";
            String s = h.getAccess(u);
            Log.d("REST","result " +s);
            return null;
        }
    }
}