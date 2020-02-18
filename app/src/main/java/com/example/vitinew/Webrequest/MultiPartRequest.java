package com.example.vitinew.Webrequest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MultiPartRequest extends Request<String> {

    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    private HttpEntity httpentity;
    private static final String FILE_PART_NAME = "profile_image";
    private final Response.Listener<String> mListener;
    private final File mFilePart;
    Map<String, String> header;

    public MultiPartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, File file,Map<String, String> header)
    {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        this.header = header;
        buildMultipartEntity();
        httpentity = entity.build();
    }

    private void buildMultipartEntity()
    {
        if (mFilePart!=null){
            entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
        }

    }

    @Override
    public String getBodyContentType()
    {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            httpentity.writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        String s = new String(response.data);
        return Response.success(s, getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response)
    {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (header == null) {
            return super.getHeaders();
        } else {
            return header;
        }
    }
}