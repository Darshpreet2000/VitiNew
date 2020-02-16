package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitinew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    TextView Username;
    private  void getallwidget(View view){
        Username= view.findViewById(R.id.username);
    }


    public Home() {
        // Required empty public constructor
    }

    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment
        setuptoolbar();
        View view=inflater.inflate(R.layout.fragment_home2, container, false);
        getallwidget(view);
        Username.setText("Mahavir");

        return view;

    }
    public void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.search);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }
}
