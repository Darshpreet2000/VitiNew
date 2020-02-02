package com.example.myapplication.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.example.myapplication.Connections.UserController;
import com.example.myapplication.R;
import com.example.myapplication.Util.API;

/**
 * A simple {@link Fragment} subclass.
 */
public class Resume extends Fragment {
    String skilladded;
    int ratingadded;
    Toolbar toolbar;
    UserController userController;
    Button addskills;
    public Resume() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addskills=getActivity().findViewById(R.id.Add_Skills);
        addskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addskills();
            }
        });
       userController=new UserController(getContext());
    }
    private void Addskills(){

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        final RatingBar rating = new RatingBar(getContext());
        final EditText addskill=new EditText(getContext());
        addskill.setHint("Add Skill");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(5,5,5,5);
        addskill.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);
        Drawable drawable = rating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#0064A8"),PorterDuff.Mode.SRC_ATOP);
        linearLayout.addView(addskill);
        //add ratingBar to linearLayout
        linearLayout.addView(rating);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Add Skills");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);



        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });



        // Button OK

        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ratingadded=rating.getProgress();
                       skilladded=addskill.getText().toString();
                    if(skilladded.length()==0){
                        addskill.setError("This is required");
                        addskill.requestFocus();
                        return;
                    }
                    ///Make API call to add
                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


         popDialog.create();
        popDialog.show();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume2, container, false);
    }
    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
