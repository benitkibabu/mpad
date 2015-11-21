package com.benitkibabu.sections_360;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benitkibabu.ncigomobile.R;

/**
 * Created by Ben on 20/10/2015.
 */
public class AppointmentFragment extends Fragment {
    public static final String ITEM_NAME = "itemName";


    public static AppointmentFragment newInstance(String name){
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public AppointmentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);


        return view;
    }
}
