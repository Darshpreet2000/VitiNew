package com.example.vitinew.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.Classes.ProjectDetails;
import com.example.vitinew.R;
import com.example.vitinew.ui.ResumeExtra.AddProject;

import java.util.ArrayList;
import java.util.List;

public class addProjectAdapter  extends RecyclerView.Adapter<addProjectAdapter.myskillholder> {
    private List< ProjectDetails> cartlist=new ArrayList<>();
    private addskilladapter.OnItemClicked onClick;

    public addProjectAdapter(List< ProjectDetails> cartlist) {
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
                .inflate(R.layout.itemprojectresume,parent,false);
        return new addProjectAdapter.myskillholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myskillholder holder, int position) {
        final ProjectDetails currentnote=cartlist.get(position);
        holder.title.setText(currentnote.getTitle());
        holder.desc.setText(currentnote.getDesc());
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class myskillholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,desc;

        public myskillholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            //        Removecart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClick.onbuttonclicked(getAdapterPosition());
        }
    }
}
