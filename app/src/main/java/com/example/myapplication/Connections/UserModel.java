package com.example.myapplication.Connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.Webrequest.GetRequest;
import com.example.myapplication.Webrequest.PostWithJsonRequest;
import com.example.myapplication.Webrequest.PutJsonRequest;
import com.example.myapplication.Webrequest.PutStringRequest;
import com.example.myapplication.Webrequest.ResponseListener;
import com.example.myapplication.Webrequest.StringPostRequest;

import org.json.JSONObject;

import java.util.Map;

public class UserModel {

    SharedPreferences mSharedPreferences;
    private Context mContext;

    UserModel(final Context pContext) {
        mContext = pContext;
        mSharedPreferences = mContext.getSharedPreferences(SyncStateContract.Constants.ACCOUNT_NAME, 0);
    }



    // API FOR POST


    void postWithUrlEncodedRequest(Map<String,String> pHeader, Map<String,String> pParm, String url , final ResponseListener mUserResponseListener){
        VolleyManager.getInstance(mContext).addRequest(new com.example.myapplication.Webrequest.PostWithUrlEncodedRequest(pHeader,pParm,url, new Response.Listener<String>() {

            @Override
            public void onResponse(String res) {
                if (mUserResponseListener!=null){
                    mUserResponseListener.onSuccess(res);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mUserResponseListener != null) {
                    mUserResponseListener.onError(error);
                }
            }
        }));
    }





    /**
     * Api For get
     *
     */

    void getRequest(Map<String,String> pHeader,Map<String,String> pParms,String url , final ResponseListener mUserResponseListener){
        VolleyManager.getInstance(mContext).addRequest(new GetRequest(pHeader,pParms,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mUserResponseListener!=null){
                    mUserResponseListener.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mUserResponseListener != null) {
                    mUserResponseListener.onError(error);
                }
            }
        }));
    }





    // API FOR POST REQUEST


    void postWithJsonRequest(String url , String token, JSONObject jsonObject, final ResponseListener mUserResponseListener){
        VolleyManager.getInstance(mContext).addRequest(new PostWithJsonRequest(url,token,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {

                if (mUserResponseListener!=null){
                    mUserResponseListener.onSuccess(res.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mUserResponseListener != null) {
                    mUserResponseListener.onError(error);
                }
            }
        }));
    }



    /**
     * Api For PUT String Request
     *
     */

    void putRequest(Map<String,String> pHeader,String url , final ResponseListener mUserResponseListener){
        VolleyManager.getInstance(mContext).addRequest(new PutStringRequest(pHeader,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (mUserResponseListener!=null){
                    mUserResponseListener.onSuccess(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mUserResponseListener != null) {
                    mUserResponseListener.onError(error);
                }
            }
        }));
    }








    // API FOR PUT JSON REQUEST


    void putWithJsonRequest(String url , String token, JSONObject jsonObject, final ResponseListener mUserResponseListener){
        VolleyManager.getInstance(mContext).addRequest(new PutJsonRequest(url,token,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {

                if (mUserResponseListener!=null){
                    mUserResponseListener.onSuccess(res.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mUserResponseListener != null) {
                    mUserResponseListener.onError(error);
                }
            }
        }));
    }






    /**
     * Api For JSON ARRAY REQUEST
     *
     */


    void postStringRequest(String url ,  String request, final ResponseListener mUserResponseListener){
        VolleyManager.getInstance(mContext).addRequest(new StringPostRequest(url,request,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (mUserResponseListener!=null){
                    mUserResponseListener.onSuccess(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mUserResponseListener != null) {
                    mUserResponseListener.onError(error);
                }
            }
        }));
    }



}