package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class Login_activity extends AppCompatActivity {
    EditText username;
    EditText password;

    public  void Loginhere(View view){
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        if(username.getText().toString()=="12345"&&password.getText().toString()=="12345"){
            setContentView(R.layout.activity_main);
        }
        else{
            Snackbar.make(view,"please check your password or username",Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
    }
}
