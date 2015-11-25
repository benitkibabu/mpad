package com.benitkibabu.ncigomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.UpdateItem;

public class DetailActivity extends AppCompatActivity {

    DbHelper db;
    UpdateItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView dateTv = (TextView) findViewById(R.id.d_dateTv);
        TextView bodyTv = (TextView) findViewById(R.id.d_bodyTv);

        db = new DbHelper(this);

        if(getIntent().hasExtra("id")){
            String id = getIntent().getStringExtra("id");
            item = db.getUpdate(id);
            if(item == null){
                goBack();
            }else{
                setTitle(item.getTitle());
                dateTv.setText(item.getDate());
                bodyTv.setText(item.getBody());
            }
        }else{
            goBack();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
