package com.example.myapplication.ui.Extras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.Connections.UserController;
import com.example.myapplication.Util.Constants;

public class BaseActivity extends AppCompatActivity {
    UserController userController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userController = new UserController(BaseActivity.this);
    }

    protected void setStringInSettings(String key, String value) {
        SharedPreferences settings = getSharedPreferences(Constants.preferences, 0);
        SharedPreferences.Editor settingsWriter = settings.edit();
        settingsWriter.putString(key, value);
        settingsWriter.commit();
    }

    protected String getStringFromSettings(String key) {
        SharedPreferences settings = getSharedPreferences(Constants.preferences, 0);
        return settings.getString(key, "");
    }


    protected void setIntInSettings(String key, int value) {
        SharedPreferences settings = getSharedPreferences(Constants.preferences, 0);
        SharedPreferences.Editor settingsWriter = settings.edit();
        settingsWriter.putInt(key, value);
        settingsWriter.commit();
    }

    protected int getIntFromSettings(String key) {
        SharedPreferences settings = getSharedPreferences(Constants.preferences, 0);
        return settings.getInt(key, 0);
    }
}
