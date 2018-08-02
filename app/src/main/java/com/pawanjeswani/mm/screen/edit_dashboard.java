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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.novoda.merlin.Merlin;
import com.pawanjeswani.mm.R;
import com.pawanjeswani.mm.model.response_user_id;
import com.pawanjeswani.mm.model.restLispojo;
import com.pawanjeswani.mm.model.restlist;
import com.pawanjeswani.mm.model.userpojo;
import com.pawanjeswani.mm.network.ApiUtils;
import com.pawanjeswani.mm.network.RerofitInstance;
import com.pawanjeswani.mm.network.apiinter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import me.omidh.liquidradiobutton.LiquidRadioButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_dashboard extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private CircularImageView ivUser;
    private ImageView ivDashBack, ivDashMenu;
    private EditText etDashUname, etDashUage, etDashUwork, etEtDashUDesc;
    private Button btnSavePro;
    private RadioGroup rgDashGender, rgDashFoodPr;
    private LiquidRadioButton rbMale, rbFemale, rbDashVeg, rbDashNV, rbDashVegan;
    private Spinner spnDashRestlist;
    private String fname = "", lname = "", email = "", id = "", birthdate = "", profile_url = "",genderst="",user_id="33";
    private String [] RestList;
    private Calendar dob = Calendar.getInstance();
    private userpojo dashuser;
    private int selected_food=1,age=18,gender=2;
    private userpojo userdetails;
    double uDashlat=0.0, uDashlon=0.0;
    private GoogleApiClient mLocationClient;
    private Location mLastLoc;
    private String [] nearbyRestArr;
    private ArrayAdapter<String> restAdapter;
    private RadioButton dumRB;
    private restLispojo uDashResponse;
    private restlist rlist;
    private List<restlist> nearbyRestaurants;
    ArrayAdapter<String> restListAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dashboard);
        //mapping with java
        ivUser = findViewById(R.id.ivUserPic);
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
        ivDashBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //getting data from fb
        Intent i = getIntent();
        if(!i.equals(null))
        {
        fname = i.getStringExtra("fname");
        lname = i.getStringExtra("lname");
        email = i.getStringExtra("email");
        id = i.getStringExtra("fbid");
        birthdate = i.getStringExtra("birthdate");
        profile_url = i.getStringExtra("profilepic");
        genderst = i.getStringExtra("gender");
        }
        else {
            fname = "jen";
            lname = "doe";
            email = "jenDoh@gmail.com";
            id = "45789632541";
            birthdate = "01/02/1995";
            profile_url = "jendeprofilepic.com";
            genderst = "female";
        }

        if(genderst.equals("male"))
            gender=1;

        etDashUname.setText("" + fname + " " + lname);
        setAge();
        setgender();
        selected_food = rgDashGender.getCheckedRadioButtonId();
        RerofitInstance rr = new RerofitInstance();


        try {
            Picasso.with(getApplicationContext()).load("https://graph.facebook.com/1782673958467396/picture?type=large")
            .error(R.drawable.intropg1).into(ivUser);
        } catch (Exception e) {
            Toast.makeText(this,
                    ""+e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        //set google api client for getting current lat lon
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mLocationClient = builder.build();

        btnSavePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnSavePro.isEnabled())
                    if(isDataCorret())
                    {
                        Toast.makeText(edit_dashboard.this, "insert User calling", Toast.LENGTH_LONG).show();
                        insertUser();
                        Intent i = new Intent(getApplicationContext(),MainScreen.class);
                        i.putExtra("user_id",user_id);
                        i.putExtra("lat",uDashlat);
                        i.putExtra("lon",uDashlon);
                      //  startActivity(i);
                      //  finish();

                    }
            }
        });

    }

    private void saveUserandpass() {
        //save user details on server
    }


    private void getRestList() {
        Call<restLispojo> rl = ApiUtils.getRestList().get_list(uDashlat,uDashlon);

        rl.enqueue(new Callback<restLispojo>() {
            @Override
            public void onResponse(Call<restLispojo> call, Response<restLispojo> response) {
                uDashResponse = response.body();
                nearbyRestaurants=uDashResponse.getNearbyRestaurants();
                RestList= new String[nearbyRestaurants.size()];
                for(int i =0;i< nearbyRestaurants.size();i++) {
                    rlist = nearbyRestaurants.get(i);
                    RestList[i] = "" + rlist.getName();
                }
                restAdapter= new ArrayAdapter<String> (getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,RestList);
                spnDashRestlist.setAdapter(restAdapter);
            }

            @Override
            public void onFailure(Call<restLispojo> call, Throwable t) {
                etDashUage.setText(""+t.getMessage());
            }
        });
    }

    private void insertUser() {
        Call<String > callingurl =
                ApiUtils.getResponseUser().insertUser(fname+" "+lname,getAge(dob),email,1,profile_url,
                        etDashUwork.getText().toString().trim(),
                        etEtDashUDesc.getText().toString().trim(),
                        getFoodType(selected_food),"123,456,789");

        callingurl.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                etEtDashUDesc.setText(response.body());
                user_id = response.body();
                Toast.makeText(edit_dashboard.this, ""+user_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //etEtDashUDesc.setText(""+call.toString());
            }
        });
    }

    private int getFoodType(int idd){
        if(idd == rbDashVeg.getId())
            return 1;
        else if(idd == rbDashNV.getId())
            return 2;
        else
            return 3;

    }
    private void setgender() {
        if (genderst.equals("male"))
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
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
            uDashlat = mLastLoc.getLatitude();
            uDashlon = mLastLoc.getLongitude();
            getRestList();
        }
        else {

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


    private boolean isDataCorret(){
        if(etDashUname.getText().toString().length()<3)
            {
                etDashUname.setError("invalid name");
                etDashUname.requestFocus();
                return false;
            }
        else if(etDashUage.getText().toString().length()<2)
        {
            etDashUage.setError("invalid Age");
            etDashUage.requestFocus();
            return false;
        }
        else if(etDashUwork.getText().toString().length()<5)
        {
            etDashUwork.setError("invalid work");
            etDashUwork.requestFocus();
            return false;
        }
        else if(etEtDashUDesc.getText().toString().length()<10)
        {
            etEtDashUDesc.setError("not less than 10");
            etEtDashUDesc.requestFocus();
            return false;
        }
        else
            return true;
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
