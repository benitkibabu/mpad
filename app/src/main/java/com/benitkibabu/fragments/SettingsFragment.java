package com.benitkibabu.fragments;


import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.benitkibabu.app.AppConfig;
import com.benitkibabu.app.Utils;
import com.benitkibabu.helper.AppPreferenceManager;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Student;
import com.benitkibabu.ncigomobile.R;


/**
 * Created by Ben on 28/09/2015.
 */
public class SettingsFragment extends Fragment {

    public static final String ITEM_NAME = "itemName";
    final static String themeArray[] = new String[]{"DEFAULT", "RED", "BLUE"};
    static int selectThemeIndex =0;

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

        Button themeBtn = (Button) view.findViewById(R.id.themeBtn);

        themeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThemePicker();
            }
        });

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
            phone.setText("MobileIMEI: " + mobileNo.substring(0, 7)  + "...");
            s.setChecked(pref.getBoolean("updateNotification"));
        }
        catch (Exception ex){
            phone.setText("No device ID");
        }

        String vers = "Version: "+AppConfig.getVersion(getActivity().getBaseContext());
        version.setText(vers);

        return view;
    }

    public static class ThemePickerDialog extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.choose_theme_text)
                    .setItems(themeArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            selectThemeIndex = which;
                            Utils.changeToTheme(getActivity(), which);
                        }
                    });
            return builder.create();
        }
    }

    public void showThemePicker() {
        DialogFragment newFragment = new ThemePickerDialog();
        newFragment.show(getActivity().getSupportFragmentManager(), "theme_picker");
    }

}
