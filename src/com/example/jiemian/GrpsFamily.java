package com.example.jiemian;

import com.example.baidumap.MapActivity;
import com.example.utils.Config;
import com.example.utils.EnvStatus;
import com.example.utils.SmsSender;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class GrpsFamily extends Activity {

	private Spinner spinner;
	private Button button;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grps_family);
		spinner = (Spinner) findViewById(R.id.spinner_numbers);
		button = (Button) findViewById(R.id.button_setNumber);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, Config.numberString);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Config.numberString);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(GrpsFamily.this,MapActivity.class);
				startActivity(intent);
				EnvStatus.isGrps=false;
				
				finish();
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_grps_family, menu);
		return true;
	}

	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
				Config.telephone=Config.numberString[arg2];
				SmsSender.sendText(GrpsFamily.this, Config.telephone,"3");
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

}
