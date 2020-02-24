package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.vitinew.ui.Extras.bottomdialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
public class Projects extends Fragment {
    ProgressBar ProjectProgressbar;
    RecyclerView PrjectRecyclerView;
    UserController userController;
    private List<ProjectDisplay> allProject = new ArrayList<>();

    Toolbar toolbar;
    public Projects() {
        // Required empty public constructor
    }

    BottomSheetDialogFragment botomSheetDialogFragment;
    private BottomSheetBehavior mBottomSheetBehavior;

    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            ProjectProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                allProject.clear();
                switch(code){
                    case "SUCCESS":
                        JSONArray InternshipArray=new JSONArray();
                        InternshipArray= jsonObject.getJSONArray("projects");
                        allProject.clear();
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
String brand=gigsObject.getString("brand");
                            String proofs=gigsObject.getString("proofs");
                           String image=gigsObject.getString("image");


                            ProjectDisplay thisProject = new ProjectDisplay();
thisProject.setCompanyName(brand);
                            thisProject.setImage(image);
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




                        }
                        PrjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        DesplayProjectAdapter adapter = new DesplayProjectAdapter(allProject);
                        // Attach the adapter to the recyclerview to populate items
                        PrjectRecyclerView.setAdapter(adapter);
                       PrjectRecyclerView.setHasFixedSize(true);


                        break;
                    default:
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                ProjectProgressbar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            ProjectProgressbar.setVisibility(GONE);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
       View view=inflater.inflate(R.layout.fragment_projects, container, false);
        // Inflate the projectlist for this fragment
        //Find bottom Sheet I
        userController = new UserController(getContext());
        ProjectProgressbar=view.findViewById(R.id.ProjectProgressbar);
        PrjectRecyclerView=view.findViewById(R.id.projectRecyclerView);
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("uid",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.AllProject,responseListener);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.internshipjump) {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.action_internships_to_my_Interships);
                }
            
                return true;
            }
        });
        return view;
    }
    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.intership_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }


}
