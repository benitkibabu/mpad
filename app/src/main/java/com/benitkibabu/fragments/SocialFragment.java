package com.benitkibabu.fragments;


import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.benitkibabu.adapters.StudentListAdapter;
import com.benitkibabu.app.AppConfig;
import com.benitkibabu.app.AppController;
import com.benitkibabu.models.Student;
import com.benitkibabu.ncigomobile.MessageActivity;
import com.benitkibabu.ncigomobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SocialFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    RecyclerView recyclerView;
    StudentListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Student> studentList;


    public SocialFragment() {
        // Required empty public constructor

    }

    public static SocialFragment newInstance(String param1) {
        SocialFragment fragment = new SocialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    Button msgBtn, chatBtn, emailBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_social, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.studentListRecyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.soc_refreshLayout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        adapter = new StudentListAdapter(getActivity(), R.layout.social_item_layout);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StudentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ShowSocialMenuDialog();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadList();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark
        );

        //Load a list of students
        LoadList();

        return v;
    }

    private void LoadList(){
        studentList = new ArrayList<>();

        swipeRefreshLayout.setRefreshing(true);

        String req = "getStudents";
        Uri uri = Uri.parse(AppConfig.LOGIN_API_URL)
                .buildUpon()
                .appendQueryParameter("tag", req)
                .build();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                try{
                    JSONObject job = new JSONObject(response);
                    boolean error = job.getBoolean("error");

                    if(!error){
                        JSONArray result = job.getJSONArray("result");
                        for(int i=0; i<result.length(); i++){
                            JSONObject st = result.getJSONObject(i);
                            String num = st.getString("student_no");
                            String email = st.getString("student_email");
                            String pass = st.getString("password");
                            String reg_id = st.getString("reg_id");
                            String course = st.getString("course");

                            Student student = new Student(num, email, pass, reg_id, course);
                            studentList.add(student);
                        }

                        adapter.clear();
                        adapter.addAll(studentList);

                    }
                }catch(JSONException jes){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("Social Fragments", "Error:" + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest, req);

    }

    public static class ShowSocialOptionMenu extends AppCompatDialogFragment {
        private static final String ITEM_NAME = "name";

        public static ShowSocialOptionMenu newInstance(String name){
            ShowSocialOptionMenu fragment = new ShowSocialOptionMenu();
            Bundle args = new Bundle();
            args.putString(ITEM_NAME, name);
            fragment.setArguments(args);

            return fragment;
        }

        public ShowSocialOptionMenu(){}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();

            //this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            final View view  = inflater.inflate(R.layout.social_option_menu, null);
            final ImageButton callBtn = (ImageButton) view.findViewById(R.id.callBtn);
            final ImageButton chatBtn = (ImageButton) view.findViewById(R.id.chatBtn);
            final ImageButton msgBtn = (ImageButton) view.findViewById(R.id.msgBtn);

            msgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(getActivity().getApplicationContext(), MessageActivity.class);
                    startActivity(i);
                }
            });

            chatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            builder.setCancelable(false);
            builder.setView(view);
            //this.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent);

            return builder.create();
        }
    }

    void ShowSocialMenuDialog(){
        DialogFragment newFragment = ShowSocialOptionMenu.newInstance("social_menu");
        newFragment.show(getActivity().getSupportFragmentManager(), "social_menu");
    }

}
