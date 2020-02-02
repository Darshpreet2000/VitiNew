package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;

public class Register extends BaseActivity {
    EditText username,name,referal,confirmpassword,email,phonenumber;
    EditText password;
    Button signup;
    UserController userController;
    ProgressBar progressBarRegister;
    public  void  Login(View view){
        Intent intent=new Intent(this,Login_activity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(SaveSharedPreference.getUserName(Register.this).length() == 0)
        {
            // call Login Activity
        }
        else
        { startActivity(new Intent(Register.this,MainActivity.class));
            // Stay at the current activity.
        }
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

        if(!Password.equals(Confirmpassword)){
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
        JSONObject request = generateRequest();

        Log.d("mukesh",request.toString());

        String url = API.SIGN_UP;


        userController.postWithJsonRequest(url , request, signUpListner);

    }
    private final ResponseListener signUpListner = new ResponseListener() {

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
                    setIntInSettings("id",id);
                    SaveSharedPreference.setUserName(Register.this,name);
                    setStringInSettings("name",name);
                    setIntInSettings(Constants.LoginStatus,1);
                    Intent i = new Intent(Register.this,MainActivity.class);
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();

                    break;
                case "REFRAL CODE DOES NOT EXIST":
                    Toast.makeText(Register.this, "REFRAL CODE DOES NOT EXIST", Toast.LENGTH_SHORT).show();
                            break;
                case "EMAIL ALREADY EXISTS":
                    Toast.makeText(Register.this, "EMAIL ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                    break;
                case "PASSWORDS DO NOT MATCH":
                    Toast.makeText(Register.this, "PASSWORDS DO NOT MATCH", Toast.LENGTH_SHORT).show();
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
            jsonData.put("name", name.getText().toString());
            if(!referal.getText().toString().isEmpty())
            jsonData.put("ref_by", referal.getText().toString());
            jsonData.put("email", email.getText().toString());
            jsonData.put("password", password.getText().toString());
            jsonData.put("password_confirmation", confirmpassword.getText().toString());
            jsonData.put("phone", phonenumber.getText().toString());


            //mainObj.put("",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonData;
    }

}
