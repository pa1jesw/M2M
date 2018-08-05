package com.pawanjeswani.mm.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.novoda.merlin.MerlinsBeard;
import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.adapter.near_users_list_adapter;
import com.pawanjeswani.mm.model.userpojoRes;
import com.pawanjeswani.mm.network.ApiUtils;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private RecyclerView rv_matched_users;
    private near_users_list_adapter nearUsersListAdapter;
    private List<userpojoRes> userslist = new ArrayList<>();

    private userpojoRes dumuser;
    private userpojoRes gotUserDetails,tempUser;
    private double umainlat,umainlon;
    private String user_id, email="";
    private int curUId;
    private GoogleApiClient mLocationClient;
    //private ArrayList<userpojoRes> usersList;
    private List<userpojoRes> usersList = new ArrayList<>();;
    private Typeface mytypef;
    private String name,work;
    private Location mLastLoc;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    private String desc;
    View view ;
    private MerlinsBeard merlinsBeard;
    private boolean gps_enabled,network_enabled;
    LocationManager lm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //drawer setting up
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mytypef = Typeface.createFromAsset(this.getAssets(),"fonts/Myriad_Pro_Regular.ttf");

        //sharedPrefernce for Facebook json
        sharedPrefs = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        //set google api client for getting current lat lon
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mLocationClient = builder.build();
        view = findViewById(R.id.viewRv);

        lm = lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Intent i = getIntent();
        if(!i.equals(null))
        {
            user_id = i.getStringExtra("user_id");
            //umainlat= i.getDoubleExtra("lat",0.0);
            //umainlon= i.getDoubleExtra("lon",0.0);
            email = i.getStringExtra("uemail");
            work = i.getStringExtra("work");
            desc=i.getStringExtra("desc");
            name= i.getStringExtra("name");
        }
        else
            Toast.makeText(this,
                    "null intent content", Toast.LENGTH_SHORT).show();
        //getting user id
        merlinsBeard = MerlinsBeard.from(getApplicationContext());
        if(merlinsBeard.isConnected()) {
        curUId = getCurUserId();

        //getting nearbyuserdetails
        calnearAPI();
        }
        else {
            Toast.makeText(this,
                    "CHeck your Internet Conncection", Toast.LENGTH_SHORT).show();
        }

      //recyclerview setting
        rv_matched_users =findViewById(R.id.users_rv);
        rv_matched_users.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_matched_users.setHasFixedSize(true);
        setRecViewItems(usersList);
    }

    private void setRecViewItems(List<userpojoRes> userslist) {
        nearUsersListAdapter = new near_users_list_adapter(userslist,this);
        rv_matched_users.setAdapter(nearUsersListAdapter);

    }

    private void calnearAPI() {
        Call<List<userpojoRes>> gettingusers = ApiUtils.getnearusers().getNearUSers(umainlat,umainlon, Integer.parseInt(user_id));

        gettingusers.enqueue(new Callback<List<userpojoRes>>() {
            @Override
            public void onResponse(Call<List<userpojoRes>> call, Response<List<userpojoRes>> response) {
                usersList = response.body();
                setRecViewItems(usersList);
            }

            @Override
            public void onFailure(Call<List<userpojoRes>> call, Throwable t) {
                Toast.makeText(MainScreen.this,
                        "No Users Nearby", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private int getCurUserId(){
        Call<userpojoRes> gettingCurrentUSer = ApiUtils.getUserDet().get_details(Integer.parseInt(user_id));

        gettingCurrentUSer.enqueue(new Callback<userpojoRes>() {
            @Override
            public void onResponse(Call<userpojoRes> call, Response<userpojoRes> response) {
                gotUserDetails = response.body();
                user_id = gotUserDetails.getId();
            }

            @Override
            public void onFailure(Call<userpojoRes> call, Throwable t) {
                Toast.makeText(MainScreen.this,
                        ""+t.getMessage(), Toast.LENGTH_LONG).show();
                user_id=" "+0;
            }
        });
        return Integer.parseInt(user_id);
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
            Intent intent = new Intent(getApplicationContext(),ChatListAct.class);
            intent.putExtra("uid",curUId);
            startActivity(intent);

        }  else if (id == R.id.nav_Edit) {
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


        } else if (id == R.id.nav_About) {
            Toast.makeText(this, "screen for app details", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_Help) {
            Toast.makeText(this, "Link to privacy policy", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_filter) {
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

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLastLoc = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if(mLastLoc!=null) {
            umainlat = mLastLoc.getLatitude();
            umainlon = mLastLoc.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mLocationClient!=null)
        {
            mLocationClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(merlinsBeard.isConnected()) {
            curUId = getCurUserId();

            //getting nearbyuserdetails
            calnearAPI();
        }
        else {
            Toast.makeText(this,
                    "CHeck your Internet Conncection", Toast.LENGTH_SHORT).show();
        }

    }
    private void checkLocation() {
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            new LovelyStandardDialog(this)
                    .setTitle("GPS Disabled")
                    .setMessage("PLease Turn On GPS")
                    .setCancelable(false)
                    .setPositiveButton("Turn On", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            getApplicationContext().startActivity(myIntent);
                        }
                    }).show();

        }
        else{}
    }

}
