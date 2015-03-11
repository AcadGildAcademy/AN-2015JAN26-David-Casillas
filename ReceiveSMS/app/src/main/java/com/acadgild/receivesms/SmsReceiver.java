package com.acadgild.receivesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle pdusBundle = intent.getExtras();
        Object[] pdus = (Object[]) pdusBundle.get("pdus");
        SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

        String address = messages.getOriginatingAddress();
        String body = messages.getMessageBody();

        Toast.makeText(context, "Address: " + address + "\nBody: " + body, Toast.LENGTH_SHORT).show();

    }

}
