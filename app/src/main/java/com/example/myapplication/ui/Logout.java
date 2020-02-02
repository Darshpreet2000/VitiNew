package com.example.myapplication.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Classes.SaveSharedPreference;
import com.example.myapplication.Login_activity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logout extends Fragment {


    public Logout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent=new Intent(getContext(),Login_activity.class);
        startActivity(intent);
        getActivity().finish();
        SaveSharedPreference.setUserName(getActivity(),"");
        ///delete shared preferences here

        return inflater.inflate(null, container, false);
    }

}
