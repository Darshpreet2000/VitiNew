package com.example.vitinew.ui;


import android.net.Uri;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Adapters.campaignAdapter;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.campaignClass;
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
public class MyCampaign extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar ProjectProgressbar;
    RecyclerView PrjectRecyclerView;
    UserController userController;
    private List<campaignClass> allcampain = new ArrayList<>();


    private Campaign.OnFragmentInteractionListener mListener;

    public MyCampaign() {
        // Required empty public constructor
    }

    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            ProjectProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("strres",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                Log.d("strmycampaign",jsonObject.toString());
                allcampain.clear();
                switch(code){
                    case "SUCCESS":
                        JSONArray CampaignArray=new JSONArray();
                        CampaignArray= jsonObject.getJSONArray("campaigninfo");
                        allcampain.clear();
                        for(int i=0;i<CampaignArray.length();i++){
                            JSONObject campaignObject=CampaignArray.getJSONObject(i);
                            campaignClass thisclass=new campaignClass();
                            thisclass.setId(campaignObject.getInt("id"));
                            thisclass.setForm(campaignObject.getInt("form"));
                            thisclass.setUcount(campaignObject.getInt("ucount"));
                            thisclass.setTitle(campaignObject.getString("title"));
                            thisclass.setBrand(campaignObject.getString("brand"));
                            thisclass.setLogo("http://live.herody.in/assets/admin/img/camp-brand-logo/"+campaignObject.getString("logo"));
                            thisclass.setDes(campaignObject.getString("des"));
                            thisclass.setStart(campaignObject.getString("start"));
                            thisclass.setBefore(campaignObject.getString("before"));
                            thisclass.setEnd(campaignObject.getString("end"));
                            thisclass.setCity(campaignObject.getString("city"));
                            thisclass.setRequirements(campaignObject.getString("requirements"));
                            thisclass.setReward(campaignObject.getString("reward"));
                            thisclass.setBenefits(campaignObject.getString("benefits"));
                            thisclass.setImp_terms(campaignObject.getString("imp_terms"));
                            thisclass.setTerms(campaignObject.getString("terms"));
                            thisclass.setDondont(campaignObject.getString("dondont"));
                            thisclass.setInstructions(campaignObject.getString("instructions"));
                            thisclass.setMethods(campaignObject.getString("methods"));
                            thisclass.setCreated_at(campaignObject.getString("created_at"));
                            thisclass.setUpdated_at(campaignObject.getString("updated_at"));


                            allcampain.add(thisclass);
                        }
                        PrjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        campaignAdapter adapter = new campaignAdapter(allcampain);

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Toolbar toolbar;
    public void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_campaign, container, false);
        ProjectProgressbar= view.findViewById(R.id.progressBarMycampaigns);
        PrjectRecyclerView=view.findViewById(R.id.MycampaignRecyclerView);
        userController = new UserController(getContext());
        setuptoolbar();
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("uid",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userController.postWithJsonRequest(API.MYCampaign,jsonObject,responseListener);
        //userController.getRequest(dataMap, API.Campaign,responseListener);

        // userController.getRequest(dataMap, API.Campaign,responseListener);
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
