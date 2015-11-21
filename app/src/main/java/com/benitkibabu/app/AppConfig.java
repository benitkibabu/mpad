package com.benitkibabu.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Ben on 06/10/2015.
 */
public class AppConfig {

    public static final String API_URL = "http://itrackerapp.gear.host/ncigo/";
    public static final String TIMETABLE_URL = "http://itrackerapp.gear.host/ncigo/timetable/mscmt_pt_mad_1.pdf";
    static DateFormat dateTimeFormat =  new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
    static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    static DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static String getDateValue(Date date){
        return dateFormat.format(date);
    }

    public static String getTimeValue(Date date){
        return timeFormat.format(date);
    }

    public static String getDateTime() {
        Date date = new Date();
        return dateTimeFormat.format(date);
    }

    public static String generateID(){
        return UUID.randomUUID().toString();
    }

    public static Date getDate(String date) throws ParseException {
        return dateTimeFormat.parse(date);
    }

    public static String getMobileIMEI(Context c){
        TelephonyManager telephonyManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getVersion(Context context){
        PackageInfo pInfo;
        String version = "Null";
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
