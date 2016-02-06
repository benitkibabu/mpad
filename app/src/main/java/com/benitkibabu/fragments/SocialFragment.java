package com.benitkibabu.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.benitkibabu.ncigomobile.MessageActivity;
import com.benitkibabu.ncigomobile.R;

public class SocialFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


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

        if(v != null){
            msgBtn = (Button) v.findViewById(R.id.msgBtn);
            chatBtn = (Button) v.findViewById(R.id.chatBtn);
            emailBtn = (Button) v.findViewById(R.id.emailBtn);


            msgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MessageActivity.class);
                    startActivity(i);
                }
            });
        }

        return v;
    }

}
