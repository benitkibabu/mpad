package com.benitkibabu.ncigomobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.benitkibabu.app.AppConfig;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.ReminderItem;

import java.util.Calendar;

public class SetReminderActivity extends AppCompatActivity {
    DbHelper db;
    EditText name, short_desc;
    static Button dateBtn, timeBtn;
    static String dateTime, d_text = "", t_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DbHelper(this);

        name = (EditText) findViewById(R.id.reminder_name);
        short_desc = (EditText) findViewById(R.id.short_description);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        timeBtn = (Button) findViewById(R.id.timeBtn);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.setButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().trim().isEmpty() || short_desc.getText().toString().trim().isEmpty()
                        || d_text.trim().isEmpty() || t_text.trim().isEmpty()) {

                    Snackbar.make(view, "Provide all information!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else {
                    dateTime = d_text + " " + t_text + ":00";
                    ReminderItem item = new ReminderItem(AppConfig.generateID(),
                            name.getText().toString(), short_desc.getText().toString(),
                            dateTime);

                    db.setReminder(item);

                    Intent upIntent = new Intent(SetReminderActivity.this, HomeActivity.class);
                    upIntent.putExtra("fragment", "Reminder");

                    if(NavUtils.shouldUpRecreateTask(SetReminderActivity.this, upIntent)){
                        TaskStackBuilder.from(SetReminderActivity.this)
                                .addNextIntent(upIntent)
                                .startActivities();
                        finish();
                    }else {
                        NavUtils.navigateUpTo(SetReminderActivity.this, upIntent);
                    }

                    db.closeDB();
                }
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private static void setDate(String t){
        d_text = t;
    }
    private static void setTime(String t){
        t_text = t;
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            
            setTime(String.format("%02d:%02d", hourOfDay, minute));
            timeBtn.setText(t_text);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            setDate(day + "-" + (month+1) + "-" + year);
            dateBtn.setText(d_text);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, HomeActivity.class);
                upIntent.putExtra("fragment", "Reminder");
                if(NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.from(this)
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                }else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
