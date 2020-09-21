package com.smartclassroom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class DevicesAdapter extends ArrayAdapter<DevicesItems> {
    Activity context;
    List<DevicesItems> dataList;

    public DevicesAdapter(Activity context, List<DevicesItems> dataList) {
        super(context, R.layout.devices_layout, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    public void setDataList(List<DevicesItems> dataList) {
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.devices_layout, null, true);

        DevicesItems data = dataList.get(position);
        TextView txt = view.findViewById(R.id.txtDeviceName);
        Switch dvs =  view.findViewById(R.id.swsDevicesItem);

        if(data.getName() != null) {
            txt.setText( data.getName());
            if (data.isSws()) {
                dvs.setChecked(true);
            }
            else {
                dvs.setChecked(false);
            }
        }
        return view;
    }
}
