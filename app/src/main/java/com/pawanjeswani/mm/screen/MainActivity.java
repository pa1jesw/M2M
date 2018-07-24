package com.pawanjeswani.mm.screen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.novoda.merlin.MerlinsBeard;
import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.adapter.sliderAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends AppCompatActivity {

    private ViewPager vpIntro;
    private Button fbtnfb,skipfb;
    private sliderAdapter adapter;
    private CircleIndicator pageIndicator;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String fname="",lname="",email="",id="",birthdate="",profile_url="",gender="";

    private MerlinsBeard merlinsBeard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fbCallback mngr
        callbackManager = CallbackManager.Factory.create();

        //internet handlet merlinbeard
        merlinsBeard = MerlinsBeard.from(getApplicationContext());

        //mapping to xml elemnts
        loginButton = (LoginButton) findViewById(R.id.login_button);
        vpIntro = findViewById(R.id.vpIntro);
        pageIndicator = findViewById(R.id.pageIndicator);
        adapter = new sliderAdapter(this);
        vpIntro.setAdapter(adapter);
        pageIndicator.setViewPager(vpIntro);
        fbtnfb = findViewById(R.id.btnFBC);
        skipfb = findViewById(R.id.btnskip);
        checklocperm();
        skipfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),edit_profile.class);
                i.putExtra("fname","firstname &");
                i.putExtra("lname","lastname from fb");
                i.putExtra("email","email");
                i.putExtra("fbid","fb unique id");
                i.putExtra("birthdate","01/02/1995");
                i.putExtra("profilepic","profile_url by fb");
                i.putExtra("gender","female");
                startActivity(i);
                finish();
            }
        });
        if(merlinsBeard.isConnected()) {
            fbtnfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fbconnect();
                }
            });
        }
        else
            Toast.makeText(this, "Please check your internet Coneection", Toast.LENGTH_SHORT).show();
        loginButton.setReadPermissions("email","public_profile","user_birthday","user_gender");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                String uid = loginResult.getAccessToken().getUserId();
                GraphRequest gr= GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayuser(object);

                    }
                });
                Bundle params = new Bundle();
                params.putString("fields","first_name,last_name, email, id, birthday, gender");
                gr.setParameters(params);
                gr.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(MainActivity.this, "exception"+exception.getCause().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayuser(JSONObject object) {

        try {
            fname = object.getString("first_name");
            lname = object.getString("last_name");
            if(object.has("email"))
                email = object.getString("email");
            else
                email = "not available";
            id = object.getString("id");
            birthdate =  object.getString("birthday");
            profile_url = "http://graph.facebook.com/" + id + "/picture?type=large";
            gender = object.getString("gender");
            Intent i = new Intent(getApplicationContext(),edit_dashboard.class);
            i.putExtra("fname",fname);
            i.putExtra("lname",lname);
            i.putExtra("email",email);
            i.putExtra("fbid",id);
            i.putExtra("birthdate",birthdate);
            i.putExtra("profilepic",profile_url);
            i.putExtra("gender",gender);
            startActivity(i);

        } catch (JSONException e) {
            Toast.makeText(this,
                    "exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //fb login process
    private void fbconnect(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
                "public_profile", "email", "user_birthday", "user_gender"));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void checklocperm(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */
                        Toast.makeText(MainActivity.this, "granted", Toast.LENGTH_SHORT).show();}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                })
                .check();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}