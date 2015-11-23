package com.benitkibabu.ncigomobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.benitkibabu.app.AppConfig;
import com.benitkibabu.app.AppController;
import com.benitkibabu.helper.AppPreferenceManager;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Course;
import com.benitkibabu.models.Student;
import com.benitkibabu.models.User;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText studentId;
    EditText password;

    Student student;
    Course studentCourse;
    List<Course> courseList = new ArrayList<>();

    AppPreferenceManager pref;
    DbHelper db;

    GoogleCloudMessaging gcm;
    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        pref = new AppPreferenceManager(this);
        //pref.setLogin(false);
        db = new DbHelper(this);

        if(pref.isLoggedIn()){
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }else{
            db.deleteUser();
        }

        getCourse();

        studentId = (EditText) findViewById(R.id.student_id_field);
       //password = (EditText) findViewById(R.id.password_field);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.loginBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = studentId.getText().toString();
                //String pass = password.getText().toString();
                if (!id.isEmpty() /* && !pass.isEmpty()*/) {
                    getStudent(id, v);
                } else {
                    Snackbar.make(v, "Please enter correct details", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    void getStudent(final String number, final View view){
        String req = "getStudent";

        Uri url =  Uri.parse(AppConfig.API_URL)
                .buildUpon()
                .appendQueryParameter("tag", req)
                .appendQueryParameter("number", number)
                .build();

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Login Activity", response);

                    try {
                        JSONObject object = new JSONObject(response);
                        boolean error = object.getBoolean("error");
                        if(!error){
                            JSONArray jr = object.getJSONArray("students");
                            JSONObject st = jr.getJSONObject(0);
                            String num = st.getString("student_number");
                            String email = st.getString("student_email");

                            student = new Student(num, email);

                            getRegId();

                            ModuleListDialog();
                        }else{
                            Snackbar.make(view, "Please enter correct details", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(request, req);

    }

    public void goToMain(){
        User user = new User(student.getStudentID(), student.getStudentEmail(),
                studentCourse.getName(), studentCourse.getCode());
        long id = db.addUser(user);
        if(id != -1) {
            pref.setLogin(true);
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }else{
            Snackbar.make(studentId, "Please try again!", Snackbar.LENGTH_LONG).show();
        }
    }

    void getCourse(){
        String req = "getAllCourse";

        Uri url =  Uri.parse(AppConfig.API_URL)
                .buildUpon()
                .appendQueryParameter("tag", req)
                .build();

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Login Activity", response);
                        if(courseList != null){
                            courseList.clear();
                        }
                        try {
                            JSONObject object = new JSONObject(response);
                            boolean error = object.getBoolean("error");
                            if(!error){
                                JSONArray st = object.getJSONArray("courses");
                                for(int i = 0; i < st.length(); i++){
                                    JSONObject c = st.getJSONObject(i);
                                    JSONObject cc = c.getJSONObject("course");
                                    Course course = new Course(
                                            cc.getString("code"),
                                            cc.getString("name"));
                                    courseList.add(course);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(request, req);

    }

    public void ModuleListDialog() {
        if(courseList != null) {
            final String[] listOfCourse = new String[courseList.size()];
            for (int i = 0; i < courseList.size(); i++) {
                listOfCourse[i] = courseList.get(i).getName();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your Course")
                    .setItems(listOfCourse, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = listOfCourse[which];
                    for(Course c: courseList){
                        if(name.equalsIgnoreCase(c.getName())){
                            studentCourse = c;
                            break;
                        }
                    }
                    if(studentCourse != null) {
                        goToMain();
                    }

                }
            });
            builder.show();
        }
    }



    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(AppConfig.SENDER_ID);
                    pref.setString("regId", regid);
                    msg = "ID=" + regid;
                    Log.i("GCM",  msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Snackbar.make(studentId, msg + "\n", Snackbar.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }
}
