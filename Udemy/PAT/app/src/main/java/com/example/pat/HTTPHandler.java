package com.example.pat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPHandler {
    public HTTPHandler() {

    }

    public String getAccess(String url){
        String response = null;
        URL u = null;
        try{
            u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            InputStream i = new BufferedInputStream(conn.getInputStream());
            response = new String(i.toString());
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public String postAccess(String url){
        String response = null;

        return response;
    }
}
