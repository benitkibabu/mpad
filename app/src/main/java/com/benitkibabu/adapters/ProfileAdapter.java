package com.benitkibabu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.benitkibabu.models.ProfileItem;
import com.benitkibabu.ncigomobile.R;

import java.util.List;

/**
 * Created by Ben on 29/09/2015.
 */
public class ProfileAdapter extends ArrayAdapter<ProfileItem> {
    Context context;
    int layoutId;
    List<ProfileItem> itemList;
    public ProfileAdapter(Context context, int layoutId, List<ProfileItem> itemList){
        super(context, layoutId, itemList);
        this.context = context;
        this.layoutId = layoutId;
        this.itemList = itemList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        ProfileHolder profileHolder;
        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(layoutId, parent, false);
            profileHolder = new ProfileHolder();
//            profileHolder.studentName = (TextView) view.findViewById(R.id.studentName);
//            profileHolder.courseName = (TextView) view.findViewById(R.id.courseName);

            view.setTag(profileHolder);
        }else{
            profileHolder = (ProfileHolder) view.getTag();
        }
        ProfileItem item = this.itemList.get(position);
//        profileHolder.studentName.setText(item.getStudentName());
//        profileHolder.courseName.setText(item.getCourseName());

        return view;
    }

    private static class ProfileHolder{
        TextView courseName, studentName;
    }
}
