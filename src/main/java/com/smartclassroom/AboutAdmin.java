package com.smartclassroom;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class AboutAdmin extends AppCompatActivity {

    DatabaseReference dbReference;
    TextView name, emial, phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_admin);

        name = findViewById(R.id.adminName);
        emial = findViewById(R.id.adminEmail);
        phn = findViewById(R.id.adminPhn);

        dbReference = FirebaseDatabase.getInstance().getReference().child("admin_info/name");
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

        dbReference = FirebaseDatabase.getInstance().getReference().child("admin_info/email");
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

        dbReference = FirebaseDatabase.getInstance().getReference().child("admin_info/phone");
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

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}