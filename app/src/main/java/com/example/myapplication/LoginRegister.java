package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
    }

    public void register(View view) {
        startActivity(new Intent(LoginRegister.this,Register.class));
    }

    public void login(View view) {

        startActivity(new Intent(LoginRegister.this,Login_activity.class));
    }
}
