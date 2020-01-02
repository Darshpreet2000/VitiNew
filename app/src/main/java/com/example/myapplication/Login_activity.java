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

    public void Register(View view){
        Intent intent=new Intent(this,Register.class);

        startActivity(intent);

    }
    public  void Loginhere(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        if(username.getText().toString()=="12345"&&password.getText().toString()=="12345"){
          //  Intent intent=new Intent(this,MainActivity.class);
          //  startActivity(intent);
        }
        else{
          //  Snackbar.make(view,"please check your password or username",Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
    }
}
