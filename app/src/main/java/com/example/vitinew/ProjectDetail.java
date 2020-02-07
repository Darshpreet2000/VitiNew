package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Classes.gigsClass;

public class ProjectDetail extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        Intent intent=getIntent();
      ProjectDisplay projectdetail= (ProjectDisplay) intent.getSerializableExtra("class");
    textView=findViewById(R.id.xyz);
    textView.setText(projectdetail.getDes());
    }
}
