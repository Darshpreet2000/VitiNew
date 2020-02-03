package com.example.vitinew.Webrequest;

import com.android.volley.VolleyError;

public interface ResponseListener {

    void onRequestStart();

    void onSuccess(String jsonString);

    void onError(VolleyError e);

}
