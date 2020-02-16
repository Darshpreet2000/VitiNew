package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Gigs extends Fragment {
    Toolbar toolbar;
    UserController userController;
    gigsClass thisgig = new gigsClass();
    ProgressBar progressBarRegister;
    private List<gigsClass> mygigs = new ArrayList<>();

    RecyclerView gigsRecyclerview;
    public My_Gigs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.my_gigs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gigsRecyclerview = view.findViewById(R.id.gigsRecyclerView);
        progressBarRegister=view.findViewById(R.id.progressBargigs);
        userController = new UserController(getContext());

        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        //dataMap.put("id","4");
        userController.getRequest(dataMap, API.USERGIGS,responseListener);
    }

    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
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
                            thisgig.setBrand(brand);
                            thisgig.setId(id);
                            thisgig.setUser_id(user_id);
                            thisgig.setPer_cost(per_cost);
                            thisgig.setCampaign_title(gigs_title);
                            thisgig.setCats(cats);
                            thisgig.setDescription(gigs_description);
                            thisgig.setLogo(logo);
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
}
