package com.benitkibabu.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.benitkibabu.models.Timetable;
import com.benitkibabu.ncigomobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 19/11/2015.
 */
public class TimetableAdapter extends ArrayAdapter<Timetable> implements ListAdapter {
    Context context;
    int layoutId;
    List<Timetable> items;
    public ItemHolder itemHolder;

    public TimetableAdapter(Context context, int layoutId, List<Timetable> items){
        super(context, layoutId);
        this.context = context;
        this.layoutId = layoutId;
        this.items = items;
    }

    public void clear() {
        if(items != null && !items.isEmpty())
            items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Timetable> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view  = inflater.inflate(layoutId, null);
            itemHolder = new ItemHolder();
            itemHolder.startTime = (TextView) view.findViewById(R.id.ti_startT);
            itemHolder.endTime = (TextView) view.findViewById(R.id.ti_endT);
            itemHolder.roomName = (TextView) view.findViewById(R.id.ti_roomName);
            itemHolder.lecturerName = (TextView) view.findViewById(R.id.ti_lecturerTv);
            itemHolder.moduleName = (TextView) view.findViewById(R.id.ti_nameTv);
            itemHolder.clickable = (LinearLayout) view.findViewById(R.id.mainHolder);
            view.setTag(itemHolder);
        }else{
            itemHolder = (ItemHolder) view.getTag();
        }

        final Timetable item = items.get(position);
        itemHolder.startTime.setText(item.getStart());
        itemHolder.endTime.setText(item.getFinish()) ;
        itemHolder.roomName.setText(item.getRoom()) ;
        itemHolder.lecturerName.setText(item.getLecturer());
        itemHolder.moduleName.setText(item.getModule());

        return view;
    }

    public static class ItemHolder{
        TextView startTime, endTime, roomName, lecturerName,moduleName;
        LinearLayout clickable;
    }

    @Override
    public int getCount() {

        return items.size();
    }
}