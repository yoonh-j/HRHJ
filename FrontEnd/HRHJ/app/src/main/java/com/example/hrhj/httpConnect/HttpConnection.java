package com.example.hrhj.httpConnect;

import com.example.hrhj.domain.Post.Post;
import com.example.hrhj.domain.User;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {

    public static final String url = "http://192.168.0.3:8080";
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();

    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection() { this.client = new OkHttpClient(); }

    public void getPostList(int uid, Callback callback){

        Gson gson = new Gson();
        String json = gson.toJson(uid);

        Request request = new Request.Builder()
                .url(url+"/getpostlist")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(request).enqueue(callback);
    }



    public void savePost(Post post, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(post);

        Request request = new Request.Builder()
                .url(url+"/savepost")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();


        client.newCall(request).enqueue(callback);
    }

    public void deletePost(Post post, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(post);

        Request request = new Request.Builder()
                .url(url+"/deletepost")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();


        client.newCall(request).enqueue(callback);
    }

    public void updatePost(Post post, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(post);

        Request request = new Request.Builder()
                .url(url+"/updatepost")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();


        client.newCall(request).enqueue(callback);
    }

    public void saveImage(File image, Callback callback) {


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image",image.getName(),RequestBody.create(MultipartBody.FORM,image))
                .build();

        Request request = new Request.Builder()
                .url(url+"/saveimage")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }


}
