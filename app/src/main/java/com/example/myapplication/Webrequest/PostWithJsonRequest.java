package com.example.myapplication.Webrequest;

import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class PostWithJsonRequest  extends JsonObjectRequest {

    public PostWithJsonRequest(String  url ,  final JSONObject jsonRequest, final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(Method.POST,url, jsonRequest, listener, errorListener);
        Log.v("url",url);
    }
}
