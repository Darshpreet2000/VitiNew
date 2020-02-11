package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ProjectDetail extends AppCompatActivity {
    ProjectDisplay projectdetail;
    TextView Description;
    private JSONObject generateApplyRequest() {
        JSONObject json = new JSONObject();
        try {
            // json.put("uid", SaveSharedPreference.getUserId(getActivity()));
            json.put("id", 2);
            json.put("uid",8);
            Log.d("Jsonstring",json.toString());
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

    TextView ProjectTitle,CompanyName,Stipend,Duration,Position,ApplyBefore,WorkPlace,
            AboutProject,AboutCompany,Start,skill,Proof,benefits;
    Button ApplyNow;

    private  void getAllwidget(){
        ApplyNow=findViewById(R.id.ProjectApplyNow);

        ProjectTitle=findViewById(R.id.ProjectTitle);
        CompanyName=findViewById(R.id.companyName);
        Stipend=findViewById(R.id.ProjectStipend);
        Duration=findViewById(R.id.ProjectDuration);
        Position=findViewById(R.id.ProjectPosition);
        ApplyBefore=findViewById(R.id.ProjectApplyBefore);
        WorkPlace=findViewById(R.id.ProjectWorkPlace);
        AboutProject=findViewById(R.id.ProjectDesCription);
        AboutCompany=findViewById(R.id.ProjectAboutCompany);
        Start=findViewById(R.id.ProjectStarting);
        skill=findViewById(R.id.ProjectSkill);
        Proof=findViewById(R.id.ProjectProofRequired);
        benefits=findViewById(R.id.ProjectBenefit);


    }
    private void SetAllWedget(ProjectDisplay projectdetail){
        ProjectTitle.setText(projectdetail.getTitle());
        CompanyName.setText(projectdetail.getCompanyName());
        Stipend.setText(projectdetail.getStipend());
        Duration.setText(projectdetail.getDuration());
        Position.setText(projectdetail.getPosition());
        ApplyBefore.setText(projectdetail.getStart());
        WorkPlace.setText(projectdetail.getWorkPlace());
        AboutProject.setText(projectdetail.getDes());
        AboutCompany.setText(projectdetail.getAboutCompany());
        Start.setText(projectdetail.getStart());
        skill.setText(projectdetail.getSkill());
        Proof.setText(projectdetail.getProofs());
        benefits.setText(projectdetail.getBenefits());

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_project_detail);
        Intent intent=getIntent();
      projectdetail= (ProjectDisplay) intent.getSerializableExtra("class");
        int id=projectdetail.getId();
        Log.d("idkkkk",String.valueOf(projectdetail.getId()) );
        getAllwidget();
       // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        JSONObject request = generateRequest();
        UserController user = new UserController(getApplicationContext());
        user.postWithJsonRequest(API.PROJECT_DETAIL, request,ProjectDetailListner);
     // Description=findViewById(R.id.ProjectDesCription);
  // Description.setText(projectdetail.getDes());
        ApplyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserController applyController=new UserController(getApplicationContext());
                JSONObject request = generateApplyRequest();
                applyController.postWithJsonRequest(API.APPLY_PROJECT, request,ProjectApplyListner);

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
                Log.d("projectDetail",response);

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        Log.d("projectDetail",jsonObject.toString());
                        JSONObject Aboutproject=jsonObject.getJSONObject("project");
                        projectdetail.setId(Aboutproject.getInt("id"));
                        projectdetail.setTitle(Aboutproject.getString("title"));
                        projectdetail.setDes(Aboutproject.getString("des"));
                        projectdetail.setCat(Aboutproject.getString("cat"));
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
                //Log.d("projectDetail",response);

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                Log.d("Applied status",code);
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
                        Toast.makeText(ProjectDetail.this,"Sorry this project is not available Now" , Toast.LENGTH_SHORT).show();
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Applied status error",e.toString());
            } finally {
                //   addskillprogressBar.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            Log.d("Applied status error",error.toString());

        }
    };
}
