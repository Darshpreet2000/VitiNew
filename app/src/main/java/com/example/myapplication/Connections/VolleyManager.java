package com.example.myapplication.Connections;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * VolleyManager.java
 * <p>
 * Created by lijiankun on 17/6/6.
 */

public class VolleyManager {

    public static VolleyManager INSTANCE = null;

    public static WeakReference<Context> mWRContext = null;

    public RequestQueue mQueue = null;


    public VolleyManager(Context context) {
        if (mWRContext == null || mWRContext.get() == null) {
            mWRContext = new WeakReference<>(context);
        }
        mQueue = getRequestQueue();
    }

    public static VolleyManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (VolleyManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VolleyManager(context);
                }
            }
        }
        return INSTANCE;
    }



    public void addRequest(Request request) {
        if (request == null) {
            return;
        }
        mQueue.add(request);
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null && mWRContext != null && mWRContext.get() != null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mQueue = Volley.newRequestQueue(mWRContext.get().getApplicationContext());
        }
        return mQueue;
    }
}
