package com.example.vitinew.Details;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class gigDetails extends AppCompatActivity {
    TextView gigsdetail,title,brand;
    TextView task;
    gigsClass gig=new gigsClass();
    Button apply;
    String image;
    ImageView imageView;
    String tasklist= "";
 UserController userController,muserController;
    private final ResponseListener AppliedresponseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("strresponse",response);

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                //String image=jsonObject.getString("image");
                String code=jsonObject.getString("code");
                Log.d("strstatus",json.toString());
                switch(code){
                    case "SUCCESS":
                        JSONArray status=new JSONArray();
                        status=jsonObject.getJSONArray("gigs");
                        Log.d("statusarray",status.toString()+""+gig.getId());
                        int currentCampaignId=gig.getId();

                        for(int i=0;i<status.length();i++){
                            JSONObject thisjsonOnject=new JSONObject();
                            thisjsonOnject=status.getJSONObject(i);
                            int k=thisjsonOnject.getInt("cid");

                            if(k==currentCampaignId){
                                int StatusCode=thisjsonOnject.getInt("status");
                                switch(StatusCode){
                                    case 0:
                                        apply.setText("Applied");
                                        apply.setClickable(false);

                                        break;

                                    case 1:
                                        apply.setText("Application Approved.");
                                        apply.setClickable(false);
                                        break;
                                    case 2:
                                        apply.setText("Application Rejected.");
                                        apply.setClickable(false);
                                        break;
                                    case 3:
                                        apply.setText("Proof submitted.");
                                        apply.setClickable(false);
                                        break;

                                    case 4:
                                        apply.setText("Proof Accepted, paid.");
                                        apply.setClickable(false);
                                        break;
                                    case 5:
                                        apply.setText("Proof Rejected.");
                                        apply.setClickable(false);
                                        break;

                                    default:
                                        apply.setClickable(true);



                                }
                                break;
                            }

                        }





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




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gig Details");
        Intent intent=getIntent();
        title=findViewById(R.id.title);
        brand=findViewById(R.id.Brand);
        imageView=findViewById(R.id.imageView1);
      gig= (gigsClass) intent.getSerializableExtra("class");
     title.setText(gig.getCampaign_title());
     brand.setText(gig.getBrand());
        image=gig.getLogo();
        task=findViewById(R.id.tasks);
        Picasso.get().load(image).into(imageView);
        userController = new UserController(gigDetails.this);
        Map<String, String> dataMap = new HashMap<String,String>();
Log.v("",""+"gig id is"+gig.getId());
        dataMap.put("id",String.valueOf(gig.getId()));
        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        if (Build.VERSION.SDK_INT >= 24) {
            expTv1.setText(Html.fromHtml("<strong><h2>About Gigs</h2></strong>"+gig.getDescription()+"<br>", Html.FROM_HTML_MODE_LEGACY));

        } else {
            expTv1.setText(Html.fromHtml("<h1 style=\"color:black;\">About Gig</h1>"+gig.getDescription()+"<br>"));
        }



        muserController=new UserController(gigDetails.this);
        JSONObject jsn=new JSONObject();
        try {
            jsn.put("id",String.valueOf(SaveSharedPreference.getUserId(gigDetails.this)));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        muserController.postWithJsonRequest(API.GigsAppliedStatus,jsn,AppliedresponseListener);


        userController.getRequest(dataMap, API.GigsDetails,responseListener);
      apply=findViewById(R.id.applygig);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    applynow();
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
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":
                        JSONArray tasks=jsonObject.getJSONArray("tasks");
                        for(int i=0;i<tasks.length();i++){
                         JSONObject obj= (JSONObject) tasks.get(i);
                         tasklist=tasklist+ (obj.getString("task"))+"\n"+"\n";
                        }
                        task.setText(tasklist);
                        break;
                    default:
                        Toast.makeText(gigDetails.this, "something wrong", Toast.LENGTH_SHORT).show();
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
      //  Toast.makeText(this, "id="+String.valueOf(gig.getId()+"uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, "uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))+"  id="+String.valueOf(gig.getId()), Toast.LENGTH_SHORT).show();

        String id=String.valueOf(gig.getId());
        String  uid=String.valueOf(SaveSharedPreference.getUserId(gigDetails.this));
        jsn.put("id",id);
        jsn.put("uid",uid);
        userController.postWithJsonRequest(API.GIGSAPPLY,jsn,applyListener);
        Log.v("JSON is",jsn.toString());
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

                        Toast.makeText(gigDetails.this, "Applied Successfully", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        Toast.makeText(gigDetails.this, ""+code, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(gigDetails.this, "something wrong", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
