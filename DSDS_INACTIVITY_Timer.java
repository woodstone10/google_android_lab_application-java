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


public class DSDS_INACTIVITY_Timer extends Activity implements OnClickListener {

	
	//private ITelephony mPhoneService;   
	private LgSvcCmd mLgSvcCmd;	
	
	public void onCreate(Bundle savedInstanceState) {
		Button btn;
		
		super.onCreate(savedInstanceState);
		
		mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());
        //mPhoneService = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));

        
		setContentView(R.layout.dsds_inactivity_timer);
		
		btn = (Button)findViewById(R.id.Bapply);
        btn.setOnClickListener((OnClickListener)this);
        btn = (Button)findViewById(R.id.Bcancel);
        btn.setOnClickListener((OnClickListener)this);
		
	}
	
	@Override
    public void onResume() {
        super.onResume();
        EditText fld = (EditText)findViewById(R.id.DSDS_INACTIVITY_Timer);
        String EDCT = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_DSDS_INACTIVITY_TMR );
        //String EDCT = "1";
		fld.setText(EDCT);
    }
	
	public void onClick(View v) {
        switch(v.getId()) {
        case R.id.Bapply:
        	EditText fld = (EditText)findViewById(R.id.DSDS_INACTIVITY_Timer);
        	Editable setting = fld.getText();
            System.out.println("LTE MAC QoS Inactivity Timer = " + setting);    	
        	mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_DSDS_INACTIVITY_TMR , setting.toString());

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
