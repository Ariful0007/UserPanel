package com.messas.dataentryuser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AdapterSub11 extends RecyclerView.Adapter<AdapterSub11.myview> {
    public List<UserModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public AdapterSub11(List<UserModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText(data.get(position).getPhonenumber());
        holder.logout.setText(""+data.get(position).getUuid());
        if (data.get(position).getImage().equals("Image"))
        {

        }
else{
    try{
        Picasso.get().load(data.get(position).getImage()).into(holder.image);
    }catch (Exception e)
    {
        e.printStackTrace();
    }
        }
holder.card_view8.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String opiton[]={"Show Details","Delete"};
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext())
                .setTitle("Options")
                .setItems(opiton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0)
                        {
                           Intent intent=new Intent(v.getContext(),UserDetailsActivity.class);
                           intent.putExtra("email",""+data.get(position).getPhonenumber());
                           v.getContext().startActivity(intent);
                        }
                        else{
                            ZoneId z = ZoneId.of( "Asia/Dhaka" );
                            LocalDate today = LocalDate.now( z );
                            firebaseFirestore.collection("User2")
                                    .document(data.get(position).getPhonenumber().toString()+"@gmail.com")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                if (task.getResult().exists())
                                                {

                                                    firebaseFirestore.collection("User2")
                                                            .document(data.get(position).getPhonenumber().toString()+"@gmail.com")
                                                            .delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()){
                                                                        Toast.makeText(v.getContext(), "Done", Toast.LENGTH_SHORT).show();
                                                                        v.getContext().startActivity(new Intent(v.getContext(),HomeActivity.class));

                                                                    }
                                                                }
                                                            });

                                                }
                                                else{
                                                    Toast.makeText(v.getContext(), "No Data found.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });


                        }
                    }
                });
        builder.create();

        builder.show();
    }
});


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout,customer_area3,customer_area8;
        CardView card_view8;
        ImageView image;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            logout=itemView.findViewById(R.id.logout);
            card_view8=itemView.findViewById(R.id.card_view8);
            image=itemView.findViewById(R.id.image);
        }
    }
}


