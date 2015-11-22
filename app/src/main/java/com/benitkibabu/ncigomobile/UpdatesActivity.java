package com.benitkibabu.ncigomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.UpdateItem;

public class UpdatesActivity extends AppCompatActivity {
    DbHelper  db;
    UpdateItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView titleTv = (TextView) findViewById(R.id.titleTv);
        TextView dateTv = (TextView) findViewById(R.id.dateTv);
        TextView bodyTv = (TextView) findViewById(R.id.bodyTv);

        db = new DbHelper(this);

        if(getIntent().hasExtra("id")){
            String id = getIntent().getStringExtra("id");
            item = db.getUpdate(id);
            if(item == null){
                goBack();
            }else{
                setTitle(item.getTitle());
                titleTv.setText(item.getTitle());
                dateTv.setText(item.getDate());
                bodyTv.setText(item.getBody());
            }
        }else{
            goBack();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
