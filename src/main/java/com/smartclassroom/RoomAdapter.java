package com.smartclassroom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends ArrayAdapter<RoomItem>{

    Activity context;
    List<RoomItem> dataList = new ArrayList<RoomItem>();

    public RoomAdapter(Activity context, List<RoomItem> dataList) {
        super(context, R.layout.rooms_layout, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.rooms_layout, null, true);

        RoomItem data = dataList.get(position);
        TextView txtRoomName = view.findViewById(R.id.txtRoomName);
        TextView txtLevelName = view.findViewById(R.id.txtLevelName);
        TextView txtBuildingName = view.findViewById(R.id.txtBuildingName);

        if(data.getRoomTitle() != null) {
            txtRoomName.setText(data.getRoomTitle().toString());
            txtLevelName.setText(data.getLevelTitle().toString());
            txtBuildingName.setText(data.getBuildingTitle().toString());
        }
        return view;
    }
}
