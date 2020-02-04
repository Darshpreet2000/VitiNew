package com.example.vitinew.ui;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Adapters.addskilladapter;
import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Login_activity;
import com.example.vitinew.MainActivity;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Resume extends Fragment {
    String skilladded;
    int ratingadded;
    List<Addskills> Finalskills=new ArrayList<>();
    Toolbar toolbar;
    ProgressBar progressBarResume;
    UserController userController;
    RecyclerView addskillList;
    Button addskills, addeducation;

    public Resume() {
        // Required empty public constructor
    }

    private void getallSkills() {
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("uid",String.valueOf(SaveSharedPreference.getUserId(getContext())));
           userController.getRequest(dataMap, API.SKILLS,getallSkillsListener);

    }
    private final ResponseListener getallSkillsListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarResume.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                JSONArray skills=jsonObject.getJSONArray("skills");
                Finalskills.clear();
                for(int i=0;i<skills.length();i++){
                    JSONObject skillobj=skills.getJSONObject(i);
                    Addskills add=new Addskills(skillobj.getString("name"),skillobj.getString("rating"));
                    Finalskills.add(add);
                }
                addskillList.setLayoutManager(new GridLayoutManager(getContext(), 2));
                addskilladapter addskilladapter=new addskilladapter(Finalskills);
                addskillList.setAdapter(addskilladapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarResume.setVisibility(GONE);

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarResume.setVisibility(GONE);

        }
    };
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addskills = getActivity().findViewById(R.id.Add_Skills);
        progressBarResume=view.findViewById(R.id.progressBarResume);
        addskillList = view.findViewById(R.id.addskillList);
        addskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addSkill);

            }
        });
        addeducation = view.findViewById(R.id.add_education);
        addeducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addEducation);
            }
        });
        userController = new UserController(getContext());
getallSkills();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume2, container, false);
    }

    private void setuptoolbar() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
