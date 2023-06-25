package com.messas.dataentryuser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.content.ContentValues.TAG;

public class AddUser extends AppCompatActivity {
    String limu;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    KProgressHUD progressHUD;
    ImageView spinner1;
    String [] sss={"PC","Print","On the way","Arrive","Suspend","Bakend","Rework"};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        try {
            limu=getIntent().getStringExtra("limu");
        }catch (Exception e)
        {
            limu=getIntent().getStringExtra("limu");
        }
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        toolbar.setTitle("Add User");
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
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        MultiDex.install(AddUser.this);
        FirebaseApp.initializeApp(this);
        progressHUD=KProgressHUD.create(AddUser.this);
        //alledit
        ide=findViewById(R.id.ide);
        phonenumber=findViewById(R.id.phonenumber);
        name=findViewById(R.id.name);

        dob=findViewById(R.id.dob);
        statusss=findViewById(R.id.statusss);
        totaltaka=findViewById(R.id.totaltaka);

        paidtakaa=findViewById(R.id.paidtakaa);
        paidtakaa.addTextChangedListener(amountText);
        totaltaka.addTextChangedListener(tooo);
        duetakka=findViewById(R.id.duetakka);
        spinner1=findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,sss);
        //Getting the instance of AutoCompleteTextView

        statusss.setThreshold(1);//will start working from first character
        statusss.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        statusss.setTextColor(Color.RED);
        spinner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 21);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();

        ZoneId z = ZoneId.of( "Asia/Dhaka" );
        LocalDate today = LocalDate.now( z );
        Button cirLoginButton=findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ide.getText().toString())||TextUtils.isEmpty(phonenumber.getText().toString())||TextUtils.isEmpty(name.getText().toString())
                        ||TextUtils.isEmpty(dob.getText().toString())||TextUtils.isEmpty(statusss.getText().toString())||TextUtils.isEmpty(totaltaka.getText().toString())||
                        TextUtils.isEmpty(paidtakaa.getText().toString())||TextUtils.isEmpty(duetakka.getText().toString()))
                {
                    Toast.makeText(AddUser.this, "Enter all information", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (flag==2)
                    {
                        AlertDialog.Builder warning=new AlertDialog.Builder(AddUser.this);
                        warning.setTitle("Conformation")
                                .setMessage("Are you want to add this data?")
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                progress_check();
                                firebaseFirestore.collection("BlockList")
                                        .document(phonenumber.getText().toString().toLowerCase() + "@gmail.com")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        progressHUD.dismiss();
                                                        Toasty.error(getApplicationContext(), "You  are in blook list.", Toast.LENGTH_SHORT, true).show();
                                                    }
                                                    else {
                                                        firebaseFirestore.collection("Name_Exsiting")
                                                                .document(phonenumber.getText().toString().toLowerCase())
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            if (task.getResult().exists()) {
                                                                                progressHUD.dismiss();
                                                                                Toasty.error(getApplicationContext(), "This phone number already taken..", Toast.LENGTH_SHORT, true).show();
                                                                                return;
                                                                            }
                                                                            else {
                                                                                final EditText input = new EditText(AddUser.this);
                                                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                input.setLayoutParams(lp);
                                                                                input.setHint("Enter Refferal Name");
                                                                                input.setText("amia");
                                                                                firebaseFirestore.collection("Old_User")
                                                                                        .document(input.getText().toString().toLowerCase())
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {

                                                                                                        //Toast.makeText(RegistrationActivity.this, "make", Toast.LENGTH_SHORT).show();
                                                                                                        firebaseAuth.createUserWithEmailAndPassword(phonenumber.getText().toString().toLowerCase() + "@gmail.com", "123456")
                                                                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                        if (task.isSuccessful()) {
                                                                                                                            final Map<String, String> userMap19 = new HashMap<>();
                                                                                                                            userMap19.put("refername", input.getText().toString().toLowerCase());
                                                                                                                            userMap19.put("refername_email", input.getText().toString().toLowerCase() + "@gmail.com");
                                                                                                                            userMap19.put("user_id", firebaseAuth.getCurrentUser().getUid());
                                                                                                                            userMap19.put("user_name", phonenumber.getText().toString().toLowerCase());
                                                                                                                            firebaseFirestore.collection("ALL_GET")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .set(userMap19)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });


                                                                                                                            Name_Exsiting name_exsiting = new Name_Exsiting(phonenumber.getText().toString().toLowerCase(),
                                                                                                                                    phonenumber.getText().toString());

                                                                                                                            firebaseFirestore.collection("Name_Exsiting")
                                                                                                                                    .document(phonenumber.getText().toString().toLowerCase())
                                                                                                                                    .set(name_exsiting)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                // old_user(username.getText().toString().toLowerCase());
                                                                                                                                                // sendStatus(mAuth.getCurrentUser().getEmail());
                                                                                                                                                // cris(mAuth.getCurrentUser().getEmail());
                                                                                                                                                //    month_counting(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getUid());


                                                                                                                                                //email=edtemail.getText().toString();
                                                                                                                                                //  todays_free(input.getText().toString().toLowerCase() +"@gmail.com");
                                                                                                                                                String   password=phonenumber.getText().toString();
                                                                                                                                                String  mobile=phonenumber.getText().toString();
                                                                                                                                                Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                                String ts = tsLong.toString();

                                                                                                                                                String image22="https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
                                                                                                                                                storeToFirestore1(name.getText().toString(), "abc@gmail.com",image22, phonenumber.getText().toString());

                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                });
                                                                                                    }
                                                                                                    else {
                                                                                                        progressHUD.dismiss();
                                                                                                        Toasty.error(getApplicationContext(), "No User Found .", Toast.LENGTH_SHORT, true).show();
                                                                                                        return;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });


                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        });
                            }
                        }).create();
                        warning.show();
                    }
                    else{
                        Toast.makeText(AddUser.this, "Pick up image", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



    }
    FirebaseStorage storage;
    StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath,second,vechileimage,vechilelicesse;//Firebase
    int flag=1;
    Uri downloadUri;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                // Toast.makeText(this, ""+PICK_IMAGE_REQUEST, Toast.LENGTH_SHORT).show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), second);
                spinner1.setImageBitmap(bitmap);
                flag=2;
                if(filePath != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Image Uploading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    StorageReference ref = storageReference.child("ProfileImage/" + UUID.randomUUID().toString());
                    ref.putFile(filePath)  .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            final Uri downloadUri1=uriTask.getResult();



                            if (uriTask.isSuccessful()) {
                                progressDialog.dismiss();
                                downloadUri=downloadUri1;


                                Toast.makeText(AddUser.this, "Image Uploaded", Toast.LENGTH_SHORT).show();


                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "ব্যর্থ "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
                }

                // nid.setImageBitmap(bitmap1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void progress_check() {
        progressHUD = KProgressHUD.create(AddUser.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Uploading Data....")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }
    AutoCompleteTextView statusss;
    EditText ide,phonenumber,name,dob,totaltaka,paidtakaa,duetakka;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        return true;
    }

    private void storeToFirestore1(final String name11, final String toString, String image22, final String email) {
        Log.d(TAG+"_TXN","8. SAVING DATA TO FIRESTORE");
        // Storing data on Firestore...




        // transcationpinstore(firebaseAuth.getCurrentUser().getEmail(),edtpass.getText().toString().toLowerCase());
        ZoneId z = ZoneId.of( "Asia/Dhaka" );
        LocalDate today = LocalDate.now( z );
        long time = System.currentTimeMillis()/1000;

        UserModel userMap=new UserModel( ide.getText().toString(),phonenumber.getText().toString()
                ,name.getText().toString(),dob.getText().toString(),statusss.getText().toString(),totaltaka.getText().toString()
                ,paidtakaa.getText().toString(),duetakka.getText().toString(),""+downloadUri,""+today,""+time,limu);

        firebaseFirestore.collection("Daily")
                .document(""+today)
                .collection("List")
                .document("REfer")
                .collection(limu)
                .document(firebaseAuth.getCurrentUser().getEmail())
                .set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        firebaseFirestore.collection("Daily")
                .document(""+today)
                .collection("List")
                .document("REfer")
                .collection("abc@gmail.com")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        firebaseFirestore.collection("Total")
                .document(limu)
                .collection("List")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful())
                {

                    // Sending Verification Link via Email
                    Log.d(TAG+"_TXN","9. DATA SAVED");
                    Log.d(TAG+"_TXN","10. SENDING EMAIL");
                    firebaseFirestore.collection("User2").document(firebaseAuth.getCurrentUser().getEmail())
                            .set(userMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        User_coun user_coun=new User_coun( name11,toString,email,"0");
                                        firebaseFirestore.collection("Users")
                                                .document(firebaseAuth.getCurrentUser().getUid()).collection("Coins")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .set(user_coun)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            MainCoin mainCoin=new MainCoin("0");
                                                            firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid())
                                                                    .collection("MainCoin")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail().toLowerCase())
                                                                    .set(mainCoin)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Map<String, String> userMap1 = new HashMap<>();

                                                                                userMap1.put("username", phonenumber.getText().toString().toLowerCase());
                                                                                firebaseFirestore.collection("Old_User")
                                                                                        .document(phonenumber.getText().toString().toLowerCase())
                                                                                        .set(userMap1)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Map<String, String> userMap7 = new HashMap<>();

                                                                                                    userMap7.put("uuid",firebaseAuth.getCurrentUser().getUid());
                                                                                                    firebaseFirestore.collection("All_UserID")
                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                            .set(userMap7)
                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        Map<String, String> paSS = new HashMap<>();

                                                                                                                        paSS.put("uuid",phonenumber.getText().toString().toLowerCase());
                                                                                                                        firebaseFirestore.collection("Password")
                                                                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                .set(paSS)
                                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        if (task.isSuccessful()) {
                                                                                                                                            firebaseAuth.signOut();
                                                                                                                                            firebaseAuth.signInWithEmailAndPassword(limu.toString().toLowerCase(),"123456")
                                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                                                            if (task.isSuccessful())
                                                                                                                                                            {
                                                                                                                                                                Handler handler=new Handler();
                                                                                                                                                                handler.postDelayed(new Runnable() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void run() {

                                                                                                                                                                        Toasty.success(getApplicationContext(), "Data Added.", Toasty.LENGTH_SHORT, true).show();


                                                                                                                                                                        progressHUD.dismiss();
                                                                                                                                                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                                                                                                                                    }
                                                                                                                                                                },500);


                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    });


                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });

                                                                                                                    }
                                                                                                                }
                                                                                                            });

                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });





                } else {
                    Log.d(TAG+"_ERR5","ERROR IN SAVING DATA");
                    progressHUD.dismiss();
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(AddUser.this,"FIRESTORE Error : "+errorMessage,Toast.LENGTH_LONG).show();

                }

            }
        });

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
                    double totaltaka1= Double.parseDouble(totaltaka.getText().toString());
                    double bakidue= totaltaka1- Double.parseDouble(check);
                   // Toast.makeText(AddUser.this, totaltaka.getText().toString()+""+check, Toast.LENGTH_SHORT).show();
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