package com.messas.dataentryuser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        toolbar.setTitle("Main Page");
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Exit")
                .setCancelable(false)
                .setMessage("Are you want to exit")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        }).create();
        builder.show();



    }

    public void addd(View view) {
        startActivity(new Intent(getApplicationContext(),AddUser.class));
    }

    public void totallclicked(View view) {
        startActivity(new Intent(getApplicationContext(), TodayUser.class));
    }

    public void fulllist(View view) {
        startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
    }
}