package com.benitkibabu.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Ben on 25/10/2015.
 */

public class AppPreferenceManager {
    public static String TAG = AppPreferenceManager.class.getSimpleName();

    SharedPreferences pref;

    Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "NCIGOAPP";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_REFRESH_RATE = "refreshRate";
    private static final String KEY_REG_ID = "regId";

    public AppPreferenceManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public void setString(String tag, String value){
        editor = pref.edit();
        editor.putString(tag, value);
        editor.apply();
    }

    public void setLogin(boolean isLoggedIn){
        editor = pref.edit();
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.apply();
    }

    public void setRefreshRate(int val){
        editor = pref.edit();
        editor.putInt(KEY_REFRESH_RATE, val);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public int getRefreshRate(){
        return pref.getInt(KEY_REFRESH_RATE, 30);
    }

    public String getStringValue(String tag){
        return pref.getString(tag, "Null");
    }
}

