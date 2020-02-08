package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectDetail extends AppCompatActivity {
    ProjectDisplay projectdetail;
    TextView Description;
    private JSONObject generateRequest() {
        JSONObject json = new JSONObject();
        try {
           // json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("id", projectdetail.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        Intent intent=getIntent();
      projectdetail= (ProjectDisplay) intent.getSerializableExtra("class");
        int id=projectdetail.getId();
        Log.d("idkkkk",String.valueOf(projectdetail.getId()) );
       // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        JSONObject request = generateRequest();
        UserController user = new UserController(getApplicationContext());
        user.postWithJsonRequest(API.PROJECT_DETAIL, request,ProjectDetailListner);
      Description=findViewById(R.id.ProjectDesCription);
   Description.setText(projectdetail.getDes());
    }
    private final ResponseListener ProjectDetailListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
            //addskillprogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("projectDetail",response);

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        Log.d("projectDetail",jsonObject.toString());
                        Toast.makeText(getApplicationContext(), "Added Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case "EMAIL DOES NOT EXIST":
                        Toast.makeText(getApplicationContext(), "Email Does Not EXIST", Toast.LENGTH_SHORT).show();
                        break;
                    case "PASSWORD NOT CORRECT":
                        Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_SHORT).show();
                        break;
                        default:
                            Toast.makeText(ProjectDetail.this, "Something wrong", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("projectDetail",e.toString());
            } finally {
                //   addskillprogressBar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };
}
