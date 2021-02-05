package com.lge.la;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.content.ContentValues;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;

import com.android.lge.lgsvcitems.*;
import com.qualcomm.qcrilhook.*;
import com.qualcomm.qcrilmsgtunnel.*;

import android.os.Handler;


import android.net.Uri;
import android.util.Log;
import android.content.ContentValues;
import android.content.ContentResolver;
import android.database.Cursor;
import android.telephony.TelephonyManager;

import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.net.Uri;
import android.net.ConnectivityManager;
import android.database.Cursor;


//import android.annotation.SystemApi; //new

import com.android.lge.lgsvcitems.*;
import com.android.internal.telephony.*;
import com.lge.android.atservice.client.LGATCMDClient;
import com.qualcomm.qcrilhook.*;
import com.qualcomm.qcrilmsgtunnel.*;
import com.lge.la.utils.ResetThread;




import android.app.AppOpsManager;

import com.lge.telephony.LGTelephonyManagerImpl;
//import com.lge.systemservice.core.LgDataUiManager;
//import com.android.internal.telephony.lgdata.LgDataFeature;


//import com.lge.constants.UsbManagerConstants;
//import com.lge.constants.SettingsConstants;
//import com.lge.constants.LGIntent;




import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;










//import com.android.internal.telephony.ITelephony;
//import com.android.internal.telephony.Phone;
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










//import android.preference.PreferenceManager;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
import com.android.lge.lgsvcitems.*;

import android.os.Handler;


import android.preference.PreferenceActivity;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;

//import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
//import com.android.internal.telephony.TelephonyIntents;
//import com.android.internal.telephony.TelephonyProperties;
//import com.android.internal.telephony.PhoneConstants;

//import android.os.SystemProperties;
//import android.os.AsyncResult;
import android.os.Message;












import com.lge.la.utils.ResetThread;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class EDCT_Timer extends Activity implements OnClickListener {

	
	//private ITelephony mPhoneService;   
	private LgSvcCmd mLgSvcCmd;	
	
	private PreferenceScreen mPreferenceScreen;
	
	public void onCreate(Bundle savedInstanceState) {
		Button btn;
		
		super.onCreate(savedInstanceState);
		
		mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());
        //mPhoneService = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));

        
		setContentView(R.layout.edct_timer);
		
		btn = (Button)findViewById(R.id.Bapply);
        btn.setOnClickListener((OnClickListener)this);
        btn = (Button)findViewById(R.id.Bcancel);
        btn.setOnClickListener((OnClickListener)this);
        
        
        
		
	}
	
	@Override
    public void onResume() {
        super.onResume();
        EditText fld = (EditText)findViewById(R.id.EDCT_Timer);
        String EDCT = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_EDCT_TMR);
        //String EDCT = "1";
		fld.setText(EDCT);
		
		
    }
	
	public void onClick(View v) {
        switch(v.getId()) {
        case R.id.Bapply:
        	EditText fld = (EditText)findViewById(R.id.EDCT_Timer);
        	Editable setting = fld.getText();
            System.out.println("EDCT Timer = " + setting);    	
        	mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_EDCT_TMR, setting.toString());
            //LgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_EDCT_TMR, Long.toString(setting));
            //LgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_EDCT_TMR, setting);
            finish();       
            break;

        case R.id.Bcancel:
            finish();
            break;

        default:
            finish();
            break;
        }
	}
	
	

	

}
