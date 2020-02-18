package com.example.vitinew.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.AboutCampaign;
import com.example.vitinew.Classes.campaignClass;
import com.example.vitinew.R;
import com.example.vitinew.gigDetails;
import com.example.vitinew.gigsAdapter;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class campaignAdapter extends  RecyclerView.Adapter<campaignAdapter.campaignsholder> {

    public campaignAdapter(List<campaignClass> campaignClasses) {
        this.campaignClasses = campaignClasses;
    }

    private List<campaignClass> campaignClasses = new ArrayList<>();
    private campaignAdapter.OnItemClicked onClick;
    Context context;

    public campaignAdapter(List<campaignClass> campaignClasses, gigsAdapter.OnItemClicked onClick) {
        this.campaignClasses = campaignClasses;
        this.onClick = (OnItemClicked) onClick;
    }

    public campaignAdapter() {

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);

        void onbuttonclicked(int position);
        //   void onbidclicked(int position);
    }

    @NonNull
    @Override
    public campaignAdapter.campaignsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.campaignlist, parent, false);
        return new campaignAdapter.campaignsholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull campaignAdapter.campaignsholder holder, int position) {

        final campaignClass currentnote = campaignClasses.get(position);
        holder.title.setText(currentnote.getTitle());
        holder.campaignbrand.setText(currentnote.getBrand());
        Picasso.get().load(currentnote.getLogo()).into(holder.campaignicon);

//        holder.description.setText(String.valueOf(currentnote.getDescription()));
        holder.campaignLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), AboutCampaign.class);
                intent.putExtra("class", (Serializable) currentnote);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return campaignClasses.size();

    }


    public class campaignsholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;

        private TextView campaignbrand;
        private TextView description;
        private ImageView campaignicon;
        private LinearLayout campaignLayout;

        public campaignsholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.campaigntitle);
            campaignicon = itemView.findViewById(R.id.campaignicon);
            campaignbrand = itemView.findViewById(R.id.campaignbrand);
            //   description=itemView.findViewById(R.id.gigs_description);
            campaignLayout = itemView.findViewById(R.id.campaignlistlayout);
        }

        @Override
        public void onClick(View v) {
            onClick.onbuttonclicked(getAdapterPosition());
            // onClick.onbidclicked(getAdapterPosition());
        }
    }

    public class ViewHolder {
    }
}