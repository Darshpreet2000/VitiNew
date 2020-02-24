package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ShowResume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resume);
        String text=getIntent().getStringExtra("text");
        TextView data=findViewById(R.id.text);

        if (Build.VERSION.SDK_INT >= 24) {
            data.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));

        } else {
            data.setText(Html.fromHtml(text));


        }
    }
}
