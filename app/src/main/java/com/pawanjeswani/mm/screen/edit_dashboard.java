package com.pawanjeswani.mm.screen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.model.userpojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import me.omidh.liquidradiobutton.LiquidRadioButton;

public class edit_dashboard extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ImageView ivDashBack, ivDashMenu;
    private EditText etDashUname, etDashUage, etDashUwork, etEtDashUDesc;
    private Button btnSavePro;
    private RadioGroup rgDashGender, rgDashFoodPr;
    private LiquidRadioButton rbMale, rbFemale, rbDashVeg, rbDashNV, rbDashVegan;
    private Spinner spnDashRestlist;
    private String fname = "", lname = "", email = "", id = "", birthdate = "", profile_url = "", gender = "";
    private Calendar dob = Calendar.getInstance();
    private userpojo dashuser;
    double uDashlat, uDashlon;
    private GoogleApiClient mLocationClient;
    private Location mLastLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dashboard);
        //mapping with java
        ivDashBack = findViewById(R.id.ivdashback);
        ivDashMenu = findViewById(R.id.ivdashmenu);
        etDashUname = findViewById(R.id.etdashName);
        etDashUwork = findViewById(R.id.etDashWork);
        etDashUage = findViewById(R.id.etDashAge);
        etEtDashUDesc = findViewById(R.id.etDashDescription);
        rgDashGender = findViewById(R.id.rgDashGender);
        rgDashFoodPr = findViewById(R.id.rgDashFood);
        rbMale = findViewById(R.id.rbDashMale);
        rbFemale = findViewById(R.id.rbDashFemale);
        rbDashVeg = findViewById(R.id.rbDashVeg);
        rbDashNV = findViewById(R.id.rbDashNonveg);
        rbDashVegan = findViewById(R.id.rbDashVegan);
        rbMale = findViewById(R.id.rbDashMale);
        spnDashRestlist = findViewById(R.id.spnDashRest);
        btnSavePro = findViewById(R.id.btnDashSave);

        //getting data from fb
        Intent i = getIntent();
        fname = i.getStringExtra("fname");
        lname = i.getStringExtra("lname");
        email = i.getStringExtra("email");
        id = i.getStringExtra("fbid");
        birthdate = i.getStringExtra("birthdate");
        profile_url = i.getStringExtra("profilepic");
        gender = i.getStringExtra("gender");

        etDashUname.setText("" + fname + " " + lname);
        setAge();
        setgender();
        Toast.makeText(this, "profile url yet to be set\n" + profile_url, Toast.LENGTH_LONG).show();

        //set google api client for getting current lat lon
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mLocationClient = builder.build();

        ivDashBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setgender() {
        if (gender.equals("male"))
            rbMale.setChecked(true);
        else
            rbFemale.setChecked(true);
    }

    private void setAge() {
        //getting age from dob
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        try {
            dob.setTime(sdf.parse(birthdate));
            etDashUage.setText("" + getAge(dob));
        } catch (ParseException e) {
            Toast.makeText(this,
                    "exception parse" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getAge(Calendar dob) {
        Calendar today = Calendar.getInstance();
        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);
        int age = curYear - dobYear;
        // if dob is month or day is behind today's month or day
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }
        return age;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLoc = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if(mLastLoc!=null) {
            uDashlat= mLastLoc.getLatitude();
            uDashlon = mLastLoc.getLongitude();
            etEtDashUDesc.setText("latLon"+uDashlat+uDashlon);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "ConnSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connfailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mLocationClient!=null)
        {
            mLocationClient.connect();
        }
    }
}
