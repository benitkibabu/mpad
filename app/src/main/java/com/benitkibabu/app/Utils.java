package com.benitkibabu.app;

import android.app.Activity;
import android.content.Intent;

import com.benitkibabu.ncigomobile.R;

/**
 * Created by Anu on 20/02/16.
 */
public class Utils {
    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_BLUE = 1;
    public final static int THEME_RED = 2;

    public static void changeToTheme(Activity activity, int theme){
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch(sTheme){
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_RED:
                activity.setTheme(R.style.Reminder);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.Nci360);
                break;
        }
    }
}
