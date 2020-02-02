package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.myapplication.Classes.SaveSharedPreference;
import com.example.myapplication.Connections.UserController;
import com.example.myapplication.Util.API;
import com.example.myapplication.Util.Constants;
import com.example.myapplication.Webrequest.ResponseListener;
import com.example.myapplication.ui.Extras.BaseActivity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;

public class Login_activity extends BaseActivity {
    EditText username;
    EditText passwordEt;
    String email=null;
    String password=null;
    UserController userController;
    ProgressBar progressBarRegister;


    public void Register(View view){
        Intent intent=new Intent(this,Register.class);

        startActivity(intent);

    }
    public  void Loginhere(View view){
        progressBarRegister.setVisibility(View.VISIBLE);

        email=username.getText().toString();
        password=passwordEt.getText().toString();
        /*Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);*/
        if (email.isEmpty()) {
            username.setError("Email is required");
            username.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordEt.setError("Password is required");
            passwordEt.requestFocus();
            return;
        }
        JSONObject request = generateRequest();

        Log.d("mukesh",request.toString());

        String url = API.LOGIN;


        userController.postWithJsonRequest(url , request, LogInListner);
        //progressBarRegister.setVisibility(GONE);





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        username=findViewById(R.id.username);
        passwordEt=findViewById(R.id.password);
        progressBarRegister=findViewById(R.id.progressBarLogin);

        userController=new UserController(Login_activity.this);
        progressBarRegister.setVisibility(GONE);

        if(SaveSharedPreference.getUserName(Login_activity.this).length() == 0)
        {
            // call Login Activity
        }
        else
        { startActivity(new Intent(Login_activity.this,MainActivity.class));
            // Stay at the current activity.
        }


    }
    private final ResponseListener LogInListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarRegister.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":
                        JSONObject user=new JSONObject();
                        user= jsonObject.getJSONObject("user");
                        int id=user.getInt("id");
                        String name=user.getString("name");
                        Intent i = new Intent(Login_activity.this,MainActivity.class);
                        SaveSharedPreference.setUserName(Login_activity.this,name);
                        SaveSharedPreference.setUserId(Login_activity.this,id);
                        Toast.makeText(Login_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();

                        break;
                    case "EMAIL DOES NOT EXIST":
                        Toast.makeText(Login_activity.this, "Email Does Not EXIST", Toast.LENGTH_SHORT).show();
                        break;
                    case "PASSWORD NOT CORRECT":
                        Toast.makeText(Login_activity.this, "invalid", Toast.LENGTH_SHORT).show();
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


    private JSONObject generateRequest(){

        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("email", email);


            jsonData.put("password", password);



            //mainObj.put("",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonData;
    }

}
