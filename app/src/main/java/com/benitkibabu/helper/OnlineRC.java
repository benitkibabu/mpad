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
}
