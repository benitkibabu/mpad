package com.benitkibabu.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benitkibabu.ncigomobile.R;

public class EventsFragment extends Fragment {

    private static final String ITEM_NAME = "itemName";

    public static EventsFragment newInstance(String name){
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public EventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        return view;
    }


}
