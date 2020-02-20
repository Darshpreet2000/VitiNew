package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.campaignClass;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AboutCampaign extends AppCompatActivity {
     ImageView imageView;
    TextView campaigndetail;
    CheckBox termAndCondition;
    TextView Reward,Term,ImpTerm,City,instruction,benifits,requirement,title
            ,Start,end;
    campaignClass  campaigns=new campaignClass();
    Button apply;
    String tasklist= "";
    UserController userController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_detail);
       campaigndetail=findViewById(R.id.campaigndescriptionDetail);
        Intent intent=getIntent();
        campaigns= (campaignClass) intent.getSerializableExtra("class");
        campaigndetail.setText(campaigns.getDes());

        Reward=findViewById(R.id.campaignReward);
        Term=findViewById(R.id.campaignTerm);
        imageView=findViewById(R.id.imageView1);

        ImpTerm=findViewById(R.id.campaignImpTerm);
        termAndCondition=findViewById(R.id.checkboxTerm);
        City=findViewById(R.id.campaigCity);
        instruction=findViewById(R.id.campaigInstuction);
        City.setText(campaigns.getCity());
        instruction.setText(campaigns.getInstructions());
        benifits=findViewById(R.id.campaignBenifits);
        requirement=findViewById(R.id.campaignRequirement);
        benifits.setText(campaigns.getBenefits());
        requirement.setText(campaigns.getRequirements());
        title=findViewById(R.id.campaigndetailTitle);
        Start=findViewById(R.id.campaignStarting);
        end=findViewById(R.id.campaignEnding);
        title.setText(campaigns.getTitle());
        Date start = DateTimeUtils.formatDate(campaigns.getStart());
        Date endD = DateTimeUtils.formatDate(campaigns.getEnd());
      String s=  DateTimeUtils.formatWithStyle(start, DateTimeStyle.LONG);
        String e=  DateTimeUtils.formatWithStyle(endD, DateTimeStyle.LONG);
        Start.setText(s);
        end.setText(e);

        Picasso.get().load(campaigns.getLogo()).into(imageView);

        userController = new UserController(AboutCampaign.this);
        Map<String, String> dataMap = new HashMap<String,String>();
        //Log.v("",""+"gig id is"+gig.getId());
        dataMap.put("id",String.valueOf(campaigns.getId()));
        userController.getRequest(dataMap, API.CampaignDetail,responseListener);
        apply=findViewById(R.id.applycampaign);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(termAndCondition.isChecked()){
                        applynow();

                    }
                    else {
                        Toast.makeText(AboutCampaign.this, "Accept term and condition ", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                Log.d("strresponse",response);

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String image=jsonObject.getString("image");
                String code=jsonObject.getString("code");
                Log.d("strresponsejson",json.toString());
                switch(code){
                    case "SUCCESS":
                        JSONObject campaign=jsonObject.getJSONObject("campaign");

                            String terms=campaign.getString("terms");
                           String impTerms= campaign.getString("imp_terms");
                            String Rewardvalue=campaign.getString("reward");


                        Reward.setText(Rewardvalue);
                        Term.setText(terms);
                        ImpTerm.setText("Imp Terms:\n"+impTerms);


                        break;
                    default:
                        Toast.makeText(AboutCampaign.this, ""+code, Toast.LENGTH_SHORT).show();
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




    private void applynow() throws JSONException {
        JSONObject jsn=new JSONObject();
       // Toast.makeText(this, "id="+String.valueOf(campaigns.getId()+"uid= "+String.valueOf(SaveSharedPreference.getUserId(AboutCampaign.this))), Toast.LENGTH_SHORT).show();
        //  Toast.makeText(this, "uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))+"  id="+String.valueOf(gig.getId()), Toast.LENGTH_SHORT).show();
        String id=String.valueOf(campaigns.getId());
        String  uid=String.valueOf(SaveSharedPreference.getUserId(AboutCampaign.this));
        jsn.put("id",id);
        jsn.put("uid",uid);
        jsn.put("terms",true);
        //+"?id="+2+"&uid="+uid+"&terms=true"
        Log.v("JSON is",jsn.toString());
        userController.postWithJsonRequest(API.CampaignApply,jsn,applyListener);

    }

    private final ResponseListener applyListener = new ResponseListener() {

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

                        Toast.makeText(AboutCampaign.this, "Applied Successfully", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        Toast.makeText(AboutCampaign.this, ""+code, Toast.LENGTH_SHORT).show();
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
            error.printStackTrace();
            Toast.makeText(AboutCampaign.this, "Apply Limit Exceeded", Toast.LENGTH_SHORT).show();
        }
    };

}
