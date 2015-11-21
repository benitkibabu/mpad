package com.benitkibabu.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benitkibabu.ncigomobile.R;

/**
 * Created by Ben on 23/10/2015.
 */
public class NciMapFragment extends Fragment {
    private static final String ITEM_NAME = "itemName";

    public static NciMapFragment newInstance(String name){
        NciMapFragment fragment = new NciMapFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;

    }
    public NciMapFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ncimap, container, false);

        return view;
    }
}
