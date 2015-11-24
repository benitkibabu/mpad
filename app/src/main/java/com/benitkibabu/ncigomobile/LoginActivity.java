package com.benitkibabu.ncigomobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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
    EditText studentId, password;
    Spinner courseSpinner;

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

        regid = pref.getStringValue("regId");

        if(regid.equalsIgnoreCase("null")){
            getRegId();
        }

        db = new DbHelper(this);

        if(pref.isLoggedIn()){
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }else{
            db.deleteUser();
        }

        //getCourse();

        studentId = (EditText) findViewById(R.id.student_id_field);
        password = (EditText) findViewById(R.id.password_field);
        courseSpinner = (Spinner) findViewById(R.id.log_course_spinner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.loginBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = studentId.getText().toString();
                String pass = password.getText().toString();
                String courseName = courseSpinner.getSelectedItem().toString();
                if (!id.isEmpty() && !pass.isEmpty()) {
                    postStudent(id, pass, courseName, v);
                } else {
                    Snackbar.make(v, "Please enter correct details", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    //Post Request
    void postStudent(final String number, final String pass, final String courseName, final View view){
        final String req = "student";
        final String email = number + "@student.ncirl.ie";
        StringRequest request = new StringRequest(Request.Method.POST,AppConfig.LOGIN_API_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Login Activity", response);

                    try {
                        JSONObject object = new JSONObject(response);
                        boolean error = object.getBoolean("error");
                        if(!error){
                            JSONObject st = object.getJSONObject("0");
                            String num = st.getString("student_no");
                            String email = st.getString("student_email");
                            String pass = st.getString("password");
                            String reg_id = st.getString("reg_id");
                            String course = st.getString("course");

                            student = new Student(num, email, pass, reg_id, course);
                            goToMain();
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
                params.put("email", email);
                params.put("password", pass);
                params.put("reg_id", regid);
                params.put("course",courseName);

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

    //Get Request
    void getStudent(final String number, final String pass, final String courseName, final View view){
        final String req = "student";
        final String email = number + "@student.ncirl.ie";
        Uri url =  Uri.parse(AppConfig.LOGIN_API_URL)
                .buildUpon()
                .appendQueryParameter("tag", req)
                .appendQueryParameter("student_no", number)
                .appendQueryParameter("email", email)
                .appendQueryParameter("password", pass)
                .appendQueryParameter("reg_id", regid)
                .appendQueryParameter("course", courseName)
                .build();
        Log.d("URL", url.toString());
        StringRequest request = new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Login Activity", response);

                        try {
                            JSONObject object = new JSONObject(response);
                            boolean error = object.getBoolean("error");
                            if(!error){
                                JSONObject st = object.getJSONObject("0");
                                String num = st.getString("student_no");
                                String email = st.getString("student_email");
                                String pass = st.getString("password");
                                String reg_id = st.getString("reg_id");
                                String course = st.getString("course");

                                student = new Student(num, email, pass, reg_id, course);
                                goToMain();
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
        });

        AppController.getInstance().addToRequestQueue(request, req);
    }

    public void goToMain(){
        long id = db.addUser(student);
        if(id != -1) {
            pref.setLogin(true);
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }else{
            Snackbar.make(studentId, "Please try again!", Snackbar.LENGTH_LONG).show();
        }
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
