package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitinew.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support extends Fragment {

    Toolbar toolbar;
    public Support() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExpandableTextView expTv1 = (ExpandableTextView) view.findViewById(R.id.expand_text_view_gig);
        ExpandableTextView expTv2 = (ExpandableTextView) view.findViewById(R.id.expand_text_view_project);

        ExpandableTextView expTv3 = (ExpandableTextView) view.findViewById(R.id.expand_text_view_campaign);

        ExpandableTextView expTv4 = (ExpandableTextView) view.findViewById(R.id.expand_text_view_contact);


        if (Build.VERSION.SDK_INT >= 24) {
            expTv1.setText(Html.fromHtml("<strong><h2>Gig Support</h2></strong>"+ "gigs@herody.in", Html.FROM_HTML_MODE_LEGACY));
            expTv2.setText(Html.fromHtml("<strong><h2>Project Support</h2></strong>"+"\n" + "projects@herody.in", Html.FROM_HTML_MODE_LEGACY));

            expTv3.setText(Html.fromHtml("<strong><h2>Campaign Support</h2></strong>"+"Campaigns@herody.in", Html.FROM_HTML_MODE_LEGACY));

            expTv4.setText(Html.fromHtml("<strong><h2>Contact Support</h2></strong>"+"support@herody.in", Html.FROM_HTML_MODE_LEGACY));

        } else {
            expTv1.setText(Html.fromHtml("<h1 style=\"color:black;\">Gig Support</h1>"+"gigs@herody.in"));
            expTv2.setText(Html.fromHtml("<h1 style=\"color:black;\">Project Support</h1>"+"\n" + "projects@herody.in"));

            expTv3.setText(Html.fromHtml("<h1 style=\"color:black;\">Campaign Support</h1>"+"Campaigns@herody.in"));

            expTv4.setText(Html.fromHtml("<h1 style=\"color:black;\">Contact Support</h1>"+"support@herody.in"));

        }

    }

    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }
}
