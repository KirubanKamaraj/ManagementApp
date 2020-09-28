package com.kirubankamaraj.managementapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class Notify extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //String name = intent.getStringExtra("name");
        String mail = "kirubankamaraj@gmail.com";
        String number = intent.getStringExtra("number");
        String body = intent.getStringExtra("body");
        String subject = "Service Notification mail for "+number;

/*
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
*/
        Intent serviceIntent = new Intent(context, EmailSend.class);
        serviceIntent.putExtra("mail", mail);
        serviceIntent.putExtra("body",body);
        serviceIntent.putExtra("subject", subject);

        context.getApplicationContext().startService(serviceIntent);

/*
        EmailSend emailSend = new  EmailSend("kirubankamaraj@gmail.com", subject, body);
        emailSend.execute();
*/
    }

}
