package com.example.vitinew.Adapters;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.Classes.AddEducation;
import com.example.vitinew.Classes.questions;
import com.example.vitinew.R;

import java.util.ArrayList;
import java.util.List;

public class questionsAdapter extends RecyclerView.Adapter<questionsAdapter.myskillholder> {

    private List<questions> cartlist=new ArrayList<>();
    Context context;
  Editable currentanswer;
    public questionsAdapter(List<questions> cartlist) {
        this.cartlist = cartlist;
    }

    @NonNull
    @Override
    public myskillholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_questions,parent,false);
        return new questionsAdapter.myskillholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myskillholder holder, int position) {
        final questions currentnote=cartlist.get(position);
        holder.question.setText(currentnote.getQuestion());
        holder.answer.setText(currentnote.getAnswer());
    }


    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class myskillholder extends RecyclerView.ViewHolder {
        private TextView question;
        private EditText answer;

        public myskillholder(@NonNull View itemView) {
            super(itemView);
            //        Removecart.setOnClickListener(this);

            question = itemView.findViewById(R.id.ques);
            answer = itemView.findViewById(R.id.ans);
        }
    }
}
