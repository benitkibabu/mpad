package com.benitkibabu.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benitkibabu.ncigomobile.R;

/**
 * Created by Ben on 28/09/2015.
 */
public class ProfileFragment extends Fragment {

    public static final String ITEM_NAME = "itemName";

    public static ProfileFragment newInstance( String name){
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public ProfileFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(
                R.layout.content_profile, container, false);

        return view;
    }
}
