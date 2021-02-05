package com.lge.la;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
//import android.os.ServiceManager;
import android.os.RemoteException;
//import android.os.SystemProperties;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Message;
import android.os.Binder;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.ContentValues;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;


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


/////////////////////////////////////////////////////////////////

public class FIELD extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	SharedPreferences prefs;
	private ListPreference p;
	
	private static final String TAG = "LabApplication";
	
	//LA2.0
	//hVoLTE VCP task
	public static final int Callend_BSR_Timer_3sec = 3;
	public static final int Callend_BSR_Timer_30sec = 30;
	public static final int Callend_BSR_Timer_60sec = 60;
	public static final int Callend_BSR_Timer_120sec = 120;
	
	public static final int Skip_BSR_In_Calling_DISABLE = 0;
	public static final int Skip_BSR_In_Calling_ENABLE = 1;
	
	
	public static final int Skip_BSR_In_LTE_Lost_When_Calling_DISABLE = 0;
	public static final int Skip_BSR_In_LTE_Lost_When_Calling_ENABLE = 1;
	
	public static final int Force_to_SRLTE_when_RRCConnection_Fail_DISABLE = 255;
	public static final int Force_to_SRLTE_when_RRCConnection_Fail_3counts = 4;
	public static final int Force_to_SRLTE_when_RRCConnection_Fail_5counts = 6;
	public static final int Force_to_SRLTE_when_RRCConnection_Fail_10counts = 11;
	public static final int Force_to_SRLTE_when_RRCConnection_Fail_20counts = 21;
	
	
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_DISABLE = 255;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_90dB = 90;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_95dB = 95;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_100dB = 100;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_105dB = 105;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_110dB = 110;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_115dB = 115;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_120dB = 120;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_125dB = 125;
	
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_T_1sec = 1;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_T_3sec = 3;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_T_5sec = 5;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_T_10sec = 10;	
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_T_20sec = 20;
	public static final int Hysteresis_to_CSFB_with_Signal_Strength_T_30sec = 30;
	
	
	public static final int Force_to_SRLTE_with_Signal_Strength_DISABLE = 0;
	public static final int Force_to_SRLTE_with_Signal_Strength_70dB = 70;
	public static final int Force_to_SRLTE_with_Signal_Strength_75dB = 75;
	public static final int Force_to_SRLTE_with_Signal_Strength_80dB = 80;
	public static final int Force_to_SRLTE_with_Signal_Strength_85dB = 85;	
	public static final int Force_to_SRLTE_with_Signal_Strength_90dB = 90;
	public static final int Force_to_SRLTE_with_Signal_Strength_95dB = 95;
	public static final int Force_to_SRLTE_with_Signal_Strength_100dB = 100;
	public static final int Force_to_SRLTE_with_Signal_Strength_105dB = 105;
	public static final int Force_to_SRLTE_with_Signal_Strength_110dB = 110;
	public static final int Force_to_SRLTE_with_Signal_Strength_115dB = 115;
	public static final int Force_to_SRLTE_with_Signal_Strength_120dB = 120;
	public static final int Force_to_SRLTE_with_Signal_Strength_125dB = 125;
	
	public static final int Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE = 0;
	public static final int Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_ENABLE = 1;
	
	public static final int Force_to_SRLTE_until_IMS_Reg_Success_DISABLE =0;
	public static final int Force_to_SRLTE_until_IMS_Reg_Success_ENABLE =1;
	
	public static final int OP_Mode_Pingpong_Timer_DISABLE = 0;
	public static final int OP_Mode_Pingpong_Timer_10sec = 10;
	public static final int OP_Mode_Pingpong_Timer_20sec = 20;
	public static final int OP_Mode_Pingpong_Timer_30sec = 30;
	public static final int OP_Mode_Pingpong_Timer_40sec = 40;
	public static final int OP_Mode_Pingpong_Timer_50sec = 50;
	public static final int OP_Mode_Pingpong_Timer_60sec = 60;
	public static final int OP_Mode_Pingpong_Timer_90sec = 90;
	public static final int OP_Mode_Pingpong_Timer_120sec = 120;
	public static final int OP_Mode_Pingpong_Timer_180sec = 180;
	

	public static final int Fast_1x_Idle_Transition_DISABLE =0;
	public static final int Fast_1x_Idle_Transition_ENABLE =1;
	
	//add LA3.0
	//LTE conn% task
	public static final int RACH_Failure_Count_to_Backoff_PLMN_1 =1;
	public static final int RACH_Failure_Count_to_Backoff_PLMN_2 =2;
	public static final int RACH_Failure_Count_to_Backoff_PLMN_3 =3;
	public static final int RACH_Failure_Count_to_Backoff_PLMN_4 =4;
	public static final int RACH_Failure_Count_to_Backoff_PLMN_5 =5;
	
	public static final int Throttled_BSR_Timer_DISABLE =0;
	public static final int Throttled_BSR_Timer_ENABLE =1;
	
	public static final int Kickoff_BSR_Scan_DISABLE =0;
	public static final int Kickoff_BSR_Scan_ENABLE =1;
	
	public static final int ACQ_DB_Scan_Scope_0 =0;
	public static final int ACQ_DB_Scan_Scope_1 =1;
	public static final int ACQ_DB_Scan_Scope_2 =2;
	public static final int ACQ_DB_Scan_Scope_3 =3;
	public static final int ACQ_DB_Scan_Scope_4 =4;
	public static final int ACQ_DB_Scan_Scope_5 =5;
	public static final int ACQ_DB_Scan_Scope_6 =6;
	public static final int ACQ_DB_Scan_Scope_7 =7;
	public static final int ACQ_DB_Scan_Scope_8 =8;
	public static final int ACQ_DB_Scan_Scope_9 =9;
	public static final int ACQ_DB_Scan_Scope_10 =10;
	
	public static final int HPLMN_Scan_Scope_0 =0;
	public static final int HPLMN_Scan_Scope_1 =1;
	public static final int HPLMN_Scan_Scope_2 =2;
	public static final int HPLMN_Scan_Scope_3 =3;
	public static final int HPLMN_Scan_Scope_4 =4;
	public static final int HPLMN_Scan_Scope_5 =5;
	public static final int HPLMN_Scan_Scope_6 =6;
	public static final int HPLMN_Scan_Scope_7 =7;
	public static final int HPLMN_Scan_Scope_8 =8;
	public static final int HPLMN_Scan_Scope_9 =9;
	public static final int HPLMN_Scan_Scope_10 =10;
	
	public static final int Skip_GW_Scan_During_VoLTE_RLF_DISABLE =0;
	public static final int Skip_GW_Scan_During_VoLTE_RLF_ENABLE =1;
	
	public static final int D2LREDIR_DISABLE =1;
	public static final int D2LREDIR_ENABLE =0;

	
	//LA 3.1
	//Enhanced CDMA Access Algorithm
	public static final int CDMA_Access_Power_Control_QCT_Default = 0;
	public static final int CDMA_Access_Power_Control_LG_Default = 1;
	public static final int CDMA_Access_Power_Control_LG_Enhanced = 2;
		
	public static final int RXAGCThreshold_60dBm = 60;
	public static final int RXAGCThreshold_90dBm = 90;
	public static final int RXAGCThreshold_95dBm = 95;
	public static final int RXAGCThreshold_100dBm = 100;
	public static final int RXAGCThreshold_105dBm = 105;
	public static final int RXAGCThreshold_110dBm = 110;
	public static final int RXAGCThreshold_115dBm = 115;
	public static final int RXAGCThreshold_120dBm = 120;
	public static final int RXAGCThreshold_125dBm = 125;
	
	public static final int ECIOThreshold_5dB = 5;
	public static final int ECIOThreshold_10dB = 10;
	public static final int ECIOThreshold_11dB = 11;
	public static final int ECIOThreshold_12dB = 12;
	public static final int ECIOThreshold_13dB = 13;
	public static final int ECIOThreshold_14dB = 14;
	public static final int ECIOThreshold_15dB = 15;
	public static final int ECIOThreshold_16dB = 16;
	public static final int ECIOThreshold_17dB = 17;
	public static final int ECIOThreshold_18dB = 18;
	public static final int ECIOThreshold_19dB = 19;
	public static final int ECIOThreshold_20dB = 20;
	public static final int ECIOThreshold_25dB = 25;
	public static final int ECIOThreshold_30dB = 30;

	public static final int APoffset_0dB = 0;
	public static final int APoffset_1dB = 1;
	public static final int APoffset_2dB = 2;
	public static final int APoffset_3dB = 3;
	public static final int APoffset_4dB = 4;
	public static final int APoffset_5dB = 5;
	public static final int APoffset_6dB = 6;
	public static final int APoffset_7dB = 7;
	public static final int APoffset_8dB = 8;	
	
	public static final int CDMA_Access_Delay_Control_QCT_Default = 0;
	public static final int CDMA_Access_Delay_Control_LG_Enhanced = 1;	
	
	public static final int CDMA_SR_Delay_Control_QCT_Default = 0;
	public static final int CDMA_SR_Delay_Control_LG_Enhanced = 1;	
	
	public static final int SP_Period_0sec = 0;
	public static final int SP_Period_1sec = 1;
	public static final int SP_Period_2sec = 2;
	public static final int SP_Period_3sec = 3;
	public static final int SP_Period_4sec = 4;
	public static final int SP_Period_5sec = 5;
	public static final int SP_Period_6sec = 6;
	public static final int SP_Period_7sec = 7;
	public static final int SP_Period_8sec = 8;

	
	//LA 3.2
	//Enhanced VoLTE Audio Performance Algorithm
	public static final int VAP_Disable = 0;
	public static final int VAP_Enable = 1;

	
	public static final int COUNT_0 = 0;
	public static final int COUNT_1 = 1;
	public static final int COUNT_2 = 2;
	public static final int COUNT_3 = 3;
	public static final int COUNT_4 = 4;
	public static final int COUNT_5 = 5;
	public static final int COUNT_6 = 6;
	public static final int COUNT_7 = 7;
	public static final int COUNT_8 = 8;
	public static final int COUNT_9 = 9;
	public static final int COUNT_10 = 10;
	public static final int COUNT_11 = 11;
	public static final int COUNT_12 = 12;
	public static final int COUNT_13 = 13;
	public static final int COUNT_14 = 14;
	public static final int COUNT_15 = 15;
	public static final int COUNT_16 = 16;
	public static final int COUNT_17 = 17;
	public static final int COUNT_18 = 18;
	public static final int COUNT_19 = 19;
	public static final int COUNT_20 = 20;
	public static final int COUNT_25 = 25;
	public static final int COUNT_30 = 30;

	
	
	public static final int VALUE_minus_0 = 0;
	public static final int VALUE_minus_1 = 1;
	public static final int VALUE_minus_2 = 2;
	public static final int VALUE_minus_3 = 3;
	public static final int VALUE_minus_4 = 4;
	public static final int VALUE_minus_5 = 5;
	public static final int VALUE_minus_6 = 6;
	public static final int VALUE_minus_7 = 7;
	public static final int VALUE_minus_8 = 8;
	public static final int VALUE_minus_9 = 9;
	public static final int VALUE_minus_10 = 10;
	public static final int VALUE_minus_11 = 11;
	public static final int VALUE_minus_12 = 12;
	public static final int VALUE_minus_13 = 13;
	public static final int VALUE_minus_14 = 14;
	public static final int VALUE_minus_15 = 15;
	public static final int VALUE_minus_16 = 16;
	public static final int VALUE_minus_17 = 17;
	public static final int VALUE_minus_18 = 18;
	public static final int VALUE_minus_19 = 19;
	public static final int VALUE_minus_20 = 20;
	public static final int VALUE_minus_21 = 21;
	public static final int VALUE_minus_22 = 22;
	public static final int VALUE_minus_23 = 23;
	public static final int VALUE_minus_24 = 24;
	public static final int VALUE_minus_25 = 25;
	public static final int VALUE_minus_26 = 26;
	public static final int VALUE_minus_27 = 27;
	public static final int VALUE_minus_28 = 28;
	public static final int VALUE_minus_29 = 29;
	public static final int VALUE_minus_30 = 30;
	public static final int VALUE_minus_60 = 60;
	public static final int VALUE_minus_90 = 90;
	public static final int VALUE_minus_95 = 95;
	public static final int VALUE_minus_100 = 100;
	public static final int VALUE_minus_101 = 101;
	public static final int VALUE_minus_102 = 102;
	public static final int VALUE_minus_103 = 103;
	public static final int VALUE_minus_104 = 104;
	public static final int VALUE_minus_105 = 105;
	public static final int VALUE_minus_106 = 106;
	public static final int VALUE_minus_107 = 107;
	public static final int VALUE_minus_108 = 108;
	public static final int VALUE_minus_109 = 109;
	public static final int VALUE_minus_110 = 110;
	public static final int VALUE_minus_111 = 111;
	public static final int VALUE_minus_112 = 112;
	public static final int VALUE_minus_113 = 113;
	public static final int VALUE_minus_114 = 114;
	public static final int VALUE_minus_115 = 115;
	public static final int VALUE_minus_116 = 116;
	public static final int VALUE_minus_117 = 117;
	public static final int VALUE_minus_118 = 118;
	public static final int VALUE_minus_119 = 119;
	public static final int VALUE_minus_120 = 120;
	public static final int VALUE_minus_121 = 121;
	public static final int VALUE_minus_122 = 122;
	public static final int VALUE_minus_123 = 123;
	public static final int VALUE_minus_124 = 124;
	public static final int VALUE_minus_125 = 125;
	public static final int VALUE_minus_126 = 126;
	public static final int VALUE_minus_127 = 127;
	public static final int VALUE_minus_128 = 128;
	public static final int VALUE_minus_129 = 129;
	public static final int VALUE_minus_130 = 130;
	public static final int VALUE_minus_135 = 135;
	public static final int VALUE_minus_140 = 140;
	public static final int VALUE_minus_145 = 145;
	public static final int VALUE_minus_150 = 150;



	

	
	
	
	//private ITelephony mPhoneService;   
	private LgSvcCmd mLgSvcCmd;	
	
	private PreferenceScreen mPreferenceScreen;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	        
    	super.onCreate(savedInstanceState); 
        
    	addPreferencesFromResource(R.layout.field_testing);        
        
    	mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());
        //mPhoneService = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    
        Log.v(TAG,"[LA] " + "FIELD.java");
        
        /* READ and SET DEFAULT VALUE */
        
        //hVoLTE VCP
        Callend_BSR_Timer();
        Skip_BSR_In_Calling();
        Skip_BSR_In_LTE_Lost_When_Calling();
        Force_to_SRLTE_when_RRCConnection_Fail();
        Hysteresis_to_CSFB_with_Signal_Strength();
        Hysteresis_to_CSFB_with_Signal_Strength_T();
        Force_to_SRLTE_with_Signal_Strength();
        Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode();
        Force_to_SRLTE_until_IMS_Reg_Success(); 
        Fast_1x_Idle_Transition();
        OP_Mode_Pingpong_Timer();
        

        //LTE conn%
        RACH_Failure_Count_to_Backoff_PLMN();
        Throttled_BSR_Timer();
        Kickoff_BSR_Scan();        
        ACQ_DB_Scan_Scope();
        HPLMN_Scan_Scope();
        Skip_GW_Scan_During_VoLTE_RLF();
        D2LREDIR();
        

        //1xIA
        AccessPowerControl();
    	initpwr_RXAGCThresholdHi();
    	initpwr_RXAGCThresholdLo();
    	initpwr_ECIOThresholdHi();
    	initpwr_ECIOThresholdLo();
    	initpwr_APplusoffset();
    	initpwr_APminusoffset();    	
    	AccessDelayControl();
    	probedealy_RXAGCThresholdHi();
    	probedealy_RXAGCThresholdLo();
    	probedealy_ECIOThresholdHi();
    	probedealy_ECIOThresholdLo();    	
    	SRDelayControl();
    	srperiod_RXAGCThresholdHi();
    	srperiod_RXAGCThresholdLo();
    	srperiod_ECIOThresholdHi();
    	srperiod_ECIOThresholdLo();
    	srperiod_SRmin();
    	srperiod_SRmax();
   	

    	//AMT
     	Force_1x_Transition();
    	Force_1x_RSRP_Hi();
    	Force_1x_RSRP_Lo();
    	Force_1x_RSRQ_Hi();
    	Force_1x_RSRQ_Lo();
    	Force_1x_2CSFB_Cnt();
    	Force_1x_2SRLTE_Cnt();
    	Force_1x_CDMA_RSSI_Hi();
    	Force_1x_CDMA_RSSI_Lo();
    	Force_1x_CDMA_ECIO_Hi();
    	Force_1x_CDMA_ECIO_Lo();
    	Force_1x_CDMA_RSSI_Hyst();
    	Force_1x_CDMA_ECIO_Hyst();
    	Force_1x_Pingpong_timer_to_SRLTE();
    	
    	
    	//VAP
    	Skip_GW_Scan_during_VoLTE_call();
    	Skip_1xDO_Scan_during_VoLTE_call();
    	Unlimited_RACH_during_VoLTE_call();
    	Unlimited_RACH_Successive_Offset_during_VoLTE_call();
    	TAU_for_VZW_only_during_VoLTE_call();
    	Force_RLF_during_VoLTE_call();
	
    	
        registerChangeListener();
    }
    
    public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "OnDestory called!!!");
	}    
    @Override
    protected void onResume() {
        super.onResume();        
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);        
        System.out.println("onResume"); 
        registerChangeListener();
    }
    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
        System.out.println("onPause");        
    }
    
	/* **************************************************************************************/
    /*                                                                                      */
	/*                              READ VALUE                                              */
    /*                                                                                      */
	/* **************************************************************************************/
	
	//CMD_HVOLTE_TASK_AI_ONE
	private void Callend_BSR_Timer() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Callend_BSR_Timer");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_ONE);
		//tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_IPV6_SUPPORT);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_ONE string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_ONE int = " + setting);
			
			switch (setting) {		
				case Callend_BSR_Timer_3sec: p.setSummary("3sec (QCT Default)"); break;						
				case Callend_BSR_Timer_30sec: p.setSummary("30sec"); break;
				case Callend_BSR_Timer_60sec: p.setSummary("60sec (LG Suggestion)"); break;
				case Callend_BSR_Timer_120sec: p.setSummary("120sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_THREE 
	private void Skip_BSR_In_Calling() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Skip_BSR_In_Calling");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_THREE string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_THREE int = " + setting);
			
			switch (setting) {		
				case Skip_BSR_In_Calling_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Skip_BSR_In_Calling_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_THREE_SECND 
	private void Skip_BSR_In_LTE_Lost_When_Calling() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Skip_BSR_In_LTE_Lost_When_Calling");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE_SECND);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_THREE_SECND string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_THREE_SECND int = " + setting);
			
			switch (setting) {		
				case Skip_BSR_In_LTE_Lost_When_Calling_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Skip_BSR_In_LTE_Lost_When_Calling_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_FOUR 
	private void Force_to_SRLTE_when_RRCConnection_Fail() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_when_RRCConnection_Fail");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_FOUR);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_FOUR string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_FOUR int = " + setting);
			
			switch (setting) {		
				case Force_to_SRLTE_when_RRCConnection_Fail_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Force_to_SRLTE_when_RRCConnection_Fail_3counts: p.setSummary("Enable with 3 counts (LG Suggestion)"); break;
				case Force_to_SRLTE_when_RRCConnection_Fail_5counts: p.setSummary("Enable with 5 counts"); break;
				case Force_to_SRLTE_when_RRCConnection_Fail_10counts: p.setSummary("Enable with 10 counts"); break;
				case Force_to_SRLTE_when_RRCConnection_Fail_20counts: p.setSummary("Enable with 20 counts"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_SEVEN 
	private void Hysteresis_to_CSFB_with_Signal_Strength() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Hysteresis_to_CSFB_with_Signal_Strength");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_FIRST string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_FIRST int = " + setting);
			
			switch (setting) {		
				case Hysteresis_to_CSFB_with_Signal_Strength_DISABLE: p.setSummary("Disable (QCT Default)"); break;	
				case Hysteresis_to_CSFB_with_Signal_Strength_90dB: p.setSummary("Enable with -90dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_95dB: p.setSummary("Enable with -95dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_100dB: p.setSummary("Enable with -100dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_105dB: p.setSummary("Enable with -105dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_110dB: p.setSummary("Enable with -110dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_115dB: p.setSummary("Enable with -115dB (LG Suggestion)"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_120dB: p.setSummary("Enable with -120dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_125dB: p.setSummary("Enable with -125dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_SEVEN_T 
	private void Hysteresis_to_CSFB_with_Signal_Strength_T() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Hysteresis_to_CSFB_with_Signal_Strength_T");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_SECND);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_SECND string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_SECND int = " + setting);
			
			switch (setting) {		
				case Hysteresis_to_CSFB_with_Signal_Strength_T_1sec: p.setSummary("1sec"); break;						
				case Hysteresis_to_CSFB_with_Signal_Strength_T_3sec: p.setSummary("3sec"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_5sec: p.setSummary("5sec"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_10sec: p.setSummary("10sec (LG Suggestion)"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_20sec: p.setSummary("20sec"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_30sec: p.setSummary("30sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}	
	
	//CMD_HVOLTE_TASK_AI_SEVEN_THIRD  
	private void Force_to_SRLTE_with_Signal_Strength() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_with_Signal_Strength");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_THIRD );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_THIRD  string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_THIRD  int = " + setting);
			
			switch (setting) {		
				case Force_to_SRLTE_with_Signal_Strength_DISABLE: p.setSummary("Disable (QCT Default)"); break;	
				case 30: p.setSummary("Enable with -70dB"); break;
				case 60: p.setSummary("Enable with -75dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_80dB: p.setSummary("Enable with -80dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_85dB: p.setSummary("Enable with -85dB"); break;				
				case Force_to_SRLTE_with_Signal_Strength_90dB: p.setSummary("Enable with -90dB (LG Suggestion)"); break;
				case Force_to_SRLTE_with_Signal_Strength_95dB: p.setSummary("Enable with -95dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_100dB: p.setSummary("Enable with -100dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_105dB: p.setSummary("Enable with -105dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_110dB: p.setSummary("Enable with -110dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_115dB: p.setSummary("Enable with -115dB"); break;
				//case Force_to_SRLTE_with_Signal_Strength_120dB: p.setSummary("Enable with -120dB"); break;
				//case Force_to_SRLTE_with_Signal_Strength_125dB: p.setSummary("Enable with -125dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_SEVEN_T 
	private void Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FOURTH );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_FOURTH  string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_SEVEN_FOURTH  int = " + setting);
			
			switch (setting) {		
				case Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_TEN 
	private void Force_to_SRLTE_until_IMS_Reg_Success() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_until_IMS_Reg_Success");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TEN);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_TEN string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_TEN int = " + setting);
			
			switch (setting) {		
				case Force_to_SRLTE_until_IMS_Reg_Success_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Force_to_SRLTE_until_IMS_Reg_Success_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HVOLTE_TASK_AI_TWELVE 
	private void Fast_1x_Idle_Transition() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Fast_1x_Idle_Transition");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TWELVE);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_TWELVE string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_AI_TWELVE int = " + setting);
			
			switch (setting) {		
				case Fast_1x_Idle_Transition_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Fast_1x_Idle_Transition_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	

	
	//CMD_HVOLTE_TASK_IMSPREFIND_HYST 
	private void OP_Mode_Pingpong_Timer() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("OP_Mode_Pingpong_Timer");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_IMSPREFIND_HYST);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_HVOLTE_TASK_IMSPREFIND_HYST string = " + tmp);
			//Log.e(TAG, "CMD_HVOLTE_TASK_IMSPREFIND_HYST int = " + setting);
			
			switch (setting) {		
				case OP_Mode_Pingpong_Timer_DISABLE: p.setSummary("Disable (QCT Default)"); break;		
				case OP_Mode_Pingpong_Timer_10sec: p.setSummary("Enable with 10secs"); break;
				case OP_Mode_Pingpong_Timer_20sec: p.setSummary("Enable with 20secs"); break;
				case OP_Mode_Pingpong_Timer_30sec: p.setSummary("Enable with 30secs"); break;
				case OP_Mode_Pingpong_Timer_40sec: p.setSummary("Enable with 40secs"); break;
				case OP_Mode_Pingpong_Timer_50sec: p.setSummary("Enable with 50secs"); break;
				case OP_Mode_Pingpong_Timer_60sec: p.setSummary("Enable with 60secs"); break;
				case OP_Mode_Pingpong_Timer_120sec: p.setSummary("Enable with 120secs"); break;
				case OP_Mode_Pingpong_Timer_180sec: p.setSummary("Enable with 180secs"); break;
				
				
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	////////////////////////////////////////////////////
	//add LA3.0
	//CMD_RACH_FAILURE_COUNT_TO_BACKOFF_PLMN 
	private void RACH_Failure_Count_to_Backoff_PLMN() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("RACH_Failure_Count_to_Backoff_PLMN");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_RACH_FAILURE_COUNT_TO_BACKOFF_PLMN);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			switch (setting) {		
				case RACH_Failure_Count_to_Backoff_PLMN_1: p.setSummary("1(3) (QCT Default)"); break;		
				case RACH_Failure_Count_to_Backoff_PLMN_2: p.setSummary("2(6) (LG Suggestion)"); break;
				case RACH_Failure_Count_to_Backoff_PLMN_3: p.setSummary("3(9)"); break;
				case RACH_Failure_Count_to_Backoff_PLMN_4: p.setSummary("4(12)"); break;
				case RACH_Failure_Count_to_Backoff_PLMN_5: p.setSummary("5(15)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_THROTTLED_BSR_TIMER 
	private void Throttled_BSR_Timer() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Throttled_BSR_Timer");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_THROTTLED_BSR_TIMER);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			switch (setting) {		
				case Throttled_BSR_Timer_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Throttled_BSR_Timer_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_KICKOFF_BSR_SCAN 
	private void Kickoff_BSR_Scan() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Kickoff_BSR_Scan");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_KICKOFF_BSR_SCAN);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			switch (setting) {		
				case Kickoff_BSR_Scan_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Kickoff_BSR_Scan_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}	
	
	//CMD_ACQ_DB_SCAN_SCOPE  
	private void ACQ_DB_Scan_Scope() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("ACQ_DB_Scan_Scope");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_N_ACQ_DB_SCAN_SCOPE);
		
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			switch (setting) {		
				case ACQ_DB_Scan_Scope_0: p.setSummary("Disable (QCT Default)"); break;		
				case ACQ_DB_Scan_Scope_1: p.setSummary("1"); break;
				case ACQ_DB_Scan_Scope_2: p.setSummary("2"); break;
				case ACQ_DB_Scan_Scope_3: p.setSummary("3"); break;
				case ACQ_DB_Scan_Scope_4: p.setSummary("4"); break;
				case ACQ_DB_Scan_Scope_5: p.setSummary("5 (LG Suggestion)"); break;
				case ACQ_DB_Scan_Scope_6: p.setSummary("6"); break;
				case ACQ_DB_Scan_Scope_7: p.setSummary("7"); break;
				case ACQ_DB_Scan_Scope_8: p.setSummary("8"); break;
				case ACQ_DB_Scan_Scope_9: p.setSummary("9"); break;
				case ACQ_DB_Scan_Scope_10: p.setSummary("10"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_HPLMN_SCAN_SCOPE  
	private void HPLMN_Scan_Scope() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("HPLMN_Scan_Scope");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_N_HPLMN_SCAN_SCOPE);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			switch (setting) {		
				case HPLMN_Scan_Scope_0: p.setSummary("Disable (QCT Default)"); break;		
				case HPLMN_Scan_Scope_1: p.setSummary("1 (LG Suggestion)"); break;
				case HPLMN_Scan_Scope_2: p.setSummary("2"); break;
				case HPLMN_Scan_Scope_3: p.setSummary("3"); break;
				case HPLMN_Scan_Scope_4: p.setSummary("4"); break;
				case HPLMN_Scan_Scope_5: p.setSummary("5"); break;
				case HPLMN_Scan_Scope_6: p.setSummary("6"); break;
				case HPLMN_Scan_Scope_7: p.setSummary("7"); break;
				case HPLMN_Scan_Scope_8: p.setSummary("8"); break;
				case HPLMN_Scan_Scope_9: p.setSummary("9"); break;
				case HPLMN_Scan_Scope_10: p.setSummary("10"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_SKIP_GW_SCAN_DURING_RLF 
	private void Skip_GW_Scan_During_VoLTE_RLF() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Skip_GW_Scan_During_VoLTE_RLF");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_SKIP_GW_SCAN_DURING_RLF);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			switch (setting) {		
				case Skip_GW_Scan_During_VoLTE_RLF_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Skip_GW_Scan_During_VoLTE_RLF_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}	
	
	//CMD_UE_INIT_D2LREDIR_DISABLE 
	private void D2LREDIR() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("D2LREDIR");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_UE_INIT_D2LREDIR_DISABLE);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			switch (setting) {		
				case D2LREDIR_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case D2LREDIR_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}	
	
	
	
	////////////////////////////////////////////////////
	//add LA3.1
	// Enhanced CDMA Access Algorithm
	//CMD_1XIA_POWER_CONTROL 
	private void AccessPowerControl() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("AccessPowerControl");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_POWER_CONTROL);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_1XIA_POWER_CONTROL string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_POWER_CONTROL int = " + setting);
			
			switch (setting) {		
				case CDMA_Access_Power_Control_QCT_Default: p.setSummary("QCT Default"); break;						
				case CDMA_Access_Power_Control_LG_Default: p.setSummary("LG Default (3dB dup)"); break;
				case CDMA_Access_Power_Control_LG_Enhanced: p.setSummary("LG Enhanced Algorithm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_POWER_RXPWR_HI 
	private void initpwr_RXAGCThresholdHi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("initpwr_RXAGCThresholdHi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_RXPWR_HI);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_RXPWR_HI string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_RXPWR_HI int = " + setting);
			
			switch (setting) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_POWER_RXPWR_LO 
	private void initpwr_RXAGCThresholdLo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("initpwr_RXAGCThresholdLo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_RXPWR_LO);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_RXPWR_LO string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_RXPWR_LO int = " + setting);
			
			switch (setting) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_POWER_ECIO_HI 
	private void initpwr_ECIOThresholdHi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("initpwr_ECIOThresholdHi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_ECIO_HI);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_ECIO_HI string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_ECIO_HI int = " + setting);
			
			switch (setting) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_POWER_ECIO_LO 
	private void initpwr_ECIOThresholdLo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("initpwr_ECIOThresholdLo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_ECIO_LO);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_ECIO_LO string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_POWER_ECIO_LO int = " + setting);
			
			switch (setting) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
    
	//CMD_1XIA_AP_PLUS_OFFSET 
	private void initpwr_APplusoffset() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("initpwr_APplusoffset");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_AP_PLUS_OFFSET);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_AP_PLUS_OFFSET string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_AP_PLUS_OFFSET int = " + setting);
			
			switch (setting) {		
				case APoffset_0dB: p.setSummary("0dB"); break;	
				case APoffset_1dB: p.setSummary("+1dB"); break;
				case APoffset_2dB: p.setSummary("+2dB"); break;
				case APoffset_3dB: p.setSummary("+3dB"); break;
				case APoffset_4dB: p.setSummary("+4dB"); break;
				case APoffset_5dB: p.setSummary("+5dB"); break;
				case APoffset_6dB: p.setSummary("+6dB"); break;
				case APoffset_7dB: p.setSummary("+7dB"); break;
				case APoffset_8dB: p.setSummary("+8dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_AP_MINUS_OFFSET 
	private void initpwr_APminusoffset() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("initpwr_APminusoffset");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_AP_MINUS_OFFSET);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_AP_MINUS_OFFSET string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_AP_MINUS_OFFSET int = " + setting);
			
			switch (setting) {		
				case APoffset_0dB: p.setSummary("0dB"); break;	
				case APoffset_1dB: p.setSummary("-1dB"); break;
				case APoffset_2dB: p.setSummary("-2dB"); break;
				case APoffset_3dB: p.setSummary("-3dB"); break;
				case APoffset_4dB: p.setSummary("-4dB"); break;
				case APoffset_5dB: p.setSummary("-5dB"); break;
				case APoffset_6dB: p.setSummary("-6dB"); break;
				case APoffset_7dB: p.setSummary("-7dB"); break;
				case APoffset_8dB: p.setSummary("-8dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	//Delay control
	//CMD_1XIA_DELAY_CONTROL 
	private void AccessDelayControl() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("AccessDelayControl");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_DELAY_CONTROL);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_1XIA_DELAY_CONTROL string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_DELAY_CONTROL int = " + setting);
			
			switch (setting) {		
				case CDMA_Access_Delay_Control_QCT_Default: p.setSummary("QCT Default"); break;						
				case CDMA_Access_Delay_Control_LG_Enhanced: p.setSummary("LG Enhanced Algorithm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_DELAY_RXPWR_HI 
	private void probedealy_RXAGCThresholdHi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("probedealy_RXAGCThresholdHi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_RXPWR_HI);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_RXPWR_HI string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_RXPWR_HI int = " + setting);
			
			switch (setting) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_DELAY_RXPWR_LO 
	private void probedealy_RXAGCThresholdLo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("probedealy_RXAGCThresholdLo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_RXPWR_LO);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_RXPWR_LO string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_RXPWR_LO int = " + setting);
			
			switch (setting) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_DELAY_ECIO_HI 
	private void probedealy_ECIOThresholdHi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("probedealy_ECIOThresholdHi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_ECIO_HI);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_ECIO_HI string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_ECIO_HI int = " + setting);
			
			switch (setting) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_ACCESS_DELAY_ECIO_LO 
	private void probedealy_ECIOThresholdLo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("probedealy_ECIOThresholdLo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_ECIO_LO);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_ECIO_LO string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_ACCESS_DELAY_ECIO_LO int = " + setting);
			
			switch (setting) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	
	//CMD_1XIA_SR_DELAY_CONTROL 
	private void SRDelayControl() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("SRDelayControl");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_CONTROL);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_CONTROL string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_CONTROL int = " + setting);
			
			switch (setting) {		
				case CDMA_SR_Delay_Control_QCT_Default: p.setSummary("QCT Default"); break;						
				case CDMA_SR_Delay_Control_LG_Enhanced: p.setSummary("LG Enhanced Algorithm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_SR_DELAY_RXPWR_HI 
	private void srperiod_RXAGCThresholdHi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("srperiod_RXAGCThresholdHi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_RXPWR_HI);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_RXPWR_HI string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_RXPWR_HI int = " + setting);
			
			switch (setting) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_SR_DELAY_RXPWR_LO 
	private void srperiod_RXAGCThresholdLo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("srperiod_RXAGCThresholdLo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_RXPWR_LO);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_RXPWR_LO string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_RXPWR_LO int = " + setting);
			
			switch (setting) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_SR_DELAY_ECIO_HI 
	private void srperiod_ECIOThresholdHi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("srperiod_ECIOThresholdHi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_ECIO_HI);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_ECIO_HI string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_ECIO_HI int = " + setting);
			
			switch (setting) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_SR_DELAY_ECIO_LO 
	private void srperiod_ECIOThresholdLo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("srperiod_ECIOThresholdLo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_ECIO_LO);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_ECIO_LO string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_DELAY_ECIO_LO int = " + setting);
			
			switch (setting) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
    
	//CMD_1XIA_SR_PERIOD_MIN 
	private void srperiod_SRmin() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("srperiod_SRmin");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_PERIOD_MIN);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_SR_PERIOD_MIN string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_PERIOD_MIN int = " + setting);
			
			switch (setting) {		
				case SP_Period_0sec: p.setSummary("0sec"); break;	
				case SP_Period_1sec: p.setSummary("1sec"); break;
				case SP_Period_2sec: p.setSummary("2sec"); break;
				case SP_Period_3sec: p.setSummary("3sec"); break;
				case SP_Period_4sec: p.setSummary("4sec"); break;
				case SP_Period_5sec: p.setSummary("5sec"); break;
				case SP_Period_6sec: p.setSummary("6sec"); break;
				case SP_Period_7sec: p.setSummary("7sec"); break;
				case SP_Period_8sec: p.setSummary("8sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_1XIA_SR_PERIOD_MAX 
	private void srperiod_SRmax() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("srperiod_SRmax");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XIA_SR_PERIOD_MAX);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_1XIA_SR_PERIOD_MAX string = " + tmp);
			//Log.e(TAG, "CMD_1XIA_SR_PERIOD_MAX int = " + setting);
			
			switch (setting) {		
				case SP_Period_0sec: p.setSummary("0sec"); break;	
				case SP_Period_1sec: p.setSummary("1sec"); break;
				case SP_Period_2sec: p.setSummary("2sec"); break;
				case SP_Period_3sec: p.setSummary("3sec"); break;
				case SP_Period_4sec: p.setSummary("4sec"); break;
				case SP_Period_5sec: p.setSummary("5sec"); break;
				case SP_Period_6sec: p.setSummary("6sec"); break;
				case SP_Period_7sec: p.setSummary("7sec"); break;
				case SP_Period_8sec: p.setSummary("8sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}

	////////////////////////////////////////////////////////////////////////////////////
	//LA 3.2
	//Enhanced VoLTE Audio Performance Algorithm
	
	//CMD_VAP_FORCE_1X_TRANSITION 
	private void Force_1x_Transition() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_Transition");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_TRANSITION);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_TRANSITION string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_TRANSITION int = " + setting);
			
			switch (setting) {		
				case VAP_Disable: p.setSummary("0(Disable)"); break;						
				case VAP_Enable: p.setSummary("1(Enable)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_RSRP_HI  
	private void Force_1x_RSRP_Hi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRP_Hi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_HI );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRP_HI  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRP_HI  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_RSRP_LO  
	private void Force_1x_RSRP_Lo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRP_Lo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_LO );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRP_LO  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRP_LO  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}

	//CMD_VAP_FORCE_1X_RSRQ_HI  
	private void Force_1x_RSRQ_Hi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRQ_Hi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRQ_HI );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRQ_HI  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRQ_HI  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_3: p.setSummary("-3dB"); break;
				case VALUE_minus_4: p.setSummary("-4dB"); break;
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_6: p.setSummary("-6dB"); break;
				case VALUE_minus_7: p.setSummary("-7dB"); break;
				case VALUE_minus_8: p.setSummary("-8dB"); break;
				case VALUE_minus_9: p.setSummary("-9dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				case VALUE_minus_16: p.setSummary("-16dB"); break;
				case VALUE_minus_17: p.setSummary("-17dB"); break;
				case VALUE_minus_18: p.setSummary("-18dB"); break;
				case VALUE_minus_19: p.setSummary("-19dB"); break;
				case VALUE_minus_20: p.setSummary("-20dB"); break;
				case VALUE_minus_21: p.setSummary("-21dB"); break;
				case VALUE_minus_22: p.setSummary("-22dB"); break;
				case VALUE_minus_23: p.setSummary("-23dB"); break;
				case VALUE_minus_24: p.setSummary("-24dB"); break;
				case VALUE_minus_25: p.setSummary("-25dB"); break;
				case VALUE_minus_26: p.setSummary("-26dB"); break;
				case VALUE_minus_27: p.setSummary("-27dB"); break;
				case VALUE_minus_28: p.setSummary("-28dB"); break;
				case VALUE_minus_29: p.setSummary("-29dB"); break;
				case VALUE_minus_30: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_RSRQ_LO  
	private void Force_1x_RSRQ_Lo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRQ_Lo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRQ_LO );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRQ_LO  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_RSRQ_LO  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_3: p.setSummary("-3dB"); break;
				case VALUE_minus_4: p.setSummary("-4dB"); break;
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_6: p.setSummary("-6dB"); break;
				case VALUE_minus_7: p.setSummary("-7dB"); break;
				case VALUE_minus_8: p.setSummary("-8dB"); break;
				case VALUE_minus_9: p.setSummary("-9dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				case VALUE_minus_16: p.setSummary("-16dB"); break;
				case VALUE_minus_17: p.setSummary("-17dB"); break;
				case VALUE_minus_18: p.setSummary("-18dB"); break;
				case VALUE_minus_19: p.setSummary("-19dB"); break;
				case VALUE_minus_20: p.setSummary("-20dB"); break;
				case VALUE_minus_21: p.setSummary("-21dB"); break;
				case VALUE_minus_22: p.setSummary("-22dB"); break;
				case VALUE_minus_23: p.setSummary("-23dB"); break;
				case VALUE_minus_24: p.setSummary("-24dB"); break;
				case VALUE_minus_25: p.setSummary("-25dB"); break;
				case VALUE_minus_26: p.setSummary("-26dB"); break;
				case VALUE_minus_27: p.setSummary("-27dB"); break;
				case VALUE_minus_28: p.setSummary("-28dB"); break;
				case VALUE_minus_29: p.setSummary("-29dB"); break;
				case VALUE_minus_30: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}

	//CMD_VAP_FORCE_1X_CSFB_CNT  
	private void Force_1x_2CSFB_Cnt() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_2CSFB_Cnt");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CSFB_CNT );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CSFB_CNT  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CSFB_CNT  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_1: p.setSummary("1sec"); break;
				case VALUE_minus_2: p.setSummary("2sec"); break;
				case VALUE_minus_3: p.setSummary("3sec"); break;
				case VALUE_minus_4: p.setSummary("4sec"); break;
				case VALUE_minus_5: p.setSummary("5sec"); break;
				case VALUE_minus_6: p.setSummary("6sec"); break;
				case VALUE_minus_7: p.setSummary("7sec"); break;
				case VALUE_minus_8: p.setSummary("8sec"); break;
				case VALUE_minus_9: p.setSummary("9sec"); break;
				case VALUE_minus_10: p.setSummary("10sec"); break;
				case VALUE_minus_11: p.setSummary("11sec"); break;
				case VALUE_minus_12: p.setSummary("12sec"); break;
				case VALUE_minus_13: p.setSummary("13sec"); break;
				case VALUE_minus_14: p.setSummary("14sec"); break;
				case VALUE_minus_15: p.setSummary("15sec"); break;
				case VALUE_minus_16: p.setSummary("16sec"); break;
				case VALUE_minus_17: p.setSummary("17sec"); break;
				case VALUE_minus_18: p.setSummary("18sec"); break;
				case VALUE_minus_19: p.setSummary("19sec"); break;
				case VALUE_minus_20: p.setSummary("20sec"); break;
				case VALUE_minus_21: p.setSummary("21sec"); break;
				case VALUE_minus_22: p.setSummary("22sec"); break;
				case VALUE_minus_23: p.setSummary("23sec"); break;
				case VALUE_minus_24: p.setSummary("24sec"); break;
				case VALUE_minus_25: p.setSummary("25sec"); break;
				case VALUE_minus_26: p.setSummary("26sec"); break;
				case VALUE_minus_27: p.setSummary("27sec"); break;
				case VALUE_minus_28: p.setSummary("28sec"); break;
				case VALUE_minus_29: p.setSummary("29sec"); break;
				case VALUE_minus_30: p.setSummary("30sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_SRLTE_CNT  
	private void Force_1x_2SRLTE_Cnt() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_2SRLTE_Cnt");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_SRLTE_CNT );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_SRLTE_CNT  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_SRLTE_CNT  int = " + setting);
		
			switch (setting) {		
			case VALUE_minus_1: p.setSummary("1sec"); break;
			case VALUE_minus_2: p.setSummary("2sec"); break;
			case VALUE_minus_3: p.setSummary("3sec"); break;
			case VALUE_minus_4: p.setSummary("4sec"); break;
			case VALUE_minus_5: p.setSummary("5sec"); break;
			case VALUE_minus_6: p.setSummary("6sec"); break;
			case VALUE_minus_7: p.setSummary("7sec"); break;
			case VALUE_minus_8: p.setSummary("8sec"); break;
			case VALUE_minus_9: p.setSummary("9sec"); break;
			case VALUE_minus_10: p.setSummary("10sec"); break;
			case VALUE_minus_11: p.setSummary("11sec"); break;
			case VALUE_minus_12: p.setSummary("12sec"); break;
			case VALUE_minus_13: p.setSummary("13sec"); break;
			case VALUE_minus_14: p.setSummary("14sec"); break;
			case VALUE_minus_15: p.setSummary("15sec"); break;
			case VALUE_minus_16: p.setSummary("16sec"); break;
			case VALUE_minus_17: p.setSummary("17sec"); break;
			case VALUE_minus_18: p.setSummary("18sec"); break;
			case VALUE_minus_19: p.setSummary("19sec"); break;
			case VALUE_minus_20: p.setSummary("20sec"); break;
			case VALUE_minus_21: p.setSummary("21sec"); break;
			case VALUE_minus_22: p.setSummary("22sec"); break;
			case VALUE_minus_23: p.setSummary("23sec"); break;
			case VALUE_minus_24: p.setSummary("24sec"); break;
			case VALUE_minus_25: p.setSummary("25sec"); break;
			case VALUE_minus_26: p.setSummary("26sec"); break;
			case VALUE_minus_27: p.setSummary("27sec"); break;
			case VALUE_minus_28: p.setSummary("28sec"); break;
			case VALUE_minus_29: p.setSummary("29sec"); break;
			case VALUE_minus_30: p.setSummary("30sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	//CMD_VAP_FORCE_1X_CDMA_RSSI_HI  
	private void Force_1x_CDMA_RSSI_Hi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_RSSI_Hi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_RSSI_HI );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_RSSI_HI  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_RSSI_HI  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_CDMA_RSSI_LO  
	private void Force_1x_CDMA_RSSI_Lo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_RSSI_Lo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_RSSI_LO );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_RSSI_LO  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_RSSI_LO  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	//CMD_VAP_FORCE_1X_CDMA_ECIO_HI  
	private void Force_1x_CDMA_ECIO_Hi() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_ECIO_Hi");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_ECIO_HI );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_ECIO_HI  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_ECIO_HI  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				case VALUE_minus_16: p.setSummary("-16dB"); break;
				case VALUE_minus_17: p.setSummary("-17dB"); break;
				case VALUE_minus_18: p.setSummary("-18dB"); break;
				case VALUE_minus_19: p.setSummary("-19dB"); break;
				case VALUE_minus_20: p.setSummary("-20dB"); break;
				case VALUE_minus_21: p.setSummary("-21dB"); break;
				case VALUE_minus_22: p.setSummary("-22dB"); break;
				case VALUE_minus_23: p.setSummary("-23dB"); break;
				case VALUE_minus_24: p.setSummary("-24dB"); break;
				case VALUE_minus_25: p.setSummary("-25dB"); break;
				case VALUE_minus_26: p.setSummary("-26dB"); break;
				case VALUE_minus_27: p.setSummary("-27dB"); break;
				case VALUE_minus_28: p.setSummary("-28dB"); break;
				case VALUE_minus_29: p.setSummary("-29dB"); break;
				case VALUE_minus_30: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_CDMA_ECIO_LO  
	private void Force_1x_CDMA_ECIO_Lo() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_ECIO_Lo");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_ECIO_LO );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_ECIO_LO  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_ECIO_LO  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				case VALUE_minus_16: p.setSummary("-16dB"); break;
				case VALUE_minus_17: p.setSummary("-17dB"); break;
				case VALUE_minus_18: p.setSummary("-18dB"); break;
				case VALUE_minus_19: p.setSummary("-19dB"); break;
				case VALUE_minus_20: p.setSummary("-20dB"); break;
				case VALUE_minus_21: p.setSummary("-21dB"); break;
				case VALUE_minus_22: p.setSummary("-22dB"); break;
				case VALUE_minus_23: p.setSummary("-23dB"); break;
				case VALUE_minus_24: p.setSummary("-24dB"); break;
				case VALUE_minus_25: p.setSummary("-25dB"); break;
				case VALUE_minus_26: p.setSummary("-26dB"); break;
				case VALUE_minus_27: p.setSummary("-27dB"); break;
				case VALUE_minus_28: p.setSummary("-28dB"); break;
				case VALUE_minus_29: p.setSummary("-29dB"); break;
				case VALUE_minus_30: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_CDMA_RSSI_HY  
	private void Force_1x_CDMA_RSSI_Hyst() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_RSSI_Hyst");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_RSSI_HY );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_RSSI_HY  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_RSSI_HY  int = " + setting);
			
			if(setting >=256)
			{
				//+
				setting = setting - 256;
				switch (setting) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("+1dB"); break;
				case VALUE_minus_2: p.setSummary("+2dB"); break;
				case VALUE_minus_3: p.setSummary("+3dB"); break;
				case VALUE_minus_4: p.setSummary("+4dB"); break;
				case VALUE_minus_5: p.setSummary("+5dB"); break;
				case VALUE_minus_6: p.setSummary("+6dB"); break;
				case VALUE_minus_7: p.setSummary("+7dB"); break;
				case VALUE_minus_8: p.setSummary("+8dB"); break;
				case VALUE_minus_9: p.setSummary("+9dB"); break;
				case VALUE_minus_10: p.setSummary("+10dB"); break;
				case VALUE_minus_11: p.setSummary("+11dB"); break;
				case VALUE_minus_12: p.setSummary("+12dB"); break;
				case VALUE_minus_13: p.setSummary("+13dB"); break;
				case VALUE_minus_14: p.setSummary("+14dB"); break;
				case VALUE_minus_15: p.setSummary("+15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
			}
			else
			{
				//-
				switch (setting) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("-1dB"); break;
				case VALUE_minus_2: p.setSummary("-2dB"); break;
				case VALUE_minus_3: p.setSummary("-3dB"); break;
				case VALUE_minus_4: p.setSummary("-4dB"); break;
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_6: p.setSummary("-6dB"); break;
				case VALUE_minus_7: p.setSummary("-7dB"); break;
				case VALUE_minus_8: p.setSummary("-8dB"); break;
				case VALUE_minus_9: p.setSummary("-9dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
				
			}
			
		
			
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_1X_CDMA_ECIO_HY  
	private void Force_1x_CDMA_ECIO_Hyst() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_ECIO_Hyst");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_ECIO_HY );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_ECIO_HY  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_CDMA_ECIO_HY  int = " + setting);
		
			if(setting >=256)
			{
				//+
				setting = setting - 256;
				switch (setting) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("+1dB"); break;
				case VALUE_minus_2: p.setSummary("+2dB"); break;
				case VALUE_minus_3: p.setSummary("+3dB"); break;
				case VALUE_minus_4: p.setSummary("+4dB"); break;
				case VALUE_minus_5: p.setSummary("+5dB"); break;
				case VALUE_minus_6: p.setSummary("+6dB"); break;
				case VALUE_minus_7: p.setSummary("+7dB"); break;
				case VALUE_minus_8: p.setSummary("+8dB"); break;
				case VALUE_minus_9: p.setSummary("+9dB"); break;
				case VALUE_minus_10: p.setSummary("+10dB"); break;
				case VALUE_minus_11: p.setSummary("+11dB"); break;
				case VALUE_minus_12: p.setSummary("+12dB"); break;
				case VALUE_minus_13: p.setSummary("+13dB"); break;
				case VALUE_minus_14: p.setSummary("+14dB"); break;
				case VALUE_minus_15: p.setSummary("+15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
			}
			else
			{
				//-
				switch (setting) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("-1dB"); break;
				case VALUE_minus_2: p.setSummary("-2dB"); break;
				case VALUE_minus_3: p.setSummary("-3dB"); break;
				case VALUE_minus_4: p.setSummary("-4dB"); break;
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_6: p.setSummary("-6dB"); break;
				case VALUE_minus_7: p.setSummary("-7dB"); break;
				case VALUE_minus_8: p.setSummary("-8dB"); break;
				case VALUE_minus_9: p.setSummary("-9dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
				
			}
		}
		p.setValue("Unknown");
	}
	
	
	//CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER  
	private void Force_1x_Pingpong_timer_to_SRLTE() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_Pingpong_timer_to_SRLTE");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER  int = " + setting);
		
			switch (setting) {		
			    case VALUE_minus_0: p.setSummary("0sec"); break;
				case VALUE_minus_1: p.setSummary("1sec"); break;
				case VALUE_minus_2: p.setSummary("2sec"); break;
				case VALUE_minus_3: p.setSummary("3sec"); break;
				case VALUE_minus_4: p.setSummary("4sec"); break;
				case VALUE_minus_5: p.setSummary("5sec"); break;
				case VALUE_minus_6: p.setSummary("6sec"); break;
				case VALUE_minus_7: p.setSummary("7sec"); break;
				case VALUE_minus_8: p.setSummary("8sec"); break;
				case VALUE_minus_9: p.setSummary("9sec"); break;
				case VALUE_minus_10: p.setSummary("10sec"); break;
				case VALUE_minus_11: p.setSummary("11sec"); break;
				case VALUE_minus_12: p.setSummary("12sec"); break;
				case VALUE_minus_13: p.setSummary("13sec"); break;
				case VALUE_minus_14: p.setSummary("14sec"); break;
				case VALUE_minus_15: p.setSummary("15sec"); break;
				case VALUE_minus_16: p.setSummary("16sec"); break;
				case VALUE_minus_17: p.setSummary("17sec"); break;
				case VALUE_minus_18: p.setSummary("18sec"); break;
				case VALUE_minus_19: p.setSummary("19sec"); break;
				case VALUE_minus_20: p.setSummary("20sec"); break;
				case VALUE_minus_21: p.setSummary("21sec"); break;
				case VALUE_minus_22: p.setSummary("22sec"); break;
				case VALUE_minus_23: p.setSummary("23sec"); break;
				case VALUE_minus_24: p.setSummary("24sec"); break;
				case VALUE_minus_25: p.setSummary("25sec"); break;
				case VALUE_minus_26: p.setSummary("26sec"); break;
				case VALUE_minus_27: p.setSummary("27sec"); break;
				case VALUE_minus_28: p.setSummary("28sec"); break;
				case VALUE_minus_29: p.setSummary("29sec"); break;
				case VALUE_minus_30: p.setSummary("30sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	//CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL 
	private void Skip_GW_Scan_during_VoLTE_call() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Skip_GW_Scan_during_VoLTE_call");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL string = " + tmp);
			//Log.e(TAG, "CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL int = " + setting);
			
			switch (setting) {		
				case VAP_Disable: p.setSummary("0(Disable)"); break;						
				case VAP_Enable: p.setSummary("1(Enable)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL 
	private void Skip_1xDO_Scan_during_VoLTE_call() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Skip_1xDO_Scan_during_VoLTE_call");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL string = " + tmp);
			//Log.e(TAG, "CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL int = " + setting);
			
			switch (setting) {		
			case VAP_Disable: p.setSummary("0(Disable)"); break;						
			case VAP_Enable: p.setSummary("1(Enable)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	

	//CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  
	private void Unlimited_RACH_during_VoLTE_call() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Unlimited_RACH_during_VoLTE_call");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  int = " + setting);
		
			switch (setting) {		
			    case VALUE_minus_0: p.setSummary("0(Disable)"); break;
				case VALUE_minus_1: p.setSummary("1(LG Suggestion)"); break;
				case VALUE_minus_2: p.setSummary("2"); break;
				case VALUE_minus_3: p.setSummary("3"); break;
				case VALUE_minus_4: p.setSummary("4"); break;
				case VALUE_minus_5: p.setSummary("5"); break;
				case VALUE_minus_6: p.setSummary("6"); break;
				case VALUE_minus_7: p.setSummary("7"); break;
				case VALUE_minus_8: p.setSummary("8"); break;
				case VALUE_minus_9: p.setSummary("9"); break;
				case VALUE_minus_10: p.setSummary("10"); break;
				case VALUE_minus_11: p.setSummary("11"); break;
				case VALUE_minus_12: p.setSummary("12"); break;
				case VALUE_minus_13: p.setSummary("13"); break;
				case VALUE_minus_14: p.setSummary("14"); break;
				case VALUE_minus_15: p.setSummary("15"); break;
				case VALUE_minus_16: p.setSummary("16"); break;
				case VALUE_minus_17: p.setSummary("17"); break;
				case VALUE_minus_18: p.setSummary("18"); break;
				case VALUE_minus_19: p.setSummary("19"); break;
				case VALUE_minus_20: p.setSummary("20"); break;
				case VALUE_minus_21: p.setSummary("21"); break;
				case VALUE_minus_22: p.setSummary("22"); break;
				case VALUE_minus_23: p.setSummary("23"); break;
				case VALUE_minus_24: p.setSummary("24"); break;
				case VALUE_minus_25: p.setSummary("25"); break;
				case VALUE_minus_26: p.setSummary("26"); break;
				case VALUE_minus_27: p.setSummary("27"); break;
				case VALUE_minus_28: p.setSummary("28"); break;
				case VALUE_minus_29: p.setSummary("29"); break;
				case VALUE_minus_30: p.setSummary("30"); break;
				case VALUE_minus_60: p.setSummary("60"); break;
				case VALUE_minus_90: p.setSummary("90"); break;
				case VALUE_minus_120: p.setSummary("120"); break;
				case VALUE_minus_150: p.setSummary("150"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	//CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  
	private void Unlimited_RACH_Successive_Offset_during_VoLTE_call() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Unlimited_RACH_Successive_Offset_during_VoLTE_call");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  int = " + setting);
		
			switch (setting) {		
				case VALUE_minus_1: p.setSummary("1"); break;
				case VALUE_minus_2: p.setSummary("2"); break;
				case VALUE_minus_3: p.setSummary("3"); break;
				case VALUE_minus_4: p.setSummary("4"); break;
				case VALUE_minus_5: p.setSummary("5(Qualcomm)"); break;					
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL 
	private void TAU_for_VZW_only_during_VoLTE_call() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("TAU_for_VZW_only_during_VoLTE_call");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL);
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);	
			
			//Log.e(TAG, "CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL string = " + tmp);
			//Log.e(TAG, "CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL int = " + setting);
			
			switch (setting) {		
			case VAP_Disable: p.setSummary("0(Disable)"); break;						
			case VAP_Enable: p.setSummary("1(Enable)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  
	private void Force_RLF_during_VoLTE_call() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Force_RLF_during_VoLTE_call");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL );
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);	
			setting = Integer.parseInt(tmp);
			
			//Log.e(TAG, "CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  string = " + tmp);
			//Log.e(TAG, "CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  int = " + setting);
		
			switch (setting) {		
			    case VALUE_minus_0: p.setSummary("0(Disable)"); break;
				case VALUE_minus_1: p.setSummary("1sec"); break;
				case VALUE_minus_2: p.setSummary("2sec(LG Suggestion)"); break;
				case VALUE_minus_3: p.setSummary("3sec"); break;
				case VALUE_minus_4: p.setSummary("4sec"); break;
				case VALUE_minus_5: p.setSummary("5sec"); break;
				case VALUE_minus_6: p.setSummary("6sec"); break;
				case VALUE_minus_7: p.setSummary("7sec"); break;
				case VALUE_minus_8: p.setSummary("8sec"); break;
				case VALUE_minus_9: p.setSummary("9sec"); break;
				case VALUE_minus_10: p.setSummary("10sec"); break;				
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
		
		
	/* **************************************************************************************/
    /*                                                                                      */
	/*                              WRITE VALUE                                             */
    /*                                                                                      */
	/* **************************************************************************************/

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		String tmp = "1";
		boolean tmpb = false;
		int tmpi = 0;		
	

		//CMD_HVOLTE_TASK_AI_ONE
		if (key.equals("Callend_BSR_Timer")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Callend_BSR_Timer_3sec;	}		
			else if (tmp.equals("1")) {  tmpi = Callend_BSR_Timer_30sec;	}	
			else if (tmp.equals("2")) {  tmpi = Callend_BSR_Timer_60sec;	}	
			else if (tmp.equals("3")) {  tmpi = Callend_BSR_Timer_120sec;	}	
			else { tmpi = Callend_BSR_Timer_3sec; }
			
			//ex) mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IPV6_SUPPORT, Long.toString(tmpi));	
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_ONE, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_ONE, Integer.toString(tmpi));
			
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_ONE int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_ONE string = " + Integer.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Callend_BSR_Timer");			
			switch (tmpi) {		
				case Callend_BSR_Timer_3sec: p.setSummary("3sec (QCT Default)"); break;						
				case Callend_BSR_Timer_30sec: p.setSummary("30sec"); break;
				case Callend_BSR_Timer_60sec: p.setSummary("60sec (LG Suggestion)"); break;
				case Callend_BSR_Timer_120sec: p.setSummary("120sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_HVOLTE_TASK_AI_THREE
		if (key.equals("Skip_BSR_In_Calling")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Skip_BSR_In_Calling_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Skip_BSR_In_Calling_ENABLE;	}			
			else { tmpi = Skip_BSR_In_Calling_DISABLE; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE, Integer.toString(tmpi));
			
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_THREE int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_THREE string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Skip_BSR_In_Calling");			
			switch (tmpi) {		
				case Skip_BSR_In_Calling_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Skip_BSR_In_Calling_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_HVOLTE_TASK_AI_THREE_SECND
		if (key.equals("Skip_BSR_In_LTE_Lost_When_Calling")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Skip_BSR_In_LTE_Lost_When_Calling_ENABLE;	}			
			else { tmpi = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE_SECND , Integer.toString(tmpi));
			
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_THREE_SECND  int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_THREE_SECND  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Skip_BSR_In_LTE_Lost_When_Calling");			
			switch (tmpi) {		
				case Skip_BSR_In_LTE_Lost_When_Calling_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Skip_BSR_In_LTE_Lost_When_Calling_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		

		//CMD_HVOLTE_TASK_AI_FOUR
		if (key.equals("Force_to_SRLTE_when_RRCConnection_Fail")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Force_to_SRLTE_when_RRCConnection_Fail_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Force_to_SRLTE_when_RRCConnection_Fail_3counts;	}
			else if (tmp.equals("2")) {  tmpi = Force_to_SRLTE_when_RRCConnection_Fail_5counts;	}	
			else if (tmp.equals("3")) {  tmpi = Force_to_SRLTE_when_RRCConnection_Fail_10counts;	}	
			else if (tmp.equals("4")) {  tmpi = Force_to_SRLTE_when_RRCConnection_Fail_20counts;	}	
			
			else { tmpi = Force_to_SRLTE_when_RRCConnection_Fail_DISABLE; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_FOUR, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_FOUR, Integer.toString(tmpi));
			
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_FOUR int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_FOUR string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_when_RRCConnection_Fail");			
			switch (tmpi) {		
				case Force_to_SRLTE_when_RRCConnection_Fail_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Force_to_SRLTE_when_RRCConnection_Fail_3counts: p.setSummary("Enable with 3 counts (LG Suggestion)"); break;
				case Force_to_SRLTE_when_RRCConnection_Fail_5counts: p.setSummary("Enable with 5 counts"); break;
				case Force_to_SRLTE_when_RRCConnection_Fail_10counts: p.setSummary("Enable with 10 counts"); break;
				case Force_to_SRLTE_when_RRCConnection_Fail_20counts: p.setSummary("Enable with 20 counts"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }

		//CMD_HVOLTE_TASK_AI_SEVEN
		if (key.equals("Hysteresis_to_CSFB_with_Signal_Strength")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_90dB;	}
			else if (tmp.equals("2")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_95dB;	}	
			else if (tmp.equals("3")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_100dB;	}	
			else if (tmp.equals("4")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_105dB;	}	
			else if (tmp.equals("5")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_110dB;	}	
			else if (tmp.equals("6")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_115dB;	}	
			else if (tmp.equals("7")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_120dB;	}
			else if (tmp.equals("8")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_125dB;	}			
			else { tmpi = Hysteresis_to_CSFB_with_Signal_Strength_DISABLE; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_FIRST int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_FIRST string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Hysteresis_to_CSFB_with_Signal_Strength");			
			switch (tmpi) {		
				case Hysteresis_to_CSFB_with_Signal_Strength_DISABLE: p.setSummary("Disable (QCT Default)"); break;	
				case Hysteresis_to_CSFB_with_Signal_Strength_90dB: p.setSummary("Enable with -90dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_95dB: p.setSummary("Enable with -95dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_100dB: p.setSummary("Enable with -100dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_105dB: p.setSummary("Enable with -105dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_110dB: p.setSummary("Enable with -110dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_115dB: p.setSummary("Enable with -115dB (LG Suggestion)"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_120dB: p.setSummary("Enable with -120dB"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_125dB: p.setSummary("Enable with -125dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_HVOLTE_TASK_AI_SEVEN
		if (key.equals("Hysteresis_to_CSFB_with_Signal_Strength_T")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_1sec;	}		
			else if (tmp.equals("1")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_3sec;	}
			else if (tmp.equals("2")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_5sec;	}		
			else if (tmp.equals("3")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;	}		
			else if (tmp.equals("4")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_20sec;	}
			else if (tmp.equals("5")) {  tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_30sec;	}	
			else { tmpi = Hysteresis_to_CSFB_with_Signal_Strength_T_1sec; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_SECND, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_SECND, Integer.toString(tmpi));
						
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_SECND int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_SECND string = " + Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Hysteresis_to_CSFB_with_Signal_Strength_T");			
			switch (tmpi) {		
				case Hysteresis_to_CSFB_with_Signal_Strength_T_1sec: p.setSummary("1sec"); break;						
				case Hysteresis_to_CSFB_with_Signal_Strength_T_3sec: p.setSummary("3sec"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_5sec: p.setSummary("5sec"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_10sec: p.setSummary("10sec (LG Suggestion)"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_20sec: p.setSummary("20sec"); break;
				case Hysteresis_to_CSFB_with_Signal_Strength_T_30sec: p.setSummary("30sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		
		//CMD_HVOLTE_TASK_AI_SEVEN
		if (key.equals("Force_to_SRLTE_with_Signal_Strength")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_70dB;	}
			else if (tmp.equals("2")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_75dB;	}	
			else if (tmp.equals("3")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_80dB;	}	
			else if (tmp.equals("4")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_85dB;	}	
			else if (tmp.equals("5")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_90dB;	}	
			else if (tmp.equals("6")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_95dB;	}	
			else if (tmp.equals("7")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_100dB;	}
			else if (tmp.equals("8")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_105dB;	}		
			else if (tmp.equals("9")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_110dB;	}
			//else if (tmp.equals("9")) {  tmpi = Force_to_SRLTE_with_Signal_Strength_115dB;	}
			else { tmpi = Force_to_SRLTE_with_Signal_Strength_DISABLE; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_THIRD  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_THIRD  int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_THIRD  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_with_Signal_Strength");			
			switch (tmpi) {		
				case Force_to_SRLTE_with_Signal_Strength_DISABLE: p.setSummary("Disable (QCT Default)"); break;	
				case Force_to_SRLTE_with_Signal_Strength_70dB: p.setSummary("Enable with -70dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_75dB: p.setSummary("Enable with -75dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_80dB: p.setSummary("Enable with -80dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_85dB: p.setSummary("Enable with -85dB"); break;				
				case Force_to_SRLTE_with_Signal_Strength_90dB: p.setSummary("Enable with -90dB (LG Suggestion)"); break;
				case Force_to_SRLTE_with_Signal_Strength_95dB: p.setSummary("Enable with -95dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_100dB: p.setSummary("Enable with -100dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_105dB: p.setSummary("Enable with -105dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_110dB: p.setSummary("Enable with -110dB"); break;
				case Force_to_SRLTE_with_Signal_Strength_115dB: p.setSummary("Enable with -115dB"); break;
				//case Force_to_SRLTE_with_Signal_Strength_120dB: p.setSummary("Enable with -120dB"); break;
				//case Force_to_SRLTE_with_Signal_Strength_125dB: p.setSummary("Enable with -125dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_HVOLTE_TASK_AI_SEVEN
		if (key.equals("Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_ENABLE;	}
			else { tmpi = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_SECND, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FOURTH , Integer.toString(tmpi));
						
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_FOURTH  int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_SEVEN_FOURTH  string = " + Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode");			
			switch (tmpi) {		
				case Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_HVOLTE_TASK_AI_TEN
		if (key.equals("Force_to_SRLTE_until_IMS_Reg_Success")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Force_to_SRLTE_until_IMS_Reg_Success_ENABLE;	}			
			else { tmpi = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TEN, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TEN, Integer.toString(tmpi));
						
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_TEN int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_TEN string = " + Integer.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Force_to_SRLTE_until_IMS_Reg_Success");			
			switch (tmpi) {		
				case Force_to_SRLTE_until_IMS_Reg_Success_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Force_to_SRLTE_until_IMS_Reg_Success_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_HVOLTE_TASK_AI_TWELVE
		if (key.equals("Fast_1x_Idle_Transition")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Fast_1x_Idle_Transition_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Fast_1x_Idle_Transition_ENABLE;	}			
			else { tmpi = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TWELVE, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TWELVE, Integer.toString(tmpi));
						
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_TWELVE int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_AI_TWELVE string = " + Integer.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Fast_1x_Idle_Transition");			
			switch (tmpi) {		
				case Fast_1x_Idle_Transition_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Fast_1x_Idle_Transition_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_HVOLTE_TASK_IMSPREFIND_HYST
		if (key.equals("OP_Mode_Pingpong_Timer")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = OP_Mode_Pingpong_Timer_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = OP_Mode_Pingpong_Timer_10sec;	}
			else if (tmp.equals("2")) {  tmpi = OP_Mode_Pingpong_Timer_20sec;	}
			else if (tmp.equals("3")) {  tmpi = OP_Mode_Pingpong_Timer_30sec;	}
			else if (tmp.equals("4")) {  tmpi = OP_Mode_Pingpong_Timer_40sec;	}
			else if (tmp.equals("5")) {  tmpi = OP_Mode_Pingpong_Timer_50sec;	}
			else if (tmp.equals("6")) {  tmpi = OP_Mode_Pingpong_Timer_60sec;	}
			else if (tmp.equals("7")) {  tmpi = OP_Mode_Pingpong_Timer_90sec;	}
			else if (tmp.equals("8")) {  tmpi = OP_Mode_Pingpong_Timer_120sec;	}
			else if (tmp.equals("9")) {  tmpi = OP_Mode_Pingpong_Timer_180sec;	}			
			else { tmpi = OP_Mode_Pingpong_Timer_DISABLE; }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_SECND, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_IMSPREFIND_HYST, Integer.toString(tmpi));
						
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_IMSPREFIND_HYST int = " + tmpi);
			//Log.e(TAG, "Write CMD_HVOLTE_TASK_IMSPREFIND_HYST string = " + Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("OP_Mode_Pingpong_Timer");			
			switch (tmpi) {		
				case OP_Mode_Pingpong_Timer_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case OP_Mode_Pingpong_Timer_10sec: p.setSummary("Enable with 10secs"); break;
				case OP_Mode_Pingpong_Timer_20sec: p.setSummary("Enable with 20secs"); break;
				case OP_Mode_Pingpong_Timer_30sec: p.setSummary("Enable with 30secs"); break;
				case OP_Mode_Pingpong_Timer_40sec: p.setSummary("Enable with 40secs"); break;
				case OP_Mode_Pingpong_Timer_50sec: p.setSummary("Enable with 50secs"); break;
				case OP_Mode_Pingpong_Timer_60sec: p.setSummary("Enable with 60secs"); break;
				case OP_Mode_Pingpong_Timer_90sec: p.setSummary("Enable with 90secs"); break;
				case OP_Mode_Pingpong_Timer_120sec: p.setSummary("Enable with 120secs"); break;
				case OP_Mode_Pingpong_Timer_180sec: p.setSummary("Enable with 180secs"); break;				
				
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		/////////////////////////////////////////////
		//add LA3.0
		//LTE conn%
		
		//LTEconn% task
		int BACKOFF_PLMN = 0;
		int THROTTLE_BSR = 0;
		int KICKOFF_BSR = 0;
		int ACQDB_SCAN = 0;
		int HPLMN_SCAN = 0;
		int SKIP_GW_DURIING_VOLTE_RLF = 0;
		int QCT_D2LREDIR = 0;
		
		//CMD_HVOLTE_TASK_AI_FOUR
		if (key.equals("RACH_Failure_Count_to_Backoff_PLMN")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  tmpi = RACH_Failure_Count_to_Backoff_PLMN_1;	}		
			else if (tmp.equals("2")) {  tmpi = RACH_Failure_Count_to_Backoff_PLMN_2;	}
			else if (tmp.equals("3")) {  tmpi = RACH_Failure_Count_to_Backoff_PLMN_3;	}	
			else if (tmp.equals("4")) {  tmpi = RACH_Failure_Count_to_Backoff_PLMN_4;	}	
			else if (tmp.equals("5")) {  tmpi = RACH_Failure_Count_to_Backoff_PLMN_5;	}				
			else { tmpi = RACH_Failure_Count_to_Backoff_PLMN_2; }			

			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_RACH_FAILURE_COUNT_TO_BACKOFF_PLMN, Integer.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("RACH_Failure_Count_to_Backoff_PLMN");			
			switch (tmpi) {		
				case RACH_Failure_Count_to_Backoff_PLMN_1: p.setSummary("1(3) (QCT Default)"); break;						
				case RACH_Failure_Count_to_Backoff_PLMN_2: p.setSummary("2(6) (LG Suggestion)"); break;
				case RACH_Failure_Count_to_Backoff_PLMN_3: p.setSummary("3(9)"); break;
				case RACH_Failure_Count_to_Backoff_PLMN_4: p.setSummary("4(12)"); break;
				case RACH_Failure_Count_to_Backoff_PLMN_5: p.setSummary("5(15)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_THROTTLED_BSR_TIMER
		if (key.equals("Throttled_BSR_Timer")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Throttled_BSR_Timer_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Throttled_BSR_Timer_ENABLE;	}			
			else { tmpi = Throttled_BSR_Timer_ENABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_THROTTLED_BSR_TIMER, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Throttled_BSR_Timer");			
			switch (tmpi) {		
				case Throttled_BSR_Timer_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Throttled_BSR_Timer_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_KICKOFF_BSR_SCAN
		if (key.equals("Kickoff_BSR_Scan")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Kickoff_BSR_Scan_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Kickoff_BSR_Scan_ENABLE;	}			
			else { tmpi = Kickoff_BSR_Scan_ENABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_KICKOFF_BSR_SCAN, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Kickoff_BSR_Scan");			
			switch (tmpi) {		
				case Kickoff_BSR_Scan_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Kickoff_BSR_Scan_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_ACQ_DB_SCAN_SCOPE  
		if (key.equals("ACQ_DB_Scan_Scope")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ACQ_DB_Scan_Scope_0;	}		
			else if (tmp.equals("1")) {  tmpi = ACQ_DB_Scan_Scope_1;	}
			else if (tmp.equals("2")) {  tmpi = ACQ_DB_Scan_Scope_2;	}
			else if (tmp.equals("3")) {  tmpi = ACQ_DB_Scan_Scope_3;	}
			else if (tmp.equals("4")) {  tmpi = ACQ_DB_Scan_Scope_4;	}
			else if (tmp.equals("5")) {  tmpi = ACQ_DB_Scan_Scope_5;	}
			else if (tmp.equals("6")) {  tmpi = ACQ_DB_Scan_Scope_6;	}
			else if (tmp.equals("7")) {  tmpi = ACQ_DB_Scan_Scope_7;	}
			else if (tmp.equals("8")) {  tmpi = ACQ_DB_Scan_Scope_8;	}
			else if (tmp.equals("9")) {  tmpi = ACQ_DB_Scan_Scope_9;	}
			else if (tmp.equals("10")) {  tmpi = ACQ_DB_Scan_Scope_10;	}						
			else { tmpi = ACQ_DB_Scan_Scope_0; }			

			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_N_ACQ_DB_SCAN_SCOPE, Integer.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("ACQ_DB_Scan_Scope");			
			switch (tmpi) {		
				case ACQ_DB_Scan_Scope_0: p.setSummary("Disable (QCT Default)"); break;						
				case ACQ_DB_Scan_Scope_1: p.setSummary("1"); break;
				case ACQ_DB_Scan_Scope_2: p.setSummary("2"); break;
				case ACQ_DB_Scan_Scope_3: p.setSummary("3"); break;
				case ACQ_DB_Scan_Scope_4: p.setSummary("4"); break;
				case ACQ_DB_Scan_Scope_5: p.setSummary("5 (LG Suggestion)"); break;
				case ACQ_DB_Scan_Scope_6: p.setSummary("6"); break;
				case ACQ_DB_Scan_Scope_7: p.setSummary("7"); break;
				case ACQ_DB_Scan_Scope_8: p.setSummary("8"); break;
				case ACQ_DB_Scan_Scope_9: p.setSummary("9"); break;
				case ACQ_DB_Scan_Scope_10: p.setSummary("10"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_HPLMN_SCAN_SCOPE  
		if (key.equals("HPLMN_Scan_Scope")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = HPLMN_Scan_Scope_0;	}		
			else if (tmp.equals("1")) {  tmpi = HPLMN_Scan_Scope_1;	}
			else if (tmp.equals("2")) {  tmpi = HPLMN_Scan_Scope_2;	}
			else if (tmp.equals("3")) {  tmpi = HPLMN_Scan_Scope_3;	}
			else if (tmp.equals("4")) {  tmpi = HPLMN_Scan_Scope_4;	}
			else if (tmp.equals("5")) {  tmpi = HPLMN_Scan_Scope_5;	}
			else if (tmp.equals("6")) {  tmpi = HPLMN_Scan_Scope_6;	}
			else if (tmp.equals("7")) {  tmpi = HPLMN_Scan_Scope_7;	}
			else if (tmp.equals("8")) {  tmpi = HPLMN_Scan_Scope_8;	}
			else if (tmp.equals("9")) {  tmpi = HPLMN_Scan_Scope_9;	}
			else if (tmp.equals("10")) {  tmpi = HPLMN_Scan_Scope_10;	}						
			else { tmpi = ACQ_DB_Scan_Scope_0; }			

			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_N_HPLMN_SCAN_SCOPE, Integer.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("HPLMN_Scan_Scope");			
			switch (tmpi) {		
				case HPLMN_Scan_Scope_0: p.setSummary("Disable (QCT Default)"); break;						
				case HPLMN_Scan_Scope_1: p.setSummary("1 (LG Suggestion)"); break;
				case HPLMN_Scan_Scope_2: p.setSummary("2"); break;
				case HPLMN_Scan_Scope_3: p.setSummary("3"); break;
				case HPLMN_Scan_Scope_4: p.setSummary("4"); break;
				case HPLMN_Scan_Scope_5: p.setSummary("5"); break;
				case HPLMN_Scan_Scope_6: p.setSummary("6"); break;
				case HPLMN_Scan_Scope_7: p.setSummary("7"); break;
				case HPLMN_Scan_Scope_8: p.setSummary("8"); break;
				case HPLMN_Scan_Scope_9: p.setSummary("9"); break;
				case HPLMN_Scan_Scope_10: p.setSummary("10"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }		
		
		
		//CMD_SKIP_GW_SCAN_DURING_RLF
		if (key.equals("Skip_GW_Scan_During_VoLTE_RLF")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = Skip_GW_Scan_During_VoLTE_RLF_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = Skip_GW_Scan_During_VoLTE_RLF_ENABLE;	}			
			else { tmpi = Skip_GW_Scan_During_VoLTE_RLF_ENABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SKIP_GW_SCAN_DURING_RLF, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Skip_GW_Scan_During_VoLTE_RLF");			
			switch (tmpi) {		
				case Skip_GW_Scan_During_VoLTE_RLF_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case Skip_GW_Scan_During_VoLTE_RLF_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
						
		
		//CMD_UE_INIT_D2LREDIR_DISABLE
		if (key.equals("D2LREDIR")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  tmpi = D2LREDIR_DISABLE;	}		
			else if (tmp.equals("0")) {  tmpi = D2LREDIR_ENABLE;	}			
			else { tmpi = D2LREDIR_ENABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_UE_INIT_D2LREDIR_DISABLE, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("D2LREDIR");			
			switch (tmpi) {		
				case D2LREDIR_DISABLE: p.setSummary("Disable (QCT Default)"); break;						
				case D2LREDIR_ENABLE: p.setSummary("Enable (LG Suggestion)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		///////////////////////////////////////////
		// LA 3.1
		// Enhanced CDMA Access Algorithm
		
		//CMD_1XIA_POWER_CONTROL
		if (key.equals("AccessPowerControl")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = CDMA_Access_Power_Control_QCT_Default;	}		
			else if (tmp.equals("1")) {  tmpi = CDMA_Access_Power_Control_LG_Default;	}		
			else if (tmp.equals("2")) {  tmpi = CDMA_Access_Power_Control_LG_Enhanced;	}		
			else { tmpi = CDMA_Access_Power_Control_QCT_Default; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_POWER_CONTROL, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("AccessPowerControl");			
			switch (tmpi) {		
				case CDMA_Access_Power_Control_QCT_Default: p.setSummary("QCT Default"); break;						
				case CDMA_Access_Power_Control_LG_Default: p.setSummary("LG Default (3dB up)"); break;
				case CDMA_Access_Power_Control_LG_Enhanced: p.setSummary("LG Enhanced Algorithm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_1XIA_ACCESS_POWER_RXPWR_HI
		if (key.equals("initpwr_RXAGCThresholdHi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = RXAGCThreshold_60dBm;	}		
			else if (tmp.equals("1")) {  tmpi = RXAGCThreshold_90dBm;	}
			else if (tmp.equals("2")) {  tmpi = RXAGCThreshold_95dBm;	}	
			else if (tmp.equals("3")) {  tmpi = RXAGCThreshold_100dBm;	}	
			else if (tmp.equals("4")) {  tmpi = RXAGCThreshold_105dBm;	}	
			else if (tmp.equals("5")) {  tmpi = RXAGCThreshold_110dBm;	}	
			else if (tmp.equals("6")) {  tmpi = RXAGCThreshold_115dBm;	}	
			else if (tmp.equals("7")) {  tmpi = RXAGCThreshold_120dBm;	}
			else if (tmp.equals("8")) {  tmpi = RXAGCThreshold_125dBm;	}				
			else { tmpi = RXAGCThreshold_60dBm; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_RXPWR_HI  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_RXPWR_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_RXPWR_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("initpwr_RXAGCThresholdHi");			
			switch (tmpi) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;				
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_ACCESS_POWER_RXPWR_LO
		if (key.equals("initpwr_RXAGCThresholdLo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = RXAGCThreshold_60dBm;	}		
			else if (tmp.equals("1")) {  tmpi = RXAGCThreshold_90dBm;	}
			else if (tmp.equals("2")) {  tmpi = RXAGCThreshold_95dBm;	}	
			else if (tmp.equals("3")) {  tmpi = RXAGCThreshold_100dBm;	}	
			else if (tmp.equals("4")) {  tmpi = RXAGCThreshold_105dBm;	}	
			else if (tmp.equals("5")) {  tmpi = RXAGCThreshold_110dBm;	}	
			else if (tmp.equals("6")) {  tmpi = RXAGCThreshold_115dBm;	}	
			else if (tmp.equals("7")) {  tmpi = RXAGCThreshold_120dBm;	}
			else if (tmp.equals("8")) {  tmpi = RXAGCThreshold_125dBm;	}				
			else { tmpi = RXAGCThreshold_60dBm; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_RXPWR_LO  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_RXPWR_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_RXPWR_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("initpwr_RXAGCThresholdLo");			
			switch (tmpi) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;				
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_ACCESS_POWER_ECIO_HI
		if (key.equals("initpwr_ECIOThresholdHi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ECIOThreshold_5dB;	}		
			else if (tmp.equals("1")) {  tmpi = ECIOThreshold_10dB;	}
			else if (tmp.equals("2")) {  tmpi = ECIOThreshold_11dB;	}	
			else if (tmp.equals("3")) {  tmpi = ECIOThreshold_12dB;	}	
			else if (tmp.equals("4")) {  tmpi = ECIOThreshold_13dB;	}	
			else if (tmp.equals("5")) {  tmpi = ECIOThreshold_14dB;	}	
			else if (tmp.equals("6")) {  tmpi = ECIOThreshold_15dB;	}	
			else if (tmp.equals("7")) {  tmpi = ECIOThreshold_16dB;	}
			else if (tmp.equals("8")) {  tmpi = ECIOThreshold_17dB;	}		
			else if (tmp.equals("9")) {  tmpi = ECIOThreshold_18dB;	}		
			else if (tmp.equals("10")) {  tmpi = ECIOThreshold_19dB;	}		
			else if (tmp.equals("11")) {  tmpi = ECIOThreshold_20dB;	}		
			else if (tmp.equals("12")) {  tmpi = ECIOThreshold_25dB;	}		
			else if (tmp.equals("13")) {  tmpi = ECIOThreshold_30dB;	}		
			else { tmpi = ECIOThreshold_5dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_ECIO_HI  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_ECIO_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_ECIO_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("initpwr_ECIOThresholdHi");			
			switch (tmpi) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }

		//CMD_1XIA_ACCESS_POWER_ECIO_LO
		if (key.equals("initpwr_ECIOThresholdLo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ECIOThreshold_5dB;	}		
			else if (tmp.equals("1")) {  tmpi = ECIOThreshold_10dB;	}
			else if (tmp.equals("2")) {  tmpi = ECIOThreshold_11dB;	}	
			else if (tmp.equals("3")) {  tmpi = ECIOThreshold_12dB;	}	
			else if (tmp.equals("4")) {  tmpi = ECIOThreshold_13dB;	}	
			else if (tmp.equals("5")) {  tmpi = ECIOThreshold_14dB;	}	
			else if (tmp.equals("6")) {  tmpi = ECIOThreshold_15dB;	}	
			else if (tmp.equals("7")) {  tmpi = ECIOThreshold_16dB;	}
			else if (tmp.equals("8")) {  tmpi = ECIOThreshold_17dB;	}		
			else if (tmp.equals("9")) {  tmpi = ECIOThreshold_18dB;	}		
			else if (tmp.equals("10")) {  tmpi = ECIOThreshold_19dB;	}		
			else if (tmp.equals("11")) {  tmpi = ECIOThreshold_20dB;	}		
			else if (tmp.equals("12")) {  tmpi = ECIOThreshold_25dB;	}		
			else if (tmp.equals("13")) {  tmpi = ECIOThreshold_30dB;	}		
			else { tmpi = ECIOThreshold_5dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_POWER_ECIO_LO  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_ECIO_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_POWER_ECIO_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("initpwr_ECIOThresholdLo");			
			switch (tmpi) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }		

		//CMD_1XIA_AP_PLUS_OFFSET
		if (key.equals("initpwr_APplusoffset")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = APoffset_0dB;	}		
			else if (tmp.equals("1")) {  tmpi = APoffset_1dB;	}
			else if (tmp.equals("2")) {  tmpi = APoffset_2dB;	}	
			else if (tmp.equals("3")) {  tmpi = APoffset_3dB;	}	
			else if (tmp.equals("4")) {  tmpi = APoffset_4dB;	}	
			else if (tmp.equals("5")) {  tmpi = APoffset_5dB;	}	
			else if (tmp.equals("6")) {  tmpi = APoffset_6dB;	}	
			else if (tmp.equals("7")) {  tmpi = APoffset_7dB;	}
			else if (tmp.equals("8")) {  tmpi = APoffset_8dB;	}		
			else { tmpi = APoffset_0dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_AP_PLUS_OFFSET  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_AP_PLUS_OFFSET  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_AP_PLUS_OFFSET  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("initpwr_APplusoffset");			
			switch (tmpi) {		
				case APoffset_0dB: p.setSummary("0dB"); break;	
				case APoffset_1dB: p.setSummary("+1dB"); break;
				case APoffset_2dB: p.setSummary("+2dB"); break;
				case APoffset_3dB: p.setSummary("+3dB"); break;
				case APoffset_4dB: p.setSummary("+4dB"); break;
				case APoffset_5dB: p.setSummary("+5dB"); break;
				case APoffset_6dB: p.setSummary("+6dB"); break;
				case APoffset_7dB: p.setSummary("+7dB"); break;
				case APoffset_8dB: p.setSummary("+8dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_AP_MINUS_OFFSET
		if (key.equals("initpwr_APminusoffset")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = APoffset_0dB;	}		
			else if (tmp.equals("1")) {  tmpi = APoffset_1dB;	}
			else if (tmp.equals("2")) {  tmpi = APoffset_2dB;	}	
			else if (tmp.equals("3")) {  tmpi = APoffset_3dB;	}	
			else if (tmp.equals("4")) {  tmpi = APoffset_4dB;	}	
			else if (tmp.equals("5")) {  tmpi = APoffset_5dB;	}	
			else if (tmp.equals("6")) {  tmpi = APoffset_6dB;	}	
			else if (tmp.equals("7")) {  tmpi = APoffset_7dB;	}
			else if (tmp.equals("8")) {  tmpi = APoffset_8dB;	}		
			else { tmpi = APoffset_0dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_AP_MINUS_OFFSET  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_AP_MINUS_OFFSET  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_AP_MINUS_OFFSET  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("initpwr_APminusoffset");			
			switch (tmpi) {		
				case APoffset_0dB: p.setSummary("0dB"); break;	
				case APoffset_1dB: p.setSummary("-1dB"); break;
				case APoffset_2dB: p.setSummary("-2dB"); break;
				case APoffset_3dB: p.setSummary("-3dB"); break;
				case APoffset_4dB: p.setSummary("-4dB"); break;
				case APoffset_5dB: p.setSummary("-5dB"); break;
				case APoffset_6dB: p.setSummary("-6dB"); break;
				case APoffset_7dB: p.setSummary("-7dB"); break;
				case APoffset_8dB: p.setSummary("-8dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//DELAY CONTROL
		//CMD_1XIA_DELAY_CONTROL
		if (key.equals("AccessDelayControl")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = CDMA_Access_Delay_Control_QCT_Default;	}		
			else if (tmp.equals("1")) {  tmpi = CDMA_Access_Delay_Control_LG_Enhanced;	}		
			else { tmpi = CDMA_Access_Delay_Control_QCT_Default; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_DELAY_CONTROL, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("AccessDelayControl");			
			switch (tmpi) {		
				case CDMA_Access_Delay_Control_QCT_Default: p.setSummary("QCT Default"); break;						
				case CDMA_Access_Delay_Control_LG_Enhanced: p.setSummary("LG Enhanced Algorithm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_1XIA_ACCESS_DELAY_RXPWR_HI
		if (key.equals("probedealy_RXAGCThresholdHi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = RXAGCThreshold_60dBm;	}		
			else if (tmp.equals("1")) {  tmpi = RXAGCThreshold_90dBm;	}
			else if (tmp.equals("2")) {  tmpi = RXAGCThreshold_95dBm;	}	
			else if (tmp.equals("3")) {  tmpi = RXAGCThreshold_100dBm;	}	
			else if (tmp.equals("4")) {  tmpi = RXAGCThreshold_105dBm;	}	
			else if (tmp.equals("5")) {  tmpi = RXAGCThreshold_110dBm;	}	
			else if (tmp.equals("6")) {  tmpi = RXAGCThreshold_115dBm;	}	
			else if (tmp.equals("7")) {  tmpi = RXAGCThreshold_120dBm;	}
			else if (tmp.equals("8")) {  tmpi = RXAGCThreshold_125dBm;	}				
			else { tmpi = RXAGCThreshold_60dBm; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_RXPWR_HI  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_RXPWR_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_RXPWR_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("probedealy_RXAGCThresholdHi");			
			switch (tmpi) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;				
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_ACCESS_DELAY_RXPWR_LO
		if (key.equals("probedealy_RXAGCThresholdLo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = RXAGCThreshold_60dBm;	}		
			else if (tmp.equals("1")) {  tmpi = RXAGCThreshold_90dBm;	}
			else if (tmp.equals("2")) {  tmpi = RXAGCThreshold_95dBm;	}	
			else if (tmp.equals("3")) {  tmpi = RXAGCThreshold_100dBm;	}	
			else if (tmp.equals("4")) {  tmpi = RXAGCThreshold_105dBm;	}	
			else if (tmp.equals("5")) {  tmpi = RXAGCThreshold_110dBm;	}	
			else if (tmp.equals("6")) {  tmpi = RXAGCThreshold_115dBm;	}	
			else if (tmp.equals("7")) {  tmpi = RXAGCThreshold_120dBm;	}
			else if (tmp.equals("8")) {  tmpi = RXAGCThreshold_125dBm;	}				
			else { tmpi = RXAGCThreshold_60dBm; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_RXPWR_LO  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_RXPWR_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_RXPWR_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("probedealy_RXAGCThresholdLo");			
			switch (tmpi) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;				
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_ACCESS_DELAY_ECIO_HI
		if (key.equals("probedealy_ECIOThresholdHi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ECIOThreshold_5dB;	}		
			else if (tmp.equals("1")) {  tmpi = ECIOThreshold_10dB;	}
			else if (tmp.equals("2")) {  tmpi = ECIOThreshold_11dB;	}	
			else if (tmp.equals("3")) {  tmpi = ECIOThreshold_12dB;	}	
			else if (tmp.equals("4")) {  tmpi = ECIOThreshold_13dB;	}	
			else if (tmp.equals("5")) {  tmpi = ECIOThreshold_14dB;	}	
			else if (tmp.equals("6")) {  tmpi = ECIOThreshold_15dB;	}	
			else if (tmp.equals("7")) {  tmpi = ECIOThreshold_16dB;	}
			else if (tmp.equals("8")) {  tmpi = ECIOThreshold_17dB;	}		
			else if (tmp.equals("9")) {  tmpi = ECIOThreshold_18dB;	}		
			else if (tmp.equals("10")) {  tmpi = ECIOThreshold_19dB;	}		
			else if (tmp.equals("11")) {  tmpi = ECIOThreshold_20dB;	}		
			else if (tmp.equals("12")) {  tmpi = ECIOThreshold_25dB;	}		
			else if (tmp.equals("13")) {  tmpi = ECIOThreshold_30dB;	}		
			else { tmpi = ECIOThreshold_5dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_ECIO_HI  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_ECIO_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_ECIO_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("probedealy_ECIOThresholdHi");			
			switch (tmpi) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }

		//CMD_1XIA_ACCESS_DELAY_ECIO_LO
		if (key.equals("probedealy_ECIOThresholdLo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ECIOThreshold_5dB;	}		
			else if (tmp.equals("1")) {  tmpi = ECIOThreshold_10dB;	}
			else if (tmp.equals("2")) {  tmpi = ECIOThreshold_11dB;	}	
			else if (tmp.equals("3")) {  tmpi = ECIOThreshold_12dB;	}	
			else if (tmp.equals("4")) {  tmpi = ECIOThreshold_13dB;	}	
			else if (tmp.equals("5")) {  tmpi = ECIOThreshold_14dB;	}	
			else if (tmp.equals("6")) {  tmpi = ECIOThreshold_15dB;	}	
			else if (tmp.equals("7")) {  tmpi = ECIOThreshold_16dB;	}
			else if (tmp.equals("8")) {  tmpi = ECIOThreshold_17dB;	}		
			else if (tmp.equals("9")) {  tmpi = ECIOThreshold_18dB;	}		
			else if (tmp.equals("10")) {  tmpi = ECIOThreshold_19dB;	}		
			else if (tmp.equals("11")) {  tmpi = ECIOThreshold_20dB;	}		
			else if (tmp.equals("12")) {  tmpi = ECIOThreshold_25dB;	}		
			else if (tmp.equals("13")) {  tmpi = ECIOThreshold_30dB;	}		
			else { tmpi = ECIOThreshold_5dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_ACCESS_DELAY_ECIO_LO  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_ECIO_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_ACCESS_DELAY_ECIO_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("probedealy_ECIOThresholdLo");			
			switch (tmpi) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }

		
		
		//SR PERIOD
		//CMD_1XIA_SR_DELAY_CONTROL
		if (key.equals("SRDelayControl")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = CDMA_SR_Delay_Control_QCT_Default;	}		
			else if (tmp.equals("1")) {  tmpi = CDMA_SR_Delay_Control_LG_Enhanced;	}		
			else { tmpi = CDMA_SR_Delay_Control_QCT_Default; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_CONTROL, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("SRDelayControl");			
			switch (tmpi) {		
				case CDMA_SR_Delay_Control_QCT_Default: p.setSummary("QCT Default"); break;						
				case CDMA_SR_Delay_Control_LG_Enhanced: p.setSummary("LG Enhanced Algorithm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_1XIA_SR_DELAY_RXPWR_HI
		if (key.equals("srperiod_RXAGCThresholdHi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = RXAGCThreshold_60dBm;	}		
			else if (tmp.equals("1")) {  tmpi = RXAGCThreshold_90dBm;	}
			else if (tmp.equals("2")) {  tmpi = RXAGCThreshold_95dBm;	}	
			else if (tmp.equals("3")) {  tmpi = RXAGCThreshold_100dBm;	}	
			else if (tmp.equals("4")) {  tmpi = RXAGCThreshold_105dBm;	}	
			else if (tmp.equals("5")) {  tmpi = RXAGCThreshold_110dBm;	}	
			else if (tmp.equals("6")) {  tmpi = RXAGCThreshold_115dBm;	}	
			else if (tmp.equals("7")) {  tmpi = RXAGCThreshold_120dBm;	}
			else if (tmp.equals("8")) {  tmpi = RXAGCThreshold_125dBm;	}				
			else { tmpi = RXAGCThreshold_60dBm; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_RXPWR_HI  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_RXPWR_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_RXPWR_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("srperiod_RXAGCThresholdHi");			
			switch (tmpi) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;				
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_SR_DELAY_RXPWR_LO
		if (key.equals("srperiod_RXAGCThresholdLo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = RXAGCThreshold_60dBm;	}		
			else if (tmp.equals("1")) {  tmpi = RXAGCThreshold_90dBm;	}
			else if (tmp.equals("2")) {  tmpi = RXAGCThreshold_95dBm;	}	
			else if (tmp.equals("3")) {  tmpi = RXAGCThreshold_100dBm;	}	
			else if (tmp.equals("4")) {  tmpi = RXAGCThreshold_105dBm;	}	
			else if (tmp.equals("5")) {  tmpi = RXAGCThreshold_110dBm;	}	
			else if (tmp.equals("6")) {  tmpi = RXAGCThreshold_115dBm;	}	
			else if (tmp.equals("7")) {  tmpi = RXAGCThreshold_120dBm;	}
			else if (tmp.equals("8")) {  tmpi = RXAGCThreshold_125dBm;	}				
			else { tmpi = RXAGCThreshold_60dBm; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_RXPWR_LO  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_RXPWR_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_RXPWR_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("srperiod_RXAGCThresholdLo");			
			switch (tmpi) {		
				case RXAGCThreshold_60dBm: p.setSummary("-60dBm"); break;	
				case RXAGCThreshold_90dBm: p.setSummary("-90dBm"); break;
				case RXAGCThreshold_95dBm: p.setSummary("-95dBm"); break;
				case RXAGCThreshold_100dBm: p.setSummary("-100dBm"); break;
				case RXAGCThreshold_105dBm: p.setSummary("-105dBm"); break;				
				case RXAGCThreshold_110dBm: p.setSummary("-110dBm"); break;
				case RXAGCThreshold_115dBm: p.setSummary("-115dBm"); break;
				case RXAGCThreshold_120dBm: p.setSummary("-120dBm"); break;
				case RXAGCThreshold_125dBm: p.setSummary("-125dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_SR_DELAY_ECIO_HI
		if (key.equals("srperiod_ECIOThresholdHi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ECIOThreshold_5dB;	}		
			else if (tmp.equals("1")) {  tmpi = ECIOThreshold_10dB;	}
			else if (tmp.equals("2")) {  tmpi = ECIOThreshold_11dB;	}	
			else if (tmp.equals("3")) {  tmpi = ECIOThreshold_12dB;	}	
			else if (tmp.equals("4")) {  tmpi = ECIOThreshold_13dB;	}	
			else if (tmp.equals("5")) {  tmpi = ECIOThreshold_14dB;	}	
			else if (tmp.equals("6")) {  tmpi = ECIOThreshold_15dB;	}	
			else if (tmp.equals("7")) {  tmpi = ECIOThreshold_16dB;	}
			else if (tmp.equals("8")) {  tmpi = ECIOThreshold_17dB;	}		
			else if (tmp.equals("9")) {  tmpi = ECIOThreshold_18dB;	}		
			else if (tmp.equals("10")) {  tmpi = ECIOThreshold_19dB;	}		
			else if (tmp.equals("11")) {  tmpi = ECIOThreshold_20dB;	}		
			else if (tmp.equals("12")) {  tmpi = ECIOThreshold_25dB;	}		
			else if (tmp.equals("13")) {  tmpi = ECIOThreshold_30dB;	}		
			else { tmpi = ECIOThreshold_5dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_ECIO_HI  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_ECIO_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_ECIO_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("srperiod_ECIOThresholdHi");			
			switch (tmpi) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }

		//CMD_1XIA_SR_DELAY_ECIO_LO
		if (key.equals("srperiod_ECIOThresholdLo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = ECIOThreshold_5dB;	}		
			else if (tmp.equals("1")) {  tmpi = ECIOThreshold_10dB;	}
			else if (tmp.equals("2")) {  tmpi = ECIOThreshold_11dB;	}	
			else if (tmp.equals("3")) {  tmpi = ECIOThreshold_12dB;	}	
			else if (tmp.equals("4")) {  tmpi = ECIOThreshold_13dB;	}	
			else if (tmp.equals("5")) {  tmpi = ECIOThreshold_14dB;	}	
			else if (tmp.equals("6")) {  tmpi = ECIOThreshold_15dB;	}	
			else if (tmp.equals("7")) {  tmpi = ECIOThreshold_16dB;	}
			else if (tmp.equals("8")) {  tmpi = ECIOThreshold_17dB;	}		
			else if (tmp.equals("9")) {  tmpi = ECIOThreshold_18dB;	}		
			else if (tmp.equals("10")) {  tmpi = ECIOThreshold_19dB;	}		
			else if (tmp.equals("11")) {  tmpi = ECIOThreshold_20dB;	}		
			else if (tmp.equals("12")) {  tmpi = ECIOThreshold_25dB;	}		
			else if (tmp.equals("13")) {  tmpi = ECIOThreshold_30dB;	}		
			else { tmpi = ECIOThreshold_5dB; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_ECIO_LO  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_ECIO_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_SR_DELAY_ECIO_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("srperiod_ECIOThresholdLo");			
			switch (tmpi) {		
				case ECIOThreshold_5dB: p.setSummary("-5dB"); break;	
				case ECIOThreshold_10dB: p.setSummary("-10dB"); break;
				case ECIOThreshold_11dB: p.setSummary("-11dB"); break;
				case ECIOThreshold_12dB: p.setSummary("-12dB"); break;
				case ECIOThreshold_13dB: p.setSummary("-13dB"); break;
				case ECIOThreshold_14dB: p.setSummary("-14dB"); break;
				case ECIOThreshold_15dB: p.setSummary("-15dB"); break;
				case ECIOThreshold_16dB: p.setSummary("-16dB"); break;
				case ECIOThreshold_17dB: p.setSummary("-17dB"); break;
				case ECIOThreshold_18dB: p.setSummary("-18dB"); break;
				case ECIOThreshold_19dB: p.setSummary("-19dB"); break;
				case ECIOThreshold_20dB: p.setSummary("-20dB"); break;
				case ECIOThreshold_25dB: p.setSummary("-25dB"); break;
				case ECIOThreshold_30dB: p.setSummary("-30dB"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_SR_PERIOD_MIN
		if (key.equals("srperiod_SRmin")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = SP_Period_0sec;	}		
			else if (tmp.equals("1")) {  tmpi = SP_Period_1sec;	}
			else if (tmp.equals("2")) {  tmpi = SP_Period_2sec;	}	
			else if (tmp.equals("3")) {  tmpi = SP_Period_3sec;	}	
			else if (tmp.equals("4")) {  tmpi = SP_Period_4sec;	}	
			else if (tmp.equals("5")) {  tmpi = SP_Period_5sec;	}	
			else if (tmp.equals("6")) {  tmpi = SP_Period_6sec;	}	
			else if (tmp.equals("7")) {  tmpi = SP_Period_7sec;	}
			else if (tmp.equals("8")) {  tmpi = SP_Period_8sec;	}		
			else { tmpi = SP_Period_0sec; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_PERIOD_MIN  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_SR_PERIOD_MIN  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_SR_PERIOD_MIN  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("srperiod_SRmin");			
			switch (tmpi) {		
				case SP_Period_0sec: p.setSummary("0sec"); break;	
				case SP_Period_1sec: p.setSummary("1sec"); break;
				case SP_Period_2sec: p.setSummary("2sec"); break;
				case SP_Period_3sec: p.setSummary("3sec"); break;
				case SP_Period_4sec: p.setSummary("4sec"); break;
				case SP_Period_5sec: p.setSummary("5sec"); break;
				case SP_Period_6sec: p.setSummary("6sec"); break;
				case SP_Period_7sec: p.setSummary("7sec"); break;
				case SP_Period_8sec: p.setSummary("8sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_1XIA_SR_PERIOD_MAX
		if (key.equals("srperiod_SRmax")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = SP_Period_0sec;	}		
			else if (tmp.equals("1")) {  tmpi = SP_Period_1sec;	}
			else if (tmp.equals("2")) {  tmpi = SP_Period_2sec;	}	
			else if (tmp.equals("3")) {  tmpi = SP_Period_3sec;	}	
			else if (tmp.equals("4")) {  tmpi = SP_Period_4sec;	}	
			else if (tmp.equals("5")) {  tmpi = SP_Period_5sec;	}	
			else if (tmp.equals("6")) {  tmpi = SP_Period_6sec;	}	
			else if (tmp.equals("7")) {  tmpi = SP_Period_7sec;	}
			else if (tmp.equals("8")) {  tmpi = SP_Period_8sec;	}		
			else { tmpi = SP_Period_0sec; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_PERIOD_MAX  , Integer.toString(tmpi));	
			
			//Log.e(TAG, "Write CMD_1XIA_SR_PERIOD_MAX  int = " + tmpi);
			//Log.e(TAG, "Write CMD_1XIA_SR_PERIOD_MAX  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("srperiod_SRmax");			
			switch (tmpi) {		
				case SP_Period_0sec: p.setSummary("0sec"); break;	
				case SP_Period_1sec: p.setSummary("1sec"); break;
				case SP_Period_2sec: p.setSummary("2sec"); break;
				case SP_Period_3sec: p.setSummary("3sec"); break;
				case SP_Period_4sec: p.setSummary("4sec"); break;
				case SP_Period_5sec: p.setSummary("5sec"); break;
				case SP_Period_6sec: p.setSummary("6sec"); break;
				case SP_Period_7sec: p.setSummary("7sec"); break;
				case SP_Period_8sec: p.setSummary("8sec"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		
		////////////////////////////////////////////////////////////////////////////////////
		//LA 3.2
		//Enhanced VoLTE Audio Performance Algorithm
		
		//CMD_VAP_FORCE_1X_TRANSITION 
		if (key.equals("Force_1x_Transition")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VAP_Disable;	}		
			else if (tmp.equals("1")) {  tmpi = VAP_Enable;	}		
			else { tmpi = VAP_Disable; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_TRANSITION, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_Transition");			
			switch (tmpi) {		
			case VAP_Disable: p.setSummary("0(Disable)"); break;						
			case VAP_Enable: p.setSummary("1(Enable)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		
		//CMD_VAP_FORCE_1X_RSRP_HI
		if (key.equals("Force_1x_RSRP_Hi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}	
			else if (tmp.equals("60")) {  tmpi = VALUE_minus_60;	}	
			else if (tmp.equals("90")) {  tmpi = VALUE_minus_90;	}
			else if (tmp.equals("95")) {  tmpi = VALUE_minus_95;	}	
			else if (tmp.equals("100")) {  tmpi = VALUE_minus_100;	}	
			else if (tmp.equals("101")) {  tmpi = VALUE_minus_101;	}
			else if (tmp.equals("102")) {  tmpi = VALUE_minus_102;	}	
			else if (tmp.equals("103")) {  tmpi = VALUE_minus_103;	}	
			else if (tmp.equals("104")) {  tmpi = VALUE_minus_104;	}	
			else if (tmp.equals("105")) {  tmpi = VALUE_minus_105;	}	
			else if (tmp.equals("106")) {  tmpi = VALUE_minus_106;	}	
			else if (tmp.equals("107")) {  tmpi = VALUE_minus_107;	}
			else if (tmp.equals("108")) {  tmpi = VALUE_minus_108;	}	
			else if (tmp.equals("109")) {  tmpi = VALUE_minus_109;	}	
			else if (tmp.equals("110")) {  tmpi = VALUE_minus_110;	}		
			else if (tmp.equals("111")) {  tmpi = VALUE_minus_111;	}
			else if (tmp.equals("112")) {  tmpi = VALUE_minus_112;	}	
			else if (tmp.equals("113")) {  tmpi = VALUE_minus_113;	}	
			else if (tmp.equals("114")) {  tmpi = VALUE_minus_114;	}	
			else if (tmp.equals("115")) {  tmpi = VALUE_minus_115;	}	
			else if (tmp.equals("116")) {  tmpi = VALUE_minus_116;	}	
			else if (tmp.equals("117")) {  tmpi = VALUE_minus_117;	}
			else if (tmp.equals("118")) {  tmpi = VALUE_minus_118;	}	
			else if (tmp.equals("119")) {  tmpi = VALUE_minus_119;	}	
			else if (tmp.equals("120")) {  tmpi = VALUE_minus_120;	}		
			else if (tmp.equals("121")) {  tmpi = VALUE_minus_121;	}
			else if (tmp.equals("122")) {  tmpi = VALUE_minus_122;	}	
			else if (tmp.equals("123")) {  tmpi = VALUE_minus_123;	}	
			else if (tmp.equals("124")) {  tmpi = VALUE_minus_124;	}	
			else if (tmp.equals("125")) {  tmpi = VALUE_minus_125;	}	
			else if (tmp.equals("126")) {  tmpi = VALUE_minus_126;	}	
			else if (tmp.equals("127")) {  tmpi = VALUE_minus_127;	}
			else if (tmp.equals("128")) {  tmpi = VALUE_minus_128;	}	
			else if (tmp.equals("129")) {  tmpi = VALUE_minus_129;	}
			else if (tmp.equals("130")) {  tmpi = VALUE_minus_130;	}
			else if (tmp.equals("135")) {  tmpi = VALUE_minus_135;	}
			else if (tmp.equals("140")) {  tmpi = VALUE_minus_140;	}
			else if (tmp.equals("145")) {  tmpi = VALUE_minus_145;	}
			else if (tmp.equals("150")) {  tmpi = VALUE_minus_150;	}
			else { tmpi = VALUE_minus_30; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_HI  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRP_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRP_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRP_Hi");			
				
			switch (tmpi) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
	   }
		
		//CMD_VAP_FORCE_1X_RSRP_LO
		if (key.equals("Force_1x_RSRP_Lo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}	
			else if (tmp.equals("60")) {  tmpi = VALUE_minus_60;	}	
			else if (tmp.equals("90")) {  tmpi = VALUE_minus_90;	}
			else if (tmp.equals("95")) {  tmpi = VALUE_minus_95;	}	
			else if (tmp.equals("100")) {  tmpi = VALUE_minus_100;	}	
			else if (tmp.equals("101")) {  tmpi = VALUE_minus_101;	}
			else if (tmp.equals("102")) {  tmpi = VALUE_minus_102;	}	
			else if (tmp.equals("103")) {  tmpi = VALUE_minus_103;	}	
			else if (tmp.equals("104")) {  tmpi = VALUE_minus_104;	}	
			else if (tmp.equals("105")) {  tmpi = VALUE_minus_105;	}	
			else if (tmp.equals("106")) {  tmpi = VALUE_minus_106;	}	
			else if (tmp.equals("107")) {  tmpi = VALUE_minus_107;	}
			else if (tmp.equals("108")) {  tmpi = VALUE_minus_108;	}	
			else if (tmp.equals("109")) {  tmpi = VALUE_minus_109;	}	
			else if (tmp.equals("110")) {  tmpi = VALUE_minus_110;	}		
			else if (tmp.equals("111")) {  tmpi = VALUE_minus_111;	}
			else if (tmp.equals("112")) {  tmpi = VALUE_minus_112;	}	
			else if (tmp.equals("113")) {  tmpi = VALUE_minus_113;	}	
			else if (tmp.equals("114")) {  tmpi = VALUE_minus_114;	}	
			else if (tmp.equals("115")) {  tmpi = VALUE_minus_115;	}	
			else if (tmp.equals("116")) {  tmpi = VALUE_minus_116;	}	
			else if (tmp.equals("117")) {  tmpi = VALUE_minus_117;	}
			else if (tmp.equals("118")) {  tmpi = VALUE_minus_118;	}	
			else if (tmp.equals("119")) {  tmpi = VALUE_minus_119;	}	
			else if (tmp.equals("120")) {  tmpi = VALUE_minus_120;	}		
			else if (tmp.equals("121")) {  tmpi = VALUE_minus_121;	}
			else if (tmp.equals("122")) {  tmpi = VALUE_minus_122;	}	
			else if (tmp.equals("123")) {  tmpi = VALUE_minus_123;	}	
			else if (tmp.equals("124")) {  tmpi = VALUE_minus_124;	}	
			else if (tmp.equals("125")) {  tmpi = VALUE_minus_125;	}	
			else if (tmp.equals("126")) {  tmpi = VALUE_minus_126;	}	
			else if (tmp.equals("127")) {  tmpi = VALUE_minus_127;	}
			else if (tmp.equals("128")) {  tmpi = VALUE_minus_128;	}	
			else if (tmp.equals("129")) {  tmpi = VALUE_minus_129;	}
			else if (tmp.equals("130")) {  tmpi = VALUE_minus_130;	}
			else if (tmp.equals("135")) {  tmpi = VALUE_minus_135;	}
			else if (tmp.equals("140")) {  tmpi = VALUE_minus_140;	}
			else if (tmp.equals("145")) {  tmpi = VALUE_minus_145;	}
			else if (tmp.equals("150")) {  tmpi = VALUE_minus_150;	}
			else { tmpi = VALUE_minus_30; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_LO  , Integer.toString(tmpi));	
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_LO  , tmp);	
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRP_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRP_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRP_Lo");			
				
			switch (tmpi) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
	    }
		
		//CMD_VAP_FORCE_1X_RSRQ_HI
		if (key.equals("Force_1x_RSRQ_Hi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_11;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_16;	}	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_17;	}
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_18;	}	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_19;	}	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}		
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_21;	}
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_22;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_23;	}	
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_24;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_26;	}	
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_27;	}
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_28;	}	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_29;	}
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRQ_HI  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRQ_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRQ_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRQ_Hi");			
				
	
			switch (tmpi) {		
			case VALUE_minus_3: p.setSummary("-3dB"); break;
			case VALUE_minus_4: p.setSummary("-4dB"); break;
			case VALUE_minus_5: p.setSummary("-5dB"); break;
			case VALUE_minus_6: p.setSummary("-6dB"); break;
			case VALUE_minus_7: p.setSummary("-7dB"); break;
			case VALUE_minus_8: p.setSummary("-8dB"); break;
			case VALUE_minus_9: p.setSummary("-9dB"); break;
			case VALUE_minus_10: p.setSummary("-10dB"); break;
			case VALUE_minus_11: p.setSummary("-11dB"); break;
			case VALUE_minus_12: p.setSummary("-12dB"); break;
			case VALUE_minus_13: p.setSummary("-13dB"); break;
			case VALUE_minus_14: p.setSummary("-14dB"); break;
			case VALUE_minus_15: p.setSummary("-15dB"); break;
			case VALUE_minus_16: p.setSummary("-16dB"); break;
			case VALUE_minus_17: p.setSummary("-17dB"); break;
			case VALUE_minus_18: p.setSummary("-18dB"); break;
			case VALUE_minus_19: p.setSummary("-19dB"); break;
			case VALUE_minus_20: p.setSummary("-20dB"); break;
			case VALUE_minus_21: p.setSummary("-21dB"); break;
			case VALUE_minus_22: p.setSummary("-22dB"); break;
			case VALUE_minus_23: p.setSummary("-23dB"); break;
			case VALUE_minus_24: p.setSummary("-24dB"); break;
			case VALUE_minus_25: p.setSummary("-25dB"); break;
			case VALUE_minus_26: p.setSummary("-26dB"); break;
			case VALUE_minus_27: p.setSummary("-27dB"); break;
			case VALUE_minus_28: p.setSummary("-28dB"); break;
			case VALUE_minus_29: p.setSummary("-29dB"); break;
			case VALUE_minus_30: p.setSummary("-30dB"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
		
		//CMD_VAP_FORCE_1X_RSRQ_LO
		if (key.equals("Force_1x_RSRQ_Lo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_11;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_16;	}	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_17;	}
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_18;	}	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_19;	}	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}		
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_21;	}
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_22;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_23;	}	
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_24;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_26;	}	
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_27;	}
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_28;	}	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_29;	}
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRQ_LO  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRQ_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_RSRQ_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_RSRQ_Lo");			
				
	
			switch (tmpi) {		
			case VALUE_minus_3: p.setSummary("-3dB"); break;
			case VALUE_minus_4: p.setSummary("-4dB"); break;
			case VALUE_minus_5: p.setSummary("-5dB"); break;
			case VALUE_minus_6: p.setSummary("-6dB"); break;
			case VALUE_minus_7: p.setSummary("-7dB"); break;
			case VALUE_minus_8: p.setSummary("-8dB"); break;
			case VALUE_minus_9: p.setSummary("-9dB"); break;
			case VALUE_minus_10: p.setSummary("-10dB"); break;
			case VALUE_minus_11: p.setSummary("-11dB"); break;
			case VALUE_minus_12: p.setSummary("-12dB"); break;
			case VALUE_minus_13: p.setSummary("-13dB"); break;
			case VALUE_minus_14: p.setSummary("-14dB"); break;
			case VALUE_minus_15: p.setSummary("-15dB"); break;
			case VALUE_minus_16: p.setSummary("-16dB"); break;
			case VALUE_minus_17: p.setSummary("-17dB"); break;
			case VALUE_minus_18: p.setSummary("-18dB"); break;
			case VALUE_minus_19: p.setSummary("-19dB"); break;
			case VALUE_minus_20: p.setSummary("-20dB"); break;
			case VALUE_minus_21: p.setSummary("-21dB"); break;
			case VALUE_minus_22: p.setSummary("-22dB"); break;
			case VALUE_minus_23: p.setSummary("-23dB"); break;
			case VALUE_minus_24: p.setSummary("-24dB"); break;
			case VALUE_minus_25: p.setSummary("-25dB"); break;
			case VALUE_minus_26: p.setSummary("-26dB"); break;
			case VALUE_minus_27: p.setSummary("-27dB"); break;
			case VALUE_minus_28: p.setSummary("-28dB"); break;
			case VALUE_minus_29: p.setSummary("-29dB"); break;
			case VALUE_minus_30: p.setSummary("-30dB"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
		
		//CMD_VAP_FORCE_1X_CSFB_CNT
		if (key.equals("Force_1x_2CSFB_Cnt")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_11;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_16;	}	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_17;	}
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_18;	}	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_19;	}	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}		
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_21;	}
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_22;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_23;	}	
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_24;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_26;	}	
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_27;	}
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_28;	}	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_29;	}
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CSFB_CNT  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CSFB_CNT  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CSFB_CNT  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_2CSFB_Cnt");			
				
	
			switch (tmpi) {		
			case VALUE_minus_1: p.setSummary("1sec"); break;
			case VALUE_minus_2: p.setSummary("2sec"); break;
			case VALUE_minus_3: p.setSummary("3sec"); break;
			case VALUE_minus_4: p.setSummary("4sec"); break;
			case VALUE_minus_5: p.setSummary("5sec"); break;
			case VALUE_minus_6: p.setSummary("6sec"); break;
			case VALUE_minus_7: p.setSummary("7sec"); break;
			case VALUE_minus_8: p.setSummary("8sec"); break;
			case VALUE_minus_9: p.setSummary("9sec"); break;
			case VALUE_minus_10: p.setSummary("10sec"); break;
			case VALUE_minus_11: p.setSummary("11sec"); break;
			case VALUE_minus_12: p.setSummary("12sec"); break;
			case VALUE_minus_13: p.setSummary("13sec"); break;
			case VALUE_minus_14: p.setSummary("14sec"); break;
			case VALUE_minus_15: p.setSummary("15sec"); break;			
			case VALUE_minus_20: p.setSummary("20sec"); break;
			case VALUE_minus_25: p.setSummary("25sec"); break;			
			case VALUE_minus_30: p.setSummary("30sec"); break;
			default: p.setSummary("Unknown Value"); break;
			}
	   }
		
		//CMD_VAP_FORCE_1X_SRLTE_CNT
		if (key.equals("Force_1x_2SRLTE_Cnt")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_11;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_16;	}	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_17;	}
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_18;	}	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_19;	}	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}		
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_21;	}
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_22;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_23;	}	
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_24;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_26;	}	
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_27;	}
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_28;	}	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_29;	}
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_SRLTE_CNT  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_SRLTE_CNT  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_SRLTE_CNT  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_2SRLTE_Cnt");			
				
	
			switch (tmpi) {		
			case VALUE_minus_1: p.setSummary("1sec"); break;
			case VALUE_minus_2: p.setSummary("2sec"); break;
			case VALUE_minus_3: p.setSummary("3sec"); break;
			case VALUE_minus_4: p.setSummary("4sec"); break;
			case VALUE_minus_5: p.setSummary("5sec"); break;
			case VALUE_minus_6: p.setSummary("6sec"); break;
			case VALUE_minus_7: p.setSummary("7sec"); break;
			case VALUE_minus_8: p.setSummary("8sec"); break;
			case VALUE_minus_9: p.setSummary("9sec"); break;
			case VALUE_minus_10: p.setSummary("10sec"); break;
			case VALUE_minus_11: p.setSummary("11sec"); break;
			case VALUE_minus_12: p.setSummary("12sec"); break;
			case VALUE_minus_13: p.setSummary("13sec"); break;
			case VALUE_minus_14: p.setSummary("14sec"); break;
			case VALUE_minus_15: p.setSummary("15sec"); break;			
			case VALUE_minus_20: p.setSummary("20sec"); break;
			case VALUE_minus_25: p.setSummary("25sec"); break;			
			case VALUE_minus_30: p.setSummary("30sec"); break;
			default: p.setSummary("Unknown Value"); break;
			}
	   }		
		
		//CMD_VAP_FORCE_1X_CDMA_RSSI_HI
		if (key.equals("Force_1x_CDMA_RSSI_Hi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}	
			else if (tmp.equals("60")) {  tmpi = VALUE_minus_60;	}	
			else if (tmp.equals("90")) {  tmpi = VALUE_minus_90;	}
			else if (tmp.equals("95")) {  tmpi = VALUE_minus_95;	}	
			else if (tmp.equals("100")) {  tmpi = VALUE_minus_100;	}	
			else if (tmp.equals("101")) {  tmpi = VALUE_minus_101;	}
			else if (tmp.equals("102")) {  tmpi = VALUE_minus_102;	}	
			else if (tmp.equals("103")) {  tmpi = VALUE_minus_103;	}	
			else if (tmp.equals("104")) {  tmpi = VALUE_minus_104;	}	
			else if (tmp.equals("105")) {  tmpi = VALUE_minus_105;	}	
			else if (tmp.equals("106")) {  tmpi = VALUE_minus_106;	}	
			else if (tmp.equals("107")) {  tmpi = VALUE_minus_107;	}
			else if (tmp.equals("108")) {  tmpi = VALUE_minus_108;	}	
			else if (tmp.equals("109")) {  tmpi = VALUE_minus_109;	}	
			else if (tmp.equals("110")) {  tmpi = VALUE_minus_110;	}		
			else if (tmp.equals("111")) {  tmpi = VALUE_minus_111;	}
			else if (tmp.equals("112")) {  tmpi = VALUE_minus_112;	}	
			else if (tmp.equals("113")) {  tmpi = VALUE_minus_113;	}	
			else if (tmp.equals("114")) {  tmpi = VALUE_minus_114;	}	
			else if (tmp.equals("115")) {  tmpi = VALUE_minus_115;	}	
			else if (tmp.equals("116")) {  tmpi = VALUE_minus_116;	}	
			else if (tmp.equals("117")) {  tmpi = VALUE_minus_117;	}
			else if (tmp.equals("118")) {  tmpi = VALUE_minus_118;	}	
			else if (tmp.equals("119")) {  tmpi = VALUE_minus_119;	}	
			else if (tmp.equals("120")) {  tmpi = VALUE_minus_120;	}		
			else if (tmp.equals("121")) {  tmpi = VALUE_minus_121;	}
			else if (tmp.equals("122")) {  tmpi = VALUE_minus_122;	}	
			else if (tmp.equals("123")) {  tmpi = VALUE_minus_123;	}	
			else if (tmp.equals("124")) {  tmpi = VALUE_minus_124;	}	
			else if (tmp.equals("125")) {  tmpi = VALUE_minus_125;	}	
			else if (tmp.equals("126")) {  tmpi = VALUE_minus_126;	}	
			else if (tmp.equals("127")) {  tmpi = VALUE_minus_127;	}
			else if (tmp.equals("128")) {  tmpi = VALUE_minus_128;	}	
			else if (tmp.equals("129")) {  tmpi = VALUE_minus_129;	}
			else if (tmp.equals("130")) {  tmpi = VALUE_minus_130;	}
			else if (tmp.equals("135")) {  tmpi = VALUE_minus_135;	}
			else if (tmp.equals("140")) {  tmpi = VALUE_minus_140;	}
			else if (tmp.equals("145")) {  tmpi = VALUE_minus_145;	}
			else if (tmp.equals("150")) {  tmpi = VALUE_minus_150;	}
			else { tmpi = VALUE_minus_30; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_RSSI_HI  , Integer.toString(tmpi));	
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_LO  , tmp);	
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_RSSI_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_RSSI_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_RSSI_Hi");			
				
			switch (tmpi) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
	    }		
		
		
		//CMD_VAP_FORCE_1X_CDMA_RSSI_LO
		if (key.equals("Force_1x_CDMA_RSSI_Lo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}	
			else if (tmp.equals("60")) {  tmpi = VALUE_minus_60;	}	
			else if (tmp.equals("90")) {  tmpi = VALUE_minus_90;	}
			else if (tmp.equals("95")) {  tmpi = VALUE_minus_95;	}	
			else if (tmp.equals("100")) {  tmpi = VALUE_minus_100;	}	
			else if (tmp.equals("101")) {  tmpi = VALUE_minus_101;	}
			else if (tmp.equals("102")) {  tmpi = VALUE_minus_102;	}	
			else if (tmp.equals("103")) {  tmpi = VALUE_minus_103;	}	
			else if (tmp.equals("104")) {  tmpi = VALUE_minus_104;	}	
			else if (tmp.equals("105")) {  tmpi = VALUE_minus_105;	}	
			else if (tmp.equals("106")) {  tmpi = VALUE_minus_106;	}	
			else if (tmp.equals("107")) {  tmpi = VALUE_minus_107;	}
			else if (tmp.equals("108")) {  tmpi = VALUE_minus_108;	}	
			else if (tmp.equals("109")) {  tmpi = VALUE_minus_109;	}	
			else if (tmp.equals("110")) {  tmpi = VALUE_minus_110;	}		
			else if (tmp.equals("111")) {  tmpi = VALUE_minus_111;	}
			else if (tmp.equals("112")) {  tmpi = VALUE_minus_112;	}	
			else if (tmp.equals("113")) {  tmpi = VALUE_minus_113;	}	
			else if (tmp.equals("114")) {  tmpi = VALUE_minus_114;	}	
			else if (tmp.equals("115")) {  tmpi = VALUE_minus_115;	}	
			else if (tmp.equals("116")) {  tmpi = VALUE_minus_116;	}	
			else if (tmp.equals("117")) {  tmpi = VALUE_minus_117;	}
			else if (tmp.equals("118")) {  tmpi = VALUE_minus_118;	}	
			else if (tmp.equals("119")) {  tmpi = VALUE_minus_119;	}	
			else if (tmp.equals("120")) {  tmpi = VALUE_minus_120;	}		
			else if (tmp.equals("121")) {  tmpi = VALUE_minus_121;	}
			else if (tmp.equals("122")) {  tmpi = VALUE_minus_122;	}	
			else if (tmp.equals("123")) {  tmpi = VALUE_minus_123;	}	
			else if (tmp.equals("124")) {  tmpi = VALUE_minus_124;	}	
			else if (tmp.equals("125")) {  tmpi = VALUE_minus_125;	}	
			else if (tmp.equals("126")) {  tmpi = VALUE_minus_126;	}	
			else if (tmp.equals("127")) {  tmpi = VALUE_minus_127;	}
			else if (tmp.equals("128")) {  tmpi = VALUE_minus_128;	}	
			else if (tmp.equals("129")) {  tmpi = VALUE_minus_129;	}
			else if (tmp.equals("130")) {  tmpi = VALUE_minus_130;	}
			else if (tmp.equals("135")) {  tmpi = VALUE_minus_135;	}
			else if (tmp.equals("140")) {  tmpi = VALUE_minus_140;	}
			else if (tmp.equals("145")) {  tmpi = VALUE_minus_145;	}
			else if (tmp.equals("150")) {  tmpi = VALUE_minus_150;	}
			else { tmpi = VALUE_minus_30; }
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_RSSI_LO  , Integer.toString(tmpi));	
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_RSRP_LO  , tmp);	
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_RSSI_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_RSSI_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_RSSI_Lo");			
				
			switch (tmpi) {		
				case VALUE_minus_30: p.setSummary("-30dBm"); break;	
				case VALUE_minus_60: p.setSummary("-60dBm"); break;
				case VALUE_minus_90: p.setSummary("-90dBm"); break;
				case VALUE_minus_95: p.setSummary("-95dBm"); break;
				case VALUE_minus_100: p.setSummary("-100dBm"); break;
				case VALUE_minus_101: p.setSummary("-101dBm"); break;
				case VALUE_minus_102: p.setSummary("-102dBm"); break;
				case VALUE_minus_103: p.setSummary("-103dBm"); break;
				case VALUE_minus_104: p.setSummary("-104dBm"); break;
				case VALUE_minus_105: p.setSummary("-105dBm"); break;
				case VALUE_minus_106: p.setSummary("-106dBm"); break;
				case VALUE_minus_107: p.setSummary("-107dBm"); break;
				case VALUE_minus_108: p.setSummary("-108dBm"); break;
				case VALUE_minus_109: p.setSummary("-109dBm"); break;
				case VALUE_minus_110: p.setSummary("-110dBm"); break;
				case VALUE_minus_111: p.setSummary("-111dBm"); break;
				case VALUE_minus_112: p.setSummary("-112dBm"); break;
				case VALUE_minus_113: p.setSummary("-113dBm"); break;
				case VALUE_minus_114: p.setSummary("-114dBm"); break;
				case VALUE_minus_115: p.setSummary("-115dBm"); break;
				case VALUE_minus_116: p.setSummary("-116dBm"); break;
				case VALUE_minus_117: p.setSummary("-117dBm"); break;
				case VALUE_minus_118: p.setSummary("-118dBm"); break;
				case VALUE_minus_119: p.setSummary("-119dBm"); break;
				case VALUE_minus_120: p.setSummary("-120dBm"); break;
				case VALUE_minus_121: p.setSummary("-121dBm"); break;
				case VALUE_minus_122: p.setSummary("-122dBm"); break;
				case VALUE_minus_123: p.setSummary("-123dBm"); break;
				case VALUE_minus_124: p.setSummary("-124dBm"); break;
				case VALUE_minus_125: p.setSummary("-125dBm"); break;
				case VALUE_minus_126: p.setSummary("-126dBm"); break;
				case VALUE_minus_127: p.setSummary("-127dBm"); break;
				case VALUE_minus_128: p.setSummary("-128dBm"); break;
				case VALUE_minus_129: p.setSummary("-129dBm"); break;
				case VALUE_minus_130: p.setSummary("-130dBm"); break;
				case VALUE_minus_135: p.setSummary("-135dBm"); break;
				case VALUE_minus_140: p.setSummary("-140dBm"); break;
				case VALUE_minus_145: p.setSummary("-145dBm"); break;
				case VALUE_minus_150: p.setSummary("-150dBm"); break;
				default: p.setSummary("Unknown Value"); break;
			}
	    }
		
		
		//CMD_VAP_FORCE_1X_CDMA_ECIO_HI
		if (key.equals("Force_1x_CDMA_ECIO_Hi")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}			
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_11;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_16;	}	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_17;	}
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_18;	}	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_19;	}	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}	
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_21;	}	
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_22;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_23;	}	
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_24;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_26;	}	
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_27;	}	
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_28;	}	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_29;	}	
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_ECIO_HI  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_ECIO_HI  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_ECIO_HI  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_ECIO_Hi");			
				
	
			switch (tmpi) {		
			case VALUE_minus_5: p.setSummary("-5dB"); break;			
			case VALUE_minus_10: p.setSummary("-10dB"); break;
			case VALUE_minus_11: p.setSummary("-11dB"); break;
			case VALUE_minus_12: p.setSummary("-12dB"); break;
			case VALUE_minus_13: p.setSummary("-13dB"); break;
			case VALUE_minus_14: p.setSummary("-14dB"); break;
			case VALUE_minus_15: p.setSummary("-15dB"); break;
			case VALUE_minus_16: p.setSummary("-16dB"); break;
			case VALUE_minus_17: p.setSummary("-17dB"); break;
			case VALUE_minus_18: p.setSummary("-18dB"); break;
			case VALUE_minus_19: p.setSummary("-19dB"); break;
			case VALUE_minus_20: p.setSummary("-20dB"); break;	
			case VALUE_minus_21: p.setSummary("-21dB"); break;	
			case VALUE_minus_22: p.setSummary("-22dB"); break;	
			case VALUE_minus_23: p.setSummary("-23dB"); break;	
			case VALUE_minus_24: p.setSummary("-24dB"); break;	
			case VALUE_minus_25: p.setSummary("-25dB"); break;	
			case VALUE_minus_26: p.setSummary("-26dB"); break;	
			case VALUE_minus_27: p.setSummary("-27dB"); break;	
			case VALUE_minus_28: p.setSummary("-28dB"); break;	
			case VALUE_minus_29: p.setSummary("-29dB"); break;	
			case VALUE_minus_30: p.setSummary("-30dB"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
		
		//CMD_VAP_FORCE_1X_CDMA_ECIO_LO
		if (key.equals("Force_1x_CDMA_ECIO_Lo")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}			
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_11;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_16;	}	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_17;	}
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_18;	}	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_19;	}	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}	
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_21;	}	
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_22;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_23;	}	
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_24;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_26;	}	
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_27;	}	
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_28;	}	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_29;	}	
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_ECIO_LO  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_ECIO_LO  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_ECIO_LO  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_ECIO_Lo");			
				
	
			switch (tmpi) {		
			case VALUE_minus_5: p.setSummary("-5dB"); break;			
			case VALUE_minus_10: p.setSummary("-10dB"); break;
			case VALUE_minus_11: p.setSummary("-11dB"); break;
			case VALUE_minus_12: p.setSummary("-12dB"); break;
			case VALUE_minus_13: p.setSummary("-13dB"); break;
			case VALUE_minus_14: p.setSummary("-14dB"); break;
			case VALUE_minus_15: p.setSummary("-15dB"); break;
			case VALUE_minus_16: p.setSummary("-16dB"); break;
			case VALUE_minus_17: p.setSummary("-17dB"); break;
			case VALUE_minus_18: p.setSummary("-18dB"); break;
			case VALUE_minus_19: p.setSummary("-19dB"); break;
			case VALUE_minus_20: p.setSummary("-20dB"); break;	
			case VALUE_minus_21: p.setSummary("-21dB"); break;	
			case VALUE_minus_22: p.setSummary("-22dB"); break;	
			case VALUE_minus_23: p.setSummary("-23dB"); break;	
			case VALUE_minus_24: p.setSummary("-24dB"); break;	
			case VALUE_minus_25: p.setSummary("-25dB"); break;	
			case VALUE_minus_26: p.setSummary("-26dB"); break;	
			case VALUE_minus_27: p.setSummary("-27dB"); break;	
			case VALUE_minus_28: p.setSummary("-28dB"); break;	
			case VALUE_minus_29: p.setSummary("-29dB"); break;	
			case VALUE_minus_30: p.setSummary("-30dB"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
	
		
		//CMD_VAP_FORCE_1X_CDMA_RSSI_HY
		if (key.equals("Force_1x_CDMA_RSSI_Hyst")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("1")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_11;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_10;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_8;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_7;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_5;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_4;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_0; }	
			
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_1+256; }	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_2+256; }	
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_3+256; }	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_4+256; }	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_5+256; }	
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_6+256; }	
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_7+256;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_8+256;	}
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_9+256;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_10+256; }	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_11+256; }		
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_12+256; }
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_13+256; }	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_14+256; }	
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_15+256; }	
			else { tmpi = VALUE_minus_0; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_RSSI_HY  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_RSSI_HY  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_RSSI_HY  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_RSSI_Hyst");			
				
	
			if(tmpi >=256)
			{
				//+
				tmpi = tmpi - 256;
				switch (tmpi) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("+1dB"); break;
				case VALUE_minus_2: p.setSummary("+2dB"); break;
				case VALUE_minus_3: p.setSummary("+3dB"); break;
				case VALUE_minus_4: p.setSummary("+4dB"); break;
				case VALUE_minus_5: p.setSummary("+5dB"); break;
				case VALUE_minus_6: p.setSummary("+6dB"); break;
				case VALUE_minus_7: p.setSummary("+7dB"); break;
				case VALUE_minus_8: p.setSummary("+8dB"); break;
				case VALUE_minus_9: p.setSummary("+9dB"); break;
				case VALUE_minus_10: p.setSummary("+10dB"); break;
				case VALUE_minus_11: p.setSummary("+11dB"); break;
				case VALUE_minus_12: p.setSummary("+12dB"); break;
				case VALUE_minus_13: p.setSummary("+13dB"); break;
				case VALUE_minus_14: p.setSummary("+14dB"); break;
				case VALUE_minus_15: p.setSummary("+15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
			}
			else
			{
				//-
				switch (tmpi) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("-1dB"); break;
				case VALUE_minus_2: p.setSummary("-2dB"); break;
				case VALUE_minus_3: p.setSummary("-3dB"); break;
				case VALUE_minus_4: p.setSummary("-4dB"); break;
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_6: p.setSummary("-6dB"); break;
				case VALUE_minus_7: p.setSummary("-7dB"); break;
				case VALUE_minus_8: p.setSummary("-8dB"); break;
				case VALUE_minus_9: p.setSummary("-9dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
				
			}
		}
	   
		
		//CMD_VAP_FORCE_1X_CDMA_ECIO_HY
		if (key.equals("Force_1x_CDMA_ECIO_Hyst")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VALUE_minus_15;	}	
			else if (tmp.equals("1")) {  tmpi = VALUE_minus_14;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_13;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_12;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_11;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_10;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_8;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_7;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_5;	}		
			else if (tmp.equals("11")) {  tmpi = VALUE_minus_4;	}
			else if (tmp.equals("12")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("13")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("14")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_0; }	
			
			else if (tmp.equals("16")) {  tmpi = VALUE_minus_1+256; }	
			else if (tmp.equals("17")) {  tmpi = VALUE_minus_2+256; }	
			else if (tmp.equals("18")) {  tmpi = VALUE_minus_3+256; }	
			else if (tmp.equals("19")) {  tmpi = VALUE_minus_4+256; }	
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_5+256; }	
			else if (tmp.equals("21")) {  tmpi = VALUE_minus_6+256; }	
			else if (tmp.equals("22")) {  tmpi = VALUE_minus_7+256;	}	
			else if (tmp.equals("23")) {  tmpi = VALUE_minus_8+256;	}
			else if (tmp.equals("24")) {  tmpi = VALUE_minus_9+256;	}	
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_10+256; }	
			else if (tmp.equals("26")) {  tmpi = VALUE_minus_11+256; }		
			else if (tmp.equals("27")) {  tmpi = VALUE_minus_12+256; }
			else if (tmp.equals("28")) {  tmpi = VALUE_minus_13+256; }	
			else if (tmp.equals("29")) {  tmpi = VALUE_minus_14+256; }	
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_15+256; }	
			else { tmpi = VALUE_minus_0; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_CDMA_ECIO_HY  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_ECIO_HY  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_CDMA_ECIO_HY  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_CDMA_ECIO_Hyst");			
				
	
			if(tmpi >=256)
			{
				//+
				tmpi = tmpi - 256;
				switch (tmpi) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("+1dB"); break;
				case VALUE_minus_2: p.setSummary("+2dB"); break;
				case VALUE_minus_3: p.setSummary("+3dB"); break;
				case VALUE_minus_4: p.setSummary("+4dB"); break;
				case VALUE_minus_5: p.setSummary("+5dB"); break;
				case VALUE_minus_6: p.setSummary("+6dB"); break;
				case VALUE_minus_7: p.setSummary("+7dB"); break;
				case VALUE_minus_8: p.setSummary("+8dB"); break;
				case VALUE_minus_9: p.setSummary("+9dB"); break;
				case VALUE_minus_10: p.setSummary("+10dB"); break;
				case VALUE_minus_11: p.setSummary("+11dB"); break;
				case VALUE_minus_12: p.setSummary("+12dB"); break;
				case VALUE_minus_13: p.setSummary("+13dB"); break;
				case VALUE_minus_14: p.setSummary("+14dB"); break;
				case VALUE_minus_15: p.setSummary("+15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
			}
			else
			{
				//-
				switch (tmpi) {
				case VALUE_minus_0: p.setSummary("0dB"); break;
				case VALUE_minus_1: p.setSummary("-1dB"); break;
				case VALUE_minus_2: p.setSummary("-2dB"); break;
				case VALUE_minus_3: p.setSummary("-3dB"); break;
				case VALUE_minus_4: p.setSummary("-4dB"); break;
				case VALUE_minus_5: p.setSummary("-5dB"); break;
				case VALUE_minus_6: p.setSummary("-6dB"); break;
				case VALUE_minus_7: p.setSummary("-7dB"); break;
				case VALUE_minus_8: p.setSummary("-8dB"); break;
				case VALUE_minus_9: p.setSummary("-9dB"); break;
				case VALUE_minus_10: p.setSummary("-10dB"); break;
				case VALUE_minus_11: p.setSummary("-11dB"); break;
				case VALUE_minus_12: p.setSummary("-12dB"); break;
				case VALUE_minus_13: p.setSummary("-13dB"); break;
				case VALUE_minus_14: p.setSummary("-14dB"); break;
				case VALUE_minus_15: p.setSummary("-15dB"); break;
				default: p.setSummary("Unknown Value"); break;
				}
				
			}
	   }
		
		
		//CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER
		if (key.equals("Force_1x_Pingpong_timer_to_SRLTE")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VALUE_minus_0;	}	
			else if (tmp.equals("1")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}
			
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}	
			
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}		
			
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}	
			
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else { tmpi = VALUE_minus_30; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_1X_PINGPONG_TO_SRLTE_TIMER  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_1x_Pingpong_timer_to_SRLTE");			
				
	
			switch (tmpi) {		
			case VALUE_minus_0: p.setSummary("0sec"); break;
			case VALUE_minus_1: p.setSummary("1sec"); break;
			case VALUE_minus_2: p.setSummary("2sec"); break;
			case VALUE_minus_3: p.setSummary("3sec"); break;
			case VALUE_minus_4: p.setSummary("4sec"); break;
			case VALUE_minus_5: p.setSummary("5sec"); break;
			case VALUE_minus_6: p.setSummary("6sec"); break;
			case VALUE_minus_7: p.setSummary("7sec"); break;
			case VALUE_minus_8: p.setSummary("8sec"); break;
			case VALUE_minus_9: p.setSummary("9sec"); break;
			case VALUE_minus_10: p.setSummary("10sec"); break;		
			case VALUE_minus_15: p.setSummary("15sec"); break;			
			case VALUE_minus_20: p.setSummary("20sec"); break;
			case VALUE_minus_25: p.setSummary("25sec"); break;			
			case VALUE_minus_30: p.setSummary("30sec"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
		
		//CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL 
		if (key.equals("Skip_GW_Scan_during_VoLTE_call")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VAP_Disable;	}		
			else if (tmp.equals("1")) {  tmpi = VAP_Enable;	}		
			else { tmpi = VAP_Disable; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Skip_GW_Scan_during_VoLTE_call");			
			switch (tmpi) {		
				case VAP_Disable: p.setSummary("0(Disable)"); break;						
				case VAP_Enable: p.setSummary("1(Enable)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL 
		if (key.equals("Skip_1xDO_Scan_during_VoLTE_call")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VAP_Disable;	}		
			else if (tmp.equals("1")) {  tmpi = VAP_Enable;	}		
			else { tmpi = VAP_Disable; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Skip_1xDO_Scan_during_VoLTE_call");			
			switch (tmpi) {		
			case VAP_Disable: p.setSummary("0(Disable)"); break;						
			case VAP_Enable: p.setSummary("1(Enable)"); break;

				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL
		if (key.equals("Unlimited_RACH_during_VoLTE_call")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VALUE_minus_0;	}	
			else if (tmp.equals("1")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}			
			else if (tmp.equals("15")) {  tmpi = VALUE_minus_15;	}				
			else if (tmp.equals("20")) {  tmpi = VALUE_minus_20;	}			
			else if (tmp.equals("25")) {  tmpi = VALUE_minus_25;	}			
			else if (tmp.equals("30")) {  tmpi = VALUE_minus_30;	}
			else if (tmp.equals("60")) {  tmpi = VALUE_minus_60;	}
			else if (tmp.equals("90")) {  tmpi = VALUE_minus_90;	}
			else if (tmp.equals("120")) {  tmpi = VALUE_minus_120;	}
			else if (tmp.equals("150")) {  tmpi = VALUE_minus_150;	}
			else { tmpi = VALUE_minus_0; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Unlimited_RACH_during_VoLTE_call");			
				
	
			switch (tmpi) {		
			case VALUE_minus_0: p.setSummary("0(Disable)"); break;
			case VALUE_minus_1: p.setSummary("1(LG Suggestion)"); break;
			case VALUE_minus_2: p.setSummary("2"); break;
			case VALUE_minus_3: p.setSummary("3"); break;
			case VALUE_minus_4: p.setSummary("4"); break;
			case VALUE_minus_5: p.setSummary("5"); break;
			case VALUE_minus_6: p.setSummary("6"); break;
			case VALUE_minus_7: p.setSummary("7"); break;
			case VALUE_minus_8: p.setSummary("8"); break;
			case VALUE_minus_9: p.setSummary("9"); break;
			case VALUE_minus_10: p.setSummary("10"); break;		
			case VALUE_minus_15: p.setSummary("15"); break;			
			case VALUE_minus_20: p.setSummary("20"); break;
			case VALUE_minus_25: p.setSummary("25"); break;			
			case VALUE_minus_30: p.setSummary("30"); break;
			case VALUE_minus_60: p.setSummary("60"); break;
			case VALUE_minus_90: p.setSummary("90"); break;
			case VALUE_minus_120: p.setSummary("120"); break;
			case VALUE_minus_150: p.setSummary("150"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
		
		//CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL
		if (key.equals("Unlimited_RACH_Successive_Offset_during_VoLTE_call")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else { tmpi = VALUE_minus_1; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Unlimited_RACH_Successive_Offset_during_VoLTE_call");			
				
	
			switch (tmpi) {		
			case VALUE_minus_1: p.setSummary("1"); break;
			case VALUE_minus_2: p.setSummary("2"); break;
			case VALUE_minus_3: p.setSummary("3"); break;
			case VALUE_minus_4: p.setSummary("4"); break;
			case VALUE_minus_5: p.setSummary("5(Qualcomm)"); break;
			default: p.setSummary("Unknown Value"); break;
		}
	   }
		
		//CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL 
		if (key.equals("TAU_for_VZW_only_during_VoLTE_call")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VAP_Disable;	}		
			else if (tmp.equals("1")) {  tmpi = VAP_Enable;	}		
			else { tmpi = VAP_Disable; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL, Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("TAU_for_VZW_only_during_VoLTE_call");			
			switch (tmpi) {		
			case VAP_Disable: p.setSummary("0(Disable)"); break;						
			case VAP_Enable: p.setSummary("1(Enable)"); break;

				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		//CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL
		if (key.equals("Force_RLF_during_VoLTE_call")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = VALUE_minus_0;	}	
			else if (tmp.equals("1")) {  tmpi = VALUE_minus_1;	}	
			else if (tmp.equals("2")) {  tmpi = VALUE_minus_2;	}	
			else if (tmp.equals("3")) {  tmpi = VALUE_minus_3;	}	
			else if (tmp.equals("4")) {  tmpi = VALUE_minus_4;	}	
			else if (tmp.equals("5")) {  tmpi = VALUE_minus_5;	}	
			else if (tmp.equals("6")) {  tmpi = VALUE_minus_6;	}	
			else if (tmp.equals("7")) {  tmpi = VALUE_minus_7;	}
			else if (tmp.equals("8")) {  tmpi = VALUE_minus_8;	}	
			else if (tmp.equals("9")) {  tmpi = VALUE_minus_9;	}	
			else if (tmp.equals("10")) {  tmpi = VALUE_minus_10;	}
			else { tmpi = VALUE_minus_10; }
			
			
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Long.toString(tmpi));	
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  , Integer.toString(tmpi));	
			
			
			
			//Log.e(TAG, "Write CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  int = " + tmpi);
			//Log.e(TAG, "Write CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  string = " + Integer.toString(tmpi));
						
			p = (ListPreference)getPreferenceScreen().findPreference("Force_RLF_during_VoLTE_call");			
				
	
			switch (tmpi) {		
			case VALUE_minus_0: p.setSummary("0(Disable)"); break;
			case VALUE_minus_1: p.setSummary("1sec"); break;
			case VALUE_minus_2: p.setSummary("2sec(LG Suggestion)"); break;
			case VALUE_minus_3: p.setSummary("3sec"); break;
			case VALUE_minus_4: p.setSummary("4sec"); break;
			case VALUE_minus_5: p.setSummary("5sec"); break;
			case VALUE_minus_6: p.setSummary("6sec"); break;
			case VALUE_minus_7: p.setSummary("7sec"); break;
			case VALUE_minus_8: p.setSummary("8sec"); break;
			case VALUE_minus_9: p.setSummary("9sec"); break;
			case VALUE_minus_10: p.setSummary("10sec"); break;		
			default: p.setSummary("Unknown Value"); break;
			}
	   }
		
	} //Write end# DO NOT TOUCH!!!
		
	public void registerChangeListener() {
		mPreferenceScreen = (PreferenceScreen) findPreference ("reboot");
		mPreferenceScreen.setOnPreferenceClickListener(new SaveEventListener());
	}

    private class SaveEventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
		
			Toast.makeText(FIELD.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();			
			//3. reboot.
			ResetThread resetThread = new ResetThread(FIELD.this, 3000);
	        resetThread.start();    
			
			return false;
		}
	}
	

	
}

