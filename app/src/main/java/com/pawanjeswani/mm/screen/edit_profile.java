    package com.pawanjeswani.mm.screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.pawanjeswani.mm.R;

    public class edit_profile extends AppCompatActivity {

    private Button btneditpr;
    private ImageView ivEditBack,ivEditMenu;
    TextView tvusername;
    private String fname = "", lname = "", email = "", id = "", birthdate = "", profile_url = "", gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        ivEditBack = findViewById(R.id.iveditback);
        ivEditMenu = findViewById(R.id.iveditmenu);
        tvusername = findViewById(R.id.tvEditUName);
        ivEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ivEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_profile.this,
                        "Menu Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        btneditpr =findViewById(R.id.btnEditPro);

        Intent i = getIntent();
        fname = i.getStringExtra("fname");
        lname = i.getStringExtra("lname");
        email = i.getStringExtra("email");
        id = i.getStringExtra("fbid");
        birthdate = i.getStringExtra("birthdate");
        profile_url = i.getStringExtra("profilepic");
        gender = i.getStringExtra("gender");

        tvusername.setText(""+fname+" "+lname);
        btneditpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),edit_dashboard.class);
                i.putExtra("fname",fname);
                i.putExtra("lname",lname);
                i.putExtra("email",email);
                i.putExtra("fbid",id);
                i.putExtra("birthdate",birthdate);
                i.putExtra("profilepic",profile_url);
                i.putExtra("gender",gender);
                startActivity(i);
            }
        });

    }


    }
