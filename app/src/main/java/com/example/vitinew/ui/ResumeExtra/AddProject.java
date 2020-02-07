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
import android.widget.ProgressBar;
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
public class AddProject extends Fragment {

EditText title,desc;
Button addbutton;
ProgressBar progressBar;
    public AddProject() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_add_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      progressBar=view.findViewById(R.id.progressbar);
        title=view.findViewById(R.id.title);
        desc=view.findViewById(R.id.description);
        UserController userController=new UserController(getContext());
        addbutton=view.findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject request=generateRequest();
                userController.postWithJsonRequest(API.UPDATEPROJECT,request,AddProjectListner);
            }
        });
    }
    private JSONObject generateRequest(){
        JSONObject json=new JSONObject();
        try {
            json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("title",title.getText().toString());
            json.put("projdes",desc.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    private final ResponseListener AddProjectListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBar.setVisibility(GONE);

        }
    };
}
