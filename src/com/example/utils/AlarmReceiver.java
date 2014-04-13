package com.example.utils;

import com.example.EleControl.EleControl;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	public void onReceive(Context arg0, Intent arg1) {
		EleControl eleControl=new EleControl();
		if (!EnvStatus.isOpenLamp) {
			SmsSender.sendText(eleControl.getBaseContext(), Config.phoneNumber,
					"openlamp");
			EnvStatus.isOpenLampChecked = true;
			EnvStatus.isCloseLampChecked = false;
		} else {
			EnvStatus.isOpenLampChecked = true;
			EnvStatus.isCloseLampChecked = false;
		}
	}
	

}
