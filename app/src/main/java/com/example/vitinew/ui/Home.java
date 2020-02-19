package com.example.vitinew.ui;


import android.content.Context;
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
import com.example.vitinew.gigsAdapter;

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
        gigsRecyclerview = view.findViewById(R.id.HomegigsRecyclerView);
        progressBarRegister=view.findViewById(R.id.progressBarHome);
        userController = new UserController(getContext());
        myuserController=new UserController(getContext());
        MyProjectProgressbar=view.findViewById(R.id.progressBarHome);
        MyPrjectRecyclerView=view.findViewById(R.id.HomeMyprojectRecyclerView);

    }
    private  void MyAllAppliedProject(){
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
       // dataMap.put("id","4");
        myuserController.getRequest(dataMap, API.MyProject,MyProjectresponseListener);



    }
    private void MyAllAppliedgigs(){
        Map<String, String> dataMap = new HashMap<String,String>();
       dataMap.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
       // dataMap.put("id","4");
        userController.getRequest(dataMap, API.USERGIGS,responseListener);


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

        MyAllAppliedgigs();
        MyAllAppliedProject();
        String username=getUserName(getContext());
        Username.setText(username);
       // navHeaderUserName.setText(username);

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
    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarRegister.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                mygigs.clear();
                switch(code){
                    case "SUCCESS":
                        JSONArray gigsarray=new JSONArray();
                        gigsarray= jsonObject.getJSONArray("gigsinfo");
                        if(gigsarray.length()==0){
                            Toast.makeText(getContext(), "you have not applied for any Gigs", Toast.LENGTH_SHORT).show();
                        }
                        for(int i=0;i<gigsarray.length();i++){
                            JSONObject gigsObject=gigsarray.getJSONObject(i);
                            int id=gigsObject.getInt("id");
                            String cats=gigsObject.getString("cats");
                            int per_cost=gigsObject.getInt("per_cost");
                            String gigs_title=gigsObject.getString("campaign_title");
                            String gigs_description=gigsObject.getString("description");
                            String user_id=gigsObject.getString("user_id");
                            String brand=gigsObject.getString("brand");
                            String logo=gigsObject.getString("logo");
                            String created_at=gigsObject.getString("created_at");
                            String updated_at=gigsObject.getString("updated_at");
                            gigsClass thisgig = new gigsClass();
                            thisgig.setBrand(brand);
                            thisgig.setId(id);
                            thisgig.setUser_id(user_id);
                            thisgig.setPer_cost(per_cost);
                            thisgig.setCampaign_title(gigs_title);
                            thisgig.setCats(cats);
                            thisgig.setDescription(gigs_description);
                            thisgig.setLogo("http://herody.in/assets/employer/profile_images/"+logo);
                            thisgig.setCreated_at_timestamp(created_at);
                            thisgig.setUpdated_at(updated_at);
                            mygigs.add(thisgig);
                        }
                        gigsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                        gigsAdapter adapter = new gigsAdapter(mygigs);
                        // Attach the adapter to the recyclerview to populate items
                        gigsRecyclerview.setAdapter(adapter);
                        gigsRecyclerview.setHasFixedSize(true);


                        break;
                    default:
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarRegister.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarRegister.setVisibility(GONE);

        }
    };


    private final ResponseListener MyProjectresponseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            MyProjectProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                //Toast.makeText(getContext(), "inside", Toast.LENGTH_SHORT).show();
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                allProject.clear();
                switch(code){
                    case "SUCCESS":
                        Log.d("myProject",jsonObject.toString());
                        JSONArray InternshipArray=new JSONArray();
                        InternshipArray= jsonObject.getJSONArray("projectsinfo");
                        //Projectidlist.clear();
                        if(InternshipArray.length()==0){
                            Toast.makeText(getContext(), "you have not applied for any Project", Toast.LENGTH_SHORT).show();
                        }
                        for(int i=0;i<InternshipArray.length();i++){
                            JSONObject gigsObject=InternshipArray.getJSONObject(i);
                            int id=gigsObject.getInt("id");
                            String cats=gigsObject.getString("cat");

                            String stipend=gigsObject.getString("stipend");
                            // int per_cost=gigsObject.getInt("per_cost");
                            String title=gigsObject.getString("title");
                            String description=gigsObject.getString("des");
                            String user=gigsObject.getString("user");
                            String start=gigsObject.getString("start");
                            String end=gigsObject.getString("end");
                            String created_at=gigsObject.getString("created_at");
                            String updated_at=gigsObject.getString("updated_at");
                            String duration=gigsObject.getString("duration");
                            String benefits=gigsObject.getString("benefits");
                            String place=gigsObject.getString("place");
                            String count=gigsObject.getString("count");
                            String skills=gigsObject.getString("skills");
                            String image=gigsObject.getString("image");

                            String proofs=gigsObject.getString("proofs");



                            ProjectDisplay thisProject = new ProjectDisplay();
                            thisProject.setId(id);
                            thisProject.setCat(cats);
                            thisProject.setTitle(title);
                            thisProject.setImage(image);
                            //  Log.d("internship",thisProject.getTitle().toString());
                            thisProject.setDes(description);
                            thisProject.setUser(user);
                            thisProject.setStart(start);
                            thisProject.setEnd(end);
                            thisProject.setCreated_at(created_at);
                            thisProject.setUpdated_at(updated_at);
                            thisProject.setDuration(duration);
                            thisProject.setBenifits(benefits);
                            thisProject.setStipend(stipend);
                            thisProject.setPlace(place);
                            thisProject.setCount(count);
                            thisProject.setSkill(skills);
                            thisProject.setProofs(proofs);
                           /* thisgig.setBrand(brand);
                            thisgig.setId(id);
                            thisgig.setUser_id(user_id);
                            thisgig.setPer_cost(per_cost);
                            thisgig.setCampaign_title(gigs_title);
                            thisgig.setCats(cats);
                            thisgig.setDescription(gigs_description);
                            thisgig.setLogo(logo);
                            thisgig.setCreated_at_timestamp(created_at);
                            thisgig.setUpdated_at(updated_at);*/
                            allProject.add(thisProject);

                        }
                        MyPrjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        DesplayProjectAdapter adapter = new DesplayProjectAdapter(allProject);
                        // Attach the adapter to the recyclerview to populate items
                        MyPrjectRecyclerView.setAdapter(adapter);
                        MyPrjectRecyclerView.setHasFixedSize(true);



                        break;
                    default:
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                MyProjectProgressbar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            MyProjectProgressbar.setVisibility(GONE);

        }
    };


}

