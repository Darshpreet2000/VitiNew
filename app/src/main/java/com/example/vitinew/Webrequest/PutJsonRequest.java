package com.example.vitinew.Webrequest;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class PutJsonRequest extends JsonObjectRequest {



    public PutJsonRequest(String  url ,  final JSONObject jsonRequest, final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(Method.PUT,url, jsonRequest, listener, errorListener);

    }


}

