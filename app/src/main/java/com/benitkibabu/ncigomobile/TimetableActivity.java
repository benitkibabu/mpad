package com.benitkibabu.ncigomobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.benitkibabu.adapters.TimetableAdapter;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Timetable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimetableActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public static DbHelper db;
    static List<Timetable> timetables = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        db = new DbHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadData();

    }

    public static void LoadData(){
        if(db != null){
            timetables.clear();
            for(Timetable tt : db.getTimetable()){
                if(tt != null){
                    timetables.add(tt);
                }
            }
            if(timetables.isEmpty()){
                Timetable t = new Timetable("Empty N/A","N/A","N/A","N/A","N/A","N/A");
                timetables.add(t);
            }
        }else{
            Timetable t = new Timetable("Null N/A","N/A","N/A","N/A","N/A","N/A");
            timetables.add(t);
        }
    }

    void showEditDialog(){
        DialogFragment newFragment = ShowInsertDialog.newInstance("insert");
        newFragment.show(getSupportFragmentManager(), "insert");
    }

    public static class ShowInsertDialog extends AppCompatDialogFragment{
        private static final String ITEM_NAME = "name";

        public static ShowInsertDialog newInstance(String name){
            ShowInsertDialog fragment = new ShowInsertDialog();
            Bundle args = new Bundle();
            args.putString(ITEM_NAME, name);
            fragment.setArguments(args);

            return fragment;
        }

        public ShowInsertDialog(){}

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            final View view  = inflater.inflate(R.layout.edit_timetable_layout, null);
            final Spinner startTimes = (Spinner) view.findViewById(R.id.et_start_time);
            final Spinner endTimes = (Spinner) view.findViewById(R.id.et_end_time);
            final Spinner roomNames = (Spinner) view.findViewById(R.id.et_room_name);
            final Spinner day = (Spinner) view.findViewById(R.id.et_day);

            final TextView moduleName = (TextView) view.findViewById(R.id.et_module_name);
            final TextView lecturerName = (TextView) view.findViewById(R.id.et_lecturer_name);

            builder.setTitle("Add New");
            builder.setCancelable(false);
            builder.setView(view).setPositiveButton(R.string.add_string,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Timetable t = new Timetable(day.getSelectedItem().toString(),
                                    moduleName.getText().toString(),
                                    lecturerName.getText().toString(),
                                    startTimes.getSelectedItem().toString(),
                                    endTimes.getSelectedItem().toString(),
                                    roomNames.getSelectedItem().toString());

                            long item = db.setTimetable(t);
                            if(item != 0 ){
                                Snackbar.make(view, "Added", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else{
                                Snackbar.make(view, "Not added", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            LoadData();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ShowInsertDialog.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            goBack();
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void goBack(){
        Intent upIntent = new Intent(this, HomeActivity.class);
        upIntent.putExtra("fragment", "Updates");
        if(NavUtils.shouldUpRecreateTask(this, upIntent)){
            TaskStackBuilder.from(this)
                    .addNextIntent(upIntent)
                    .startActivities();
            finish();
        }else {
            NavUtils.navigateUpTo(this, upIntent);
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MONDAY";
                case 1:
                    return "TUESDAY";
                case 2:
                    return "WEDNESDAY";
                case 3:
                    return "THURSDAY";
                case 4:
                    return "FRIDAY";
                case 5:
                    return "SATURDAY";
                case 6:
                    return "SUNDAY";
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        String[] dayList;
        TimetableAdapter adapter;
        RecyclerView recyclerView;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_timetable, container, false);
            dayList = getResources().getStringArray(R.array.day_of_week_array);

            int position = getArguments().getInt(ARG_SECTION_NUMBER);
            List<Timetable> tempList = new ArrayList<>();
            for(Timetable t : timetables){
                if(t.getDay().equalsIgnoreCase(dayList[position])){
                    tempList.add(t);
                }
            }
            if(!tempList.isEmpty()){
                Collections.sort(tempList, new Comparator<Timetable>() {
                    @Override
                    public int compare(Timetable t1, Timetable t2) {
                        String start1 = t1.getStart();
                        String start2 = t2.getStart();
                        return start1.compareTo(start2);
                    }
                });
            }
            adapter = new TimetableAdapter(getActivity(), R.layout.timetable_item_layout);
            adapter.clear();
            adapter.addAll(tempList);

            recyclerView = (RecyclerView) view.findViewById(R.id.t_RecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            return view;
        }
    }
}
