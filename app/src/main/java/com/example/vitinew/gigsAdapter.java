package com.example.vitinew;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Details.gigDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class gigsAdapter  extends
        RecyclerView.Adapter<gigsAdapter.gigsholder> {

    public gigsAdapter(List<gigsClass> gigs) {
        this.gigs = gigs;
    }

    private List<gigsClass> gigs=new ArrayList<>();
    private OnItemClicked onClick;
 Context context;
    public gigsAdapter(List<gigsClass> gigs, OnItemClicked onClick) {
        this.gigs =gigs;
        this.onClick = onClick;
    }

    public gigsAdapter() {

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
        void onbuttonclicked(int position);
        //   void onbidclicked(int position);
    }

    @NonNull
    @Override
    public gigsAdapter.gigsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gigslist,parent,false);
        return new gigsholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull gigsholder holder, int position) {

        final gigsClass currentnote=gigs.get(position);
        holder.title.setText(currentnote.getCampaign_title());
        Log.e("cost",String.valueOf(currentnote.getPer_cost()));
        holder.gigsbrand.setText(currentnote.getBrand());
        Picasso.get().load(currentnote.getLogo()).into(holder.gigsicon);
        holder.percost.setText(String.valueOf(currentnote.getPer_cost()));

        //        holder.description.setText(String.valueOf(currentnote.getDescription()));
        holder.gigsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), gigDetails.class);
                intent.putExtra("class",currentnote);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return gigs.size();

    }


    public class gigsholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView gigsbrand,percost;
        private TextView description;
        private ImageView gigsicon;
        private LinearLayout gigsLayout;
        public gigsholder(@NonNull View itemView) {
            super(itemView);
            percost=itemView.findViewById(R.id.stipendtext);
            title=itemView.findViewById(R.id.gigstitle);
            gigsicon=itemView.findViewById(R.id.gigsicon);
            gigsbrand=itemView.findViewById(R.id.gigsbrand);
         //   description=itemView.findViewById(R.id.gigs_description);
            gigsLayout=itemView.findViewById(R.id.gigs_layout);

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
