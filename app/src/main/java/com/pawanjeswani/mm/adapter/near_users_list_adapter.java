package com.pawanjeswani.mm.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.model.userpojoRes;
import com.pawanjeswani.mm.network.ApiUtils;
import com.pawanjeswani.mm.screen.MainActivity;
import com.pawanjeswani.mm.screen.User_main;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class near_users_list_adapter extends RecyclerView.Adapter<near_users_list_adapter.user_holder> implements AnimateViewHolder {
    private List<userpojoRes> mData;
    private Activity activity;
    private Typeface mytypef;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

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
    public void onBindViewHolder(@NonNull final user_holder holder, final int position) {
        sharedPrefs = holder.itemView.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        final userpojoRes user = mData.get(position);
        holder.tv_rec_uname.setText(""+user.getName());
        holder.tv_rec_uage.setText(""+user.getAge());
        holder.tv_rec_uwork.setText(""+user.getWork());
        Picasso.with(activity).load("https://graph.facebook.com/"+user.getFbId()+"/picture?type=large")
                .error(R.drawable.intropg1).into(holder.iv_rec_user);
        holder.iv_rec_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, User_main.class);
                i.putExtra("user_id",user.getId());
                i.putExtra("name",user.getName());
                i.putExtra("emaill",user.getEmail());
                i.putExtra("age",""+user.getAge());
                i.putExtra("fbid",user.getFbId());
                i.putExtra("work",user.getWork());
                activity.startActivity(i);


            }
        });
        holder.btn_rec_grab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Call<String> call=  ApiUtils.getRightA().rightSwiped(Integer.parseInt(sharedPrefs.getString("uid","")),
                        Integer.parseInt(user.getId()));

               call.enqueue(new Callback<String>() {
                   @Override
                   public void onResponse(Call<String> call, Response<String> response) {
                       removed(holder.getAdapterPosition());
                   }

                   @Override
                   public void onFailure(Call<String> call, Throwable t) {
                       holder.btn_rec_grab.setText(t.getMessage());
                   }
               });
            }
        });
        holder.btn_rec_ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String > callleft = ApiUtils.getLeftA().leftSwiped(Integer.parseInt(sharedPrefs.getString("uid","")),
                        Integer.parseInt(user.getId()));
                callleft.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        removed(holder.getAdapterPosition());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void removed(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

    }

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

    }


    public class user_holder extends RecyclerView.ViewHolder {
        ImageView iv_rec_user;
        TextView tv_rec_uname;
        TextView tv_rec_uage;
        TextView tv_rec_uwork;
        Button btn_rec_grab,btn_rec_ignore,btn_rec_save;


        public user_holder(View itemView) {
            super(itemView);
            mytypef = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Myriad_Pro_Regular.ttf");
            iv_rec_user = itemView.findViewById(R.id.iv_rec_user);
            tv_rec_uname = itemView.findViewById(R.id.tv_rec_username);
            tv_rec_uage = itemView.findViewById(R.id.tv_rec_user_age);
            tv_rec_uwork = itemView.findViewById(R.id.tv_rec_user_work);
            btn_rec_grab = itemView.findViewById(R.id.btn_rec_Grabit);
            btn_rec_ignore = itemView.findViewById(R.id.btn_rec_ignore);
            btn_rec_save = itemView.findViewById(R.id.btn_rec_Save);
            tv_rec_uage.setTypeface(mytypef);
            tv_rec_uname.setTypeface(mytypef);
            tv_rec_uwork.setTypeface(mytypef);
            btn_rec_grab.setTypeface(mytypef);
            btn_rec_ignore.setTypeface(mytypef);

        }
    }
}
