package com.example.SysSetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jiemian.R;
import com.example.utils.Config;

public class SysSetting extends Activity {
	
	private EditText etIpAddress,etPortNumber,etPhoneNumber,etRefreshRate;
	private Button applySysSetting;
	private SharedPreferences mSharedPreferences;
	private Intent intentSocket;
	private static int MODE=Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syssetting);
		
		etIpAddress=(EditText)findViewById(R.id.etIpAddress);
		etPortNumber=(EditText)findViewById(R.id.etPortNumber);
		etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);
		etRefreshRate=(EditText)findViewById(R.id.etRefreshRate);
		applySysSetting=(Button)findViewById(R.id.applySysSetting);
		
		mSharedPreferences = getSharedPreferences("TestSharedPreferences", MODE);
		
		applySysSetting.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Config.ipAddress=etIpAddress.getText().toString();
				Config.portNumber=etPortNumber.getText().toString();
				Config.phoneNumber=etPhoneNumber.getText().toString();
				Config.refreshRate=etRefreshRate.getText().toString();
				
				Toast.makeText(SysSetting.this, "系统设置已应用", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	protected void onStart() {
		super.onStart();
		
		String ipAddress=mSharedPreferences.getString("ipAddress", "172.20.252.137");
		String portNumber=mSharedPreferences.getString("portNumber", "2001");
		String phoneNumber=mSharedPreferences.getString("phoneNumber", "13354286900");
		String refreshRate=mSharedPreferences.getString("refreshRate", "1");
		
		etIpAddress.setText(ipAddress);
		etPortNumber.setText(portNumber);
		etPhoneNumber.setText(phoneNumber);
		etRefreshRate.setText(refreshRate)
;		
		Config.ipAddress=etIpAddress.getText().toString();
		Config.portNumber=etPortNumber.getText().toString();
		Config.phoneNumber=etPhoneNumber.getText().toString();
		Config.refreshRate=etRefreshRate.getText().toString();
	}

	protected void onStop() {
		super.onStop();
		SharedPreferences.Editor mEditor = mSharedPreferences.edit();  
	    mEditor.putString("ipAddress",etIpAddress.getText().toString());  
	    mEditor.putString("portNumber",etPortNumber.getText().toString());  
	    mEditor.putString("phoneNumber",etPhoneNumber.getText().toString()); 
	    mEditor.putString("refreshRate", etRefreshRate.getText().toString());
	    mEditor.commit();  
	}

}
