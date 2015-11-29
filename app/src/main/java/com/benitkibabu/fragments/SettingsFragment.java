package com.benitkibabu.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.benitkibabu.app.AppConfig;
import com.benitkibabu.helper.AppPreferenceManager;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Student;
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
    }


    AppPreferenceManager pref;
    DbHelper db ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        pref = new AppPreferenceManager(getActivity().getBaseContext());
        db = new DbHelper(getActivity().getBaseContext());

        Student u = db.getUser();
        TextView no = (TextView) view.findViewById(R.id.set_student_id_tv);
        TextView email = (TextView) view.findViewById(R.id.set_student_email_tv);
        TextView phone = (TextView) view.findViewById(R.id.set_phone_id_tv);
        TextView version = (TextView) view.findViewById(R.id.set_app_version_tv);
        TextView device_id = (TextView) view.findViewById(R.id.set_device_id_tv);
        Switch s = (Switch) view.findViewById(R.id.update_switch);

        if(u!= null) {
            no.setText("Student Number: " + u.getStudentID());
            email.setText("Email: " + u.getStudentEmail());
        }

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pref.setBoolean("updateNotification", isChecked);
            }
        });


        String dvidTxt = "GCM ID: " +
                (pref.getStringValue("regId").length()>20 ?
                        pref.getStringValue("regId").substring(0, 20)+"..."
                        :pref.getStringValue("regId"));
        device_id.setText(dvidTxt);

        try {
            String mobileNo = AppConfig.getMobileIMEI(getActivity().getBaseContext());
            phone.setText(mobileNo);
            s.setChecked(pref.getBoolean("updateNotification"));
        }
        catch (Exception ex){
            phone.setText("No GCM device ID");
        }

        String vers = "Version: "+AppConfig.getVersion(getActivity().getBaseContext());
        version.setText(vers);

        return view;
    }


}
