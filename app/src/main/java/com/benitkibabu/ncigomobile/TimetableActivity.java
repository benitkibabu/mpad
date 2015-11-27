package com.benitkibabu.ncigomobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.benitkibabu.adapters.SectionsPagerAdapter;
import com.benitkibabu.fragments.ItemFragment;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Timetable;

public class TimetableActivity extends AppCompatActivity  implements ItemFragment.OnFragmentInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public static DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DbHelper(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void showEditDialog(){
        DialogFragment newFragment = ShowInsertDialog.newInstance("insert_timetable");
        newFragment.show(getSupportFragmentManager(), "insert_timetable");
    }

    @Override
    public void onFragmentInteraction(int id) {
        Toast.makeText(this, "Item id: " + id + " clicked", Toast.LENGTH_LONG).show();
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

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();

            final View view  = inflater.inflate(R.layout.edit_timetable_layout, null);
            final Spinner startTimes = (Spinner) view.findViewById(R.id.et_start_time);
            final Spinner endTimes = (Spinner) view.findViewById(R.id.et_end_time);
            final Spinner roomNames = (Spinner) view.findViewById(R.id.et_room_name);
            final Spinner day = (Spinner) view.findViewById(R.id.et_day);

            final TextView moduleName = (TextView) view.findViewById(R.id.et_module_name);
            final TextView lecturerName = (TextView) view.findViewById(R.id.et_lecturer_name);

            builder.setIcon(R.drawable.ic_timetable_black_48dp);
            builder.setTitle("New");
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

}
