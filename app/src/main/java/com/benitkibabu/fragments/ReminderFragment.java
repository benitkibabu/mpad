package com.benitkibabu.fragments;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.benitkibabu.adapters.CustomReminderAdapter;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.ReminderItem;
import com.benitkibabu.ncigomobile.R;
import com.benitkibabu.ncigomobile.SetReminderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 28/09/2015.
 */
public class ReminderFragment extends Fragment {

    public static final String ITEM_NAME = "itemName";

    ListView listView;
    List<ReminderItem> reminderItemList;
    CustomReminderAdapter adapter;

    DbHelper db;

    float hx = Float.NaN, hy = Float.NaN;
    static final int DELTA = 50;

    public static ReminderFragment newInstance( String name){
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public ReminderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new DbHelper(getActivity().getBaseContext());
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.set_reminder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SetReminderActivity.class);
                getActivity().startActivity(i);
            }
        });

        reminderItemList = new ArrayList<>();

        reminderItemList = db.getReminders();

        adapter = new CustomReminderAdapter(getActivity(), R.layout.reminder_item,
                reminderItemList);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ReminderItem item = reminderItemList.get(position);

            }
        });

        return view;
    }

//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        if(HomeActivity.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
//            inflater.inflate(R.menu.menu_reminder, menu);
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_remove_reminder:
//                //ReminderNotification.notify(getActivity(), "Testing Notification", "Some work to be done here", 360);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
