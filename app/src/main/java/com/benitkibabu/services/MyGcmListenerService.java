package com.benitkibabu.services;

import android.os.Bundle;
import android.util.Log;

import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.UpdateItem;
import com.benitkibabu.ncigomobile.MyNotification;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Ben on 22/11/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        try {
            Log.d("MGC", "MSG: " + data.toString());
            String id = data.getString("id");
            String title = data.getString("title");
            String body = data.getString("body");
            String target = data.getString("target");
            String date = data.getString("date");

            DbHelper db = new DbHelper(this);
            UpdateItem item = new UpdateItem(id, title, body, target, date);
            db.addUpdate(item);

            showNotification(id, title, body);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showNotification(String id, String title, String body){
        MyNotification.notify(this, ""+id, title, body, 1);
    }

}
