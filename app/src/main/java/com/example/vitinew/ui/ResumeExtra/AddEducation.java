package com.example.vitinew.ui.ResumeExtra;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEducation extends Fragment {

    Spinner type;
    EditText name,course,start,end;
    Button addedu;
    ProgressBar progressbaraddedu;
    ArrayList<String> arrayList = new ArrayList<>();

    String Type,Name,Course,Start,End;

    public AddEducation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_add_education, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type=view.findViewById(R.id.type);
        name=view.findViewById(R.id.name);
        course=view.findViewById(R.id.course);
        start=view.findViewById(R.id.start);
        progressbaraddedu=view.findViewById(R.id.progressbaraddedu);
        end=view.findViewById(R.id.end);
        setupspinner();
        addedu=view.findViewById(R.id.addedubutton);
     addedu.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Type=type.getSelectedItem().toString();
             Name=name.getText().toString();
             Course=course.getText().toString();
             Start=start.getText().toString();
             End=end.getText().toString();
             if(Type.isEmpty()||Name.isEmpty()||Course.isEmpty()||Start.isEmpty()||End.isEmpty()){
                 Toast.makeText(getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                 return;
             }
             UserController userController;
             userController=new UserController(getContext());
             JSONObject request = generateRequest();
             userController.postWithJsonRequest(API.UPDATEEDUCATION,request,AddEducationListner);
         }
     });
    }
    private void setupspinner(){
        arrayList.add("School");
        arrayList.add("High School");
        arrayList.add("Degree");
        arrayList.add("Post Graduation");
        arrayList.add("Diploma");
        arrayList.add("Phd");
        arrayList.add("Certification");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList){};
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(arrayAdapter);
        type.setPrompt("Choose Category");
    }
    private JSONObject generateRequest(){
        JSONObject json=new JSONObject();
        try {
            json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("type",Type);
            json.put("name",Name);
            json.put("course",Course);
            json.put("start",Start);
            json.put("end",End);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    private final ResponseListener AddEducationListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressbaraddedu.setVisibility(View.VISIBLE);
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
                progressbaraddedu.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressbaraddedu.setVisibility(GONE);

        }
    };
}
