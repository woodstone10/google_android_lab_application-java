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


/////////////////////////////////////////////////////////////////

public class IOT extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	SharedPreferences prefs;
	private ListPreference p;
	
	private static final String TAG = "LabApplication";
	
	public static final int DISABLE = 0;
	public static final int ENABLE = 1;
	
	public static final boolean IMS_DISABLE = false;
	public static final boolean IMS_ENABLE = true;
	
	public static final int IMS_TEST_MODE_DISABLE = 0;
	public static final int IMS_TEST_MODE_ENABLE  = 1;
	
	public static final int IPV6_DISABLE = 0;
	public static final int IPV6_ENABLE  = 1; 
	
	public static final boolean MOBILE_DATA_DISABLE = false;
	public static final boolean MOBILE_DATA_ENABLE  = true;
	
	public static final int UE_USAGE_SETTING_VOICE_CENTRIC = 0;
	public static final int UE_USAGE_SETTING_DATA_CENTRIC  = 1;
	
	public static final int INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE = 0;
	public static final int INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE  = 1;
	
	public static final int GW_GCF_SETTING_DISABLE = 0;
	public static final int GW_GCF_SETTING_ENABLE  = 1; 
	
	public static final int EVDO_REVISION_0 = 0;
	public static final int EVDO_REVISION_A_WO_EHRPD  = 1;
	public static final int EVDO_REVISION_A_W_EHRPD  = 2;
	public static final int EVDO_REVISION_B_WO_EHRPD  = 3;
	public static final int EVDO_REVISION_B_W_EHRPD  = 4;	
	
    //mode preference - lgrilhook_oem_rapi_radio
    public static final int LGRIL_MODE_PREF_CDMA_EVDO_AUTOMATIC		=0;
    public static final int LGRIL_MODE_PREF_CDMA_ONLY			=3;
    public static final int LGRIL_MODE_PREF_EVDO_ONLY			=2;
    public static final int LGRIL_MODE_PREF_GSM_WCDMA_AUTOMATIC			=4;
    public static final int LGRIL_MODE_PREF_GSM_ONLY			=5;
    public static final int LGRIL_MODE_PREF_WCDMA_ONLY			=6;
    public static final int LGRIL_MODE_PREF_LTE_ONLY			=7;
    public static final int LGRIL_MODE_PREF_GLOBAL_MODE			=8;
    public static final int LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE			=9;
    public static final int LGRIL_MODE_PREF_GSMWCDMA_LTE_DUAL_MODE			=10;
    public static final int LGRIL_MODE_PREF_MAX_VALUE			=11;	
	public static final int MODE_PREFERENCE_LTE_ONLY  = LGRIL_MODE_PREF_LTE_ONLY;
	public static final int MODE_PREFERENCE_GLOBAL = LGRIL_MODE_PREF_GLOBAL_MODE;	
	
	public static final int ATC_DISABLE = 0;
	public static final int ATC_ENABLE  = 1;		    
	
	public static final int ECM_MODE_DISABLE = 0;
	public static final int ECM_MODE_ENABLE  = 1; 
	
	public static final int OP_MODE_HVOLTE = 1;
	public static final int OP_MODE_SRLTE  = 2; //SRLTE
	public static final int OP_MODE_CSFB  = 3; //CSFB	
	
	public static final int CHECK_EHPLMN_LIST_FLAG_NOT_CHECK_SIM_EHPLMN = 0;
	public static final int CHECK_EHPLMN_LIST_FLAG_CHECK_SIM_EHPLMN = 1;	
	
	public static final int CSG_DISABLE = 0;
	public static final int CSG_ENABLE  = 1; 	
	
	public static final int AG_DISABLE = 0;
	public static final int AG_ENABLE  = 1; 
	
	public static final int SPV_TIMER_CONNECTED_DEFAULT = 0;
	public static final int SPV_TIMER_CONNECTED_TEST  = 1; 
	
	public static final int T3245_DEFAULT = 0;
	public static final int T3245_TEST  = 1; 
	
	public static final int CDMA1XSIB8_SCAN_OPTION_DISABLE = 0;
	public static final int CDMA1XSIB8_SCAN_OPTION_ENABLE  = 1; 
	
	public static final int DATA_APN_SWITCHING_DISABLE = 0;
	public static final int DATA_APN_SWITCHING_ENABLE  = 1; 	
	
	public static final int CDG2_FLAG_DISABLE = 0;
	public static final int CDG2_FLAG_ENABLE  = 1; 

	public static final int BLOCK_DNS_QUERY_DISABLE = 0;
	public static final int BLOCK_DNS_QUERY_ENABLE  = 1; 
	
	public static final int DUMMY_IMS_PREF_SEND_DISABLE = 0;
	public static final int DUMMY_IMS_PREF_SEND_ENABLE  = 1;	
	
	public static final int GUTI_DELETE_DISABLE = 0;
	public static final int GUTI_DELETE_ENABLE  = 1; 
	
	//BSR
	public static final int BSR_DISABLE = 0;
	public static final int BSR_ENABLE_VZW  = 1;
	public static final int BSR_ENABLE_SPCS  = 2;

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
	
	public static final int LTE_Blacklist_PCI_LG_Suggestion =5;
	public static final int LTE_Blacklist_PCI_QCT_Default =300;	
	
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

	//1xIA
	public static final int CDMA_Access_Power_Control_QCT_Default = 0;
	public static final int CDMA_Access_Power_Control_LG_Default = 1;
	public static final int CDMA_Access_Power_Control_LG_Enhanced = 2;
	
	public static final int CDMA_Access_Delay_Control_QCT_Default = 0;
	public static final int CDMA_Access_Delay_Control_LG_Enhanced = 1;	
	
	public static final int CDMA_SR_Delay_Control_QCT_Default = 0;
	public static final int CDMA_SR_Delay_Control_LG_Enhanced = 1;	
	
	//VAP
	public static final int VAP_Disable = 0;
	public static final int VAP_Enable = 1;
	
	//private ITelephony mPhoneService;   
	private LgSvcCmd mLgSvcCmd;	
	
	//private LGATCMDClient mATClient;	
	//private SQLiteDatabase imsDB = null;	
	//TURN ON/OFF MOBILE DATA
    //private IotHiddenMenuUtility mUtility;
    //ConnectivityManager mCm;
    //private TelephonyManager mTm;    
    //public static final String ACTION_DATA_CALLING_SETMOBILE = "com.lge.callingsetmobile";	
	//private Phone hphone = null;
	
	private PreferenceScreen mPreferenceScreen;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	        
    	super.onCreate(savedInstanceState); 
        
    	addPreferencesFromResource(R.layout.iot_predefined);        
      
        mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());
        
        //mPhoneService = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        //mATClient = new LGATCMDClient(this);
		//mATClient.bindService();	
		
		//TURN ON/OFF MOBILE DATA
        //mCm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //mTm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //mTm.setDataEnabled(false);
		//setDataEnabled(false);	
		/*
        // Initialize LGE IMS configuration database
        if (imsDB == null) {
            try {
                imsDB = SQLiteDatabase.openDatabase(IMS.DBFP, null, SQLiteDatabase.OPEN_READWRITE);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return;
            }
        }
		*/
    
        Log.v(TAG,"[LA] " + "IOT.java");
             
        /* TEST PLAN SELECTION */
        Default_IOT_Test(); //write only
        
        /* LTE LAB TEST */
        IMS_Test_Mode();
        IPv6_enable();
        //Mode_Preference(); //mode to preference (DETAILS.java)        
        UE_Usage_Setting();
        APN_Switching_Roam_Network();
        Check_EHPLMN_List();
        Data_APN_Switching();
        Block_DNS_Query();	
        Dummy_IMS_Pref_Send();
        CSG_enable();
        AG_enable();
        //GUTI_Delete(); //write only
        //ECM_Mode(); //not working
        OP_Mode(); //error
        
        /* 2G/3G LAB TEST */
        GW_GCF_Setting();
        EVDO_Revision();
        CDMA1xSIB8_Scan_Option(); //error
        CDG2_Flag();
        
        /* TIMERS */
        SPV_Timer_Connected();
        //EDCT Timer ->edit window
        //LTE MAC QoS Inactivity Timer ->edit window
        //1x Hold Srv Timer ->edit window
        //Hold Hy2 Oper Flag Timer ->edit window
        //E911 Twwan Timer ->edit window
        T3245_Test();
        Timer_Reset(); //write only
        //MRU/RAL Clear //write only
                
        /* BSR */
        //Periscope BSR //write only
        //Telescope BSR //write only
        //Active eHRPD to LTE Transition //write only
                
        /* Others */
        //ATC_enable(); //not working
        
                        
        //register
        registerChangeListener();
    }
    
    public void onDestroy() {
		//mATClient.unbindService();
		super.onDestroy();
		Log.e(TAG, "OnDestory called!!!");
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
        //getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        
        System.out.println("onPause");        
    }
    
    
    
	/*                              
	 * READ VALUE
	 */
	private void Default_IOT_Test() {		
		p = (ListPreference)getPreferenceScreen().findPreference("IOT_Test");
		p.setValue("Unknown");	    	
	}
	
	//CMD_IMS_TEST_MODE
	private void IMS_Test_Mode() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("IMS_Test_Mode");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_IMS_TEST_MODE);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case IMS_TEST_MODE_DISABLE: p.setSummary("Disabled (Default)"); break;						
				case IMS_TEST_MODE_ENABLE: p.setSummary("Enabled"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_IPV6_SUPPORT
	private void IPv6_enable() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("IPv6_enable");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_IPV6_SUPPORT);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case IPV6_DISABLE: p.setSummary("Disabled"); break;						
				case IPV6_ENABLE: p.setSummary("Enabled (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_MODE_PREFERENCE
	private void Mode_Preference() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Mode_Preference");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE);
		
		if (tmp != null){
			//setting = (int)Long.parseLong(tmp, 16);				
			setting = Integer.parseInt(tmp);
			
			switch (setting) {			
				case LGRIL_MODE_PREF_CDMA_EVDO_AUTOMATIC: p.setSummary("CDMA/EVDO"); break;
				case LGRIL_MODE_PREF_CDMA_ONLY: p.setSummary("CDMA Only"); break;		
				case LGRIL_MODE_PREF_EVDO_ONLY: p.setSummary("EVDO Only"); break;		
				case LGRIL_MODE_PREF_GSM_WCDMA_AUTOMATIC: p.setSummary("GSM/WCDMA"); break;		
				case LGRIL_MODE_PREF_GSM_ONLY: p.setSummary("GSM Only"); break;		
				case LGRIL_MODE_PREF_WCDMA_ONLY: p.setSummary("WCDMA Only"); break;		
				case LGRIL_MODE_PREF_LTE_ONLY: p.setSummary("LTE Only"); break;		
				case LGRIL_MODE_PREF_GLOBAL_MODE: p.setSummary("Global(Automatic) (Default)"); break;		
				case LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE: p.setSummary("CDMA/EVDO/LTE"); break;		
				case LGRIL_MODE_PREF_GSMWCDMA_LTE_DUAL_MODE: p.setSummary("GSM/WCDMA/LTE"); break;		
				case LGRIL_MODE_PREF_MAX_VALUE: p.setSummary("Unknow Value"); break;		
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_SPRINT_LTE_SET_UE_USAGE
	private void UE_Usage_Setting() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("UE_Usage_Setting");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_SPRINT_LTE_GET_UE_USAGE);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case UE_USAGE_SETTING_VOICE_CENTRIC: p.setSummary("Voice Centric (Default)"); break;						
				case UE_USAGE_SETTING_DATA_CENTRIC: p.setSummary("Data Centric"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_LTE_VZW_DR_TEST_FLAG
	private void APN_Switching_Roam_Network() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("APN_Switching_Roam_Network");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_VZW_DR_TEST_FLAG);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE: p.setSummary("Disable"); break;						
				case INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE: p.setSummary("Enable (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//RIL_REQUEST_SET_MODEM_INFO + LGE_MODEM_INFO_GCF_TEST_MODE
	private void GW_GCF_Setting() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("GW_GCF_Setting");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_GET_GCF);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case GW_GCF_SETTING_DISABLE: p.setSummary("Disable (Default)"); break;						
				case GW_GCF_SETTING_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	
	}


	//CMD_EVDO_REVISION
	private void EVDO_Revision() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("EVDO_Revision");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_EVDO_REVISION);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case EVDO_REVISION_0: p.setSummary("Rev.0"); break;						
				case EVDO_REVISION_A_WO_EHRPD: p.setSummary("Rev.A wo eHRPD"); break;
				case EVDO_REVISION_A_W_EHRPD: p.setSummary("Rev.A w eHRPD (Default)"); break;
				case EVDO_REVISION_B_WO_EHRPD: p.setSummary("Rev.B wo eHRPD"); break;
				case EVDO_REVISION_B_W_EHRPD: p.setSummary("Rev.B w eHRPD"); break;				
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
	//ECM MODE
