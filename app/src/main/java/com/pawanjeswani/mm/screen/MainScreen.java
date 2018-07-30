package com.pawanjeswani.mm.screen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.adapter.near_users_list_adapter;
import com.pawanjeswani.mm.model.userpojo;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rv_matched_users;
    private near_users_list_adapter nearUsersListAdapter;
    private ArrayList<userpojo> userslist = new ArrayList<>();
    private userpojo dumuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dumuser = new userpojo("4578963","pawan jeswani","45","789","1",
                "sdsd","sdsd","sdsd","dsdss","dsdsdsd");
        userslist.add(dumuser);
        userslist.add(dumuser);
        userslist.add(dumuser);
        userslist.add(dumuser);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rv_matched_users =findViewById(R.id.users_rv);
        rv_matched_users.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_matched_users.setHasFixedSize(true);
        nearUsersListAdapter = new near_users_list_adapter(userslist,this);
        rv_matched_users.setAdapter(nearUsersListAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
