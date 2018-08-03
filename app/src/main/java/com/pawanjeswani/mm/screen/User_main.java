package com.pawanjeswani.mm.screen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.adapter.near_users_list_adapter;
import com.pawanjeswani.mm.model.userpojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class User_main extends AppCompatActivity{

    private String user_id,name,work,uname,desc,email;
    private int age,id,fbid;
    private TextView tv_detail_uname,tv_detail_udesc,tv_detail_username,tv_detail_user_age,tv_detail_user_work;
    private  Button btn_detail_Grabit,btn_detail_ignore;
    private com.mikhaellopez.circularimageview.CircularImageView iv_detail_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_detail_uname=findViewById(R.id.tv_detail_uname);
        tv_detail_udesc=findViewById(R.id.tv_detail_udesc);
        tv_detail_user_age=findViewById(R.id.tv_detail_user_age);
        tv_detail_user_work=findViewById(R.id.tv_detail_user_work);
        btn_detail_Grabit=findViewById(R.id.btn_detail_Grabit);
        btn_detail_ignore=findViewById(R.id.btn_detail_ignore);
        tv_detail_username=findViewById(R.id.tv_detail_username);
        iv_detail_user=findViewById(R.id.iv_detail_user);
/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

*/
        Intent i = getIntent();
        if(!i.equals(null))
        {
        user_id = i.getStringExtra("user_id");
        name = i.getStringExtra("name");
        email = i.getStringExtra("emaill");
        fbid = i.getIntExtra("fbid",457896321);
        work = i.getStringExtra("work");
        age = i.getIntExtra("age",20);
        }
        tv_detail_username.setText(name);
        tv_detail_user_work.setText(work);
        //tv_detail_user_age.setText(age);
        tv_detail_udesc.setText(work);
        Picasso.with(this).load("https://graph.facebook.com/"+fbid+"/picture?type=large")
                .error(R.drawable.intropg1).into(iv_detail_user);

        btn_detail_ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ChatListAct.class);
                i.putExtra("uem",email);
                startActivity(i);
            }
        });
        btn_detail_Grabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

  /*  @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Chat) {
            // Handle the camera action
        } else if (id == R.id.nav_Request) {

        } else if (id == R.id.nav_Edit) {

        } else if (id == R.id.nav_About) {

        } else if (id == R.id.nav_Help) {

        } else if (id == R.id.nav_filter) {

        }
    return true;
    }*/
}
