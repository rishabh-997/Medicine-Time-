package com.gautam.medicinetime.Leaderboard;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gautam.medicinetime.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView username,useremail,userpoints;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name,email,points;

    RecyclerView.Adapter adapter;
    List<LeaderboardModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        username=findViewById(R.id.txt_view_leader_user_name);
        useremail=findViewById(R.id.txt_view_leader_user_email);
        userpoints=findViewById(R.id.txt_view_leader_user_points);
        recyclerView=findViewById(R.id.leaderboard_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);


        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        if(firebaseUser!=null)
        {
            email=firebaseUser.getEmail();
        }

        loaddata();


    }
    public  void loaddata()
    {  list=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String mail;
                    mail=ds.child("Email").getValue(String.class);
                    name=ds.child("Name").getValue(String.class);
                    points=ds.child("Points").getValue(String.class);
                    if(email.equals(mail))
                    {
                        useremail.setText(email);
                        username.setText(name);
                        userpoints.setText(points);
                    }
                    LeaderboardModel leaderboardModel=new LeaderboardModel(name,points);
                    list.add(leaderboardModel);

                }

                Collections.sort(list,LeaderboardModel.By_Point);

                        adapter=new LeaderboardAdapter(list,getApplicationContext());
              recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
