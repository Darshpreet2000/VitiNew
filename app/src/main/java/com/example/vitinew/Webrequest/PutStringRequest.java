package com.example.vitinew.Webrequest;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class PutStringRequest extends StringRequest {

    Map<String,String> mHeader = new ArrayMap<>();

    public PutStringRequest(Map<String,String> pHeader, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.PUT,url, listener, errorListener);
        this.mHeader = pHeader;

    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }


}
