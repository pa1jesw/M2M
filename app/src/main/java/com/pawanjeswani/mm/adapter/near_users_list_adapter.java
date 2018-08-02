package com.pawanjeswani.mm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.model.userpojoRes;
import com.pawanjeswani.mm.screen.User_main;
import com.squareup.picasso.Picasso;

import java.util.List;

public class near_users_list_adapter extends RecyclerView.Adapter<near_users_list_adapter.user_holder> {
    private List<userpojoRes> mData;
    private Activity activity;

    public near_users_list_adapter(List<userpojoRes> mData, Activity activity) {
        this.mData = mData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public user_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_rec_view,parent,
                false);
        return new user_holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull user_holder holder, int position) {
        userpojoRes user = mData.get(position);
        holder.tv_rec_uname.setText(""+user.getName());
        holder.tv_rec_uage.setText(""+user.getAge());
        holder.tv_rec_uwork.setText(""+user.getWork());
        Picasso.with(activity).load("https://graph.facebook.com/"+user.getFbId()+"/picture?type=large")
                .error(R.drawable.intropg1).into(holder.iv_rec_user);
        Intent i = new Intent(activity, User_main.class);
        i.putExtra("user_id",user.getId());
        i.putExtra("name",user.getName());
        i.putExtra("age",user.getAge());
        i.putExtra("fbid",user.getFbId());
        i.putExtra("work",user.getWork());
        activity.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class user_holder extends RecyclerView.ViewHolder {
        ImageView iv_rec_user;
        TextView tv_rec_uname;
        TextView tv_rec_uage;
        TextView tv_rec_uwork;
        Button btn_rec_grab,btn_rec_ignore,btn_rec_save;


        public user_holder(View itemView) {
            super(itemView);

            iv_rec_user = itemView.findViewById(R.id.iv_rec_user);
            iv_rec_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            tv_rec_uname = itemView.findViewById(R.id.tv_rec_username);
            tv_rec_uage = itemView.findViewById(R.id.tv_rec_user_age);
            tv_rec_uwork = itemView.findViewById(R.id.tv_rec_user_work);
            btn_rec_grab = itemView.findViewById(R.id.btn_rec_Grabit);
            btn_rec_ignore = itemView.findViewById(R.id.btn_rec_ignore);
            btn_rec_save = itemView.findViewById(R.id.btn_rec_Save);

            btn_rec_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_rec_save.setText("saved");
                }
            });
            btn_rec_grab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_rec_grab.setText("grabed");
                }
            });
            btn_rec_ignore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_rec_ignore.setText("ignored");
                }
            });

        }
    }
}
