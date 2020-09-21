package com.smartclassroom;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends AppCompatActivity {
    DatabaseReference dbReference;
    TextView roomName, temp, pir, autoControl, name;
    GridView lstViewData;
    ArrayList<DevicesItems> dataList;
    DevicesAdapter devicesAdapter;
    int count = 0;
    String[] pathKey = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        String path = getIntent().getStringExtra("path");

        roomName = findViewById(R.id.txtRoomName);
        temp = findViewById(R.id.txtTem);
        pir = findViewById(R.id.txtPir);
        autoControl = findViewById(R.id.txtAutoControl);
        //Toast.makeText(ControlPanel.this, path, Toast.LENGTH_LONG).show();

        dbReference = FirebaseDatabase.getInstance().getReference().child(path + "/title");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                roomName.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child(path + "/status/temperature");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                assert value != null;
                temp.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child(path + "/status/pir");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.getValue(Boolean.class);
                assert value != null;
                pir.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child(path + "/status/auto_control");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.getValue(Boolean.class);
                assert value != null;
                autoControl.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        // ____________________________________________this for devices query__________________________________________________
        lstViewData = findViewById(R.id.listDevices);
        name = findViewById(R.id.txtDeviceName);

        dataList = new ArrayList<DevicesItems>();
        devicesAdapter = new DevicesAdapter(this, dataList);

        dbReference = FirebaseDatabase.getInstance().getReference().child(path);
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //DevicesItems data = dataSnapshot1.getValue(DevicesItems.class);
                    //pathKey[count++] = dataSnapshot1.getKey();
                    String powerPoint = dataSnapshot1.getKey();
                    assert powerPoint != null;
                    if(powerPoint.contains("power_point"))
                    {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            String socket = dataSnapshot2.getKey();
                            assert socket != null;
                            if(socket.contains("socket"))
                            {
                                DevicesItems dataItem = new DevicesItems();
                                for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                    String data = dataSnapshot3.getKey();
                                    assert data != null;
                                    if(data.contains("title"))
                                    {
                                        String title = dataSnapshot3.getValue(String.class);
                                        dataItem.setName(title);
                                   }
                                    else if(data.contains("state"))
                                    {
                                        boolean sws = dataSnapshot3.getValue(boolean.class);
                                        dataItem.setSws(sws);
                                        pathKey[count++] = dataSnapshot3.getRef().toString();
                                    }
                                }
                                dataList.add(dataItem);
                            }
                        }
                    }
                }
                lstViewData.setAdapter(devicesAdapter);

                lstViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Switch s = view.findViewById(R.id.swsDevicesItem);

                        String result = pathKey[position].substring(45, pathKey[position].length());
                        DatabaseReference dbR = FirebaseDatabase.getInstance().getReference().child(result);
                        if (s.isChecked()) {
                            dbR.setValue(false);
                            s.setChecked(false);
                        } else {
                            dbR.setValue(true);
                            s.setChecked(true);
                        }
                        Toast.makeText(ControlPanel.this, "Update Database", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}