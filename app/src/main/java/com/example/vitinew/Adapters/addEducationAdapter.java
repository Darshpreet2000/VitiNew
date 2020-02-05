package com.example.vitinew.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.Classes.AddEducation;
import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.R;

import java.util.ArrayList;
import java.util.List;

public class addEducationAdapter  extends RecyclerView.Adapter<addEducationAdapter.myskillholder> {
    private List<AddEducation> cartlist=new ArrayList<>();
    private addskilladapter.OnItemClicked onClick;

    public addEducationAdapter(List<AddEducation> cartlist) {
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
                .inflate(R.layout.itemaddeducation,parent,false);
        return new addEducationAdapter.myskillholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myskillholder holder, int position) {
        final AddEducation currentnote=cartlist.get(position);
         holder.type.setText(currentnote.getType());
        holder.name.setText(currentnote.getName());
        holder.course.setText(currentnote.getCourse());
        holder.start.setText(currentnote.getStart());
        holder.end.setText(currentnote.getEnd());

    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class myskillholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView type,name,course,start,end;

        public myskillholder(@NonNull View itemView) {
            super(itemView);
            //        Removecart.setOnClickListener(this);

           type=itemView.findViewById(R.id.type);

            name=itemView.findViewById(R.id.name);

            course=itemView.findViewById(R.id.course);
            start=itemView.findViewById(R.id.start);

            end=itemView.findViewById(R.id.end);
        }

        @Override
        public void onClick(View v) {
            onClick.onbuttonclicked(getAdapterPosition());
        }
    }
}