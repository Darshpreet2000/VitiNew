package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Adapters.DesplayProjectAdapter;
import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.example.vitinew.Adapters.gigsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static com.example.vitinew.Classes.SaveSharedPreference.getUserName;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    private List<ProjectDisplay> allProject = new ArrayList<>();
    TextView Username,navHeaderUserName;
    UserController userController;
    ProgressBar progressBarRegister;
    UserController myuserController;
    RecyclerView MyPrjectRecyclerView;
    ProgressBar MyProjectProgressbar;
    private List<gigsClass> mygigs = new ArrayList<>();
    RecyclerView gigsRecyclerview;
    private  void getallwidget(View view){
        Username= view.findViewById(R.id.username);
       // navHeaderUserName= view.findViewById(R.id.NavHeaderUserName);

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

        String username=getUserName(getContext());
        Username.setText(username);
       // navHeaderUserName.setText(username);

        return view;

    }
    public void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }



}

