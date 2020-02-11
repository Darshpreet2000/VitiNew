package com.example.vitinew.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addskilladapter  extends RecyclerView.Adapter<addskilladapter.myskillholder> {
    private List<Addskills> cartlist=new ArrayList<>();
    private addskilladapter.OnItemClicked onClick;
    Context context;

    public addskilladapter(List<Addskills> cartlist, Context context) {
        this.cartlist = cartlist;
        this.context = context;
    }

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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=cartlist.get(position).getId();
                UserController user=new UserController(context);

                Map<String, String> dataMap = new HashMap<String,String>();
                dataMap.put("id",id);


                user.getRequest(dataMap,API.DELETESKILL,getallProjectsListener);
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
        private TextView title,rating;
        private ImageView delete;
        public myskillholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.skilltitle);
            delete=itemView.findViewById(R.id.deleteicon);

            rating=itemView.findViewById(R.id.skillrating);
    //        Removecart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClick.onbuttonclicked(getAdapterPosition());
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
                Log.v("response del",jsonObject.toString());
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