package com.example.vitinew.Webrequest;

import androidx.collection.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class StringPostRequest extends StringRequest {

    Map<String ,String> mHeader = new ArrayMap<>();
    String requestBody;

    public StringPostRequest(String  url ,  final String jsonRequest, final Response.Listener listener, final Response.ErrorListener errorListener) {
        super(Method.POST,url, listener, errorListener);
        mHeader.put("Accept", "application/json");
        mHeader.put("Content-Type", "application/json");
      //  mHeader.put("Authorization","Bearer "+token);
        this.requestBody = jsonRequest;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return requestBody == null ? null : requestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
            return null;
        }
    }


}
