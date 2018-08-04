package com.pawanjeswani.mm.screen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.pawanjeswani.mm.R;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.chat.Chat;


public class ChatListAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean isPswdGot;
    String Pswd="",email="";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    static final String  APP_ID = "72622";
    static final String  AUTH_KEY = "S7nmy393teRB2Zm";
    static final String  AUTH_SECRET = "QhbFUhd5jwKWjAB";
    static final String  ACCOUNT_KEY = "CMe1J7yE-4s-r16g-s-Q";
    private Typeface mytypef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutInflater layoutif = getLayoutInflater();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mytypef = Typeface.createFromAsset(this.getAssets(),"fonts/Myriad_Pro_Regular.ttf");

        intializeFramework();
        registerSession();

        //sharedPrefernce for getPswd
        sharedPrefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        Intent i = getIntent();
        if(!i.equals(null)){
            email = i.getStringExtra("uem");
            Pswd = i.getStringExtra("name");
            Pswd.concat(i.getStringExtra("work"));
            editor.putString("chatemail", email);
            editor.commit();
        }

        createQBUser();
    }

    private void createQBUser() {

            //if (!sharedPrefs.getBoolean("isgetPswd", false) && !sharedPrefs.getString("chatemail","").isEmpty()) {
            QBUser chatUser = new QBUser(email,Pswd);

            QBUsers.signUp(chatUser).performAsync(new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    Toast.makeText(ChatListAct.this,
                            "created user with "+qbUser.getPassword(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(QBResponseException e) {
                    Toast.makeText(ChatListAct.this,
                            "error in signup"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void registerSession() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(ChatListAct.this,
                        "err register"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intializeFramework() {
        QBSettings.getInstance().init(getApplicationContext(),APP_ID,AUTH_KEY,AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
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
