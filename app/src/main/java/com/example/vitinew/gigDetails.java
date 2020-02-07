package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitinew.Classes.gigsClass;

public class gigDetails extends AppCompatActivity {
    TextView gigsdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);
        gigsdetail=findViewById(R.id.gigsdescriptionDetail);
        Intent intent=getIntent();
        gigsClass gig= (gigsClass) intent.getSerializableExtra("class");
        gigsdetail.setText(gig.getDescription());
        //Toast.makeText(this, gig.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
