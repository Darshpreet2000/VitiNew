package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.myapplication.Connections.UserController;
import com.example.myapplication.Util.API;
import com.example.myapplication.Webrequest.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;

public class Register extends AppCompatActivity {
    EditText username,name,referal,confirmpassword,email,phonenumber;
    EditText password;
    Button signup;
    UserController userController;
    ProgressBar progressBarRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.FullName);
        referal=findViewById(R.id.referal);
        userController=new UserController(Register.this);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        email=findViewById(R.id.Email);
        progressBarRegister=findViewById(R.id.progressbarRegister);
        phonenumber=findViewById(R.id.Phonenumber);
    }
    public void Signup(View view){
        final String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        final String Name = name.getText().toString().trim();
        final String Phonenumber = phonenumber.getText().toString().trim();
        final String Confirmpassword = confirmpassword.getText().toString().trim();

        if(Password!=Confirmpassword){
            password.setError("Confirm Password must be same as Password");
            password.requestFocus();
            return;

        }
        if (Email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }


        if (Phonenumber.length() != 10) {
            phonenumber.setError("Please Enter Valid Phone Number");
            phonenumber.requestFocus();
            return;
        }
        if (Name.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        JSONArray request = generateRequest();

        Log.d("mukesh",request.toString());

        String url = API.SIGN_UP;


        userController.postWithArrayString(url , request.toString(), signUpListner);

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private final ResponseListener signUpListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
         progressBarRegister.setVisibility(View.VISIBLE);
       }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getInt("status")==200)
                {
                    Intent i = new Intent(Register.this,MainActivity.class);
                    startActivity(i);

                    finish();
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


    private JSONArray generateRequest(){

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("name", name.getText().toString());
            jsonData.put("email", email.getText().toString());
            jsonData.put("password", password.getText().toString());
            jsonData.put("password_confirmation", confirmpassword.getText().toString());

            jsonData.put("phone", phonenumber.getText().toString());

            jsonArray.put(jsonData);

            //mainObj.put("",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

}