/*
	private void ECM_Mode() {		
		String tmp;
		int setting;		
		String usbConfig = SystemProperties.get("persist.sys.usb.config");
		p = (ListPreference)getPreferenceScreen().findPreference("ECM_mode");
	    if (usbConfig != null && usbConfig.contains(UsbManagerConstants.USB_FUNCTION_TETHER)) {
	        //mECMModeCtrlBtn.setChecked(true);
	    	p.setSummary("Enable");
	    } else {
	        //mECMModeCtrlBtn.setChecked(false);
	    	p.setSummary("Disable (Default)");
	    }
	    p.setValue("Unknown");
	}
*/
	
	//OP MODE (CMD_CARRIER_POLICY_UE_MODE)
	private void OP_Mode() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("OP_Mode");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_CARRIER_POLICY_UE_MODE);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case OP_MODE_HVOLTE: p.setSummary("VZW hVoLTE"); break;						
				case OP_MODE_SRLTE: p.setSummary("VZW SRLTE"); break;
				case OP_MODE_CSFB: p.setSummary("VZW CSFB"); break;							
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	
	}
	
	
	//Check EHPLMN List (CMD_CHECK_EHPLMN_LIST_FLAG )
	private void Check_EHPLMN_List() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Check_EHPLMN_List");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_CHECK_EHPLMN_LIST_FLAG);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case CHECK_EHPLMN_LIST_FLAG_NOT_CHECK_SIM_EHPLMN: p.setSummary("Not Check SIM¡¯s eHPLMN"); break;						
				case CHECK_EHPLMN_LIST_FLAG_CHECK_SIM_EHPLMN: p.setSummary("Check SIM¡¯s eHPLMN (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");	
	}
	
	
	//CMD_CSG_ENABLE_FLAG
	private void CSG_enable() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("CSG_enable");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_CSG_ENABLE_FLAG);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case CSG_DISABLE: p.setSummary("Disabled"); break;						
				case CSG_ENABLE: p.setSummary("Enabled (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_AUTONOMOUS_GAP_ENABLE_FLAG
	private void AG_enable() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("AG_enable");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_AUTONOMOUS_GAP_ENABLE_FLAG);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case AG_DISABLE: p.setSummary("Disabled"); break;						
				case AG_ENABLE: p.setSummary("Enabled (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}


	/*
	private void getATC_enable() {
		LGATCMDClient.Response response;
		
		//mATClient = new LGATCMDClient(this);
		mATClient.bindService();	

		p = (ListPreference)getPreferenceScreen().findPreference("ATC_enable");
		response = mATClient.request(4050, "2".getBytes());
		
		if (response != null) 
		{ 
			Log.d(TAG, "Result: " + response.result);
		
			Log.d(TAG, "in run() request 2");
			Log.d(TAG, "in run() response.data = " + response.data);
			Log.d(TAG, "in run() response.result = " + response.result);
			
			
			if (response.result == 0) {
				 p.setSummary("Enabled"); 
			}
			else {
				p.setSummary("Disabled");
			}		
			
		}
		
		p.setValue("Unknown");		
	}
	*/
	
	//CMD_SPV_TIMER_CONNECTED
	private void SPV_Timer_Connected() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("SPV_Timer_Connected");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_SPV_TIMER_CONNECTED);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case SPV_TIMER_CONNECTED_DEFAULT: p.setSummary("5 (Default)"); break;						
				case SPV_TIMER_CONNECTED_TEST: p.setSummary("FFFF (Test)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
		
	//CMD_T3245_TEST
	private void T3245_Test() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("T3245_Test");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_T3245_TEST);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case T3245_DEFAULT: p.setSummary("Default"); break;						
				case T3245_TEST: p.setSummary("1800 (Test)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}

	//CMD_1XSIB8_SCAN_OPTION
	private void CDMA1xSIB8_Scan_Option() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("CDMA1xSIB8_Scan_Option");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_1XSIB8_SCAN_OPTION);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case CDMA1XSIB8_SCAN_OPTION_DISABLE: p.setSummary("Disable"); break;						
				case CDMA1XSIB8_SCAN_OPTION_ENABLE: p.setSummary("Enable (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_DATA_APN_SWITCHING
	private void Data_APN_Switching() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Data_APN_Switching");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_DATA_APN_SWITCHING);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case DATA_APN_SWITCHING_DISABLE: p.setSummary("Disable"); break;						
				case DATA_APN_SWITCHING_ENABLE: p.setSummary("Enable (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
		
	//CMD_CDG2_TEST_FLAG
	private void CDG2_Flag() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("CDG2_Flag");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_CDG2_TEST_FLAG);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case CDG2_FLAG_DISABLE: p.setSummary("Disable (Default)"); break;						
				case CDG2_FLAG_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	//CMD_BLOCK_DNS_QUERY
	private void Block_DNS_Query() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Block_DNS_Query");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_BLOCK_DNS_QUERY);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case BLOCK_DNS_QUERY_DISABLE: p.setSummary("Disable (Default)"); break;						
				case BLOCK_DNS_QUERY_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
		
	//CMD_DUMMY_IMS_PREF_SEND_FLAG
	private void Dummy_IMS_Pref_Send() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Dummy_IMS_Pref_Send_Flag");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_DUMMY_IMS_PREF_SEND_FLAG);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case DUMMY_IMS_PREF_SEND_DISABLE: p.setSummary("Disable (LG Default)"); break;						
				case DUMMY_IMS_PREF_SEND_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
	
	
/*
	//CMD_GUTI_DELETE
	private void GUTI_Delete() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("GUTI_Delete");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_GUTI_DELETE);
		if (tmp != null){
			setting = (int)Long.parseLong(tmp, 16);		
			switch (setting) {		
				case GUTI_DELETE_DISABLE: p.setSummary("Disable"); break;						
				case GUTI_DELETE_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
		}
		p.setValue("Unknown");
	}
*/	


	/*
	 * RESET
	 */
	private void Timer_Reset() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Timer_Reset");
		p.setValue("Unknown");
	}
	
    
	/* 
	 * WRITE VALUE
	 */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		String tmp = "1";
		boolean tmpb = false;
		int tmpi = 0;		
		
		//LA2.0
		boolean IMS = false;
		int IMS_TESTMODE = 0;	
		int IPV6 = 0;
		boolean DATA = false;
		int MODE = 0;
		int USAGE = 0;
		int APN_SWITCHING_WHEN_ROAMING = 0;
		int ECM = 0;
		int OP_MODE = 0;
		int EDCT_TIMER = 0;
		int LTE_MAC_QOS_INACTIVITY_TIMER = 0;
		int T3245 = 0;
		int DATA_APN_SWITCHING = 0;
		int SPV_TIMER_CONNECTED = 0;
		int CDG2_FLAG = 0;
		int BLOCK_DNS_QUERY = 0;
		
		//BSR
		int PERISCOPE_BSR = 0;
		int TELESCOPE_BSR = 0;
		int ACTIVE_TO_LTE = 0;
			
		//hVoLTE VCP task
		int CALLEND_BSR_TIMER = 0;
		int SKIP_BSR_IN_CALLING = 0;
		int SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = 0;
		int FORCE_TO_SRLTE_WHEN_RACH_FAIL = 0;
		int HYST_TO_CSFB_SIGNAL_STRENGTH = 0;
		int HYST_TO_CSFB_TIMER = 0;
		int FORCE_TO_SRLTE_SIGNAL_STRENGTH = 0;
		int FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = 0; 
		int FORCE_TO_SRLTE_UNTIL_IMS_REG = 0;
		int OP_MODE_PINGPONG_TIMER = 0;
		int FAST_1X_TRANSITION = 0;
		
		//LTEconn% task
		int BACKOFF_PLMN = 0;
		int THROTTLE_BSR = 0;
		int KICKOFF_BSR = 0;
		int LTE_BLACKLIST_PCI = 0;
		int ACQDB_SCAN = 0;
		int HPLMN_SCAN = 0;
		int SKIP_GW_DURIING_VOLTE_RLF = 0;
		int QCT_D2LREDIR = 0;
		
		//1xIA
		int Enh_1xAcc_Pwr =0;
		int Enh_1xAcc_Delay =0;
		int Enh_1xAcc_SR_delay =0;
		
		//VAP
		int VAP_AMT = 0;
		int VAP_Skip_GW_Scan_during_VoLTE_call = 0;
		int VAP_Skip_1xDO_Scan_during_VoLTE_call = 0;
		int VAP_Unlimited_RACH_during_VoLTE_call = 0;
		int VAP_TAU_for_VZW_only_during_VoLTE_call = 0;
		int VAP_Force_RLF_during_VoLTE_call = 0;
		
					
		int newValue = 0;

		if (key.equals("IOT_Test")) {			
			tmp = sharedPreferences.getString(key, "");		

			Log.e(TAG,"[LA] Test Plan Selection (radio) = " + tmp);
			
			/*
			 * <string-array name="IOT_Test">
			    <item>VzW Default</item>
				<item>HW Testing</item>
				<item>3GPP GCF</item>
				<item>VzW DR/SS</item>
				<item>VzW DT (Spirent)</item>
				<item>VzW DT (Anritsu, PQA)</item>
				<item>VzW Interband</item>
				<item>VzW GMSS</item>
				<item>VzW MMDR</item>
				<item>VzW Stress</item>
				<item>VzW IMS</item>
				<item>VzW VoLTE/E911</item>
				<item>VzW hVoLTE</item>
				<item>VzW ATC</item>
				<item>VzW UICC</item>
				<item>3GPP UICC</item>
				<item>IRAT LTE-CDMA</item>
				<item>IRAT LTE-GW</item>
				<item>USC Default</item>
			</string-array>	
			<string-array name="IOT_Test_Value">
				<item>0</item>
				<item>1</item>
				<item>2</item>
				<item>3</item>
				<item>4</item>
				<item>5</item>
				<item>6</item>
				<item>7</item>
				<item>8</item>
				<item>9</item>
				<item>10</item>
				<item>11</item>
				<item>12</item>
				<item>13</item>
				<item>14</item>
				<item>15</item>
				<item>16</item>
				<item>17</item>
				<item>18</item>
			</string-array>

			if (tmp.equals("0")) {  //VzW Commercial
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_HVOLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 30;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_DEFAULT;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_DISABLE;		 			
			}			
			else if (tmp.equals("1")) {  //HW Testing
				IMS = IMS_DISABLE;
				IMS_TESTMODE = IMS_TEST_MODE_ENABLE;	
				IPV6 = IPV6_DISABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}			
			else if (tmp.equals("2")) {  //3GPP GCF
				IMS = IMS_DISABLE;
				IMS_TESTMODE = IMS_TEST_MODE_ENABLE;	
				IPV6 = IPV6_DISABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				//OP_MODE = OP_MODE_SRLTE;
				OP_MODE = OP_MODE_CSFB;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("3")) { //VzW DR/SS
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_TEST;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}			 
			else if (tmp.equals("4")) { //VzW DT (Spirent) 
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_ENABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}	
			else if (tmp.equals("5")) { //VzW DT (Anritsu)
				IMS = IMS_DISABLE;
				IMS_TESTMODE = IMS_TEST_MODE_ENABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_ENABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}	
			else if (tmp.equals("6")) { //VzW Interband 
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}		
			else if (tmp.equals("7")) { //VzW GMSS 
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC; 
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}			
			else if (tmp.equals("8")) { //VzW MMDR
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}		
			else if (tmp.equals("9")) { //VzW Stress
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_DISABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}			
			else if (tmp.equals("10")) { //VzW IMS
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_HVOLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("11")) { //VzW VoLTE/VS/E911
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_HVOLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("12")) { //VzW hVoLTE
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_HVOLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("13")) { //VzW ATC
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("14")) { //VzW UICC
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_DISABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("15")) { //3GPP UICC
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_DISABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}		    
			else if (tmp.equals("16")) { //IRAT LTE-CDMA
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_ENABLE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("17")) { //IRAT LTE-GW
				IMS = IMS_DISABLE;
				IMS_TESTMODE = IMS_TEST_MODE_ENABLE;	
				IPV6 = IPV6_DISABLE;
				DATA = MOBILE_DATA_DISABLE;
				MODE = LGRIL_MODE_PREF_GSMWCDMA_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				//OP_MODE = OP_MODE_SRLTE;
				OP_MODE = OP_MODE_CSFB;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;
			}
			else if (tmp.equals("18")) { //USC Default
				IMS = IMS_DISABLE;
				IMS_TESTMODE = IMS_TEST_MODE_ENABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				//OP_MODE = OP_MODE_SRLTE;
				OP_MODE = OP_MODE_SRLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 30;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_DEFAULT;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_DISABLE;
			}
			else { 
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;
				DATA = MOBILE_DATA_ENABLE;
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;
				ECM = ECM_MODE_DISABLE;
				OP_MODE = OP_MODE_HVOLTE;
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 30;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_DEFAULT;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;	
				BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_DISABLE;
			}		
			*/
			
			if (tmp.equals("0")) {  //VzW Commercial					
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;				
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;					
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 30;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_DEFAULT;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_DISABLE;		
	 			OP_MODE = OP_MODE_HVOLTE;
	 			
	 			//BSR
	 			PERISCOPE_BSR = BSR_ENABLE_VZW;
	 			TELESCOPE_BSR = BSR_ENABLE_VZW;
	 			ACTIVE_TO_LTE = BSR_ENABLE_VZW;
	 			
	 			//hVoLTE VCP 
	 			CALLEND_BSR_TIMER = Callend_BSR_Timer_60sec;
	 			SKIP_BSR_IN_CALLING = Skip_BSR_In_Calling_ENABLE;
	 			SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_RACH_FAIL = Force_to_SRLTE_when_RRCConnection_Fail_3counts;
	 			HYST_TO_CSFB_SIGNAL_STRENGTH = Hysteresis_to_CSFB_with_Signal_Strength_115dB;
	 			HYST_TO_CSFB_TIMER = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;
	 			FORCE_TO_SRLTE_SIGNAL_STRENGTH = Force_to_SRLTE_with_Signal_Strength_90dB;
	 			FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_ENABLE; 
	 			FORCE_TO_SRLTE_UNTIL_IMS_REG = Force_to_SRLTE_until_IMS_Reg_Success_ENABLE;
	 			OP_MODE_PINGPONG_TIMER = OP_Mode_Pingpong_Timer_DISABLE;
	 			FAST_1X_TRANSITION = Fast_1x_Idle_Transition_ENABLE;
	 				 			
	 			//LTE conn% 
	 			BACKOFF_PLMN = RACH_Failure_Count_to_Backoff_PLMN_2;
	 			THROTTLE_BSR = Throttled_BSR_Timer_DISABLE;
	 			KICKOFF_BSR = Kickoff_BSR_Scan_DISABLE;
	 			LTE_BLACKLIST_PCI = LTE_Blacklist_PCI_LG_Suggestion;
	 			ACQDB_SCAN = ACQ_DB_Scan_Scope_5;
	 			HPLMN_SCAN = HPLMN_Scan_Scope_1;
	 			SKIP_GW_DURIING_VOLTE_RLF = Skip_GW_Scan_During_VoLTE_RLF_DISABLE; //change default value = disable
	 			QCT_D2LREDIR = D2LREDIR_ENABLE;
	 			
	 			//1xIA
	 			Enh_1xAcc_Pwr = CDMA_Access_Power_Control_LG_Enhanced;
	 			Enh_1xAcc_Delay = CDMA_Access_Delay_Control_LG_Enhanced;
	 			Enh_1xAcc_SR_delay = CDMA_SR_Delay_Control_LG_Enhanced;
	 			
	 			//VAP
	 			VAP_AMT = VAP_Enable;
	 			VAP_Skip_GW_Scan_during_VoLTE_call = VAP_Enable;
	 			VAP_Skip_1xDO_Scan_during_VoLTE_call = VAP_Enable;
	 			VAP_Unlimited_RACH_during_VoLTE_call = VAP_Enable;
	 			VAP_TAU_for_VZW_only_during_VoLTE_call = VAP_Enable;
	 			VAP_Force_RLF_during_VoLTE_call = 2;	 			
			} else if (tmp.equals("1")) {  //UE1
				IMS = IMS_DISABLE;
				IMS_TESTMODE = IMS_TEST_MODE_ENABLE;	
				IPV6 = IPV6_DISABLE;				
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;				
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_DISABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;	
	 			OP_MODE = OP_MODE_SRLTE;
	 			
	 			//BSR
	 			PERISCOPE_BSR = BSR_DISABLE;
	 			TELESCOPE_BSR = BSR_DISABLE;
	 			ACTIVE_TO_LTE = BSR_DISABLE;
	 			
	 			//hVoLTE VCP task - Commercial
	 			CALLEND_BSR_TIMER = Callend_BSR_Timer_3sec;
	 			SKIP_BSR_IN_CALLING = Skip_BSR_In_Calling_DISABLE;
	 			SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_RACH_FAIL = Force_to_SRLTE_when_RRCConnection_Fail_DISABLE;
	 			HYST_TO_CSFB_SIGNAL_STRENGTH = Hysteresis_to_CSFB_with_Signal_Strength_DISABLE;
	 			HYST_TO_CSFB_TIMER = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;
	 			FORCE_TO_SRLTE_SIGNAL_STRENGTH = Force_to_SRLTE_with_Signal_Strength_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE; 
	 			FORCE_TO_SRLTE_UNTIL_IMS_REG = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE;
	 			OP_MODE_PINGPONG_TIMER = OP_Mode_Pingpong_Timer_DISABLE;
	 			FAST_1X_TRANSITION = Fast_1x_Idle_Transition_DISABLE;
	 				 			
	 			//LTEconn% task - Commercial
	 			BACKOFF_PLMN = RACH_Failure_Count_to_Backoff_PLMN_1;
	 			THROTTLE_BSR = Throttled_BSR_Timer_DISABLE;
	 			KICKOFF_BSR = Kickoff_BSR_Scan_DISABLE;
	 			LTE_BLACKLIST_PCI = LTE_Blacklist_PCI_QCT_Default;
	 			ACQDB_SCAN = ACQ_DB_Scan_Scope_1;
	 			HPLMN_SCAN = HPLMN_Scan_Scope_0;
	 			SKIP_GW_DURIING_VOLTE_RLF = Skip_GW_Scan_During_VoLTE_RLF_DISABLE; //change default value = disable
	 			QCT_D2LREDIR = D2LREDIR_DISABLE;
	 			
	 			//1xIA
	 			Enh_1xAcc_Pwr = CDMA_Access_Power_Control_QCT_Default;
	 			Enh_1xAcc_Delay = CDMA_Access_Delay_Control_QCT_Default;
	 			Enh_1xAcc_SR_delay = CDMA_SR_Delay_Control_QCT_Default;
	 			
	 			//VAP
	 			VAP_AMT = VAP_Disable;
	 			VAP_Skip_GW_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Skip_1xDO_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Unlimited_RACH_during_VoLTE_call = VAP_Disable;
	 			VAP_TAU_for_VZW_only_during_VoLTE_call = VAP_Disable;
	 			VAP_Force_RLF_during_VoLTE_call = VAP_Disable;				
			} else if (tmp.equals("2")) {  //UE2					
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;				
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;				
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;	
	 			OP_MODE = OP_MODE_HVOLTE;
	 			
	 			//BSR
	 			PERISCOPE_BSR = BSR_DISABLE;
	 			TELESCOPE_BSR = BSR_DISABLE;
	 			ACTIVE_TO_LTE = BSR_DISABLE;
	 			
	 			//hVoLTE VCP task - Commercial
	 			CALLEND_BSR_TIMER = Callend_BSR_Timer_3sec;
	 			SKIP_BSR_IN_CALLING = Skip_BSR_In_Calling_DISABLE;
	 			SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_RACH_FAIL = Force_to_SRLTE_when_RRCConnection_Fail_DISABLE;
	 			HYST_TO_CSFB_SIGNAL_STRENGTH = Hysteresis_to_CSFB_with_Signal_Strength_DISABLE;
	 			HYST_TO_CSFB_TIMER = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;
	 			FORCE_TO_SRLTE_SIGNAL_STRENGTH = Force_to_SRLTE_with_Signal_Strength_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE; 
	 			FORCE_TO_SRLTE_UNTIL_IMS_REG = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE;
	 			OP_MODE_PINGPONG_TIMER = OP_Mode_Pingpong_Timer_DISABLE;
	 			FAST_1X_TRANSITION = Fast_1x_Idle_Transition_DISABLE;
	 				 			
	 			//LTEconn% task - Commercial
	 			BACKOFF_PLMN = RACH_Failure_Count_to_Backoff_PLMN_1;
	 			THROTTLE_BSR = Throttled_BSR_Timer_DISABLE;
	 			KICKOFF_BSR = Kickoff_BSR_Scan_DISABLE;
	 			LTE_BLACKLIST_PCI = LTE_Blacklist_PCI_QCT_Default;
	 			ACQDB_SCAN = ACQ_DB_Scan_Scope_1;
	 			HPLMN_SCAN = HPLMN_Scan_Scope_0;
	 			SKIP_GW_DURIING_VOLTE_RLF = Skip_GW_Scan_During_VoLTE_RLF_DISABLE; //change default value = disable
	 			QCT_D2LREDIR = D2LREDIR_DISABLE;
	 			
	 			//1xIA
	 			Enh_1xAcc_Pwr = CDMA_Access_Power_Control_QCT_Default;
	 			Enh_1xAcc_Delay = CDMA_Access_Delay_Control_QCT_Default;
	 			Enh_1xAcc_SR_delay = CDMA_SR_Delay_Control_QCT_Default;
	 			
	 			//VAP
	 			VAP_AMT = VAP_Disable;
	 			VAP_Skip_GW_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Skip_1xDO_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Unlimited_RACH_during_VoLTE_call = VAP_Disable;
	 			VAP_TAU_for_VZW_only_during_VoLTE_call = VAP_Disable;
	 			VAP_Force_RLF_during_VoLTE_call = VAP_Disable;
			} else if (tmp.equals("3")) {  //UE3					
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;				
				MODE = MODE_PREFERENCE_LTE_ONLY;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;				
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_TEST;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;	
	 			OP_MODE = OP_MODE_SRLTE;
	 			
	 			//BSR
	 			PERISCOPE_BSR = BSR_DISABLE;
	 			TELESCOPE_BSR = BSR_DISABLE;
	 			ACTIVE_TO_LTE = BSR_DISABLE;
	 			
	 			//hVoLTE VCP task - Commercial
	 			CALLEND_BSR_TIMER = Callend_BSR_Timer_3sec;
	 			SKIP_BSR_IN_CALLING = Skip_BSR_In_Calling_DISABLE;
	 			SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_RACH_FAIL = Force_to_SRLTE_when_RRCConnection_Fail_DISABLE;
	 			HYST_TO_CSFB_SIGNAL_STRENGTH = Hysteresis_to_CSFB_with_Signal_Strength_DISABLE;
	 			HYST_TO_CSFB_TIMER = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;
	 			FORCE_TO_SRLTE_SIGNAL_STRENGTH = Force_to_SRLTE_with_Signal_Strength_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE; 
	 			FORCE_TO_SRLTE_UNTIL_IMS_REG = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE;
	 			OP_MODE_PINGPONG_TIMER = OP_Mode_Pingpong_Timer_DISABLE;
	 			FAST_1X_TRANSITION = Fast_1x_Idle_Transition_DISABLE;
	 				 			
	 			//LTEconn% task - Commercial
	 			BACKOFF_PLMN = RACH_Failure_Count_to_Backoff_PLMN_1;
	 			THROTTLE_BSR = Throttled_BSR_Timer_DISABLE;
	 			KICKOFF_BSR = Kickoff_BSR_Scan_DISABLE;
	 			LTE_BLACKLIST_PCI = LTE_Blacklist_PCI_QCT_Default;
	 			ACQDB_SCAN = ACQ_DB_Scan_Scope_1;
	 			HPLMN_SCAN = HPLMN_Scan_Scope_0;
	 			SKIP_GW_DURIING_VOLTE_RLF = Skip_GW_Scan_During_VoLTE_RLF_DISABLE; //change default value = disable
	 			QCT_D2LREDIR = D2LREDIR_DISABLE;
	 			
	 			//1xIA
	 			Enh_1xAcc_Pwr = CDMA_Access_Power_Control_QCT_Default;
	 			Enh_1xAcc_Delay = CDMA_Access_Delay_Control_QCT_Default;
	 			Enh_1xAcc_SR_delay = CDMA_SR_Delay_Control_QCT_Default;
	 			
	 			//VAP
	 			VAP_AMT = VAP_Disable;
	 			VAP_Skip_GW_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Skip_1xDO_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Unlimited_RACH_during_VoLTE_call = VAP_Disable;
	 			VAP_TAU_for_VZW_only_during_VoLTE_call = VAP_Disable;
	 			VAP_Force_RLF_during_VoLTE_call = VAP_Disable;
			} else if (tmp.equals("4")) {  //UE4					
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;				
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_DATA_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE;				
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 255;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_TEST;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_ENABLE;	
	 			OP_MODE = OP_MODE_SRLTE;
	 			
	 			//BSR
	 			PERISCOPE_BSR = BSR_DISABLE;
	 			TELESCOPE_BSR = BSR_DISABLE;
	 			ACTIVE_TO_LTE = BSR_DISABLE;
	 			
	 			//hVoLTE VCP task - Commercial
	 			CALLEND_BSR_TIMER = Callend_BSR_Timer_3sec;
	 			SKIP_BSR_IN_CALLING = Skip_BSR_In_Calling_DISABLE;
	 			SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_RACH_FAIL = Force_to_SRLTE_when_RRCConnection_Fail_DISABLE;
	 			HYST_TO_CSFB_SIGNAL_STRENGTH = Hysteresis_to_CSFB_with_Signal_Strength_DISABLE;
	 			HYST_TO_CSFB_TIMER = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;
	 			FORCE_TO_SRLTE_SIGNAL_STRENGTH = Force_to_SRLTE_with_Signal_Strength_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_DISABLE; 
	 			FORCE_TO_SRLTE_UNTIL_IMS_REG = Force_to_SRLTE_until_IMS_Reg_Success_DISABLE;
	 			OP_MODE_PINGPONG_TIMER = OP_Mode_Pingpong_Timer_DISABLE;
	 			FAST_1X_TRANSITION = Fast_1x_Idle_Transition_DISABLE;
	 				 			
	 			//LTEconn% task - Commercial
	 			BACKOFF_PLMN = RACH_Failure_Count_to_Backoff_PLMN_1;
	 			THROTTLE_BSR = Throttled_BSR_Timer_DISABLE;
	 			KICKOFF_BSR = Kickoff_BSR_Scan_DISABLE;
	 			LTE_BLACKLIST_PCI = LTE_Blacklist_PCI_QCT_Default;
	 			ACQDB_SCAN = ACQ_DB_Scan_Scope_1;
	 			HPLMN_SCAN = HPLMN_Scan_Scope_0;
	 			SKIP_GW_DURIING_VOLTE_RLF = Skip_GW_Scan_During_VoLTE_RLF_DISABLE; //change default value = disable
	 			QCT_D2LREDIR = D2LREDIR_DISABLE;
	 			
	 			//1xIA
	 			Enh_1xAcc_Pwr = CDMA_Access_Power_Control_QCT_Default;
	 			Enh_1xAcc_Delay = CDMA_Access_Delay_Control_QCT_Default;
	 			Enh_1xAcc_SR_delay = CDMA_SR_Delay_Control_QCT_Default;
	 			
	 			//VAP
	 			VAP_AMT = VAP_Disable;
	 			VAP_Skip_GW_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Skip_1xDO_Scan_during_VoLTE_call = VAP_Disable;
	 			VAP_Unlimited_RACH_during_VoLTE_call = VAP_Disable;
	 			VAP_TAU_for_VZW_only_during_VoLTE_call = VAP_Disable;
	 			VAP_Force_RLF_during_VoLTE_call = VAP_Disable;
			} 
			else
			{
				IMS = IMS_ENABLE;
				IMS_TESTMODE = IMS_TEST_MODE_DISABLE;	
				IPV6 = IPV6_ENABLE;				
				MODE = MODE_PREFERENCE_GLOBAL;
				USAGE = UE_USAGE_SETTING_VOICE_CENTRIC;		
				APN_SWITCHING_WHEN_ROAMING = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE;					
				EDCT_TIMER = 30;
				LTE_MAC_QOS_INACTIVITY_TIMER = 30;
				SPV_TIMER_CONNECTED = SPV_TIMER_CONNECTED_DEFAULT;
				T3245 = T3245_DEFAULT;
				DATA_APN_SWITCHING = DATA_APN_SWITCHING_ENABLE;					
	 			BLOCK_DNS_QUERY = BLOCK_DNS_QUERY_DISABLE;		
	 			OP_MODE = OP_MODE_HVOLTE;
	 			
	 			//BSR
	 			PERISCOPE_BSR = BSR_ENABLE_VZW;
	 			TELESCOPE_BSR = BSR_ENABLE_VZW;
	 			ACTIVE_TO_LTE = BSR_ENABLE_VZW;
	 			
	 			//hVoLTE VCP 
	 			CALLEND_BSR_TIMER = Callend_BSR_Timer_60sec;
	 			SKIP_BSR_IN_CALLING = Skip_BSR_In_Calling_ENABLE;
	 			SKIP_BSR_IN_LTE_LOST_WHEN_CALLING = Skip_BSR_In_LTE_Lost_When_Calling_DISABLE;
	 			FORCE_TO_SRLTE_WHEN_RACH_FAIL = Force_to_SRLTE_when_RRCConnection_Fail_3counts;
	 			HYST_TO_CSFB_SIGNAL_STRENGTH = Hysteresis_to_CSFB_with_Signal_Strength_115dB;
	 			HYST_TO_CSFB_TIMER = Hysteresis_to_CSFB_with_Signal_Strength_T_10sec;
	 			FORCE_TO_SRLTE_SIGNAL_STRENGTH = Force_to_SRLTE_with_Signal_Strength_90dB;
	 			FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE = Force_to_SRLTE_when_IMS_Fail_on_Unknown_Mode_ENABLE; 
	 			FORCE_TO_SRLTE_UNTIL_IMS_REG = Force_to_SRLTE_until_IMS_Reg_Success_ENABLE;
	 			OP_MODE_PINGPONG_TIMER = OP_Mode_Pingpong_Timer_DISABLE;
	 			FAST_1X_TRANSITION = Fast_1x_Idle_Transition_ENABLE;
	 				 			
	 			//LTE conn% 
	 			BACKOFF_PLMN = RACH_Failure_Count_to_Backoff_PLMN_2;
	 			THROTTLE_BSR = Throttled_BSR_Timer_DISABLE;
	 			KICKOFF_BSR = Kickoff_BSR_Scan_DISABLE;
	 			LTE_BLACKLIST_PCI = LTE_Blacklist_PCI_LG_Suggestion;
	 			ACQDB_SCAN = ACQ_DB_Scan_Scope_5;
	 			HPLMN_SCAN = HPLMN_Scan_Scope_1;
	 			SKIP_GW_DURIING_VOLTE_RLF = Skip_GW_Scan_During_VoLTE_RLF_DISABLE; //change default value = disable
	 			QCT_D2LREDIR = D2LREDIR_ENABLE;
	 			
	 			//1xIA
	 			Enh_1xAcc_Pwr = CDMA_Access_Power_Control_LG_Enhanced;
	 			Enh_1xAcc_Delay = CDMA_Access_Delay_Control_LG_Enhanced;
	 			Enh_1xAcc_SR_delay = CDMA_SR_Delay_Control_LG_Enhanced;
	 			
	 			//VAP
	 			VAP_AMT = VAP_Enable;
	 			VAP_Skip_GW_Scan_during_VoLTE_call = VAP_Enable;
	 			VAP_Skip_1xDO_Scan_during_VoLTE_call = VAP_Enable;
	 			VAP_Unlimited_RACH_during_VoLTE_call = VAP_Enable;
	 			VAP_TAU_for_VZW_only_during_VoLTE_call = VAP_Enable;
	 			VAP_Force_RLF_during_VoLTE_call = 2;
			}
			
			//LTE Lab Test
			setImsEnable(IMS);
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IMS_TEST_MODE, Long.toString(IMS_TESTMODE));			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IPV6_SUPPORT, Long.toString(IPV6));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE, Long.toString(MODE)); 			
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SPRINT_LTE_SET_UE_USAGE, Long.toString(USAGE)); 			
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_VZW_DR_TEST_FLAG, Long.toString(APN_SWITCHING_WHEN_ROAMING));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_EDCT_TMR, Long.toString(EDCT_TIMER));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_DSDS_INACTIVITY_TMR, Long.toString(LTE_MAC_QOS_INACTIVITY_TIMER));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SPV_TIMER_CONNECTED, Long.toString(SPV_TIMER_CONNECTED));	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_T3245_TEST, Long.toString(T3245));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_DATA_APN_SWITCHING, Long.toString(DATA_APN_SWITCHING));	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_BLOCK_DNS_QUERY, Long.toString(BLOCK_DNS_QUERY));	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_CARRIER_POLICY_UE_MODE, Long.toString(OP_MODE));
 			//BSR
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_PERI_BSR, Long.toString(PERISCOPE_BSR));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_TELE_BSR, Long.toString(TELESCOPE_BSR));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_ACTIVE_EHRPD_BSR_SCAN, Long.toString(ACTIVE_TO_LTE));
 			//hVoLTE VCP 
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_ONE, Integer.toString(CALLEND_BSR_TIMER));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE, Integer.toString(SKIP_BSR_IN_CALLING));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_THREE_SECND , Integer.toString(SKIP_BSR_IN_LTE_LOST_WHEN_CALLING));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_FOUR, Integer.toString(FORCE_TO_SRLTE_WHEN_RACH_FAIL));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FIRST, Integer.toString(HYST_TO_CSFB_SIGNAL_STRENGTH));	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_SECND, Integer.toString(HYST_TO_CSFB_TIMER));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_THIRD  , Integer.toString(FORCE_TO_SRLTE_SIGNAL_STRENGTH));	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_SEVEN_FOURTH , Integer.toString(FORCE_TO_SRLTE_WHEN_IMS_FAIL_ON_UNKNOWN_MODE));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TEN, Integer.toString(FORCE_TO_SRLTE_UNTIL_IMS_REG));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_AI_TWELVE, Integer.toString(FAST_1X_TRANSITION));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_HVOLTE_TASK_IMSPREFIND_HYST, Integer.toString(OP_MODE_PINGPONG_TIMER));		 			
 			//LTEconn% 
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_RACH_FAILURE_COUNT_TO_BACKOFF_PLMN, Integer.toString(BACKOFF_PLMN));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_THROTTLED_BSR_TIMER, Integer.toString(THROTTLE_BSR));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_KICKOFF_BSR_SCAN, Integer.toString(KICKOFF_BSR)); 			
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_BLACKLIST_PCI_TIMER , Integer.toString(LTE_BLACKLIST_PCI)); 			
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_N_ACQ_DB_SCAN_SCOPE, Integer.toString(ACQDB_SCAN));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_N_HPLMN_SCAN_SCOPE, Integer.toString(HPLMN_SCAN));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SKIP_GW_SCAN_DURING_RLF, Integer.toString(SKIP_GW_DURIING_VOLTE_RLF));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_UE_INIT_D2LREDIR_DISABLE, Integer.toString(QCT_D2LREDIR)); 			
 			//1xIA
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_POWER_CONTROL, Integer.toString(Enh_1xAcc_Pwr));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_DELAY_CONTROL, Integer.toString(Enh_1xAcc_Delay));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XIA_SR_DELAY_CONTROL, Integer.toString(Enh_1xAcc_SR_delay));			 			
 			//VAP
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_1X_TRANSITION, Integer.toString(VAP_AMT));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_SKIP_GW_SCAN_DURING_VOLTE_CALL, Integer.toString(VAP_Skip_GW_Scan_during_VoLTE_call));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_SKIP_1X_DO_SCAN_DURING_VOLTE_CALL, Integer.toString(VAP_Skip_1xDO_Scan_during_VoLTE_call));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_UNLIMITED_RACH_COUNT_DURING_VOLTE_CALL  , Integer.toString(VAP_Unlimited_RACH_during_VoLTE_call));	
 			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_UNLIMITED_RACH_SUCCESSIVE_OFFSET_DURING_VOLTE_CALL  , Integer.toString(tmpi));	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_TAU_FOR_VZW_ONLY_DURING_VOLTE_CALL, Integer.toString(VAP_TAU_for_VZW_only_during_VoLTE_call));
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VAP_FORCE_RLF_COUNT_DURING_VOLTE_CALL  , Integer.toString(VAP_Force_RLF_during_VoLTE_call));	
			
 			 			
 			Toast.makeText(IOT.this, "Reset...", Toast.LENGTH_SHORT ).show();
			//Log.d("LA", "ResetThread");
	        ResetThread resetThread = new ResetThread(IOT.this, 3000);
	        //Log.d("LA", "ResetThread start()");
	        resetThread.start();  
		}
		

		/* ********************* */	
		/*    IMS Test Mode      */
		/* ********************* */
		if (key.equals("IMS_Test_Mode")) {	
			tmp = sharedPreferences.getString(key, "");		
			
			if      (tmp.equals("0")) {  
				tmpb = IMS_DISABLE;
				tmpi = IMS_TEST_MODE_DISABLE;
			}			
			else if (tmp.equals("1")) {
				tmpb = IMS_ENABLE;
				tmpi = IMS_TEST_MODE_ENABLE; 
			}			
			else { 
				tmpb = IMS_ENABLE;
				tmpi = IMS_TEST_MODE_ENABLE;			 
			}
			
			setImsEnable(tmpb);
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IMS_TEST_MODE, Long.toString(tmpi));
							
			p = (ListPreference)getPreferenceScreen().findPreference("IMS_Test_Mode");
			switch (tmpi) {		
				case IMS_TEST_MODE_DISABLE: p.setSummary("Disabled (Default)"); break;						
				case IMS_TEST_MODE_ENABLE: p.setSummary("Enabled"); break;
				default: p.setSummary("Unknown Value"); break;
			}
	    }
		
		
		/* ********************* */	
		/*    IPv6 Enable        */
		/* ********************* */
		if (key.equals("IPv6_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = IPV6_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = IPV6_ENABLE;	}			
			else { tmpi = IPV6_ENABLE; }
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IPV6_SUPPORT, Long.toString(tmpi));	
			
			p = (ListPreference)getPreferenceScreen().findPreference("IPv6_enable");			
			switch (tmpi) {		
				case IPV6_DISABLE: p.setSummary("Disabled"); break;						
				case IPV6_ENABLE: p.setSummary("Enabled (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		
		/* ********************* */	
		/*    Data Enable        */
		/* ********************* */
/*		
		if (key.equals("Data_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  setDataEnabled(false);	}		
			else if (tmp.equals("1")) {  setDataEnabled(true);	}			
			else { setDataEnabled(true); }
			//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IPV6_SUPPORT, Long.toString(tmpi));			
	    }
*/		
		
		/* ********************* */	
		/*    Mode Preference    */
		/* ********************* */
		if (key.equals("Mode_Preference")) {			
 			tmp = sharedPreferences.getString(key, "");					
 			if      (tmp.equals("8")) {  tmpi = LGRIL_MODE_PREF_GLOBAL_MODE;	}			
 			else if (tmp.equals("0")) {  tmpi = LGRIL_MODE_PREF_CDMA_EVDO_AUTOMATIC;	}			
 			else if (tmp.equals("3")) {  tmpi = LGRIL_MODE_PREF_CDMA_ONLY; }
 			else if (tmp.equals("2")) {  tmpi = LGRIL_MODE_PREF_EVDO_ONLY; }			
 			else if (tmp.equals("4")) {  tmpi = LGRIL_MODE_PREF_GSM_WCDMA_AUTOMATIC; }			
 			else if (tmp.equals("5")) {  tmpi = LGRIL_MODE_PREF_GSM_ONLY; }		
 			else if (tmp.equals("6")) {  tmpi = LGRIL_MODE_PREF_WCDMA_ONLY; }			
 			else if (tmp.equals("7")) {  tmpi = LGRIL_MODE_PREF_LTE_ONLY; }		
 			else if (tmp.equals("9")) {  tmpi = LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE; }			
 			else if (tmp.equals("10")) {  tmpi = LGRIL_MODE_PREF_GSMWCDMA_LTE_DUAL_MODE; }			
 			else { tmpi = LGRIL_MODE_PREF_GLOBAL_MODE; }		
 			
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE, Long.toString(tmpi));
 			
 			p = (ListPreference)getPreferenceScreen().findPreference("Mode_Preference");				
			switch (tmpi) {			
				case LGRIL_MODE_PREF_CDMA_EVDO_AUTOMATIC: p.setSummary("CDMA/EVDO"); break;
				case LGRIL_MODE_PREF_CDMA_ONLY: p.setSummary("CDMA Only"); break;		
				case LGRIL_MODE_PREF_EVDO_ONLY: p.setSummary("EVDO Only"); break;		
				case LGRIL_MODE_PREF_GSM_WCDMA_AUTOMATIC: p.setSummary("GSM/WCDMA"); break;		
				case LGRIL_MODE_PREF_GSM_ONLY: p.setSummary("GSM Only"); break;		
				case LGRIL_MODE_PREF_WCDMA_ONLY: p.setSummary("WCDMA Only"); break;		
				case LGRIL_MODE_PREF_LTE_ONLY: p.setSummary("LTE Only"); break;		
				case LGRIL_MODE_PREF_GLOBAL_MODE: p.setSummary("Global(Automatic) (Default)"); break;		
				case LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE: p.setSummary("CDMA/EVDO/LTE"); break;		
				case LGRIL_MODE_PREF_GSMWCDMA_LTE_DUAL_MODE: p.setSummary("GSM/WCDMA/LTE"); break;		
				case LGRIL_MODE_PREF_MAX_VALUE: p.setSummary("Unknow Value"); break;		
				default: p.setSummary("Unknown Value"); break;
			}
 			
 	    } 	
		
		/* ********************* */	
		/*    UE_Usage_Setting   */
		/* ********************* */
		if (key.equals("UE_Usage_Setting")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = UE_USAGE_SETTING_VOICE_CENTRIC;	}		
			else if (tmp.equals("1")) {  tmpi = UE_USAGE_SETTING_DATA_CENTRIC;	}			
			else { tmpi = UE_USAGE_SETTING_VOICE_CENTRIC; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SPRINT_LTE_SET_UE_USAGE, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("UE_Usage_Setting");				
			switch (tmpi) {		
				case UE_USAGE_SETTING_VOICE_CENTRIC: p.setSummary("Voice Centric (Default)"); break;						
				case UE_USAGE_SETTING_DATA_CENTRIC: p.setSummary("Data Centric"); break;
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		
		/* ********************* */	
		/*    APN_Switching      */
		/* ********************* */
		if (key.equals("APN_Switching_Roam_Network")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE ;	}		
			else if (tmp.equals("1")) {  tmpi = INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE  ;	}			
			else { tmpi = INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_VZW_DR_TEST_FLAG, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("APN_Switching_Roam_Network");			
			switch (tmpi) {		
				case INITIAL_APN_SWITCHING_ROAM_NETWORK_DISABLE: p.setSummary("Disable"); break;						
				case INITIAL_APN_SWITCHING_ROAM_NETWORK_ENABLE: p.setSummary("Enable (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }

		/* ********************* */	
		/*    GW_GCF_Setting     */
		/* ********************* */
		if (key.equals("GW_GCF_Setting")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) { tmpi = GW_GCF_SETTING_DISABLE; }		
			else if (tmp.equals("1")) { tmpi = GW_GCF_SETTING_ENABLE; }			
			else { tmpi = GW_GCF_SETTING_DISABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SET_GCF, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("GW_GCF_Setting");
			switch (tmpi) {		
				case GW_GCF_SETTING_DISABLE: p.setSummary("Disable (Default)"); break;						
				case GW_GCF_SETTING_ENABLE  : p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		/* ********************* */	
		/*    EVDO_Revision      */
		/* ********************* */
		if (key.equals("EVDO_Revision")) {	
			tmp = sharedPreferences.getString(key, "");
			if      (tmp.equals("0")) {  tmpi = EVDO_REVISION_0;	}			
 			else if (tmp.equals("1")) {  tmpi = EVDO_REVISION_A_WO_EHRPD;	}			
 			else if (tmp.equals("2")) {  tmpi = EVDO_REVISION_A_W_EHRPD; }
 			else if (tmp.equals("3")) {  tmpi = EVDO_REVISION_B_WO_EHRPD; }			
 			else if (tmp.equals("4")) {  tmpi = EVDO_REVISION_B_W_EHRPD; }
 			else { tmpi = EVDO_REVISION_A_W_EHRPD; }		
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_EVDO_REVISION, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("EVDO_Revision");
			switch (tmpi) {		
				case EVDO_REVISION_0: p.setSummary("Rev.0"); break;						
				case EVDO_REVISION_A_WO_EHRPD: p.setSummary("Rev.A wo eHRPD"); break;
				case EVDO_REVISION_A_W_EHRPD: p.setSummary("Rev.A w eHRPD (Default)"); break;
				case EVDO_REVISION_B_WO_EHRPD: p.setSummary("Rev.B wo eHRPD"); break;
				case EVDO_REVISION_B_W_EHRPD: p.setSummary("Rev.B w eHRPD"); break;				
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		/* ********************* */	
		/*     Timer Reset       */
		/* ********************* */	
		if (key.equals("Timer_Reset")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = 0;	}		
			else if (tmp.equals("1")) {  tmpi = 3346;	}
			else if (tmp.equals("2")) {  tmpi = 3402;	}		
			else if (tmp.equals("3")) {  tmpi = 3442;	}				
			else { tmpi = 0; }
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_RESET_TIMERS, Long.toString(tmpi));		
			
			Toast.makeText(IOT.this, "Reset...", Toast.LENGTH_SHORT ).show();
			//Log.d("LA", "ResetThread");
	        ResetThread resetThread = new ResetThread(IOT.this, 3000);
	        //Log.d("LA", "ResetThread start()");
	        resetThread.start();  
	    }
		
		/* ********************* */	
		/*      ATC enable       */
		/* ********************* */	
		/*
		if (key.equals("ATC_enable")) {	

			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmp2 = ATC_DISABLE; }		
			else if (tmp.equals("1")) {  tmp2 = ATC_ENABLE;	}			
			else { tmp2 = ATC_ENABLE; }			
			
			if( tmp2 == ATC_ENABLE){
				mATClient.request(4050, "1".getBytes());	
			} else {
				mATClient.request(4050, "0".getBytes());	
			}
	    }
	    */
		
		/* ********************* */	
		/*    ECM Mode           */
		/* ********************* */
/*		
		if (key.equals("ECM_mode")) {	
			tmp = sharedPreferences.getString(key, "");			
			
			if      (tmp.equals("1")) {  
				//  Autorun OFF
				Settings.System.putInt(getContentResolver(), SettingsConstants.System.AUTORUN_SWITCH, 0);
                sendBroadcast(new Intent(LGIntent.AUTORUN_ENABLE_CHANGED));

				SystemProperties.set("persist.sys.usb.config", UsbManagerConstants.USB_FUNCTION_TETHER + ",adb");				
            }else if (tmp.equals("0")) {  
				// Autorun ON            	
            	Settings.System.putInt(getContentResolver(), SettingsConstants.System.AUTORUN_SWITCH, 1);
                sendBroadcast(new Intent(LGIntent.AUTORUN_ENABLE_CHANGED));

				SystemProperties.set("persist.sys.usb.config", "boot,adb");				
			}else { 
				SystemProperties.set("persist.sys.usb.config", UsbManagerConstants.USB_FUNCTION_TETHER + ",adb");
			}	
			
			
			String usbConfig = SystemProperties.get("persist.sys.usb.config");
			p = (ListPreference)getPreferenceScreen().findPreference("ECM_mode");
            if (usbConfig != null && usbConfig.contains(UsbManagerConstants.USB_FUNCTION_TETHER)) {
                //mECMModeCtrlBtn.setChecked(true);
            	p.setSummary("Enable");
            } else {
                //mECMModeCtrlBtn.setChecked(false);
            	p.setSummary("Disable (Default)");
            }

	    }
		
/*		
		if (key.equals("ECM_mode")) {	
			tmp = sharedPreferences.getString(key, "");					
			if      (tmp.equals("1")) {  
				updateVoLTETestSetting(1);		
            }else if (tmp.equals("0")) {  
            	updateVoLTETestSetting(0);
                //updateVoLTEOfftoUcStateDB();
			}else { 

			}	
	    }

		
		if (key.equals("ECM_mode")) {	
			tmp = sharedPreferences.getString(key, "");						
			if      (tmp.equals("1")) {
				//RegStateManager.getInstance().updateVoiceDomain(true);
                mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IMS_HVOLTE_ADAPTIVE_RAT_PRIORITY_ORDER, "1");
            }else if (tmp.equals("0")) {  
            	 //RegStateManager.getInstance().updateVoiceDomain(false);
                 mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_IMS_HVOLTE_ADAPTIVE_RAT_PRIORITY_ORDER, "0");
			}else { 

			}	
	    }
*/		
/*
		newValue = 4;
		values.put(setting_test_mask, newValue.toString());

        imsDB.beginTransaction();

        try {
            affectedRows = imsDB.update(lgims_setting, values, "id = '1'", null);
            imsDB.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            imsDB.endTransaction();
        }
*/

		
		/* ********************* */	
		/*    hVoLTE On/Off      */
		/* ********************* */
		if (key.equals("OP_Mode")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) { tmpi = OP_MODE_HVOLTE; }		
			else if (tmp.equals("2")) { tmpi = OP_MODE_SRLTE; }			
			else if (tmp.equals("3")) { tmpi = OP_MODE_CSFB; }			
			else { tmpi = OP_MODE_HVOLTE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_CARRIER_POLICY_UE_MODE, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("OP_Mode");
			switch (tmpi) {		
				case OP_MODE_HVOLTE: p.setSummary("VZW hVoLTE"); break;						
				case OP_MODE_SRLTE  : p.setSummary("VZW SRLTE"); break;
				case OP_MODE_CSFB  : p.setSummary("VZW CSFB"); break;
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		
		
		
		/* ************************* */	
		/*  Check_EHPLMN_List Flag   */
		/* ************************* */
		if (key.equals("Check_EHPLMN_List")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) { tmpi = CHECK_EHPLMN_LIST_FLAG_NOT_CHECK_SIM_EHPLMN; }		
			else if (tmp.equals("1")) { tmpi = CHECK_EHPLMN_LIST_FLAG_CHECK_SIM_EHPLMN; }			
			else { tmpi = CHECK_EHPLMN_LIST_FLAG_NOT_CHECK_SIM_EHPLMN; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_CHECK_EHPLMN_LIST_FLAG, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Check_EHPLMN_List");
			switch (tmpi) {		
				case CHECK_EHPLMN_LIST_FLAG_NOT_CHECK_SIM_EHPLMN: p.setSummary("Not Check SIM¡¯s eHPLMN"); break;						
				case CHECK_EHPLMN_LIST_FLAG_CHECK_SIM_EHPLMN  : p.setSummary("Check SIM¡¯s eHPLMN (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("CSG_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = CSG_DISABLE; }		
			else if (tmp.equals("1")) {  tmpi = CSG_ENABLE;	}			
			else { tmpi = CSG_DISABLE; }
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_CSG_ENABLE_FLAG, Long.toString(tmpi));	
			
			p = (ListPreference)getPreferenceScreen().findPreference("CSG_enable");			
			switch (tmpi) {		
				case CSG_DISABLE: p.setSummary("Disabled"); break;						
				case CSG_ENABLE: p.setSummary("Enabled (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		if (key.equals("AG_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = AG_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = AG_ENABLE;	}			
			else { tmpi = AG_DISABLE; }
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_AUTONOMOUS_GAP_ENABLE_FLAG, Long.toString(tmpi));	
			
			p = (ListPreference)getPreferenceScreen().findPreference("AG_enable");			
			switch (tmpi) {		
				case AG_DISABLE: p.setSummary("Disabled)"); break;						
				case AG_ENABLE: p.setSummary("Enabled (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}
			
	    }
		
		if (key.equals("SPV_Timer_Connected")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = SPV_TIMER_CONNECTED_DEFAULT;	}		
			else if (tmp.equals("1")) {  tmpi = SPV_TIMER_CONNECTED_TEST;	}			
			else { tmpi = SPV_TIMER_CONNECTED_DEFAULT; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_SPV_TIMER_CONNECTED, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("SPV_Timer_Connected");			
			switch (tmpi) {		
				case SPV_TIMER_CONNECTED_DEFAULT: p.setSummary("5 (Default)"); break;						
				case SPV_TIMER_CONNECTED_TEST: p.setSummary("FFFF (Test)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("T3245_Test")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = T3245_DEFAULT;	}		
			else if (tmp.equals("1")) {  tmpi = T3245_TEST;	}			
			else { tmpi = T3245_DEFAULT; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_T3245_TEST, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("T3245_Test");			
			switch (tmpi) {		
				case T3245_DEFAULT: p.setSummary("Default"); break;						
				case T3245_TEST: p.setSummary("1800 (Test)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("CDMA1xSIB8_Scan_Option")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = CDMA1XSIB8_SCAN_OPTION_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = CDMA1XSIB8_SCAN_OPTION_ENABLE;	}			
			else { tmpi = CDMA1XSIB8_SCAN_OPTION_ENABLE; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_1XSIB8_SCAN_OPTION, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("CDMA1xSIB8_Scan_Option");			
			switch (tmpi) {		
				case CDMA1XSIB8_SCAN_OPTION_DISABLE: p.setSummary("Disable"); break;						
				case CDMA1XSIB8_SCAN_OPTION_ENABLE: p.setSummary("Enable (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("Data_APN_Switching")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = DATA_APN_SWITCHING_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = DATA_APN_SWITCHING_ENABLE;	}			
			else { tmpi = DATA_APN_SWITCHING_ENABLE; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_DATA_APN_SWITCHING, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Data_APN_Switching");			
			switch (tmpi) {		
				case DATA_APN_SWITCHING_DISABLE: p.setSummary("Disable"); break;						
				case DATA_APN_SWITCHING_ENABLE: p.setSummary("Enable (Default)"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("CDG2_Flag")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = CDG2_FLAG_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = CDG2_FLAG_ENABLE;	}			
			else { tmpi = CDG2_FLAG_DISABLE; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_CDG2_TEST_FLAG, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("CDG2_Flag");			
			switch (tmpi) {		
				case CDG2_FLAG_DISABLE: p.setSummary("Disable (Default)"); break;						
				case CDG2_FLAG_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("Block_DNS_Query")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = BLOCK_DNS_QUERY_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = BLOCK_DNS_QUERY_ENABLE;	}			
			else { tmpi = BLOCK_DNS_QUERY_DISABLE; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_BLOCK_DNS_QUERY, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Block_DNS_Query");			
			switch (tmpi) {		
				case BLOCK_DNS_QUERY_DISABLE: p.setSummary("Disable (Default)"); break;						
				case BLOCK_DNS_QUERY_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("Dummy_IMS_Pref_Send_Flag")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = DUMMY_IMS_PREF_SEND_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = DUMMY_IMS_PREF_SEND_ENABLE;	}			
			else { tmpi = DUMMY_IMS_PREF_SEND_DISABLE; }			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_DUMMY_IMS_PREF_SEND_FLAG, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("Dummy_IMS_Pref_Send_Flag");			
			switch (tmpi) {		
				case DUMMY_IMS_PREF_SEND_DISABLE: p.setSummary("Disable (LG Default)"); break;						
				case DUMMY_IMS_PREF_SEND_ENABLE: p.setSummary("Enable"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("GUTI_Delete")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  
				tmpi = GUTI_DELETE_ENABLE;
				//tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_GUTI_DELETE);	
				mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_GUTI_DELETE, Long.toString(tmpi));
			}		
			else { tmpi = GUTI_DELETE_DISABLE; }			
			//tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_GUTI_DELETE);
						
			
			p = (ListPreference)getPreferenceScreen().findPreference("GUTI_Delete");			
			switch (tmpi) {		
				case GUTI_DELETE_DISABLE: p.setSummary("Cancel"); break;						
				case GUTI_DELETE_ENABLE: p.setSummary("GUTI Deleted"); break;
				default: p.setSummary("Cancel"); break;
			}
	    }
		
		if (key.equals("MRU_RAL_Clear")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("1")) {  
				tmpi = ENABLE;
				//tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_MRU_RAL_CLEAR);	
				mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MRU_RAL_CLEAR, Long.toString(tmpi));
			}		
			else { tmpi = DISABLE; }			
			//tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_GUTI_DELETE);
						
			p = (ListPreference)getPreferenceScreen().findPreference("MRU_RAL_Clear");			
			switch (tmpi) {		
				case DISABLE: p.setSummary("Cancel"); break;						
				case ENABLE: p.setSummary("MRU/RAL Clear"); break;
				default: p.setSummary("Cancel"); break;
			}
	    }
		
		/* ********************* */	
		/*    BSR      
		public static final int BSR_DISABLE = 0;
		public static final int BSR_ENABLE_VZW  = 1;
		public static final int BSR_ENABLE_SPCS  = 2;
		*/
		/* ********************* */
		if (key.equals("PERISCOPE_BSR_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = BSR_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = BSR_ENABLE_VZW;	}	
			else if (tmp.equals("2")) {  tmpi = BSR_ENABLE_SPCS;	}	
			else { tmpi = BSR_DISABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_PERI_BSR, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("PERISCOPE_BSR_enable");			
			switch (tmpi) {		
				case BSR_DISABLE: p.setSummary("Disable"); break;						
				case BSR_ENABLE_VZW: p.setSummary("VZW"); break;
				case BSR_ENABLE_SPCS: p.setSummary("Sprint"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("TELESCOPE_BSR_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = BSR_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = BSR_ENABLE_VZW;	}	
			else if (tmp.equals("2")) {  tmpi = BSR_ENABLE_SPCS;	}	
			else { tmpi = BSR_DISABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_TELE_BSR, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("TELESCOPE_BSR_enable");			
			switch (tmpi) {		
				case BSR_DISABLE: p.setSummary("Disable"); break;						
				case BSR_ENABLE_VZW: p.setSummary("VZW"); break;
				case BSR_ENABLE_SPCS: p.setSummary("Sprint"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		
		if (key.equals("ACTIVE_EHRPD_TO_LTE_TRANSITION_enable")) {	
			tmp = sharedPreferences.getString(key, "");
			
			if      (tmp.equals("0")) {  tmpi = BSR_DISABLE;	}		
			else if (tmp.equals("1")) {  tmpi = BSR_ENABLE_VZW;	}	
			else if (tmp.equals("2")) {  tmpi = BSR_ENABLE_SPCS;	}	
			else { tmpi = BSR_DISABLE; }
			
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_ACTIVE_EHRPD_BSR_SCAN, Long.toString(tmpi));
			
			p = (ListPreference)getPreferenceScreen().findPreference("ACTIVE_EHRPD_TO_LTE_TRANSITION_enable");			
			switch (tmpi) {		
				case BSR_DISABLE: p.setSummary("Disable"); break;						
				case BSR_ENABLE_VZW: p.setSummary("VZW"); break;
				case BSR_ENABLE_SPCS: p.setSummary("Sprint"); break;
				default: p.setSummary("Unknown Value"); break;
			}			
	    }
		

		
		


		
		/* ********************* */	
		/*      Reboot           */
		/* ********************* */	
		/*
		if (key.equals("Reboot_the_device")) 
		{	
			tmp = sharedPreferences.getString(key, "");
	        Toast.makeText(IOT.this, "Reset...", Toast.LENGTH_SHORT ).show();
			//Log.d("LA", "ResetThread");
	        ResetThread resetThread = new ResetThread(IOT.this, 3000);
	        //Log.d("LA", "ResetThread start()");
	        resetThread.start(); 
	    }		
		*/	
		
		
		/* ********************************************************************/
	    /*                              REBOOT                                */
	    /* ********************************************************************/
		/*
		Toast.makeText(IOT.this, "Reset...", Toast.LENGTH_SHORT ).show();
		//Log.d("LA", "ResetThread");
        ResetThread resetThread = new ResetThread(IOT.this, 3000);
        //Log.d("LA", "ResetThread start()");
        resetThread.start();   
  		*/
	}
	
	public void registerChangeListener() {
		mPreferenceScreen = (PreferenceScreen) findPreference ("reboot");
		mPreferenceScreen.setOnPreferenceClickListener(new SaveEventListener());
	}

    private class SaveEventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
		
			Toast.makeText(IOT.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();			
			//3. reboot.
			ResetThread resetThread = new ResetThread(IOT.this, 3000);
	        resetThread.start();    
			
			return false;
		}
	}
	
	
	/* *********************************************************************/
    /*                             LIBRARY                                 */
    /*                       COPY FROM PHONE SOURCE ...                    */
	/* *********************************************************************/

	private void setImsEnable(boolean bEnable )
	{
		Uri IMS_CONTENT_URI = Uri.parse("content://com.lge.ims.provider.lgims/lgims_subscriber");

		ContentResolver cr = this.getContentResolver();
		Cursor cursor = null;

		try
		{
			cursor= cr.query(IMS_CONTENT_URI, null, null, null, null);
			if( cursor == null)
			{
				Log.d("IMS", "cursor is null");
				return;
			}

			// set subscribe data on IMS DB
			ContentValues newValues = new ContentValues();
			if( bEnable == true )
			{
				newValues.put("admin_ims", "true");
			}
			else
			{
				newValues.put("admin_ims", "false");			
			}

			cr.update(IMS_CONTENT_URI, newValues, null, null);
			
		}
		catch (Exception e)
		{
			Log.d("IMS", "Exception = " + e);
		}
		finally
		{
			if( cursor != null )
				cursor.close();
		}
	}
	
/*	
    private void updateVoLTETestSetting(int volteOn) {
        String AUTHORITY = "com.android.phone.CallSettingsProvider";
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/callsettings");
        String KEY_ID = "_id";
        String KEY_NAME = "name";
        String KEY_VALUE_STR = "value_str";;
        String KEY_VALUE_INT = "value_int";
        String KEY = "vzw_volte_test_key";
        String selection = KEY_NAME + "=" + "'" + KEY + "'";
        ContentValues cv = new ContentValues();

        cv.put(KEY_VALUE_STR, "");
        cv.put(KEY_VALUE_INT, volteOn);

        try {
            getApplicationContext().getContentResolver().update(CONTENT_URI, cv, selection, null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
        }
    }
*/
    
/*
    @SystemApi
    public void setDataEnabled(boolean enable) {

        long subId = 1;
        
        setDataEnabledUsingSubId(subId, enable);

    }

    @SystemApi
    public void setDataEnabledUsingSubId(long subId, boolean enable) {
    	try {
            AppOpsManager appOps = (AppOpsManager)mContext.getSystemService(Context.APP_OPS_SERVICE);
            if (enable) {
                if (appOps.noteOp(AppOpsManager.OP_DATA_CONNECT_CHANGE) != AppOpsManager.MODE_ALLOWED) {
                    Log.w(TAG, "Permission denied by user.");


                   if(LgDataFeature.DataFeature.LGP_DATA_CONNECTIVITYSERVICE_CTTL_CMCC.getValue()) {
                         if (mLgDataUiManager != null) {
                               mLgDataUiManager.sendDataUiMessage(LgDataUiManager.EVENT_CTTL_DATA_ENABLE_POPUP);
                         }
                   }
   
                   return;
                }
            }


            int uid = Binder.getCallingUid();
            PackageManager pm = mContext.getPackageManager();
            String uidname = pm.getNameForUid(uid);

            Log.d(TAG, "[LG_DATA] setMobileDataEnabled CallingUid(" + uid + ")");
            Log.d(TAG, "[LG_DATA] setMobileDataEnabled CallingUidName(" + uidname  + ")");

            Intent intent = new Intent(TelephonyManager.ACTION_DATA_CALLING_SETMOBILE);
            intent.putExtra("CallingPackagesName", uidname);
            intent.putExtra("enabled", enable);
            mContext.sendBroadcast(intent);


            getITelephony().setDataEnabledUsingSubId(subId, enable);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling setDataEnabled", e);
        }
    }
*/
/*	
	//public void setMobileDataState(boolean mobileDataEnabled)
	public void setDataEnabled(boolean enable) {
	{
	    try
	    {
	        TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

	        Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

	        if (null != setMobileDataEnabledMethod)
	        {
	            setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
	        }
	    }
	    catch (Exception ex)
	    {
	        Log.e(TAG, "Error setting mobile data state", ex);
	    }
	}
*/
	
}

