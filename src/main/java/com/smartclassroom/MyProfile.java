package com.smartclassroom;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyProfile extends AppCompatActivity {

    DatabaseReference dbReference;
    TextView name, dep, emial, phn, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        name = findViewById(R.id.profileName);
        dep = findViewById(R.id.profileDep);
        emial = findViewById(R.id.profileEmail);
        phn = findViewById(R.id.profilePhone);
        type = findViewById(R.id.profileType);

        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        dbReference = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser + "/name");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                assert value != null;
                name.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser + "/department");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                assert value != null;
                dep.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser + "/email");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                assert value != null;
                emial.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser + "/phone");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                assert value != null;
                phn.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child("users/" + currentUser + "/type");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                assert value != null;
                type.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}