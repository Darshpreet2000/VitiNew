package com.example.vitinew.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.R;

import java.util.ArrayList;
import java.util.List;

public class addskilladapter  extends RecyclerView.Adapter<addskilladapter.myskillholder> {
    private List<Addskills> cartlist=new ArrayList<>();
    private addskilladapter.OnItemClicked onClick;

    public addskilladapter(List<Addskills> cartlist) {
        this.cartlist = cartlist;
      //  this.onClick = onClick;
    }

    public interface OnItemClicked {
        void onbuttonclicked(int position);
        //   void onbidclicked(int position);
    }


    @NonNull
    @Override
    public myskillholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_skills,parent,false);
        return new addskilladapter.myskillholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myskillholder holder, int position) {
        final Addskills currentnote=cartlist.get(position);
        holder.title.setText(currentnote.getSkillname());
        holder.rating.setText(currentnote.getRating());
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class myskillholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,rating;

        public myskillholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.skilltitle);
               rating=itemView.findViewById(R.id.skillrating);
    //        Removecart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClick.onbuttonclicked(getAdapterPosition());
        }
    }
}