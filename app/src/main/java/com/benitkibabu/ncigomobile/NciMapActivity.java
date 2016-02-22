package com.benitkibabu.ncigomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.benitkibabu.app.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NciMapActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //change theme of the application
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_nci_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showMap();
    }

    public void showMap(){
        Fragment f = getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(f != null){
            SupportMapFragment sp = (SupportMapFragment)f;
            sp.getMapAsync(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng nci = new LatLng(53.3487801,-6.2426813);
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(nci).title("National College of Ireland"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nci, 18));

    }
}
