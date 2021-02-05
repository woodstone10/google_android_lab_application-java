package com.lge.la.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

public class ResetThread extends Thread {

//	private static final int SLEEP_100 = 100; //unit : us
	private static final int SLEEP_200 = 200; //unit : us
//	private static final int SLEEP_1000 = 1000;	//unit : us

	private Context context;
	private int sleep;

	public ResetThread(Context context) {
		this(context, SLEEP_200);
	} 

	public ResetThread(Context context, int sleep) {
		this.context = context;
		this.sleep = sleep;
	}

	public void run() {
		SystemClock.sleep(sleep);

//		Intent intent = new Intent();
//		intent.setClassName("com.android.settings", "com.android.settings.Reboot");
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//		context.startActivity(intent);
		//Log.d("LA", "ResetThread getSystemService()");
		PowerManager pm =(PowerManager)context.getSystemService(Context.POWER_SERVICE);
		try {
			//Log.d("LA", "ResetThread pm.rebbot()");
			pm.reboot("boot");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

}