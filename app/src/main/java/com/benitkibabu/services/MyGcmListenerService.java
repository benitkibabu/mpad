package com.benitkibabu.services;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
/**
 * Created by Ben on 22/11/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("body");
        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
    }

}
