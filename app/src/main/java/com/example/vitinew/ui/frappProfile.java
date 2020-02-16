package com.example.vitinew.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.example.vitinew.gigsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class frappProfile extends Fragment {

    UserController user;
    Toolbar toolbar;
    EditText phone, city, address, name, zip, state;
    ProgressBar progressBarRegister;
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
    int GET_FROM_GALLERY = 1;

    public frappProfile() {
        // Required empty public constructor
    }

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
        user = new UserController(getContext());
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();

                user.postWithJsonRequest(API.PROFILE, json, responseListener);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getallwidgets() {
        name = getView().findViewById(R.id.name);
        city = getView().findViewById(R.id.city);
        state = getView().findViewById(R.id.state);
        zip = getView().findViewById(R.id.zip);
        phone = getView().findViewById(R.id.phone_number);
        address = getView().findViewById(R.id.address);
        upload = getView().findViewById(R.id.upload);
        save = getView().findViewById(R.id.save);
        progressBarRegister = getView().findViewById(R.id.progressbar);
    }

    private void setuptoolbar() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
