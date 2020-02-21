
package com.example.vitinew.ui;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class City extends Fragment {

    Toolbar toolbar;
 Button apply;
 TextView referall;
    public City() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_city2, container, false);
    }
    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apply=view.findViewById(R.id.button);
        referall=view.findViewById(R.id.code);
        UserController user =new UserController(getContext());

        Map<String, String> dataMap = new HashMap<String,String>();
        Log.e("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        dataMap.put("id",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        //dataMap.put("id","4");
        user.getRequest(dataMap, API.USERDETAILS,responseListener);
     apply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
             sharingIntent.setType("text/plain");
             String shareBody = referall.getText().toString();
             sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Referall Code Herody");
             sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
             startActivity(Intent.createChooser(sharingIntent, "Share via"));
         }
     });
    }

    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":

                        JSONObject j=new JSONObject();
                        j=jsonObject.getJSONObject("user");
                        String codes=j.getString("ref_code");
                        referall.setText(codes);
                        break;
                    default:
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };
}
