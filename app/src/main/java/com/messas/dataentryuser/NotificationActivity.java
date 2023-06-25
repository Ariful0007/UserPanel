package com.messas.dataentryuser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NotificationActivity extends AppCompatActivity {

    String names,database;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    AdapterSub11 getDataAdapter1;
    List<UserModel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    ZoneId z = ZoneId.of( "Asia/Dhaka" );
    LocalDate today = LocalDate.now( z );
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);

        setSupportActionBar(toolbar);
firebaseAuth=FirebaseAuth.getInstance();
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_myarrow);
        try {


            toolbar.setTitle("All List");
        }catch (Exception e) {


            toolbar.setTitle("All List");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_myarrow);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);

        ///for date
        firebaseFirestore= FirebaseFirestore.getInstance();

        ////

        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new AdapterSub11(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference =         firebaseFirestore.collection("Total")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .document();
        recyclerView = findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


        name=findViewById(R.id.name);

        ////
        name=findViewById(R.id.name);
        name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //fullsearch(query);

                //phoneSerach(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchAllUser(newText);

                //phoneSerach1(newText);
                return false;
            }
        });
    }
    String message="";
    double total=0;
    SearchView name;
    FirebaseFirestore firebaseFirestore;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));


    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        return true;
    }
    private void searchAllUser(String newText) {
        final KProgressHUD progressDialog=  KProgressHUD.create(NotificationActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Searching  Data.....")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        firebaseFirestore.collection("Total")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        getList.clear();
                        int count=0;
                        progressDialog.dismiss();
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            count++;
                            String dta = documentSnapshot.getString("name");
                            String dta1 = documentSnapshot.getString("statusss");
                            String dta2 = documentSnapshot.getString("duetakka");
                            String dta3 = documentSnapshot.getString("duetakka");
                            if (dta.toLowerCase().toString().contains(newText.toLowerCase().toString())||
                                    dta1.toLowerCase().toString().contains(newText.toLowerCase().toString())
                                    || dta2.toLowerCase().toString().contains(newText.toLowerCase().toString())
                                    || dta3.toLowerCase().toString().contains(newText.toLowerCase().toString())) {
                                // Toast.makeText(AllcategoruySearch.this, ""+dta, Toast.LENGTH_SHORT).show();
                                UserModel add_customer=new UserModel(
                                        documentSnapshot.getString("ide"),
                                        documentSnapshot.getString("phonenumber"),
                                        documentSnapshot.getString("name"),
                                        documentSnapshot.getString("dob"),
                                        documentSnapshot.getString("statusss"),
                                        documentSnapshot.getString("totaltaka"),
                                        documentSnapshot.getString("paidtakaa"),
                                        documentSnapshot.getString("duetakka"),
                                        documentSnapshot.getString("image"),
                                        documentSnapshot.getString("uuid"),
                                        documentSnapshot.getString("time"),
                                        documentSnapshot.getString("refer")



                                );
                                getList.add(add_customer);

                            }
                            getDataAdapter1 = new AdapterSub11(getList);
                            recyclerView.setAdapter(getDataAdapter1);


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(NotificationActivity.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void reciveData() {

        firebaseFirestore.collection("Total")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                                UserModel get = ds.getDocument().toObject(UserModel.class);
                                getList.add(get);
                                getDataAdapter1.notifyDataSetChanged();
                            }

                        }
                    }
                });

    }

}