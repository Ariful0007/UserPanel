package com.messas.dataentryuser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;

public class UserDetailsActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    KProgressHUD progressHUD;
    ImageView spinner1;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        try {
            email=getIntent().getStringExtra("email");
        }catch (Exception e)
        {
            email=getIntent().getStringExtra("email");
        }
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        toolbar.setTitle("User Details");
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_myarrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_myarrow);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);

        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        MultiDex.install(UserDetailsActivity.this);
        FirebaseApp.initializeApp(this);
        progressHUD= KProgressHUD.create(UserDetailsActivity.this);
        //alledit
        ide=findViewById(R.id.ide);
        ide.setEnabled(true);
        phonenumber=findViewById(R.id.phonenumber);
        phonenumber.setEnabled(false);
        name=findViewById(R.id.name);
        name.setEnabled(true);

        dob=findViewById(R.id.dob);
        dob.setEnabled(true);
        statusss=findViewById(R.id.statusss);
        statusss.setEnabled(true);
        totaltaka=findViewById(R.id.totaltaka);
        totaltaka.setEnabled(true);

        paidtakaa=findViewById(R.id.paidtakaa);
        paidtakaa.setEnabled(true);
        duetakka=findViewById(R.id.duetakka);
        duetakka.setEnabled(true);
        spinner1=findViewById(R.id.spinner1);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        paidtakaa.addTextChangedListener(amountText);
        totaltaka.addTextChangedListener(tooo);
        firebaseFirestore.collection("User2")
                .document(email+"@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().exists())
                            {
                                try {
                                    ide.setText(task.getResult().getString("ide"));
                                    phonenumber.setText(task.getResult().getString("phonenumber"));
                                    name.setText(task.getResult().getString("name"));
                                    dob.setText(task.getResult().getString("dob"));
                                    statusss.setText(task.getResult().getString("statusss"));
                                    totaltaka.setText(task.getResult().getString("totaltaka"));
                                    paidtakaa.setText(task.getResult().getString("paidtakaa"));
                                    duetakka.setText(task.getResult().getString("duetakka"));
                                    Picasso.get().load(task.getResult().getString("image")).into(spinner1);
                                    uuiddd= task.getResult().getString("image");
                                }catch (Exception e)
                                {
                                    ide.setText(task.getResult().getString("ide"));
                                    phonenumber.setText(task.getResult().getString("phonenumber"));
                                    name.setText(task.getResult().getString("name"));
                                    dob.setText(task.getResult().getString("dob"));
                                    statusss.setText(task.getResult().getString("statusss"));
                                    totaltaka.setText(task.getResult().getString("totaltaka"));
                                    paidtakaa.setText(task.getResult().getString("paidtakaa"));
                                    duetakka.setText(task.getResult().getString("duetakka"));
                                }
                            }
                        }
                    }
                });
        Button cirLoginButton=findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ide.getText().toString())||TextUtils.isEmpty(phonenumber.getText().toString())||TextUtils.isEmpty(name.getText().toString())
                        ||TextUtils.isEmpty(dob.getText().toString())||TextUtils.isEmpty(statusss.getText().toString())||TextUtils.isEmpty(totaltaka.getText().toString())||
                        TextUtils.isEmpty(paidtakaa.getText().toString())||TextUtils.isEmpty(duetakka.getText().toString()))
                {
                    Toast.makeText(UserDetailsActivity.this, "Some information missing", Toast.LENGTH_SHORT).show();
                }
                else{
                    long time = System.currentTimeMillis()/1000;
                    ZoneId z = ZoneId.of( "Asia/Dhaka" );
                    LocalDate today = LocalDate.now( z );
                    UserModel userMap=new UserModel( ide.getText().toString(),phonenumber.getText().toString()
                            ,name.getText().toString(),dob.getText().toString(),statusss.getText().toString(),totaltaka.getText().toString()
                            ,paidtakaa.getText().toString(),duetakka.getText().toString(),""+uuiddd,""+today,""+time,"abc@gmail.com");


                    firebaseFirestore.collection("User2").document(phonenumber.getText().toString().toString()+"@gmail.com")
                            .set(userMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(UserDetailsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),TodayUser.class));
                                    }
                                }
                            });
                }
            }
        });


    }
    String uuiddd;

    EditText ide,phonenumber,name,dob,statusss,totaltaka,paidtakaa,duetakka;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        return true;
    }
    String check;
    TextWatcher amountText=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            check=s.toString();
            if (TextUtils.isEmpty(check)){

            }
            else{
                if(TextUtils.isEmpty(totaltaka.getText().toString()))
                {

                }
                else{
                    double totaltaka1= Double.parseDouble(String.valueOf(totaltaka.getText().toString()));
                    double bakidue= totaltaka1- Double.parseDouble(check);
                    duetakka.setText(""+bakidue);
                }
            }

        }
    };
    TextWatcher tooo=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            check=s.toString();
            if (TextUtils.isEmpty(check)){

            }
            else{
                if(TextUtils.isEmpty(paidtakaa.getText().toString()))
                {

                }
                else{
                    double totaltaka1= Double.parseDouble(String.valueOf(paidtakaa.getText().toString()));
                    double bakidue=Double.parseDouble(check)- totaltaka1;
                    duetakka.setText(""+bakidue);
                }
            }

        }
    };

}