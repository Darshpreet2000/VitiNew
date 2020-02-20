package com.example.vitinew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Typeface.BOLD;
import static android.view.View.GONE;

public class gigDetails extends AppCompatActivity {
    TextView gigsdetail,title,brand;
    TextView task;
    gigsClass gig=new gigsClass();
    Button apply;
    String image;
    ImageView imageView;
    String tasklist= "";
 UserController userController;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);
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
            expTv1.setText(Html.fromHtml("<strong><h2>About Gigs</h2></strong>"+gig.getDescription(), Html.FROM_HTML_MODE_LEGACY));

        } else {
            expTv1.setText(Html.fromHtml("<h1 style=\"color:black;\">About Gig</h1>"+gig.getDescription()));
        }

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
        Toast.makeText(this, "id="+String.valueOf(gig.getId()+"uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, "uid= "+String.valueOf(SaveSharedPreference.getUserId(gigDetails.this))+"  id="+String.valueOf(gig.getId()), Toast.LENGTH_SHORT).show();
     String id=String.valueOf(gig.getId());
        String  uid=String.valueOf(SaveSharedPreference.getUserId(gigDetails.this));
        userController.postWithJsonRequest(API.GIGSAPPLY+"?id="+id+"&uid="+uid,jsn,applyListener);
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


}
