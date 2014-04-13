package com.example.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SocketService extends Service {

	private Thread SocketThread;
	private boolean flag=false;
	private Socket socket=null;
	private Runnable socketRunnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i=0;
			try {
				socket = new Socket(Config.ipAddress, Integer.parseInt(Config.portNumber));
				InputStream inputStream=socket.getInputStream();

				PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
				EnvStatus.smoke="无烟雾";
				EnvStatus.fire="无火焰";
				EnvStatus.blaze="无强光";
				while(flag)
				{
					boolean isRead=(!(EnvStatus.isTempChecked))&&(!(EnvStatus.isHumChecked))&&(!(EnvStatus.isSmokeChecked))&&(!(EnvStatus.isFireChecked))&&(!(EnvStatus.isBlazeChecked));
					i++;
					
					if(EnvStatus.isGetTemp&&EnvStatus.isTempChecked)
					{
						writer.print("8419");
						writer.flush();
					}
					if(EnvStatus.isGetHum&&EnvStatus.isHumChecked)
					{
						writer.print("8419");
						writer.flush();
					}
					if(EnvStatus.isGetSmoke&&EnvStatus.isSmokeChecked)
					{
						writer.print("8118");
						writer.flush();
					}
					if(EnvStatus.isGetFire&&EnvStatus.isFireChecked)
					{
						writer.print("8218");
						writer.flush();
					}
					if(EnvStatus.isGetBlaze&&EnvStatus.isBlazeChecked)
					{
						writer.print("8318");
						writer.flush();
					}
					if(EnvStatus.isOpenLamp&&EnvStatus.isOpenLampChecked)
					{
						writer.print("8219");
						writer.flush();
					}
					if(EnvStatus.isCloseLamp&&EnvStatus.isCloseLampChecked)
					{
						writer.print("8209");
						writer.flush();
					}
					
					if(!isRead)
					{
						Log.v("ABCDEFGHIJK","read为假");
					}
					else
					{
						Log.v("ABCDEFGHIJK","read为真");
					}
					EnvStatus.isOpenLampChecked=false;
					EnvStatus.isCloseLampChecked=false;
					Thread.sleep(1000);
				}

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		SocketThread=null;
		if(SocketThread==null)
		{
			SocketThread=new Thread(null,socketRunnable,"SocketThread");
			
		}
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			SocketThread.interrupt();
			SocketThread=null;
			flag=false;
			if(socket!=null)
			{
				socket.close();
				Toast.makeText(this, "连接已断开", Toast.LENGTH_SHORT).show();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		
		if(!flag)
		{
			try {
				SocketThread.start();
				Toast.makeText(this, "程序已连接", Toast.LENGTH_SHORT).show();
				
				flag=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onStart(intent, startId);
	}
	

}
