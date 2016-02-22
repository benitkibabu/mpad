package com.benitkibabu.ncigomobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.benitkibabu.adapters.CustomPagerAdapter;
import com.benitkibabu.app.Utils;
import com.benitkibabu.fragments.NciThreeSixtyFragment;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Timetable;
import com.benitkibabu.sections_360.AppointmentFragment;
import com.benitkibabu.sections_360.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

public class ThreeSixtyPage extends AppCompatActivity {

    public static DbHelper db;
    private CustomPagerAdapter mSectionsPagerAdapter;

    private List<Fragment> fragmentList;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //change theme of the application
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_360page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DbHelper(this);

        fragmentList = new ArrayList<>();
        addFragments();
        mSectionsPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragmentList,
                getResources().getStringArray(R.array.nci360_array));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_service);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
    }

    void addFragments(){
        Fragment fragment;
        fragment = NciThreeSixtyFragment.newInstance("Home");
        fragmentList.add(fragment);
        fragment = ServiceFragment.newInstance("Tickets");
        fragmentList.add(fragment);
        fragment = AppointmentFragment.newInstance("Appointment");
        fragmentList.add(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
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

    void showEditDialog(){
        DialogFragment newFragment = ShowInsertDialog.newInstance("new_service");
        newFragment.show(getSupportFragmentManager(), "new_service");
    }

    public static class ShowInsertDialog extends AppCompatDialogFragment {
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

            LayoutInflater inflater = getActivity().getLayoutInflater();

            final View view  = inflater.inflate(R.layout.new_service_layout, null);

            final Spinner ns_service = (Spinner) view.findViewById(R.id.ns_service);
            final Spinner ns_topic = (Spinner) view.findViewById(R.id.ns_topic);
            final TextView ns_subject = (TextView) view.findViewById(R.id.ns_subject);
            final TextView ns_body = (TextView) view.findViewById(R.id.ns_bodyText);

            builder.setIcon(R.drawable.ic_information_black_48dp);
            builder.setTitle("New Service");
            builder.setCancelable(false);
            builder.setView(view).setPositiveButton(R.string.add_string,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Snackbar.make(view, "Added", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
}
