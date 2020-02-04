package com.example.vitinew.Webrequest;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class PostWithJsonRequest  extends JsonObjectRequest {

    public PostWithJsonRequest(String  url ,  final JSONObject jsonRequest, final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(Method.POST,url, jsonRequest, listener, errorListener);
        Log.v("url",url);
    }
}
