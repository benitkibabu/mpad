package com.benitkibabu.ncigomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.benitkibabu.app.AppConfig;
import com.benitkibabu.app.AppController;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.helper.OnlineRC;
import com.benitkibabu.models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    DbHelper db;

    EditText firstNameET, lastNameET, studentNoET, emailET;
    Student student;
    ImageButton editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DbHelper(this);

        firstNameET = (EditText) findViewById(R.id.firstName);
        lastNameET = (EditText) findViewById(R.id.lastName);
        studentNoET = (EditText) findViewById(R.id.student_no);
        emailET = (EditText) findViewById(R.id.student_email);

        student = db.getUser();

        LoadInfo();

        editBtn = (ImageButton) findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstNameET.setEnabled(!firstNameET.isEnabled());
                lastNameET.setEnabled(!lastNameET.isEnabled());

                if(firstNameET.isEnabled()){
                    editBtn.setImageResource(android.R.drawable.ic_menu_save);
                }else{
                    editBtn.setImageResource(android.R.drawable.ic_menu_edit);
                }

                if(!firstNameET.getText().toString().equals(student.getFirstName()) ||
                        !lastNameET.getText().toString().equals(student.getLastName())){

                    updateInfo(v, student.getStudentID(),
                            firstNameET.getText().toString(),
                            lastNameET.getText().toString());
                }else{
                    Snackbar.make(v, "No Changes made", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void LoadInfo(){
        if(student != null){
            setTitle(student.getFirstName() + " "+ student.getLastName());
            firstNameET.setText(student.getFirstName());
            lastNameET.setText(student.getLastName());
            studentNoET.setText(student.getStudentID());
            emailET.setText(student.getStudentEmail());
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

    private void updateInfo(final View view, final String number, final String firstName, final String lastName){
        final String req = "updateProfile";
        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.LOGIN_API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Profile Activity", response);

                        try {
                            JSONObject object = new JSONObject(response);
                            boolean error = object.getBoolean("error");
                            if(!error){
                                JSONArray stu = object.getJSONArray("result");
                                for (int i = 0; i < stu.length(); i++) {
                                    JSONObject st = stu.getJSONObject(0);
                                    String num = st.getString("student_no");
                                    String email = st.getString("student_email");
                                    String pass = st.getString("password");
                                    String reg_id = st.getString("reg_id");
                                    String course = st.getString("course");
                                    String status = st.getString("status");
                                    String first_name = st.getString("first_name");
                                    String last_name = st.getString("last_name");

                                    student = new Student(num, email, pass, reg_id, course, status, first_name, last_name);
                                    db.updateUser(student);
                                    Snackbar.make(view, "Profile Updated!", Snackbar.LENGTH_LONG).show();
                                    break;
                                }
                                LoadInfo();

                            }else{
                                Snackbar.make(view, "Failed:" + object.getString("error_msg"), Snackbar.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                }

                if (error instanceof TimeoutError) {
                    Log.e("Volley", "TimeoutError");
                }else if(error instanceof NoConnectionError){
                    Log.e("Volley", "NoConnectionError");
                } else if (error instanceof AuthFailureError) {
                    Log.e("Volley", "AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("Volley", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("Volley", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.e("Volley", "ParseError");
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", req);
                params.put("student_no",number);
                params.put("first_name", firstName);
                params.put("last_name", lastName);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, req);
    }
}
