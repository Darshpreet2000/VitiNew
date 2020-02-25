package com.example.vitinew.Details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.App;
import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.example.vitinew.question_answer;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDetail extends AppCompatActivity {
    ProjectDisplay projectdetail;
    RecyclerView questions;
    List<com.example.vitinew.Classes.questions> questionsList = new ArrayList<>();
    TextView Description;
    UserController muserController;


    private JSONObject generateApplyRequest() {
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            // json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("id", id);
            json.put("uid", SaveSharedPreference.getUserId(getApplicationContext()));
            for (int i = 0; i < questionsList.size(); i++) {
                View view = questions.getChildAt(i);
                EditText nameEditText = (EditText) view.findViewById(R.id.ans);
                String name = nameEditText.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(ctx, "All Fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    array.put(name);
                }
            }
            json.put("answer", array);
            Log.d("Jsonstring", json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject generateRequest() {
        JSONObject json = new JSONObject();
        try {
            // json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("id", projectdetail.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    Context ctx;
    ExpandableTextView AboutProject, AboutCompany,skill,benefits;
    TextView ProjectTitle, CompanyName, Stipend, Duration, Position, ApplyBefore, WorkPlace,
            Start,Proof;
    Button apply;
    ImageView imageView;

    private void getAllwidget() {
        apply = findViewById(R.id.ProjectApplyNow);
        imageView = findViewById(R.id.imageView1);
        ProjectTitle = findViewById(R.id.ProjectTitle);
        CompanyName = findViewById(R.id.companyName);
      //  Stipend = findViewById(R.id.ProjectStipend);
        Duration = findViewById(R.id.ProjectDuration);
        Position = findViewById(R.id.ProjectPosition);
        ApplyBefore = findViewById(R.id.ProjectApplyBefore);
    //    WorkPlace = findViewById(R.id.ProjectWorkPlace);
        AboutProject = (ExpandableTextView) findViewById(R.id.expand_text_view);

        AboutCompany = (ExpandableTextView) findViewById(R.id.expand_text_view2);
        // AboutCompany=findViewById(R.id.ProjectAboutCompany);
        Start = findViewById(R.id.ProjectStarting);
        skill = (ExpandableTextView) findViewById(R.id.expand_text_view_skill);
        Proof = findViewById(R.id.ProjectProofRequired);
        benefits = (ExpandableTextView) findViewById(R.id.expand_text_view_benefit);


    }

    private void SetAllWedget(ProjectDisplay projectdetail) {
        ProjectTitle.setText(projectdetail.getTitle());
        CompanyName.setText(projectdetail.getCompanyName());
//        Stipend.setText(projectdetail.getStipend());
        Duration.setText(projectdetail.getDuration());
        Position.setText(projectdetail.getPosition());
        String s=projectdetail.getStart();
        String e=projectdetail.getEnd();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date s1=dateFormat.parse(s);

            Date e1=dateFormat.parse(e);
            Start.setText(s1.toString());
          ApplyBefore.setText(e1.toString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
  //      WorkPlace.setText(projectdetail.getWorkPlace());
        if (Build.VERSION.SDK_INT >= 24) {
            AboutProject.setText(Html.fromHtml("<strong><h2>About Project</h2></strong>" + projectdetail.getDes()+"<br>", Html.FROM_HTML_MODE_LEGACY));


        } else {
            AboutProject.setText(Html.fromHtml("<strong><h2>About Project</h2></strong>" + projectdetail.getDes()+"<br>"));

        }
        if (Build.VERSION.SDK_INT >= 24) {
            benefits.setText(Html.fromHtml("<strong><h2>Benefits</h2></strong>" +projectdetail.getBenefits()+"<br>", Html.FROM_HTML_MODE_LEGACY));
            AboutCompany.setText(Html.fromHtml("<strong><h2>About Company</h2></strong>" + projectdetail.getAboutCompany()+"<br>", Html.FROM_HTML_MODE_LEGACY));
            skill.setText(Html.fromHtml("<strong><h2>Skills</h2></strong>" +projectdetail.getSkill()+"<br>", Html.FROM_HTML_MODE_LEGACY));

        } else {
            skill.setText(Html.fromHtml("<strong><h2>Skills Required</h2></strong>" +projectdetail.getSkill()+"<br>"));

            benefits.setText(Html.fromHtml("<strong><h2>Benefits</h2></strong>" +projectdetail.getBenefits()+"<br>"));
            AboutCompany.setText(Html.fromHtml("<strong><h2>About Company</h2></strong>" + projectdetail.getAboutCompany()+"<br>"));

        }
        Proof.setText(projectdetail.getProofs());

    }

    int id;
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
                        status=jsonObject.getJSONArray("projects");
                        Log.d("statusarray",status.toString()+""+projectdetail.getId());
                        int currentCampaignId=projectdetail.getId();

                        for(int i=0;i<status.length();i++){
                            JSONObject thisjsonOnject=new JSONObject();
                            thisjsonOnject=status.getJSONObject(i);
                            int k=thisjsonOnject.getInt("jid");

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
                                        break;



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Project Details");
        questions = findViewById(R.id.projectRecyclerView);
        Intent intent = getIntent();
        projectdetail = (ProjectDisplay) intent.getSerializableExtra("class");
        id = projectdetail.getId();

        Log.d("idkkkk", String.valueOf(projectdetail.getId()));
        getAllwidget();

        Picasso.get().load(projectdetail.getImage()).into(imageView);
        // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        JSONObject request = generateRequest();
        muserController=new UserController(ProjectDetail.this);
        JSONObject jsn=new JSONObject();
        try {
            jsn.put("id",String.valueOf(SaveSharedPreference.getUserId(ProjectDetail.this)));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        muserController.postWithJsonRequest(API.ProjectAppliedStatus,jsn,AppliedresponseListener);
        UserController user = new UserController(getApplicationContext());
        ctx = ProjectDetail.this;
        user.postWithJsonRequest(API.PROJECT_DETAIL, request, ProjectDetailListner);
        // Description=findViewById(R.id.ProjectDesCription);
        // Description.setText(projectdetail.getDes());
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    UserController applyController=new UserController(getApplicationContext());

                Intent i = new Intent(ProjectDetail.this, question_answer.class);
                i.putExtra("class", (Serializable) projectdetail);
                startActivity(i);
                //JSONObject request = generateApplyRequest();
                //applyController.postWithJsonRequest(API.APPLY_PROJECT, request,ProjectApplyListner);

            }
        });
    }

    private final ResponseListener ProjectDetailListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
            //addskillprogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("projectDetail", response);
                JSONObject json = new JSONObject(response);
                Log.v("JSON", "" + json.toString());
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        Log.d("projectDetail", jsonObject.toString());
                        JSONArray questionsarray = jsonObject.getJSONArray("questions");
                        JSONObject company = jsonObject.getJSONObject("company");
                        JSONObject Aboutproject = jsonObject.getJSONObject("project");
                        projectdetail.setId(Aboutproject.getInt("id"));
                        projectdetail.setTitle(Aboutproject.getString("title"));
                        projectdetail.setDes(Aboutproject.getString("des"));
                        projectdetail.setCat(Aboutproject.getString("cat"));
                        projectdetail.setPosition(Aboutproject.getString("count"));
                        projectdetail.setStart(Aboutproject.getString("start"));
                        projectdetail.setEnd(Aboutproject.getString("end"));
                        projectdetail.setDuration(Aboutproject.getString("duration"));
                        projectdetail.setStipend(Aboutproject.getString("stipend"));
                        projectdetail.setBenefits(Aboutproject.getString("benefits"));
                        projectdetail.setPlace(Aboutproject.getString("place"));
                        projectdetail.setCount(Aboutproject.getString("count"));
                        projectdetail.setSkill(Aboutproject.getString("skills"));
                        projectdetail.setProofs(Aboutproject.getString("proofs"));
                        projectdetail.setUser(Aboutproject.getString("user"));
                        projectdetail.setUpdated_at(Aboutproject.getString("updated_at"));
                        projectdetail.setCreated_at(Aboutproject.getString("created_at"));
                        projectdetail.setCompanyName(company.getString("name"));
                        projectdetail.setAboutCompany(company.getString("description"));
                        SetAllWedget(projectdetail);
                        //Toast.makeText(getApplicationContext(), "Added Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case "EMAIL DOES NOT EXIST":
                        Toast.makeText(getApplicationContext(), "Email Does Not EXIST", Toast.LENGTH_SHORT).show();
                        break;
                    case "PASSWORD NOT CORRECT":
                        Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(ProjectDetail.this, "Something wrong", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                //Log.d("projectDetail",e.toString());
            } finally {
                //   addskillprogressBar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };

    private final ResponseListener ProjectApplyListner = new ResponseListener() {

        @Override
        public void onRequestStart() {
            //addskillprogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("projectDetail", response);

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                Log.d("Applied status", code);
                switch (code) {
                    case "SUCCESS":
                        Toast.makeText(ProjectDetail.this, "Applied Successfully", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Added Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case "USER NOT FOUND":
                        Toast.makeText(getApplicationContext(), "Unauthorised user", Toast.LENGTH_SHORT).show();
                        break;
                    case "USER HAS ALREADY APPLIED FOR THIS PROJECT":
                        Toast.makeText(getApplicationContext(), "Already Applied For this Project", Toast.LENGTH_SHORT).show();
                        break;
                    case "PROJECT NOT FOUND":
                        Toast.makeText(ProjectDetail.this, "Sorry this project is not available Now", Toast.LENGTH_SHORT).show();
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Applied status error", e.toString());
            } finally {
                //   addskillprogressBar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            Toast.makeText(getApplicationContext(), "Already Applied For this Project", Toast.LENGTH_SHORT).show();
            Log.d("Applied status error", error.toString());

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
