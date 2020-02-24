package com.example.vitinew.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitinew.Classes.ProjectDisplay;
import com.example.vitinew.Details.ProjectDetail;
import com.example.vitinew.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DesplayProjectAdapter extends
        RecyclerView.Adapter<DesplayProjectAdapter.DesplayProjectHolder> {

    public DesplayProjectAdapter(List<ProjectDisplay> Project) {
        this.Project = Project;
    }

    private List<ProjectDisplay> Project=new ArrayList<>();
    private DesplayProjectAdapter.OnItemClicked onClick;

    public DesplayProjectAdapter(List<ProjectDisplay> Project, DesplayProjectAdapter.OnItemClicked onClick) {
        this.Project =Project;
        this.onClick = onClick;
    }

    public DesplayProjectAdapter() {

    }

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
        void onbuttonclicked(int position);
        //   void onbidclicked(int position);
    }

    @NonNull
    @Override
    public DesplayProjectAdapter.DesplayProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projectlist,parent,false);
        return new DesplayProjectAdapter.DesplayProjectHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull DesplayProjectAdapter.DesplayProjectHolder holder, int position) {

         ProjectDisplay currentnote=Project.get(position);
        holder.Projecttitle.setText(currentnote.getTitle());

       // holder.description.setText(currentnote.getDes());
        holder.position.setText(currentnote.getCount());
        holder.stipend.setText(currentnote.getStipend());
      holder.time.setText(currentnote.getDuration());
        Log.d("this",currentnote.getTitle());

        Picasso.get().load(currentnote.getImage()).into(holder.gigsicon);
        holder.cat.setText(currentnote.getCompanyName());
        ///holder.gigsbrand.setText();
      //  holder.description.setText(String.valueOf(currentnote.getDescription()));
       holder.ProjectListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), ProjectDetail.class);
                intent.putExtra("class", (Serializable) currentnote);

                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return Project.size();

    }


    class DesplayProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView Projecttitle,time,position,stipend,cat;
        private TextView gigscat;
        private TextView gigsbrand;
        private TextView description;
        private ImageView gigsicon;
        private CardView ProjectListLayout;
        public DesplayProjectHolder(@NonNull View itemView) {
            super(itemView);
            Projecttitle=itemView.findViewById(R.id.ProjectTitle);
            time=itemView.findViewById(R.id.duration);
            position=itemView.findViewById(R.id.postions);
           stipend=itemView.findViewById(R.id.stipend);
     cat=itemView.findViewById(R.id.category);
        gigsicon=itemView.findViewById(R.id.projectlogo);
            //gigsicon=itemView.findViewById(R.id.gigsicon);
           // gigscat=itemView.findViewById(R.id.gigscats);
          //  gigsbrand=itemView.findViewById(R.id.gigsbrand);
         //   description=itemView.findViewById(R.id.ProjectDes);
            ProjectListLayout=itemView.findViewById(R.id.projectlistlayout);

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

