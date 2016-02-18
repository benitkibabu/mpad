package com.benitkibabu.helper;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

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
import com.benitkibabu.models.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OnlineRC {
    static Student s = null;
    public static Student getStudent(final String number, final String pass, final String courseName, final View view, final String regid){
        s = null;
        return s;

    }

    static boolean result = false;
    public static boolean updateStatus(final String currentId, final String status){
        final String req = "updateStatus";
        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.LOGIN_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    result = obj.getBoolean("error");
                    if(!result){
                        Log.d("OnlineRC", "ERROR FALSE:" + obj.getString("error_msg"));
                    }else{
                        Log.d("OnlineRC", "ERROR TRUE:" + obj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Social Fragments", "Error:" + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("tag", req);
                params.put("student_no", currentId);
                params.put("status", status.toUpperCase());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, req);

        return result;
    }

    static boolean profileChange = false;
    public static boolean updateProfile(final String currentId, final String firstName, final String lastName){
        final String req = "updateProfile";
        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.LOGIN_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    profileChange = obj.getBoolean("error");
                    if(!profileChange){
                        Log.d("OnlineRC", "ERROR FALSE:" + obj.getString("error_msg"));
                    }else{
                        Log.d("OnlineRC", "ERROR TRUE:" + obj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Social Fragments", "Error:" + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("tag", req);
                params.put("student_no", currentId);
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, req);

        return profileChange;
    }

    static Student student;
    public static Student getStudentInfo(final String student_no){
        final String req = "getStudentInfo";
        student = null;
        Uri uri = new Uri.Builder()
                .appendPath(AppConfig.LOGIN_API_URL)
                .appendQueryParameter("tag", req)
                .appendQueryParameter("student_no", student_no)
                .build();
        StringRequest request = new StringRequest(Request.Method.GET,uri.toString(),
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
                                String status = st.getString("status");
                                String first_name = st.getString("first_name");
                                String last_name = st.getString("last_name");

                                student = new Student(num, email, pass, reg_id, course, status, first_name, last_name);

                            }else{
                                Log.e("Volley", "Failed:" + object.getString("error_msg"));
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

        return student;
    }
}
