package com.benitkibabu.ncigomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.benitkibabu.fragments.SettingsFragment;
import com.benitkibabu.fragments.UpdatesFragment;
import com.benitkibabu.helper.AppPreferenceManager;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.helper.OnlineRC;
import com.benitkibabu.models.Student;

import com.benitkibabu.fragments.SocialFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static DrawerLayout drawer;
    static NavigationView navigationView;

    TextView courseName, studentNumber;

    AppPreferenceManager pref;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = new AppPreferenceManager(this);
        db = new DbHelper(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.inflateHeaderView(R.layout.nav_header_home);

        studentNumber = (TextView) v.findViewById(R.id.nav_student_number);
        courseName = (TextView) v.findViewById(R.id.nav_course_name);

        Student u = db.getUser();
        if(u == null){
            logout();
        }else {
            studentNumber.setText(u.getStudentEmail());
            courseName.setText(u.getCourse());
        }

        showFragment();
    }

    private void showFragment(){
        Fragment fragment = null;
        if(getIntent().hasExtra("fragment")){
            String frag = getIntent().getStringExtra("fragment");
            if(frag != null) {
                if (frag.equalsIgnoreCase("Updates")) {
                    fragment = UpdatesFragment.newInstance("Updates");
                }
                else if (frag.equalsIgnoreCase("Social")) {
                    fragment = SocialFragment.newInstance("Social");
                }else if (frag.equalsIgnoreCase("Settings")) {
                    fragment = SettingsFragment.newInstance("Settings");
                }
            }
        }else {
            fragment = UpdatesFragment.newInstance("Updates");
            getIntent().putExtra("fragment","Updates");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!pref.isLoggedIn()){
            logout();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    public void setColor(int color) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(color));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_news) {
            fragment = UpdatesFragment.newInstance(item.getTitle().toString());
            this.getIntent().putExtra("fragment", item.getTitle().toString());
        }
        else if (id == R.id.nav_timetable) {
            Intent i = new Intent(this, TimetableActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_social) {
            fragment = SocialFragment.newInstance(item.getTitle().toString());
            this.getIntent().putExtra("fragment", item.getTitle().toString());
        }
        else if (id == R.id.nav_help) {
            Intent i = new Intent(this, ThreeSixtyPage.class);
            startActivity(i);
        }
        else if (id == R.id.nav_map) {
            Intent i = new Intent(this, NciMapActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_settings) {
            fragment = SettingsFragment.newInstance(item.getTitle().toString());
            this.getIntent().putExtra("fragment", item.getTitle().toString());
        }
        else if (id == R.id.nav_logout) {
            logout();
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void changeStatus(){
        boolean result = OnlineRC.updateStatus(db.getUser().getStudentID(), "OFFLINE");
        if(!result){
            Log.d("Social Fragment", "Update Failed.");
        }
    }

    void logout() {
        if(db.getUser() != null) {
            changeStatus();
            db.deleteUser();
        }
        pref.setLogin(false);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
