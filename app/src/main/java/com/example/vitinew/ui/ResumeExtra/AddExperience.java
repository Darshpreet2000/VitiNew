package com.example.vitinew.ui.ResumeExtra;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitinew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExperience extends Fragment {


    public AddExperience() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_experience, container, false);
    }

}
