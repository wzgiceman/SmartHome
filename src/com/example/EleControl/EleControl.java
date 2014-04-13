package com.example.EleControl;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.jiemian.R;
import com.example.utils.AlarmReceiver;
import com.example.utils.Config;
import com.example.utils.EnvStatus;
import com.example.utils.SmsSender;

public class EleControl extends Activity {

	private Button lamp_open, lamp_close, auto_light;
	public ImageView iv1;
	public  TextView tv;
	public RadioButton radioWIFIForEle;
	public RadioButton radioMSGForEle;
	public RadioGroup radioGroup2;
	String mySendMsg, myReceivedMsg;
	private TimePickerDialog timePickerDialog = null;
	private Handler handler;
	private Runnable runnable;

	private AlarmManager alarmManager = null;
	Calendar cal = Calendar.getInstance();
	final int DIALOG_TIME = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elecontrol);

		alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);  
		lamp_open = (Button) findViewById(R.id.lamp_open);
		lamp_close = (Button) findViewById(R.id.lamp_close);
		auto_light = (Button) findViewById(R.id.button_auto);
		handler=new Handler();

		tv = (TextView) findViewById(R.id.lamp_status);
		iv1 = (ImageView) findViewById(R.id.imageView1);
		radioMSGForEle = (RadioButton) findViewById(R.id.radioMSGForEle);
		radioWIFIForEle = (RadioButton) findViewById(R.id.radioWIFIForEle);
		radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
		
		runnable=new Runnable() {
			
			@Override
			public void run() {
				lampState(EnvStatus.isOpenLampChecked);
				handler.postDelayed(this, 1000);
			}
		};
		handler.post(runnable);

		if (radioWIFIForEle.isChecked()) {
			EnvStatus.isOpenLamp = true;
			EnvStatus.isCloseLamp = true;
		}

		auto_light.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DIALOG_TIME);
			}
		});

		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radioWIFIForEle) {
					EnvStatus.isOpenLamp = true;
					EnvStatus.isCloseLamp = true;
				} else if (checkedId == R.id.radioMSGForEle) {
					EnvStatus.isOpenLamp = false;
					EnvStatus.isCloseLamp = false;
				}
			}

		});

		lamp_open.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!EnvStatus.isOpenLamp&&!EnvStatus.isCloseLamp){
					SmsSender.sendText(getBaseContext(), Config.phoneNumber,
							"openlamp");
					EnvStatus.isOpenLampChecked=true;
					EnvStatus.isCloseLampChecked=false;
				}else{
					EnvStatus.isOpenLampChecked=true;
					EnvStatus.isCloseLampChecked=false;
				}
				
			}
		});

		lamp_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!EnvStatus.isOpenLamp&&!EnvStatus.isCloseLamp){
					SmsSender.sendText(getBaseContext(), Config.phoneNumber,
							"openlamp");
					EnvStatus.isCloseLampChecked=true;
					EnvStatus.isOpenLampChecked=false;
				}else{
					EnvStatus.isCloseLampChecked=true;
					EnvStatus.isOpenLampChecked=false;
				}
			
			}
		});

	}
	
	
	void lampState(boolean state){
		if(state){
			tv.setText("电灯状态:打开");
			iv1.setImageResource(R.drawable.open);
		}else{
			tv.setText("电灯状态:关闭");
			iv1.setImageResource(R.drawable.close);
		}
		lamp_close.setClickable(state);
		lamp_open.setClickable(!state);
		
	}
	
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(runnable);
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_TIME:
			dialog = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {
						public void onTimeSet(TimePicker timePicker,
								int hourOfDay, int minute) {
							Calendar c = Calendar.getInstance();// 获取日期对象
							c.setTimeInMillis(System.currentTimeMillis()); // 设置Calendar对象
							c.set(Calendar.HOUR, hourOfDay); // 设置闹钟小时数
							c.set(Calendar.MINUTE, minute); // 设置闹钟的分钟数
							c.set(Calendar.SECOND, 0); // 设置闹钟的秒数
							c.set(Calendar.MILLISECOND, 0); // 设置闹钟的毫秒数
							Intent intent = new Intent(EleControl.this,
									AlarmReceiver.class); // 创建Intent对象
							PendingIntent pi = PendingIntent.getBroadcast(
									EleControl.this, 0, intent, 0); // 创建PendingIntent
							alarmManager.set(AlarmManager.RTC_WAKEUP,
									c.getTimeInMillis(), pi); // 设置闹钟，当前时间就唤醒
							Toast.makeText(EleControl.this, "闹钟设置成功",
									Toast.LENGTH_LONG).show();// 提示用户
						}
					}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
					false);
			break;
		}
		return dialog;
	}
}
