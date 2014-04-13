package com.example.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SmsSender {

	public static void sendText(Context context,String phoneNumber,String msgBody)
	{
		SmsManager smsManager=SmsManager.getDefault();
		PendingIntent mPi=PendingIntent.getBroadcast(context, 0, new Intent(), 0);
		smsManager.sendTextMessage(phoneNumber, null, msgBody, mPi, null);
	}
}
