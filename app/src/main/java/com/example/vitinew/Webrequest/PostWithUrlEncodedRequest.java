package com.example.vitinew.Webrequest;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class PostWithUrlEncodedRequest extends StringRequest {

    Map<String,String> mHeader = new ArrayMap<>();
    Map<String,String> mParms = new ArrayMap<>();

    public PostWithUrlEncodedRequest(Map<String,String> pHeader, Map<String,String> pParms, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,url, listener, errorListener);
        this.mHeader = pHeader;
        this.mParms = pParms;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParms;
    }


}
