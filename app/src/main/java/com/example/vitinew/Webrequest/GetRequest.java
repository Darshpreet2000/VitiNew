package com.example.vitinew.Webrequest;

import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class GetRequest  extends StringRequest {

    Map<String,String> mParms = new ArrayMap<>();

    public GetRequest(Map<String,String> pParms,String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,url, listener, errorListener);

        this.mParms = pParms;

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParms;
    }


}
