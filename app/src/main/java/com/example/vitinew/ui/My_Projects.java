package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Adapters.DesplayProjectAdapter;
import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
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
public class My_Projects extends Fragment {
    ProgressBar MyProjectProgressbar;
    RecyclerView MyPrjectRecyclerView;
    UserController userController;
    UserController myuserController;
    private List<ProjectDisplay> allProject = new ArrayList<>();
    private ArrayList<Integer> Projectidlist = new ArrayList<Integer>();

    Toolbar toolbar;
    public My_Projects() {
        // Required empty public constructor
    }
    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            MyProjectProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("strvalue",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                allProject.clear();
                switch(code){
                    case "SUCCESS":
                        Log.d("myProjectt",jsonObject.toString());
                        JSONObject gigsObject=new JSONObject();
                        gigsObject= jsonObject.getJSONObject("project");
                        allProject.clear();

                            //JSONObject gigsObject=InternshipArray.getJSONObject(i);
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

                            String proofs=gigsObject.getString("proofs");



                            ProjectDisplay thisProject = new ProjectDisplay();
                            thisProject.setId(id);
                            thisProject.setCat(cats);
                            thisProject.setTitle(title);
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
                        Projectidlist.clear();
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

                            String proofs=gigsObject.getString("proofs");
                            String image=gigsObject.getString("image");



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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        View view =inflater.inflate(R.layout.my_projects, container, false);

        // Inflate the projectlist for this fragment
        userController = new UserController(getContext());
        myuserController=new UserController(getContext());
        MyProjectProgressbar=view.findViewById(R.id.MyProjectProgressbar);
        MyPrjectRecyclerView=view.findViewById(R.id.MyprojectRecyclerView);
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        //dataMap.put("id","4");
        myuserController.getRequest(dataMap, API.MyProject,MyProjectresponseListener);

     /*   MyProjectProgressbar.setVisibility(View.VISIBLE);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, String> dataMap1 = new HashMap<String,String>();

                for (int i = 0; i < Projectidlist.size(); i++) {
                    dataMap1.put("id",Projectidlist.get(i).toString());
                    Handler handler1=new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            userController.getRequest(dataMap1, API.PROJECT_DETAIL,responseListener);

                        }
                    },3000);



                }
                MyProjectProgressbar.setVisibility(View.INVISIBLE);


            }
        },3000);
*/



        return view;
    }
    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
