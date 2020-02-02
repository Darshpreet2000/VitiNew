package com.example.myapplication.Connections;

import android.content.Context;

import com.example.myapplication.Webrequest.ResponseListener;

import org.json.JSONObject;

import java.util.Map;

public class UserController {

    private final Context mContext;
    private UserModel mUserModel;


    public UserController(final Context pContext) {
        mContext = pContext;
        mUserModel = new UserModel(pContext);
    }


    /**
     * Request for post with header
     */




    /**
     * Request for GET with header
     */


    public void getRequest(Map<String, String> pHeader, Map<String, String> pParms, String url, ResponseListener pResponseListener) {
        if (pResponseListener != null) {
            pResponseListener.onRequestStart();
        }
        mUserModel.getRequest(pHeader, pParms, url, pResponseListener);
    }


    /**
     * Request for Post with json object
     */

    public void postWithJsonRequest(String url,  JSONObject jsonObject, ResponseListener pResponseListener) {
        if (pResponseListener != null) {
            pResponseListener.onRequestStart();
        }
        mUserModel.postWithJsonRequest(url,jsonObject, pResponseListener);
    }


    /**
     * Request for PUT with header
     */







    /**
     * Request for Post with json Array
     */

    public void postWithArrayString(String url, String jsonArray, ResponseListener pResponseListener) {
        if (pResponseListener != null) {
            pResponseListener.onRequestStart();
        }
        mUserModel.postStringRequest(url,  jsonArray, pResponseListener);
    }


}