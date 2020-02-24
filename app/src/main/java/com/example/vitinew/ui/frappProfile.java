package com.example.vitinew.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.MainActivity;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.MultiPartRequest;
import com.example.vitinew.Webrequest.ResponseListener;

import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class frappProfile extends Fragment {
CardView card;
    UserController user;
    Toolbar toolbar;
    File mFile;
    EditText phone, city, address, name, zip, state;
    ProgressBar progressBarRegister;
    Bitmap bitmap;
    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarRegister.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str", response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        Toast.makeText(getContext(), "Data Uploaded", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarRegister.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarRegister.setVisibility(GONE);

        }
    };
    Button upload, save;
  ImageView imageView;
    int GET_FROM_GALLERY = 1;

    public frappProfile() {
        // Required empty public constructor
    }
TextView refer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment

        setuptoolbar();
        return inflater.inflate(R.layout.fragment_frapp_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getallwidgets();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        user = new UserController(getContext());

        Map<String, String> dataMap = new HashMap<String,String>();
        Log.e("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        dataMap.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        //dataMap.put("id","4");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject request = generaterequest();
                UserController user=new UserController(getContext());
                user.postWithJsonRequest(API.PROFILE,request,responseListener);
                String path=getPath(filePath);
                      //  uploadFile(path);
                        fileUploadFunction(path);
                //createProduct(String.valueOf(SaveSharedPreference.getUserId(getContext())));
            }
        });
    }

    private JSONObject generaterequest() {
        JSONObject json = new JSONObject();
        try{
            json.put("id", SaveSharedPreference.getUserId(getContext()));
            json.put("name", name.getText());
            json.put("state",state.getText());
            json.put("city", city.getText());
            json.put("address",address.getText());
            json.put("phone", phone.getText());
            json.put("zip_code", zip.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getallwidgets() {
        name = getView().findViewById(R.id.name);

        CardView card;
        card=getView().findViewById(R.id.card_view);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        }); city = getView().findViewById(R.id.city);
        state = getView().findViewById(R.id.state);
        zip = getView().findViewById(R.id.zip);
        phone = getView().findViewById(R.id.phone_number);
        address = getView().findViewById(R.id.address);
        upload = getView().findViewById(R.id.upload);
        save = getView().findViewById(R.id.save);
        progressBarRegister = getView().findViewById(R.id.progressbar);
        imageView=getView().findViewById(R.id.uploadimage);
    }

    private void setuptoolbar() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }







    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    //method to show file chooser
    private void showFileChooser() {
       requestStoragePermission();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createProduct(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Map<String, String> mHeader = new HashMap<>();
        MultiPartRequest mMultipartRequest = new MultiPartRequest(API.IMAGE+"?id="+data, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                String s = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_LONG).show();
                } else {
                    s = new String(error.networkResponse.data);
                }
                try {
                    JSONObject responseJsonObj = new JSONObject(s);
                    if (responseJsonObj.has("message")) {
                        Toast.makeText(getContext(), responseJsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.v("Response is",response.toString());
                    if (jsonObject.has("code")){
                        String status  = jsonObject.getString("code");
                        if (status.equals("SUCCESS")){
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, mFile,mHeader);
        mMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(mMultipartRequest);
    }
    public void fileUploadFunction(String path) {
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            MultipartUploadRequest request = new MultipartUploadRequest(getActivity(), API.IMAGE)
                    .setMethod("POST");
            request.addParameter("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
            request.addFileToUpload(path, "profile_image");
           // request.setNotificationConfig(new UploadNotificationConfig());
            request.setMaxRetries(2);

            request.startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Profile",exc.getMessage());
        }
    }
    private int uploadFile(String imagePath) {
        Log.i("PATH",imagePath);
        OkHttpClient client = new OkHttpClient();
        File fileSource = new File(imagePath);
        if (fileSource.isFile()){
            Log.i("EXIST","exist");
        }else {
            Log.i("NOT EXIST","not exist");
        }
        final MediaType MEDIA_TYPE;
        String imageType;
        if (imagePath.endsWith("png")){
            MEDIA_TYPE = MediaType.parse("image/png");
            imageType = ".png";
        }else {
            MEDIA_TYPE = MediaType.parse("image/jpeg");
            imageType = ".jpg";
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String fileName = "Image_"+timeStamp+imageType;


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id",String.valueOf(SaveSharedPreference.getUserId(getContext())))
                .addFormDataPart("profile_image",fileName, RequestBody.create(MEDIA_TYPE,fileSource))
                .build();
        Request request = new Request.Builder()
                .url(API.IMAGE)//your webservice url
                .post(requestBody)
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                Log.i("SUCC",""+response.message());
            }
            String resp = response.message();
            Log.i("MSG",resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }



}
