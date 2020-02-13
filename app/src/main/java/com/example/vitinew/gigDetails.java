package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class gigDetails extends AppCompatActivity {
    TextView gigsdetail;
    TextView task;
    String tasklist= "";
 UserController userController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);
        gigsdetail=findViewById(R.id.gigsdescriptionDetail);
        Intent intent=getIntent();
        gigsClass gig= (gigsClass) intent.getSerializableExtra("class");
        gigsdetail.setText(gig.getDescription());
       task=findViewById(R.id.tasks);
        userController = new UserController(gigDetails.this);
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("id",String.valueOf(gig.getId()));
        userController.getRequest(dataMap, API.GigsDetails,responseListener);
    }



    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":
                        JSONArray tasks=jsonObject.getJSONArray("tasks");
                        for(int i=0;i<tasks.length();i++){
                         JSONObject obj= (JSONObject) tasks.get(i);
                         tasklist=tasklist+ (obj.getString("task"))+"\n"+"\n";
                        }
                        task.setText(tasklist);
                        break;
                    default:
                        Toast.makeText(gigDetails.this, "something wrong", Toast.LENGTH_SHORT).show();
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




}
