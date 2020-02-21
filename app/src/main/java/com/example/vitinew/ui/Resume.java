package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.vitinew.Adapters.addEducationAdapter;
import com.example.vitinew.Adapters.addExperienceAdapter;
import com.example.vitinew.Adapters.addProjectAdapter;
import com.example.vitinew.Adapters.addskilladapter;
import com.example.vitinew.Classes.AddEducation;
import com.example.vitinew.Classes.AddExp;
import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.Classes.ProjectDetails;
import com.example.vitinew.Classes.SaveSharedPreference;
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
public class Resume extends Fragment {
    String skilladded;
    int ratingadded;
    ImageView hdel, achdel;
    TextView username;
    List<Addskills> Finalskills = new ArrayList<>();
    List<ProjectDetails> Finalprojects = new ArrayList<ProjectDetails>();
    List<AddEducation> FinalEducations = new ArrayList<>();
    List<AddExp> FinalExperience = new ArrayList<>();
    Toolbar toolbar;
    ProgressBar progressBarResume;
    UserController userController;
    RecyclerView addExperience;
    RecyclerView addskillList, addedu, addProjects;
    ImageView addskillsi, addeducationi, addprojecti, addhobbyi, addexperiencei, addachievementi;
    Button addskills, addeducation, addproject, addhobby, addexperience, addachievement, socialprofile;

    public Resume() {
        // Required empty public constructor
    }

    private void getallHobbies() {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("id", String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.USERDETAILS, getallHobbiesListener);
    }

    private void getallEducation() {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("uid", String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.EDUCATION, getallEducationListener);

    }

    private void getallExperiences() {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("uid", String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.Experiences, getallexperienceListener);


    }

    private void getallSkills() {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("uid", String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.SKILLS, getallSkillsListener);

    }

    private void getallProjects() {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("uid", String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.PROJECT, getallProjectsListener);

    }

