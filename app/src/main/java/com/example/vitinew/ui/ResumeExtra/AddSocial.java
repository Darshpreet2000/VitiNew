package com.example.vitinew.ui.ResumeExtra;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSocial extends Fragment {
EditText fb,twitter,linkedin,github,insta;
Button add;
    public AddSocial() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       fb=view.findViewById(R.id.fb);
        twitter=view.findViewById(R.id.twitter);
        linkedin=view.findViewById(R.id.linkedin);
        github=view.findViewById(R.id.github);
        insta=view.findViewById(R.id.insta);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController user =new UserController(getContext());
                JSONObject request=generateRequest();
                user.postWithJsonRequest(API.UpdateSocial,request,AddEducationListner);
            }
        });
    }

    private final ResponseListener AddEducationListner = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":
                        Toast.makeText(getContext(), "Added Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case "EMAIL DOES NOT EXIST":
                        Toast.makeText(getContext(), "Email Does Not EXIST", Toast.LENGTH_SHORT).show();
                        break;
                    case "PASSWORD NOT CORRECT":
                        Toast.makeText(getContext(), "invalid", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };


    private JSONObject generateRequest(){
        JSONObject json=new JSONObject();
        try {
            json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("fb",fb.getText().toString());
            json.put("twitter",twitter.getText().toString());
            json.put("linkedin",linkedin.getText().toString());
            json.put("github",github.getText().toString());
            json.put("insta",insta.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_add_social, container, false);
    }

}
