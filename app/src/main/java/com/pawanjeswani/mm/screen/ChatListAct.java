package com.pawanjeswani.mm.screen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import org.jivesoftware.smack.chat.Chat;

import java.util.Random;


public class ChatListAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean isPswdGot=false;
    String Pswd="",email="";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    static final String  APP_ID = "72622";
    static final String  AUTH_KEY = "S7nmy393teRB2Zm";
    static final String  AUTH_SECRET = "QhbFUhd5jwKWjAB";
    static final String  ACCOUNT_KEY = "CMe1J7yE-4s-r16g-s-Q";
    private boolean isSignUp;
    private boolean matchedUser= true;
    private Typeface mytypef;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    private QBUser chatUser;


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
       // login();

        //sharedPrefernce for getPswd
        sharedPrefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        Intent i = getIntent();
        if(!i.equals(null)){
            email = i.getStringExtra("uem");
            //Pswd = i.getStringExtra("name");
            //Pswd.concat(i.getStringExtra("work"));
            editor.putString("chatemail", email);
            Pswd = getRandomString(8);
            editor.putString("curUPswd", Pswd);
            editor.commit();
        }
        if(isSignUp)
            createQBUser();
        else {
           // loginQBUser();
            createSession();
        }


    }

    private void createSession() {
        final ProgressDialog mDialog = new ProgressDialog(ChatListAct.this);
        mDialog.setMessage("PLease Wait for a moment...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        QBAuth.createSession(chatUser).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
            mDialog.cancel();
                Toast.makeText(ChatListAct.this,
                        "No Users Available for Chat", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(ChatListAct.this,
                        "SssErr"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginQBUser() {
        if(matchedUser)
        {
            Toast.makeText(this,
                    "LOgging in ", Toast.LENGTH_SHORT).show();

        }
    }

    private static String getRandomString ( int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private void createQBUser() {

            chatUser = new QBUser(email,Pswd);

            QBUsers.signUp(chatUser).performAsync(new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                            isSignUp = true;
                            editor.putBoolean("isSignUp",isSignUp);
                            editor.commit();
                }

                @Override
                public void onError(QBResponseException e) {
                    Toast.makeText(ChatListAct.this,
                            "error in signup"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void login() {
        QBUsers.signIn(chatUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {


            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(ChatListAct.this, "errln"+e.getMessage(), Toast.LENGTH_LONG).show();
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

        if (id == R.id.nav_cl_Chat) {
            // Handle the camera action


        }  else if (id == R.id.nav_cl_Edit) {
            Intent intent = new Intent(getApplicationContext(),edit_dashboard.class);
            intent.putExtra("fname",sharedPrefs.getString("curUName",""));
            intent.putExtra("fromAct",2);
            intent.putExtra("lname","");
            intent.putExtra("email",sharedPrefs.getString("curUEmail",""));
            intent.putExtra("work",sharedPrefs.getString("curUWork",""));
            intent.putExtra("desc",sharedPrefs.getString("curUDesc",""));
            intent.putExtra("fbid",sharedPrefs.getString("cuUFbid",""));
            intent.putExtra("birthdate",sharedPrefs.getString("curUBirth",""));
            intent.putExtra("gender",sharedPrefs.getString("curUGen",""));
            intent.putExtra("profilepic",sharedPrefs.getString("curUProfile",""));
            startActivity(intent);


        } else if (id == R.id.nav_cl_About) {
            Toast.makeText(this, "screen for app details", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_cl_Help) {
            Toast.makeText(this, "Link to privacy policy", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_cl_filter) {
            myView();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void myView() {
        new LovelyCustomDialog(this,R.style.Theme_AppCompat_Dialog)
                .setView(R.layout.filter_container).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(! sharedPrefs.getBoolean("isSignUp",false)){

        }
        else
        {

        }

    }
}

