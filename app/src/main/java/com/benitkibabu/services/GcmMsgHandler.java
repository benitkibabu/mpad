package com.benitkibabu.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import com.benitkibabu.ncigomobile.MyNotification;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmMsgHandler extends IntentService {

    private static final String ACTION_FOO = "com.benitkibabu.services.action.FOO";

    private static final String EXTRA_PARAM1 = "com.benitkibabu.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.benitkibabu.services.extra.PARAM2";

    public GcmMsgHandler() {
        super("GcmMsgHandler");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GcmMsgHandler.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();

            GoogleCloudMessaging gcm  = GoogleCloudMessaging.getInstance(this);

            String msgType = gcm.getMessageType(intent);
            String mtitle = extras.getString("title");
            String mbody = extras.getString("body");

            showNotification(mtitle, mbody);
            GcmBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    public void showNotification(String title, String body){
       // MyNotification.notify(this,title,body, 360);
    }
}
