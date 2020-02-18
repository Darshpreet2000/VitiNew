package com.example.vitinew.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.AddEducation;
import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.Classes.ProjectDetails;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Util.Constants;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class addEducationAdapter  extends RecyclerView.Adapter<addEducationAdapter.myskillholder> {
    private List<AddEducation> cartlist=new ArrayList<>();
   Context context;

    public addEducationAdapter(List<AddEducation> cartlist, Context context) {
        this.cartlist = cartlist;
        this.context = context;
    }

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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=cartlist.get(position).getId();
                UserController user=new UserController(context);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("id",id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                user.postWithJsonRequest(API.DELETEEDU,jsonObject,getallProjectsListener);
                cartlist.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class myskillholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView type,name,course,start,end;
       private ImageView delete;
        public myskillholder(@NonNull View itemView) {
            super(itemView);
            //        Removecart.setOnClickListener(this);
             delete=itemView.findViewById(R.id.deleteicon);
           type=itemView.findViewById(R.id.type);

            name=itemView.findViewById(R.id.name);

            course=itemView.findViewById(R.id.course);
            start=itemView.findViewById(R.id.start);

            end=itemView.findViewById(R.id.end);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private final ResponseListener getallProjectsListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        Toast.makeText(context, "Deleted Successful", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            } finally {
            }
        }
        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };
}