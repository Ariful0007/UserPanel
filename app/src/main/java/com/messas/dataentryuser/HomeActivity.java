package com.messas.dataentryuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("User2_employee")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().exists())
                            {
                                try {
                                    toolbar.setTitle("Main Page ("+task.getResult().getString("name")+")");
                                }catch (Exception e)
                                {
                                    toolbar.setTitle("Main Page ("+task.getResult().getString("name")+")");
                                }
                            }
                        }
                    }
                });
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
        Intent intent=new Intent(getApplicationContext(), AddUser.class);
        intent.putExtra("limu",""+firebaseAuth.getCurrentUser().getEmail().toString());
        startActivity(intent);
    }

    public void totallclicked(View view) {
        startActivity(new Intent(getApplicationContext(), TodayUser.class));
    }

    public void fulllist(View view) {
        startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
    }
}