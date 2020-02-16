package com.example.vitinew.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Login_activity;

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
        // Inflate the projectlist for this fragment
        Intent intent=new Intent(getContext(),Login_activity.class);
        startActivity(intent);
        getActivity().finish();
     SaveSharedPreference.clear(getActivity());
        return inflater.inflate(null, container, false);
    }

}
