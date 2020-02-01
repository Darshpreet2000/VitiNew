package com.example.myapplication.Webrequest;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class PutJsonRequest extends JsonObjectRequest {



    public PutJsonRequest(String  url ,  final JSONObject jsonRequest, final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(Method.PUT,url, jsonRequest, listener, errorListener);

    }


}

