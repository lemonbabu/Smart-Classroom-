package com.smartclassroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Converter;

public class Room extends AppCompatActivity {
    DatabaseReference dbReference, newRef;
    public static String accessPath, buildingTitle, levelTitle, roomTitle;
    String[] pathKey = new String[100];
    GridView lstViewData;
    List<String> pathList = new ArrayList<>();
    private static ArrayList<RoomItem> dataList = new ArrayList<>();
    int count = 0;
    ProgressBar prsBr;

    private RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        prsBr = findViewById(R.id.prsBr);

        lstViewData =  findViewById(R.id.lstViewData);
        roomAdapter = new RoomAdapter(Room.this, dataList);
        lstViewData.setAdapter(roomAdapter);
        dataList.clear();

        dbReference = FirebaseDatabase.getInstance().getReference().child("authentication/" + currentUser + "/access_path");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                assert value != null;
                String[] str = value.split(",");
                pathList = Arrays.asList(str);
                for(String s: pathList) {
                    final RoomItem data = new RoomItem();
                    String[] pathChild =  s.split("/");
                    String buildingPath = "all_building/" + pathChild[1] + "/title";

                    //building title query
                    newRef = FirebaseDatabase.getInstance().getReference().child(buildingPath);
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            buildingTitle = dataSnapshot1.getValue(String.class);
                            data.setBuildingTitle(buildingTitle);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });

                    String levelPath = "all_building/" + pathChild[1] + "/" + pathChild[2] + "/title";
                    newRef = FirebaseDatabase.getInstance().getReference().child(levelPath);
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            levelTitle = dataSnapshot1.getValue(String.class);
                            data.setLevelTitle(levelTitle);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });

                    if(value.length() > 40)
                        pathChild[3] = pathChild[3].substring(0, pathChild[3].length() - 1);
                    String roomPath = "all_building/" + pathChild[1] + "/" + pathChild[2] + "/"  + pathChild[3] + "/title";
                    pathKey[count++] = "all_building/" + pathChild[1] + "/" + pathChild[2] + "/"  + pathChild[3];
                    newRef = FirebaseDatabase.getInstance().getReference().child(roomPath);
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            roomTitle = dataSnapshot1.getValue(String.class);
                            data.setRoomTitle(roomTitle);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });

                    dataList.add(data);
                    try
                    {
                        Thread.sleep(700);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
                try
                {
                    Thread.sleep(700);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                lstViewData.setAdapter(roomAdapter);
                prsBr.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        lstViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ControlPanel.class);
                intent.putExtra("path",pathKey[position]);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

}
