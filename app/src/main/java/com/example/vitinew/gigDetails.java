package com.example.vitinew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    gigsClass gig=new gigsClass();
    Button apply;
    String tasklist= "";
 UserController userController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);
        gigsdetail=findViewById(R.id.gigsdescriptionDetail);
        Intent intent=getIntent();
      gig= (gigsClass) intent.getSerializableExtra("class");
        gigsdetail.setText(gig.getDescription());
        task=findViewById(R.id.tasks);
        userController = new UserController(gigDetails.this);
        Map<String, String> dataMap = new HashMap<String,String>();
Log.v("",""+"gig id is"+gig.getId());
        dataMap.put("id",String.valueOf(gig.getId()));
        userController.getRequest(dataMap, API.GigsDetails,responseListener);
      apply=findViewById(R.id.applygig);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    applynow();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                String image=jsonObject.getString("image");
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




    private void applynow() throws JSONException {
        JSONObject jsn=new JSONObject();
       Log.v("",""+"uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))+"  id="+String.valueOf(gig.getId()));
        Toast.makeText(this, "uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))+"  id="+String.valueOf(gig.getId()), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, "uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))+"  id="+String.valueOf(gig.getId()), Toast.LENGTH_SHORT).show();
        jsn.put("id",String.valueOf(gig.getId()));
        jsn.put("uid",String.valueOf(SaveSharedPreference.getUserId(gigDetails.this)));
        jsn.put("id",String.valueOf(gig.getId()));
       userController.postWithJsonRequest(API.GIGSAPPLY,jsn,applyListener);
    }

    private final ResponseListener applyListener = new ResponseListener() {

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

                        Toast.makeText(gigDetails.this, "Applied Successfulley", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(gigDetails.this, "something wrong", Toast.LENGTH_SHORT).show();

        }
    };


}
