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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Adapters.DesplayProjectAdapter;
import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.PaymentViaAmazonGiftCard;
import com.example.vitinew.PaymentViaPaytm;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wallet extends Fragment {
    TextView referall, gig, project, campaign;
    LinearLayout withdrawPaytm,WithdrawAmazonGiftCard;
    Toolbar toolbar;

    public Wallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment
        setuptoolbar();

        return inflater.inflate(R.layout.fragment_wallet2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        referall = view.findViewById(R.id.referal);
        gig = view.findViewById(R.id.gigs);
        project = view.findViewById(R.id.projects);
        campaign = view.findViewById(R.id.campaigns);
        WithdrawAmazonGiftCard=view.findViewById(R.id.WithdrawAmazonGiftCard);
        withdrawPaytm=view.findViewById(R.id.WithdrawViaPaytm);
        withdrawPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PaymentViaPaytm.class);
                intent.putExtra("id",1);
                startActivity(intent);
                //paytm process
                //id 1

            }
        });
        WithdrawAmazonGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //withdraw amazon gift card
                //id 2
                Intent intent=new Intent(getContext(), PaymentViaAmazonGiftCard.class);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });
        UserController user = new UserController(getContext());
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("id", String.valueOf(SaveSharedPreference.getUserId(getContext())));
        //dataMap.put("id","4");
        user.getRequest(dataMap, API.TRANSACTIONS, MyProjectresponseListener);


    }

    private final ResponseListener MyProjectresponseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                //Toast.makeText(getContext(), "inside", Toast.LENGTH_SHORT).show();
                Log.d("str", response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        String referralEarnings = jsonObject.getString("referralEarnings");

                        String gigEarnings = jsonObject.getString("gigEarnings");

                        String projectEarnings = jsonObject.getString("projectEarnings");

                        String campaignEarnings = jsonObject.getString("campaignEarnings");

                        referall.setText(referralEarnings);
                        gig.setText(gigEarnings);
                        project.setText(projectEarnings);
                        campaign.setText(campaignEarnings);
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

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

    private void setuptoolbar() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
