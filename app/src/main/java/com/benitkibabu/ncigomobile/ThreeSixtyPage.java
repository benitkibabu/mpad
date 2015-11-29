package com.benitkibabu.ncigomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.benitkibabu.adapters.CustomPagerAdapter;
import com.benitkibabu.fragments.NciThreeSixtyFragment;
import com.benitkibabu.sections_360.AppointmentFragment;
import com.benitkibabu.sections_360.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

public class ThreeSixtyPage extends AppCompatActivity {


    private CustomPagerAdapter mSectionsPagerAdapter;

    private List<Fragment> fragmentList;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_360page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentList = new ArrayList<>();
        addFragments();
        mSectionsPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragmentList,
                getResources().getStringArray(R.array.nci360_array));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

}
