    package com.pawanjeswani.mm.screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.pawanjeswani.mm.R;

    public class edit_profile extends AppCompatActivity {

    private Button btneditpr;
    private ImageView ivEditBack,ivEditMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        ivEditBack = findViewById(R.id.iveditback);
        ivEditMenu = findViewById(R.id.iveditmenu);
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
        btneditpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),edit_dashboard.class);
                startActivity(i);
            }
        });
    }
}
