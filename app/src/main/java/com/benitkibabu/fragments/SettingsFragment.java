package com.benitkibabu.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benitkibabu.app.AppConfig;
import com.benitkibabu.helper.AppPreferenceManager;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Student;
import com.benitkibabu.models.User;
import com.benitkibabu.ncigomobile.R;


/**
 * Created by Ben on 28/09/2015.
 */
public class SettingsFragment extends Fragment {

    public static final String ITEM_NAME = "itemName";

    public static SettingsFragment newInstance(String name){
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }


    AppPreferenceManager pref;
    DbHelper db ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        pref = new AppPreferenceManager(getActivity().getBaseContext());
        db = new DbHelper(getActivity().getBaseContext());

        Student u = db.getUser();
        TextView no = (TextView) view.findViewById(R.id.set_student_id_tv);
        TextView email = (TextView) view.findViewById(R.id.set_student_email_tv);
        TextView phone = (TextView) view.findViewById(R.id.set_phone_id_tv);
        TextView version = (TextView) view.findViewById(R.id.set_app_version_tv);
        TextView device_id = (TextView) view.findViewById(R.id.set_device_id_tv);

        if(u!= null) {
            no.setText(u.getStudentID());
            email.setText(u.getStudentEmail());
        }

        String dvidTxt = "Device ID: " + pref.getStringValue("regId");
        device_id.setText(dvidTxt.substring(0, 50));

        String mobileNo = AppConfig.getMobileIMEI(getActivity().getBaseContext());
        phone.setText(mobileNo);

        String vers = AppConfig.getVersion(getActivity().getBaseContext());
        version.setText(vers);

        return view;
    }


}
