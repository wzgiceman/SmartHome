package com.example.jiemian;

import android.app.TabActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

import com.example.EleControl.EleControl;
import com.example.EnvMonitoring.EnvMonitoring;
import com.example.SysSetting.SysSetting;
import com.example.baidumap.MapActivity;
import com.example.utils.SMSReceiver;

public class MainActivity extends TabActivity {

	private SMSReceiver smsReceiver;
	public static final String string = "android.provider.Telephony.SMS_RECEIVED";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator("系统设置")
				.setContent(new Intent().setClass(this, SysSetting.class)));
		tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator("环境监控")
				.setContent(new Intent().setClass(this, EnvMonitoring.class)));
		tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator("家电控制")
				.setContent(new Intent().setClass(this, EleControl.class)));
		tabHost.addTab(tabHost.newTabSpec("TAB4").setIndicator("定位自己")
				.setContent(new Intent().setClass(this, MapActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("TAB4").setIndicator("人员定位")
				.setContent(new Intent().setClass(this,GrpsFamily.class)));
		tabHost.setCurrentTab(0);
		
		startService();
	}
	
	private void startService() {
		smsReceiver=new SMSReceiver();
		IntentFilter filter = new IntentFilter(string);
		registerReceiver(smsReceiver, filter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);
	}
}
