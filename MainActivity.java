package com.lge.la;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.widget.TabHost;
import android.app.TabActivity;
import android.content.Intent;

import com.android.lge.lgsvcitems.LgSvcCmd;
//import com.android.internal.telephony.PhoneConstants;
//import com.android.internal.telephony.TelephonyProperties;
import com.lge.android.atservice.client.LGATCMDClient;


import com.android.internal.telephony.*;

//import android.os.ServiceManager;
import android.os.RemoteException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
//import android.os.SystemProperties;
import android.util.Log;
import android.os.Handler;
//import android.os.ServiceManager;
import android.os.RemoteException;

import com.android.internal.telephony.Phone;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;

//public class MainActivity extends PreferenceActivity {
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.layout.activity_main);
//    }
//}


public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
	
	TabHost mTabHost=null;
	private ListPreference p;
	private static final String TAG = "LabApplication";
	
	private LgSvcCmd mLgSvcCmd;
	private Handler mLgSvcItemsHandler;
	private Runnable lgScvItemsRunnable;
		
	/*
	private LGATCMDClient mATClient;
	private Handler mHandler = new Handler();
	
	Runnable m_display_run = new Runnable(){
		public void run(){
			LGATCMDClient.Response response;
			try{
				//response = mATClient.request(4020,"2".getBytes());
				Thread.sleep(1000);
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	};
	*/
		
    @Override
    public void onCreate(Bundle savedInstanceState) { 	
	    super.onCreate(savedInstanceState);
     
        
     
        mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());        
  		mLgSvcItemsHandler = new Handler();  		
  		lgScvItemsRunnable = new Runnable() { 
  			@Override
  			public void run() {
  				recreate();
  			}
  		};
  		if (mLgSvcCmd.getIQcrilMsgTunnelServiceStatus() == false) { 
  			mLgSvcItemsHandler.postDelayed(lgScvItemsRunnable , 100); 
  		}
  		        
  		
  		//mATClient = new LGATCMDClient(this);
		//mATClient.bindService();		
		//mHandler.postDelayed(m_display_run, 500); 	
    	
    	setContentView(R.layout.main_tabs);        
        mTabHost = getTabHost();      
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Pref").setContent(new Intent(this, DETAILS.class)));
        
        mTabHost.addTab(mTabHost.newTabSpec("tab_test4").setIndicator("Feature").setContent(new Intent(this, FEATURES.class)));
        
        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Lab").setContent(new Intent(this, IOT.class)));
        
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("Field").setContent(new Intent(this, FIELD.class)));
                     

    }
    
    public void onDestroy() {
		//mATClient.unbindService();
		super.onDestroy();
		Log.d(TAG, "OnDestory called!!!");
	}

}