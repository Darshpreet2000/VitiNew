package com.example.myapplication.Webrequest;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class PutJsonRequest extends JsonObjectRequest {

    Map<String ,String> mHeader = new ArrayMap<>();

    public PutJsonRequest(String  url , String token, final JSONObject jsonRequest, final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(Method.PUT,url, jsonRequest, listener, errorListener);
        mHeader.put("Accept", "application/json");
        mHeader.put("Content-Type", "application/json");
        mHeader.put("Authorization","Bearer "+token);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }

}

