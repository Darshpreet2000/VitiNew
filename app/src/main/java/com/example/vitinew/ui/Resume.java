package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Resume extends Fragment {
    String skilladded;
    int ratingadded;
    Toolbar toolbar;
    UserController userController;
    ListView addskillList;
    Button addskills,addeducation;
    public Resume() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addskills=getActivity().findViewById(R.id.Add_Skills);
        addskillList=view.findViewById(R.id.addskillList);
        addskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addSkill);
            }
        });
        addeducation=view.findViewById(R.id.add_education);
        addeducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addEducation);
            }
        });
       userController=new UserController(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume2, container, false);
    }
    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
