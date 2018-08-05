package com.pawanjeswani.mm.screen;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
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
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

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
    private EditText etDashUname, etDashUage, etDashUwork, etEtDashUDesc,etIntRest;
    private TextView tvUsernm;
    private Button btnSavePro;
    private RadioGroup rgDashGender, rgDashFoodPr;
    private LiquidRadioButton rbMale, rbFemale, rbDashVeg, rbDashNV, rbDashVegan;
    private Spinner spnDashRestlist;
    private String fname = "", lname = "", email = "", id = "", birthdate = "", profile_url = "",genderst="",user_id="33",interstrests,passintids;
    private String [] RestList, nearbyRestArrid;
    private Calendar dob = Calendar.getInstance();
    private int selected_food=1,age=18,gender=0;
    double uDashlat=0.0, uDashlon=0.0;
    private GoogleApiClient mLocationClient;
    private Location mLastLoc;
    private restLispojo uDashResponse;
    private restlist rlist;
    private List<restlist> nearbyRestaurants;
    boolean isinserted = true;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    Typeface mytypef;
    private boolean isGotRestList;
    private int genderid;
    boolean gps_enabled,network_enabled;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dashboard);

        //set google api client for getting current lat lon
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mLocationClient = builder.build();

        //mapping with java
        mytypef = Typeface.createFromAsset(this.getAssets(),"fonts/Myriad_Pro_Regular.ttf");
        tvUsernm = findViewById(R.id.tvDashUname);
        etIntRest = findViewById(R.id.etIntRests);
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
        ivDashMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_dashboard.this, "PLease Fill The Details First", Toast.LENGTH_LONG).show();
            }
        });
        tvUsernm.setTypeface(mytypef);
        etDashUage.setTypeface(mytypef);
        etDashUname.setTypeface(mytypef);
        etEtDashUDesc.setTypeface(mytypef);
        etDashUwork.setTypeface(mytypef);
        etIntRest.setTypeface(mytypef);
        rbDashNV.setTypeface(mytypef);
        rbDashVeg.setTypeface(mytypef);
        rbDashVegan.setTypeface(mytypef);
        rbFemale.setTypeface(mytypef);
        rbMale.setTypeface(mytypef);
        etDashUage.setTypeface(mytypef);
        etDashUage.setTypeface(mytypef);

        //sharedPrefernce for Facebook json
        sharedPrefs = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = false;
        network_enabled = false;
        checkLocation();
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

        editor.putInt("curUGen",gender);
        editor.putString("curUBirth",birthdate);
        editor.putString("curUGen",genderst);
        editor.commit();
        }
        /*else {
            fname = "jen";
            lname = "doe";
            email = "jenDoh@gmail.com";
            id = "45789632541";
            birthdate = "01/02/1995";
            profile_url = "jendeprofilepic.com";
            genderst = "female";
        }*/

        if(genderst.equals("male"))
            gender=1;
        etDashUname.setText("" + fname + " " + lname);
        setAge();
        setgender();
        selected_food = rgDashGender.getCheckedRadioButtonId();
        genderid= rgDashGender.getCheckedRadioButtonId();

        try {
            Picasso.with(getApplicationContext()).load("https://graph.facebook.com/1782673958467396/picture?type=large")
            .error(R.drawable.intropg1).into(ivUser);
        } catch (Exception e) {
            Toast.makeText(this,
                    ""+e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        etIntRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGotRestList)
                    getInterstRest(RestList);
                else
                    Toast.makeText(edit_dashboard.this,
                            "Please wait", Toast.LENGTH_SHORT).show();
            }
        });

        btnSavePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnSavePro.isEnabled())
                    if(isDataCorret())
                    {

                        callMianScreen(user_id,uDashlat,uDashlon);
                        if(isinserted)
                        {
                            editor.putString("curuname",fname+" "+lname);
                            editor.commit();
                            insertUser();

                        }
                    }

            }
        });

    }

    private void callMianScreen(String user_id, double uDashlat, double uDashlon) {
        Intent i = new Intent(getApplicationContext(),MainScreen.class);
        i.putExtra("user_id",user_id);
        i.putExtra("lat",uDashlat);
        i.putExtra("lon",uDashlon);
        i.putExtra("uemail",email);
        i.putExtra("name",fname+" "+lname);
        i.putExtra("desc",etEtDashUDesc.getText().toString());
        i.putExtra("work",etDashUwork.getText().toString());
        startActivity(i);
        finish();
    }

    private void getInterstRest(final String[] adapter) {
        new LovelyChoiceDialog(this, R.style.CheckBoxTintTheme)
                .setTitle("Select Restuarants")
                .setMessage("From the Folowing")
                .setItemsMultiChoice(adapter, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                        interstrests=" ";
                        for(int i=0;i<items.size();i++)
                        {
                            interstrests+=RestList[positions.get(i)]+" , ";
                        }
                        setpassintids(positions,items);
                        etIntRest.setText(interstrests);
                    }
                })
                .setConfirmButtonText("Confirm")
                .show();
    }

    private void setpassintids(List<Integer> positions, List<String> items) {
        for(int i=0;i<items.size();i++)
        {
            passintids=nearbyRestArrid[positions.get(i)]+" , ";
        }
    }


    private void getRestList() {
        Call<restLispojo> rl = ApiUtils.getRestList().get_list(uDashlat,uDashlon);

        rl.enqueue(new Callback<restLispojo>() {
            @Override
            public void onResponse(Call<restLispojo> call, Response<restLispojo> response) {
                uDashResponse = response.body();
                nearbyRestaurants=uDashResponse.getNearbyRestaurants();
                RestList= new String[nearbyRestaurants.size()];
                isGotRestList = true;
                nearbyRestArrid= new String[nearbyRestaurants.size()];
                for(int i =0;i< nearbyRestaurants.size();i++) {
                    rlist = nearbyRestaurants.get(i);
                    nearbyRestArrid[i]= ""+rlist.getId();
                    RestList[i] = "" + rlist.getName();
                }

                //restAdapter= new ArrayAdapter<String> (getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,RestList);
                //getInterstRest(RestList);
            }

            @Override
            public void onFailure(Call<restLispojo> call, Throwable t) {
                etDashUage.setText(""+t.getMessage());
            }
        });
    }

    private void insertUser() {

        Call<String > callingurl =
                ApiUtils.getResponseUser().insertUser(etDashUname.getText().toString(), Integer.parseInt(etDashUage.getText().toString()),
                        email,""+id,gender,"QBUserDet",
                        etDashUwork.getText().toString(),
                        etEtDashUDesc.getText().toString(),
                        getFoodType(selected_food),passintids);
            editor.putString("curUName",etDashUname.getText().toString());
            editor.putString("curUEmail",email);
            editor.putString("curUWork",etDashUwork.getText().toString());
            editor.putString("curUDesc",etEtDashUDesc.getText().toString());
            editor.putInt("curUAge", Integer.parseInt(etDashUage.getText().toString()));
            editor.putString("cuUFbid",id);
            editor.commit();

        callingurl.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                isinserted = true;
                user_id = response.body();
                editor.putString("uid",user_id);
                editor.commit();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(edit_dashboard.this,
                        "Failed to insert user"+t.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (genderst.equals("male")){
            rbMale.setChecked(true);
            gender=1;
            }
        else{
            rbFemale.setChecked(true);
            gender=0;
    }
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
            editor.putString("userLat",String.valueOf(uDashlat));
            editor.putString("userLon",String.valueOf(uDashlon));
            isGotRestList=true;
            editor.commit();
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
        setGenderId();
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

    private void setGenderId() {
        genderid= rgDashGender.getCheckedRadioButtonId();
        if(genderid==rbFemale.getId())
            gender=0;
        else
            gender=1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mLocationClient!=null)
        {
            mLocationClient.connect();
        }
        if(getIntent().getIntExtra("fromAct",0)!=0){
            isinserted=false;

        } else
        {
            if(!(sharedPrefs.getString("uid","33").equals("33")))

            callMianScreen(sharedPrefs.getString("uid","33"),uDashlat,uDashlon);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
      /*  Profile userProfile = Profile.getCurrentProfile();
        if (userProfile != null){

        }else{

        }
        if(!(sharedPrefs.getString("uid","33").equals("33")))
        {

            callMianScreen(sharedPrefs.getString("uid","33"),uDashlat,uDashlon);
        }*/
        checkLocation();
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
                    .setCancelable(false)
                    .setMessage("PLease Turn On GPS")
                    .setPositiveButton("Turn On", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            getApplicationContext().startActivity(myIntent);
                        }
                    }).show();

        }
        else
            getRestList();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