    private final ResponseListener getallProjectsListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarResume.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                JSONArray skills = jsonObject.getJSONArray("projects");
                Finalprojects.clear();
                for (int i = 0; i < skills.length(); i++) {
                    JSONObject skillobj = skills.getJSONObject(i);
                    ProjectDetails add = new ProjectDetails(skillobj.getString("title"), skillobj.getString("des"), skillobj.getString("id"));
                    Finalprojects.add(add);
                }
                if(!Finalprojects.isEmpty()){
                    addprojecti.setVisibility(View.VISIBLE);
                    addproject.setVisibility(View.INVISIBLE);
                }
                addProjects.setLayoutManager(new LinearLayoutManager(getContext()));
                addProjectAdapter addskilladapter = new addProjectAdapter(Finalprojects, getContext());
                addProjects.setAdapter(addskilladapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarResume.setVisibility(GONE);

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarResume.setVisibility(GONE);

        }
    };
    private final ResponseListener getallHobbiesListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarResume.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                JSONObject user = jsonObject.getJSONObject("user");
                String uername=user.getString("name");
                String hobbies = user.getString("hobbies");
                String achievements = user.getString("achievements");
                String twitter = user.getString("twitter");
                String linkedin = user.getString("linkedin");
                String github = user.getString("github");
                String insta = user.getString("insta");
                TextView achieve = getActivity().findViewById(R.id.achievementstext);
                TextView usertext=getActivity().findViewById(R.id.username_resume);
                 usertext.setText(uername);
                achieve.setText(achievements);
                TextView social = getActivity().findViewById(R.id.socialtext);
                TextView hobby = getActivity().findViewById(R.id.hobbytext);
                social.setText("Twitter\n" + twitter + "\n" + "LinkedIn\n" + linkedin + "\n" + "Github\n" + github + "\n" + "Instagram\n" + insta);
                hobby.setText(hobbies);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarResume.setVisibility(GONE);

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarResume.setVisibility(GONE);

        }
    };

    private final ResponseListener getallexperienceListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarResume.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                JSONArray skills = jsonObject.getJSONArray("exps");
                FinalExperience.clear();
                for (int i = 0; i < skills.length(); i++) {
                    JSONObject skillobj = skills.getJSONObject(i);

                    String id = skillobj.getString("id");
                    String org = skillobj.getString("company");
                    String des = skillobj.getString("designation");
                    String desc = skillobj.getString("des");
                    String start = skillobj.getString("start");
                    String end = skillobj.getString("end");
                    AddExp add = new AddExp(org, des, desc, start, end, id);
                    FinalExperience.add(add);
                }
                addExperience.setLayoutManager(new LinearLayoutManager(getContext()));
                addExperienceAdapter addskilladapter = new addExperienceAdapter(FinalExperience, getContext());
                addExperience.setAdapter(addskilladapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarResume.setVisibility(GONE);

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarResume.setVisibility(GONE);

        }
    };

    private final ResponseListener getallSkillsListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarResume.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                JSONArray skills = jsonObject.getJSONArray("skills");
                Finalskills.clear();
                for (int i = 0; i < skills.length(); i++) {
                    JSONObject skillobj = skills.getJSONObject(i);
                    Addskills add = new Addskills(skillobj.getString("name"), skillobj.getString("rating"), skillobj.getString("id"));
                    Finalskills.add(add);
                }
                if(!Finalskills.isEmpty()){
                    addskillsi.setVisibility(View.VISIBLE);
                    addskills.setVisibility(View.INVISIBLE);
                }
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                DisplayMetrics outMetrics = new DisplayMetrics();
                display.getMetrics(outMetrics);

                float density  = getResources().getDisplayMetrics().density;
                float dpWidth  = outMetrics.widthPixels / density;
                int columns = Math.round(dpWidth/300);
                GridLayoutManager mLayoutManager;
                mLayoutManager = new GridLayoutManager(getActivity(),columns);
                addskillList.setLayoutManager(mLayoutManager);
                addskilladapter addskilladapter = new addskilladapter(Finalskills, getContext());
                addskillList.setAdapter(addskilladapter);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarResume.setVisibility(GONE);

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarResume.setVisibility(GONE);

        }
    };

    private final ResponseListener getallEducationListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarResume.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                JSONArray skills = jsonObject.getJSONArray("edus");
                FinalEducations.clear();
                for (int i = 0; i < skills.length(); i++) {
                    JSONObject skillobj = skills.getJSONObject(i);
                    AddEducation add = new AddEducation(skillobj.getString("type"), skillobj.getString("name"), skillobj.getString("course"), skillobj.getString("start"), skillobj.getString("end"), skillobj.getString("id"));
                    FinalEducations.add(add);
                }
              if(!FinalEducations.isEmpty()){
                  addeducationi.setVisibility(View.VISIBLE);
                  addeducation.setVisibility(View.INVISIBLE);
              }
                addedu.setLayoutManager(new LinearLayoutManager(getContext()));
                addEducationAdapter addskilladapter = new addEducationAdapter(FinalEducations, getContext());
                addedu.setAdapter(addskilladapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarResume.setVisibility(GONE);

            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarResume.setVisibility(GONE);

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addskills = getActivity().findViewById(R.id.Add_Skills);
        progressBarResume = view.findViewById(R.id.progressBarResume);
        addedu = view.findViewById(R.id.addedu);
        addProjects = view.findViewById(R.id.projectsrecycle);
        addproject = view.findViewById(R.id.project_button);
        addskillList = view.findViewById(R.id.addskillList);
        addhobby = view.findViewById(R.id.Add_Hobbies);
        addexperience = view.findViewById(R.id.addexperiences);
        addExperience = view.findViewById(R.id.experienceList);
        addachievement = view.findViewById(R.id.Add_Achievements);
///////////////////

        addskillsi = view.findViewById(R.id.addSkilli);
        addprojecti = view.findViewById(R.id.addProjecti);
        addexperiencei = view.findViewById(R.id.addexperiencei);
        addeducationi= view.findViewById(R.id.addEducationi);
      addskillsi.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addSkill);

          }
      });
      addprojecti.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addProject);

          }
      });
      addexperiencei.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addExperience);

          }
      });
addeducationi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addEducation);

    }
});
        addachievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addAchievements);

            }
        });
        addexperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addExperience);
            }
        });
        addhobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addHobbies);
            }
        });
        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addProject);

            }
        });
        addskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addSkill);

            }
        });
        addeducation = view.findViewById(R.id.add_education);
        socialprofile = view.findViewById(R.id.Add_Social);
        socialprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addSocial);
            }
        });

        addeducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_resume_to_addEducation);
            }
        });
        userController = new UserController(getContext());
        getallSkills();
        getallEducation();
        getallProjects();
        getallHobbies();
        getallExperiences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_resume2, container, false);
    }

    private void setuptoolbar() {
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
