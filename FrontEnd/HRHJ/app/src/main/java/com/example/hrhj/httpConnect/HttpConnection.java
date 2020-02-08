package com.example.hrhj.httpConnect;

import okhttp3.OkHttpClient;

public class HttpConnection {

    private final String url = "http://192.168.0.3:8080";
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();

    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() { this.client = new OkHttpClient(); }


}
