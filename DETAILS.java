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

import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
//import com.android.internal.telephony.TelephonyIntents;
//import com.android.internal.telephony.TelephonyProperties;
//import com.android.internal.telephony.PhoneConstants;


//import android.os.SystemProperties;
//import android.os.AsyncResult;
import android.os.Message;




import com.lge.la.utils.ResetThread;

public class DETAILS extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	SharedPreferences prefs;
	private ListPreference p;
	
	private static final String TAG = "LabApplication";

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
    
    
    //MCFG LGP selection - lgrilhook_get_mcfg_val
    /* typedef enum
    {
        LGE_NO_MCFG = 0x00,
        LGE_VZW_MCFG,
        LGE_SPR_MCFG,
        LGE_USC_MCFG,
        LGE_ACG_MCFG,
        LGE_ATT_MCFG,
        LGE_INVAILD_MCFG,
        LGE_NO_INITIALIZED,
    } lge_mcfg_type; //temp soo
    */
    public static final int LGE_NO_MCFG	= 0;
    public static final int LGE_VZW_MCFG = 1;
    public static final int LGE_SPR_MCFG = 2;
    public static final int LGE_USC_MCFG = 3;
    public static final int LGE_ACG_MCFG = 4;
    public static final int LGE_ATT_MCFG = 5;
    public static final int LGE_INVAILD_MCFG = 6;
    public static final int LGE_NO_INITIALIZED = 7;
    public static final int LGE_DEFAULT_MCFG = 99;

    
    
    //Masking bit
    //13   12  11  10  09  08 07 06 05 04 03 02 01 00
    //1000 800 400 200 100 80 40 20 10 08 04 02 01 00
    
    private CheckBoxPreference mDetailsCheckBoxPreference;
 	private PreferenceScreen mDetailsPreferenceScreen;
 	
 	/*
 	 * Read Option
 	 */
 	private final int mReadOption = 1; //Common value
 	//band_pref
 	private final int mCurrentUsingBand = 0;
	private final int mCurrentBandData = 1; //lte_bandpref
	private final int mCurrentBandData_extn = 2; //lte_bandpref_extn_65_256

 	
	
	
    //
    //band preference
    //
	private int mBandKeyCnt_total = 14;
	private int mBandKeyCnt = 13;	
	private int mBandKeyCnt_extn = 1;	
	private boolean Using_Band[] = {
		true, //1
		true, //2
		true, //3
		true, //4
		true, //5
		true, //6
		true, //7
		true, //8
		true, //9
		true, //10
		true, //11
		true, //12
		true, //13
		true //14	
	};
	
	//band 1 ~ 64
	private String Band_Keys[] = {
		"key_lte_band_1",	
		"key_lte_band_2",
		"key_lte_band_3",
		"key_lte_band_4",
		"key_lte_band_5",			
		"key_lte_band_7",
		"key_lte_band_12",	
		"key_lte_band_13",
		"key_lte_band_17",	
		"key_lte_band_20",
		"key_lte_band_25",
		"key_lte_band_26",	
		"key_lte_band_41"
	};
	private long Band_Masking_Value[] = {
		0x0001, //B1
		0x0002, //B2
		0x0004, //B3
		0x0008, //B4		
		0x0010, //B5
		//0x0020, //B6
		0x0040, //B7
		//0x0080, //B8		
		//0x0100, //B9
		//0x0200, //B10
		//0x0400, //B11
		0x0800, //B12		
		0x1000, //B13
		//0x2000, //B14
		//0x4000, //B15
		//0x8000, //B16			
		0x10000, //B17
		//0x20000, //B18
		//0x40000, //B19
		0x80000, //B20		   
		//0x100000, //B21
		//0x200000, //B22
		//0x400000, //B23
		//0x800000, //B24		  
		0x1000000, //B25
		0x2000000, //B26
		//0x4000000, //B27
		//0x8000000, //B28		  
		//0x10000000, //B29
		//0x20000000, //B30
		//0x40000000, //B31
		//0x80000000, //B32		  
		//0x100000000L, //B33
		//0x200000000L, //B34
		//0x400000000L, //B35
		//0x800000000L, //B36		  
		//0x1000000000L, //B37
		//0x2000000000L, //B38
		//0x4000000000L, //B39
		//0x8000000000L, //B40			  
		0x10000000000L //B41
		//0x20000000000L, //B42
		//0x40000000000L, //B43
		//0x80000000000L, //B44		
		//0x100000000000L, //B45
		//0x200000000000L, //B46
		//0x400000000000L, //B47
		//0x800000000000L, //B48		
		//0x1000000000000L, //B49
		//0x2000000000000L, //B50
		//0x4000000000000L, //B51
		//0x8000000000000L, //B52		  
		//0x10000000000000L, //B53
		//0x20000000000000L, //B54
		//0x40000000000000L, //B55
		//0x80000000000000L, //B56		  
		//0x100000000000000L, //B57
		//0x200000000000000L, //B58
		//0x400000000000000L, //B59
		//0x800000000000000L, //B60		  
		//0x1000000000000000L, //B61
		//0x2000000000000000L, //B62
		//0x4000000000000000L, //B63
		//0x8000000000000000L, //B64
	};
	
	//band 65 ~ 128
	private String Band_Keys_extn[] = {
		"key_lte_band_66"
	};
	private long Band_Masking_Value_extn[] = {
		/* LTE Band 65 ~ 128, +64 */
		//0x0001, //B1
		0x0002 //B2
		//0x0004, //B3
		//0x0008, //B4		
		//0x0010, //B5
		//0x0020, //B6
		//0x0040, //B7
		//0x0080, //B8		
		//0x0100, //B9
		//0x0200, //B10
		//0x0400, //B11
		//0x0800, //B12		
		//0x1000, //B13
		//0x2000, //B14
		//0x4000, //B15
		//0x8000, //B16			
		//0x10000, //B17
		//0x20000, //B18
		//0x40000, //B19
		//0x80000, //B20		
		//0x100000, //B21
		//0x200000, //B22
		//0x400000, //B23
		//0x800000, //B24		  
		//0x1000000, //B25
		//0x2000000, //B26
		//0x4000000, //B27
		//0x8000000, //B28		  
		//0x10000000, //B29
		//0x20000000, //B30
		//0x40000000, //B31
		//0x80000000, //B32		  
		//0x100000000L, //B33
		//0x200000000L, //B34
		//0x400000000L, //B35
		//0x800000000L, //B36		  
		//0x1000000000L, //B37
		//0x2000000000L, //B38
		//0x4000000000L, //B39
		//0x8000000000L, //B40			  
		//0x10000000000L, //B41
		//0x20000000000L, //B42
		//0x40000000000L, //B43
		//0x80000000000L, //B44		
		//0x100000000000L, //B45
		//0x200000000000L, //B46
		//0x400000000000L, //B47
		//0x800000000000L, //B48		
		//0x1000000000000L, //B49
		//0x2000000000000L, //B50
		//0x4000000000000L, //B51
		//0x8000000000000L, //B52		  
		//0x10000000000000L, //B53
		//0x20000000000000L, //B54
		//0x40000000000000L, //B55
		//0x80000000000000L, //B56		  
		//0x100000000000000L, //B57
		//0x200000000000000L, //B58
		//0x400000000000000L, //B59
		//0x800000000000000L, //B60		  
		//0x1000000000000000L, //B61
		//0x2000000000000000L, //B62
		//0x4000000000000000L, //B63
		//0x8000000000000000L, //B64
	};	
	
	
	//
	//roam preference
	//
	/*
	private CheckBoxPreference mRoamCheckBoxPreference;
	private PreferenceScreen mRoamPreferenceScreen;
	private int mRoamKeyCnt = 6;
	private final int mCurrentUsingRoam = 0;
	private final int mCurrentRoamData = 1;
	private String Roam_Keys[] = {
		"key_lte_roam_2",
		"key_lte_roam_3",
		"key_lte_roam_4",
		"key_lte_roam_5",
		"key_lte_roam_7",
		"key_lte_roam_13"
	};

	private int Roam_Masking_Value[] = {
		0x0002,
		0x0004,
		0x0008,
		0x0010,
		0x0040,
		0x1000
	};
	private boolean Using_Roam[] = {
		true,
		true,
		true,
		true,
		true,
		true
	};
	*/	
	
	
	//13   12  11  10  09  08 07 06 05 04 03 02 01 00
    //1000 800 400 200 100 80 40 20 10 08 04 02 01 00

	/*
    //CA2 preference
    //
    private CheckBoxPreference mCA2CheckBoxPreference;
	private PreferenceScreen mCA2PreferenceScreen;
	//private int mCA2KeyCnt = 6;
	private int mCA2KeyCnt = 11;
	private final int mCurrentUsingCA2 = 0;
	private final int mCurrentCA2Data = 1;
	private String CA2_Keys[] = {
			"key_lte_ca2_1",
		"key_lte_ca2_2",
		"key_lte_ca2_3",
		"key_lte_ca2_4",
		"key_lte_ca2_5",
		"key_lte_ca2_7",
			"key_lte_ca2_12",
		"key_lte_ca2_13",
			"key_lte_ca2_17",
			"key_lte_ca2_20",
			"key_lte_ca2_25"
	};

	private int CA2_Masking_Value[] = {
			0x00000001,
		0x00000002,
		0x00000004,
		0x00000008,
		0x00000010,
		0x00000040,
			0x00000800,
		0x00001000,
			0x00010000,
			0x00080000,
			0x10000000		
	};
	private boolean Using_CA2[] = {
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true
	};
	
	//
    //CA4 preference
    //
    private CheckBoxPreference mCA4CheckBoxPreference;
	private PreferenceScreen mCA4PreferenceScreen;
	//private int mCA4KeyCnt = 6;
	private int mCA4KeyCnt = 11;
	private final int mCurrentUsingCA4 = 0;
	private final int mCurrentCA4Data = 1;
	private String CA4_Keys[] = {
			"key_lte_ca4_1",
		"key_lte_ca4_2",
		"key_lte_ca4_3",
		"key_lte_ca4_4",
		"key_lte_ca4_5",
		"key_lte_ca4_7",
			"key_lte_ca4_12",
		"key_lte_ca4_13",
			"key_lte_ca4_17",
			"key_lte_ca4_20",
			"key_lte_ca4_25"
	};

	private int CA4_Masking_Value[] = {
			0x00000001,
		0x00000002,
		0x00000004,
		0x00000008,
		0x00000010,
		0x00000040,
			0x00000800,
		0x00001000,
			0x00010000,
			0x00080000,
			0x10000000
	};
	private boolean Using_CA4[] = {
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true
	};
	
	
	//
    //CA5 preference
    //
    private CheckBoxPreference mCA5CheckBoxPreference;
	private PreferenceScreen mCA5PreferenceScreen;
	//private int mCA4KeyCnt = 6;
	private int mCA5KeyCnt = 11;
	private final int mCurrentUsingCA5 = 0;
	private final int mCurrentCA5Data = 1;
	private String CA5_Keys[] = {
			"key_lte_ca5_1",
		"key_lte_ca5_2",
		"key_lte_ca5_3",
		"key_lte_ca5_4",
		"key_lte_ca5_5",
		"key_lte_ca5_7",
			"key_lte_ca5_12",
		"key_lte_ca5_13",
			"key_lte_ca5_17",
			"key_lte_ca5_20",
			"key_lte_ca5_25"
	};

	private int CA5_Masking_Value[] = {
			0x00000001,
		0x00000002,
		0x00000004,
		0x00000008,
		0x00000010,
		0x00000040,
			0x00000800,
		0x00001000,
			0x00010000,
			0x00080000,
			0x10000000
	};
	private boolean Using_CA5[] = {
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			true
	};
	
	//
    //CA13 preference
    //
    private CheckBoxPreference mCA13CheckBoxPreference;
	private PreferenceScreen mCA13PreferenceScreen;
	//private int mCA13KeyCnt = 5;
	private int mCA13KeyCnt = 11;
	private final int mCurrentUsingCA13 = 0;
	private final int mCurrentCA13Data = 1;
	private String CA13_Keys[] = {		
			"key_lte_ca13_1",
		"key_lte_ca13_2",
		"key_lte_ca13_3",
		"key_lte_ca13_4",
		"key_lte_ca13_5",
		"key_lte_ca13_7",
			"key_lte_ca13_12",
		"key_lte_ca13_13",
			"key_lte_ca13_17",
			"key_lte_ca13_20",
			"key_lte_ca13_25"
	};

	private int CA13_Masking_Value[] = {
			0x00000001,
		0x00000002,
		0x00000004,
		0x00000008,
		0x00000010,
		0x00000040,
			0x00000800,
		0x00001000,
			0x00010000,
			0x00080000,
			0x10000000
	};
	private boolean Using_CA13[] = {
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true
	};
	*/	  
	
	private LgSvcCmd mLgSvcCmd;
	private Handler mLgSvcItemsHandler;
	private Runnable lgScvItemsRunnable;
	
	static final int PREFERRED_NETWORKMODE = Phone.PREFERRED_NT_MODE;
	private Phone mPhone;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      

        addPreferencesFromResource(R.layout.details);  //Radio box
        addPreferencesFromResource(R.layout.details_cb);  //Check box
        //addPreferencesFromResource(R.layout.lte_band_preference); //band preference
        //addPreferencesFromResource(R.layout.lte_roam_preference); //roam preference
        //addPreferencesFromResource(R.layout.lte_ca2_preference); //CA2 preference
        //addPreferencesFromResource(R.layout.lte_ca4_preference); //CA4 preference
        //addPreferencesFromResource(R.layout.lte_ca5_preference); //CA5 preference
        //addPreferencesFromResource(R.layout.lte_ca13_preference); //CA13 preference
        //addPreferencesFromResource(R.layout.lte_ca41_preference); //CA13 preference
        //addPreferencesFromResource(R.layout.lte_ca66_preference); //CA13 preference
        //addPreferencesFromResource(R.layout.fdd_cap_r11);
        //addPreferencesFromResource(R.layout.fgi);
        //addPreferencesFromResource(R.layout.fgi_rel9);
        //addPreferencesFromResource(R.layout.fgi_r10);
        //addPreferencesFromResource(R.layout.reboot);
        
        mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());   
        
        System.out.println("onCreate"); 
        
        Log.v(TAG,"[LA] " + "DETAILS.java");
        
        
        /* Radio box */
        //Mode pref
        getMode_Preference();
        
        //MCFG pref
        getMCFG_LGP_selection();
        
        
        /* Check box */
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus
        /* checkbox enable read NV/EFS data */
        setUsingBand();
        //setUsingRoam();
        //setUsingCA2();
        //setUsingCA4();
        //setUsingCA5();
        //setUsingCA13();        

        
        /* checkbox check read NV/EFS data */
		setInitValue();
		
        /* listening checkbox change */
		//registerBandChangeListener();
		//registerRoamChangeListener();
		//registerCA2ChangeListener();
		//registerCA4ChangeListener();
		//registerCA5ChangeListener();
		//registerCA13ChangeListener();
		//register_fdd_cap_r11_ChangeListener();
		//register_fgi_r10_ChangeListener();
		registerChangeListener();
	}
    
    public void onDestroy() {		
		super.onDestroy();		
	}
    
    @Override
    protected void onResume() {
        super.onResume();         
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);        
        System.out.println("onResume");        
    }        

    @Override
    protected void onPause() {
        super.onPause();      
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);          
        System.out.println("onPause");
    }
    
    
    
    /*
     * Read value
     */    
    private void getMode_Preference() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("Mode_Preference");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE);			
		
			if (tmp != null){
				//Log.d(TAG,"mode pref tmp = " + tmp);
				//setting = (int)Long.parseLong(tmp, 16);		
				setting = Integer.parseInt(tmp);
				//setting = String.parseInt(tmp);
								
				Log.d(TAG,"[LA] mode_pref (radio) = " + setting);
				//if(setting <10)
				//{				
				switch (setting) {			
				//switch (tmp) {
					case LGRIL_MODE_PREF_CDMA_EVDO_AUTOMATIC: p.setSummary("CDMA/EVDO"); break;
					case LGRIL_MODE_PREF_CDMA_ONLY: p.setSummary("CDMA Only"); break;		
					case LGRIL_MODE_PREF_EVDO_ONLY: p.setSummary("EVDO Only"); break;		
					case LGRIL_MODE_PREF_GSM_WCDMA_AUTOMATIC: p.setSummary("GSM/WCDMA"); break;		
					case LGRIL_MODE_PREF_GSM_ONLY: p.setSummary("GSM Only"); break;		
					case LGRIL_MODE_PREF_WCDMA_ONLY: p.setSummary("WCDMA Only"); break;		
					case LGRIL_MODE_PREF_LTE_ONLY: p.setSummary("LTE Only"); break;		
					case LGRIL_MODE_PREF_GLOBAL_MODE: p.setSummary("Global (Default)"); break;		
					case LGRIL_MODE_PREF_CDMAEVDO_LTE_DUAL_MODE: p.setSummary("CDMA/EVDO/LTE"); break;
					case LGRIL_MODE_PREF_GSMWCDMA_LTE_DUAL_MODE: p.setSummary("GSM/WCDMA/LTE"); break;		
					case LGRIL_MODE_PREF_MAX_VALUE: p.setSummary("Unknow Value"); break;	
					default: p.setSummary("Unknown Value"); break;
				}
				//}
			}
		p.setValue("Unknown");	
	}    
    
    private void getMCFG_LGP_selection() {		
		String tmp;
		int setting;		
		p = (ListPreference)getPreferenceScreen().findPreference("MCFG_LGP_selection");
		tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_MCFG_MODEM);			
		
			if (tmp != null){
				//Log.d(TAG,"MCFG LGP selection (string) = " + tmp);
				//setting = (int)Long.parseLong(tmp, 16);		
				setting = Integer.parseInt(tmp);
				//setting = String.parseInt(tmp);
								
				Log.d(TAG,"[LA] mcfg_lgp (radio) = " + setting);
				//if(setting <10)
				//{				
				switch (setting) {			
				//switch (tmp) {
					case LGE_NO_MCFG: p.setSummary("No MCFG"); break;
					case LGE_VZW_MCFG: p.setSummary("VZW"); break;		
					case LGE_SPR_MCFG: p.setSummary("SPR"); break;		
					case LGE_USC_MCFG: p.setSummary("USC"); break;		
					case LGE_ACG_MCFG: p.setSummary("ACG"); break;		
					case LGE_ATT_MCFG: p.setSummary("ATT"); break;		
					case LGE_INVAILD_MCFG: p.setSummary("Invalid"); break;		
					case LGE_NO_INITIALIZED: p.setSummary("No Initialized"); break;	
					case LGE_DEFAULT_MCFG: p.setSummary("SIM based (Default)"); break;	
					default: p.setSummary("Unknown Value"); break;
				}
				//}
			}
		p.setValue("Unknown");	
	}
    
   
    public void setUsingBand() {
    	long val = read_lte_bandpref(mCurrentBandData);
		Log.d(TAG, "[LA] lte_bandpref (hex) = " + Long.toHexString(val));
		for ( int i = 0; i < mBandKeyCnt; i++ ) {
			if(Band_Masking_Value[i]==(val & Band_Masking_Value[i])){
				Log.w(TAG, "[LA] lte_bandpref Keys[i] : " + Band_Keys[i] + " enable");
				Using_Band[i] = ( Band_Masking_Value[i]==(val & Band_Masking_Value[i]) );
			}
		}
		
		long val_extn = read_lte_bandpref(mCurrentBandData_extn); 
		Log.d(TAG, "[LA] lte_bandpref_extn_65_256 (hex) = " + Long.toHexString(val_extn));
		for ( int i = 0; i < mBandKeyCnt_extn; i++ ) {
			if(Band_Masking_Value_extn[i]==(val_extn & Band_Masking_Value_extn[i])){
				Log.w(TAG, "[LA] lte_bandpref_extn_65_256 Keys[i] : " + Band_Keys_extn[i] + " enable");
				Using_Band[mBandKeyCnt+i] = ( Band_Masking_Value_extn[i]==(val_extn & Band_Masking_Value_extn[i]) );
			}
		}		

		/*
		for ( int i = 0; i < mBandKeyCnt_total; i++ ) {
			if(i < mBandKeyCnt){
				mBandCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys[i]);
				mBandCheckBoxPreference.setEnabled(Using_Band[i]);
			} 
			
			else if(i >= mBandKeyCnt){
			 	mBandCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys_extn[i-mBandKeyCnt]);	
				mBandCheckBoxPreference.setEnabled(Using_Band[i]);
			}						
		}
		*/
		for ( int i = 0; i < mBandKeyCnt_total; i++ ) {
			if(i < mBandKeyCnt){
				mDetailsCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys[i]);
				mDetailsCheckBoxPreference.setEnabled(Using_Band[i]);
			} 
			
			else if(i >= mBandKeyCnt){
				mDetailsCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys_extn[i-mBandKeyCnt]);	
				mDetailsCheckBoxPreference.setEnabled(Using_Band[i]);
			}						
		}
	}
       
        
    /*
    public void setUsingRoam() {
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus

		//1. Read Current Using Band Data through LGRIL
		int val = readRoamData(mCurrentUsingRoam);
		//Log.e(TAG, "(HexString)val = read_lte_bandpref(mCurrentUsingBand) = " + Integer.toHexString(val));

		//2. Calculating masking value, and Change Using_Band data
		for ( int i = 0; i < mRoamKeyCnt; i++ ) {
			if(Roam_Masking_Value[i]==(val & Roam_Masking_Value[i]))
				//Log.e(TAG, "Band_Keys[i] : " + Band_Keys[i] + " enable");
			Using_Roam[i] = ( Roam_Masking_Value[i]==(val & Roam_Masking_Value[i]) );
		}

		//3. Change layout about focus
		for ( int i = 0; i < mRoamKeyCnt; i++ ) {
			mRoamCheckBoxPreference = (CheckBoxPreference) findPreference (Roam_Keys[i]);
			mRoamCheckBoxPreference.setEnabled(Using_Roam[i]);
		}
	}
    
    //CA2
    public void setUsingCA2() {
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus

		//1. Read Current Using Band Data through LGRIL
    	int val = readCA2Data(mCurrentUsingCA2);
		
		//2. Calculating masking value, and Change Using_Band data
		for ( int i = 0; i < mCA2KeyCnt; i++ ) {
			if(CA2_Masking_Value[i]==(val & CA2_Masking_Value[i]))
				//Log.e(TAG, "Band_Keys[i] : " + Band_Keys[i] + " enable");
			Using_CA2[i] = ( CA2_Masking_Value[i]==(val & CA2_Masking_Value[i]) );
		}

		//3. Change layout about focus
		for ( int i = 0; i < mCA2KeyCnt; i++ ) {
			mCA2CheckBoxPreference = (CheckBoxPreference) findPreference (CA2_Keys[i]);
			mCA2CheckBoxPreference.setEnabled(Using_CA2[i]);
		}
	}

	//CA4
    public void setUsingCA4() {
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus

		//1. Read Current Using Band Data through LGRIL
    	int val = readCA4Data(mCurrentUsingCA4);
		//Log.e(TAG, "(HexString)val = readCA4Data = " + Integer.toHexString(val));
    	
		//2. Calculating masking value, and Change Using_Band data
		for ( int i = 0; i < mCA4KeyCnt; i++ ) {
			if(CA4_Masking_Value[i]==(val & CA4_Masking_Value[i]))
				//Log.e(TAG, "Band_Keys[i] : " + Band_Keys[i] + " enable");
			Using_CA4[i] = ( CA4_Masking_Value[i]==(val & CA4_Masking_Value[i]) );
		}

		//3. Change layout about focus
		for ( int i = 0; i < mCA4KeyCnt; i++ ) {
			mCA4CheckBoxPreference = (CheckBoxPreference) findPreference (CA4_Keys[i]);
			mCA4CheckBoxPreference.setEnabled(Using_CA4[i]);
			
			//Log.e(TAG, "Using_CA4[i] : " + Using_CA4[i] );
		}
	}   

    //CA5
    public void setUsingCA5() {
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus

		//1. Read Current Using Band Data through LGRIL
    	int val = readCA5Data(mCurrentUsingCA5);
		//Log.e(TAG, "(HexString)val = readCA5Data = " + Integer.toHexString(val));
    	
		//2. Calculating masking value, and Change Using_Band data
		for ( int i = 0; i < mCA5KeyCnt; i++ ) {
			if(CA5_Masking_Value[i]==(val & CA5_Masking_Value[i]))
				//Log.e(TAG, "Band_Keys[i] : " + Band_Keys[i] + " enable");
			Using_CA5[i] = ( CA5_Masking_Value[i]==(val & CA5_Masking_Value[i]) );
		}

		//3. Change layout about focus
		for ( int i = 0; i < mCA5KeyCnt; i++ ) {
			mCA5CheckBoxPreference = (CheckBoxPreference) findPreference (CA5_Keys[i]);
			mCA5CheckBoxPreference.setEnabled(Using_CA5[i]);
			
			//Log.e(TAG, "Using_CA5[i] : " + Using_CA5[i] );
		}
	}

 
    //CA13
    public void setUsingCA13() {
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus

		//1. Read Current Using Band Data through LGRIL
    	int val = readCA13Data(mCurrentUsingCA13);
		
		//2. Calculating masking value, and Change Using_Band data
		for ( int i = 0; i < mCA13KeyCnt; i++ ) {
			if(CA13_Masking_Value[i]==(val & CA13_Masking_Value[i]))
				//Log.e(TAG, "Band_Keys[i] : " + Band_Keys[i] + " enable");
			Using_CA13[i] = ( CA13_Masking_Value[i]==(val & CA13_Masking_Value[i]) );
		}

		//3. Change layout about focus
		for ( int i = 0; i < mCA13KeyCnt; i++ ) {
			mCA13CheckBoxPreference = (CheckBoxPreference) findPreference (CA13_Keys[i]);
			mCA13CheckBoxPreference.setEnabled(Using_CA13[i]);
		}
	}
    */
    
    
        
    /*
     * Setting from read value    
     */
    public void setInitValue() {
    	//LTE band pref
  	    long val_lte_band = read_lte_bandpref(mCurrentBandData);
		long val_lte_band_extn = read_lte_bandpref(mCurrentBandData_extn); 
		for ( int i = 0; i < mBandKeyCnt_total; i++ ) {
			if(i < mBandKeyCnt){
				mDetailsCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys[i]);
				mDetailsCheckBoxPreference.setChecked( Band_Masking_Value[i]==(val_lte_band & Band_Masking_Value[i]) );
			} else if(i >= mBandKeyCnt){
				mDetailsCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys_extn[i-mBandKeyCnt]);	
				mDetailsCheckBoxPreference.setChecked( Band_Masking_Value_extn[i-mBandKeyCnt]==(val_lte_band_extn & Band_Masking_Value_extn[i-mBandKeyCnt]) );
			}						
		}
		
				 		
  		/*
  		int val2 = readRoamData(mCurrentRoamData);
  		
  		for ( int i = 0; i < mRoamKeyCnt; i++ ) {
  			mRoamCheckBoxPreference = (CheckBoxPreference) findPreference (Roam_Keys[i]);
  			mRoamCheckBoxPreference.setChecked( Roam_Masking_Value[i]==(val2 & Roam_Masking_Value[i]) );
  		}
  		*/
  		
  		/*		
  		int val_ca2 = readCA2Data(mCurrentCA2Data);
  		int val_ca4 = readCA4Data(mCurrentCA4Data);
  		int val_ca5 = readCA5Data(mCurrentCA5Data);
  		int val_ca13 = readCA13Data(mCurrentCA13Data);
  		
  		for ( int i = 0; i < mCA2KeyCnt; i++ ) {
  			mCA2CheckBoxPreference = (CheckBoxPreference) findPreference (CA2_Keys[i]);
  			mCA2CheckBoxPreference.setChecked( CA2_Masking_Value[i]==(val_ca2 & CA2_Masking_Value[i]) );
  		}
  		
  		for ( int i = 0; i < mCA4KeyCnt; i++ ) {
  			mCA4CheckBoxPreference = (CheckBoxPreference) findPreference (CA4_Keys[i]);
  			mCA4CheckBoxPreference.setChecked( CA4_Masking_Value[i]==(val_ca4 & CA4_Masking_Value[i]) );
  		}
  		
  		for ( int i = 0; i < mCA5KeyCnt; i++ ) {
  			mCA5CheckBoxPreference = (CheckBoxPreference) findPreference (CA5_Keys[i]);
  			mCA5CheckBoxPreference.setChecked( CA5_Masking_Value[i]==(val_ca5 & CA5_Masking_Value[i]) );
  		}
  		
  		for ( int i = 0; i < mCA13KeyCnt; i++ ) {
  			mCA13CheckBoxPreference = (CheckBoxPreference) findPreference (CA13_Keys[i]);
  			mCA13CheckBoxPreference.setChecked( CA13_Masking_Value[i]==(val_ca13 & CA13_Masking_Value[i]) );
  		}
  		 */

    }
    
    
    /*
     * Change Listener
     */
    public void registerChangeListener() {    	
    	mDetailsPreferenceScreen = (PreferenceScreen) findPreference ("details_cb_save_and_reboot");
    	mDetailsPreferenceScreen.setOnPreferenceClickListener(new SaveEventListener());  
    }  
    /*
    public void registerBandChangeListener() {
		mBandPreferenceScreen = (PreferenceScreen) findPreference ("band_key_save_and_reboot");
		mBandPreferenceScreen.setOnPreferenceClickListener(new SaveBandEventListener());
	}  
    public void registerRoamChangeListener() {
		mRoamPreferenceScreen = (PreferenceScreen) findPreference ("roam_key_save_and_reboot");
		mRoamPreferenceScreen.setOnPreferenceClickListener(new SaveRoamEventListener());
	}
	public void registerCA2ChangeListener() {
		mCA2PreferenceScreen = (PreferenceScreen) findPreference ("ca2_key_save_and_set");
		mCA2PreferenceScreen.setOnPreferenceClickListener(new SaveCA2EventListener());
	}
    public void registerCA4ChangeListener() {
		mCA4PreferenceScreen = (PreferenceScreen) findPreference ("ca4_key_save_and_set");
		mCA4PreferenceScreen.setOnPreferenceClickListener(new SaveCA4EventListener());
	}
    public void registerCA5ChangeListener() {
		mCA5PreferenceScreen = (PreferenceScreen) findPreference ("ca5_key_save_and_set");
		mCA5PreferenceScreen.setOnPreferenceClickListener(new SaveCA5EventListener());
	}
    public void registerCA13ChangeListener() {
		mCA13PreferenceScreen = (PreferenceScreen) findPreference ("ca13_key_save_and_set");
		mCA13PreferenceScreen.setOnPreferenceClickListener(new SaveCA13EventListener());
	}
	*/
    
    
    /*
     * Write value
     */
 	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
 		// TODO Auto-generated method stub
 		String tmp = "1"; 		
 		int tmpi = 0;
 		
 		
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
 			
 			Log.e(TAG, "[LA] mode_pref (radio) = " + tmpi); 			
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE, Long.toString(tmpi));  
 			
 			/*
 			tmp2 = Phone.NT_MODE_GLOBAL;				
 			//changes the Settings.System accordingly to modemNetworkMode 			
 	        android.provider.Settings.Global.putInt(
 	                mPhone.getContext().getContentResolver(),
 	                android.provider.Settings.Global.PREFERRED_NETWORK_MODE,
 	                tmp2);
 	        */
 			
 			/* **********************/
 			/* Reboot the device... */
 			/* **********************/
 			//Toast.makeText(DETAILS.this, "Reset...", Toast.LENGTH_SHORT ).show();
 	        //ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
 	        //resetThread.start();    
 	    } 	
 		
 		if (key.equals("MCFG_LGP_selection")) {			
 			tmp = sharedPreferences.getString(key, "");					
 			if      (tmp.equals("0")) {  tmpi = LGE_NO_MCFG;	}			
 			else if (tmp.equals("1")) {  tmpi = LGE_VZW_MCFG;	}			
 			else if (tmp.equals("2")) {  tmpi = LGE_SPR_MCFG; }
 			else if (tmp.equals("3")) {  tmpi = LGE_USC_MCFG; }			
 			else if (tmp.equals("4")) {  tmpi = LGE_ACG_MCFG; }			
 			else if (tmp.equals("5")) {  tmpi = LGE_ATT_MCFG; }		
 			else if (tmp.equals("6")) {  tmpi = LGE_INVAILD_MCFG; }			
 			else if (tmp.equals("7")) {  tmpi = LGE_NO_INITIALIZED; }	
 			else if (tmp.equals("8")) {  tmpi = 0xFF; }	
 			else { tmpi = 0xFF; }	
 			 			
 			Log.e(TAG, "[LA] mcfg_lgp (radio) = " + tmpi); 	
 			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MCFG_MODEM, Long.toString(tmpi));  
 	    } 	 		
 	} 	  
    
    private class SaveEventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {

			/*
			 * LTE band pref
			 */
			long resultBandValue = 0;
			for ( int i = 0; i < mBandKeyCnt; i++ ) {
				mDetailsCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys[i]);
				if (mDetailsCheckBoxPreference.isChecked()) {
					resultBandValue |= Band_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] lte_bandpref = " + resultBandValue);
			send_lte_bandpref(mCurrentBandData, resultBandValue);
						
			resultBandValue = 0;
			for ( int i = 0; i < mBandKeyCnt_extn; i++ ) {
				mDetailsCheckBoxPreference = (CheckBoxPreference) findPreference (Band_Keys_extn[i]);
				if (mDetailsCheckBoxPreference.isChecked()) {
					resultBandValue |= Band_Masking_Value_extn[i];
				}
			}			
			Log.e(TAG,"[LA] lte_bandpref_extn_65_256 = " + resultBandValue);
			send_lte_bandpref(mCurrentBandData_extn, resultBandValue);
			
			
						
			/*
			 * Reboot the device
			 */
			Toast.makeText(DETAILS.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();
			ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
	        resetThread.start();
			
			return false;
		}
	}
    
    
    //Roam Preference 
    /*
    private class SaveRoamEventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
			//1. calculate masking value from checkbox.
			//2. send masking data to lgrilhook.
			//3. reboot.

			//1. calculate masking value from checkbox.
			int resultRoamValue = 0;
			for ( int i = 0; i < mRoamKeyCnt; i++ ) {
				mRoamCheckBoxPreference = (CheckBoxPreference) findPreference (Roam_Keys[i]);
				if (mRoamCheckBoxPreference.isChecked()) {
					resultRoamValue |= Roam_Masking_Value[i];
				}
			}

			//Log.e(TAG,"result band value toString = " + Integer.toString(resultBandValue));

			//2. send masking data to lgrilhook.
			sendRoamData(resultRoamValue);

			//3. reboot.
			Toast.makeText(DETAILS.this, "Reset...", Toast.LENGTH_SHORT ).show();
	        ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
	        resetThread.start();    

			return false;
		}
	}
    */
    
    //CA Preference
    /*
    //CA2 Preference 
    private class SaveCA2EventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
			//1. calculate masking value from checkbox.
			//2. send masking data to lgrilhook.
			//3. reboot.

			//1. calculate masking value from checkbox.
			int resultCA2Value = 0;
			for ( int i = 0; i < mCA2KeyCnt; i++ ) {
				mCA2CheckBoxPreference = (CheckBoxPreference) findPreference (CA2_Keys[i]);
				if (mCA2CheckBoxPreference.isChecked()) {
					resultCA2Value |= CA2_Masking_Value[i];
				}
			}
			//Log.e(TAG,"result band value toString = " + Integer.toString(resultBandValue));

			//2. send masking data to lgrilhook.
			sendCA2Data(resultCA2Value);

			
			Toast.makeText(DETAILS.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();
			//3. reboot.
			ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
	        resetThread.start();    
			
			return false;
		}
	}

    //CA4 Preference 
    private class SaveCA4EventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
			//1. calculate masking value from checkbox.
			//2. send masking data to lgrilhook.
			//3. reboot.

			//1. calculate masking value from checkbox.
			int resultCA4Value = 0;
			for ( int i = 0; i < mCA4KeyCnt; i++ ) {
				mCA4CheckBoxPreference = (CheckBoxPreference) findPreference (CA4_Keys[i]);
				if (mCA4CheckBoxPreference.isChecked()) {
					resultCA4Value |= CA4_Masking_Value[i];
				}
			}
			//Log.e(TAG,"result band value toString = " + Integer.toString(resultBandValue));

			//2. send masking data to lgrilhook.
			sendCA4Data(resultCA4Value);

			
			Toast.makeText(DETAILS.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();
			//3. reboot.
			ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
	        resetThread.start();    

			return false;
		}
	}
        
    //CA5 Preference 
    private class SaveCA5EventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
			//1. calculate masking value from checkbox.
			//2. send masking data to lgrilhook.
			//3. reboot.

			//1. calculate masking value from checkbox.
			int resultCA5Value = 0;
			for ( int i = 0; i < mCA5KeyCnt; i++ ) {
				mCA5CheckBoxPreference = (CheckBoxPreference) findPreference (CA5_Keys[i]);
				if (mCA5CheckBoxPreference.isChecked()) {
					resultCA5Value |= CA5_Masking_Value[i];
				}
			}
			//Log.e(TAG,"result band value toString = " + Integer.toString(resultBandValue));

			//2. send masking data to lgrilhook.
			sendCA5Data(resultCA5Value);

			
			Toast.makeText(DETAILS.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();
			//3. reboot.
			ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
	        resetThread.start();    

			return false;
		}
	}
    
    //CA13 Preference 
    private class SaveCA13EventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {
			//1. calculate masking value from checkbox.
			//2. send masking data to lgrilhook.
			//3. reboot.

			//1. calculate masking value from checkbox.
			int resultCA13Value = 0;
			for ( int i = 0; i < mCA13KeyCnt; i++ ) {
				mCA13CheckBoxPreference = (CheckBoxPreference) findPreference (CA13_Keys[i]);
				if (mCA13CheckBoxPreference.isChecked()) {
					resultCA13Value |= CA13_Masking_Value[i];
				}
			}
			//Log.e(TAG,"result band value toString = " + Integer.toString(resultBandValue));

			//2. send masking data to lgrilhook.
			sendCA13Data(resultCA13Value);

			
			
			Toast.makeText(DETAILS.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();
			//3. reboot.
			ResetThread resetThread = new ResetThread(DETAILS.this, 3000);
	        resetThread.start();    

			return false;
		}
	}
    */

    
    /*
     * read_efs()    
     */
    public long read_lte_bandpref(int option) {
		String res;
		
	    if (option == mCurrentBandData) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND_PREFERENCE);
			//Log.d(TAG,"[LA] Read EFS lte_bandpref (dec) = " + res);
		} else if (option == mCurrentBandData_extn) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_VZW_LTE_BAND_65_128);
			//Log.d(TAG,"[LA] Read EFS lte_bandpref_extn_65_256 (dec) = " + res);
		} else {
			Log.e(TAG,"[LA] Read EFS lte_bandpref ERROR option = " + option);
			res = null;
		}		

		if (res!=null) {
			//turn Integer.parseInt(res);
			return Long.parseLong(res);
			//return BigInteger.valueOf(res); //not working
			//return BigInteger.parseBigInteger(res); //not working
		} else {
			return 0;
		}
	}
    
            
    /*
    public int readRoamData(int option) {
		String res;
		if (option == mCurrentUsingRoam) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_ROAMING_ALLOWED_LTE_BAND);
		} else {	//option == mCurrentBandData
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_ROAMING_ALLOWED_LTE_BAND);
		}

		if (res!=null) {
			return Integer.parseInt(res);
		} else {
			return 0;
		}
	}
	*/
    /*
    public int readCA2Data(int option) {
		String res = "1";
		if (option == mCurrentUsingCA2) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND2_CA_BC_CONFIG);
		} else {	//option == mCurrentBandData
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND2_CA_BC_CONFIG);
		}

		if (res!=null) {
			return Integer.parseInt(res);			
		} else {
			return 0;
		}
	}
    
    public int readCA4Data(int option) {
		String res = "1";
		if (option == mCurrentUsingCA4) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND4_CA_BC_CONFIG);
		} else {	//option == mCurrentBandData
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND4_CA_BC_CONFIG);
		}

		if (res!=null) {
			return Integer.parseInt(res);		
		} else {
			return 0;
		}
	}
    
    public int readCA5Data(int option) {
		String res = "1";
		if (option == mCurrentUsingCA5) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND5_CA_BC_CONFIG);
		} else {	//option == mCurrentBandData
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND5_CA_BC_CONFIG);
		}

		if (res!=null) {
			return Integer.parseInt(res);		
		} else {
			return 0;
		}
	}
    
    public int readCA13Data(int option) {
		String res ="1";
		if (option == mCurrentUsingCA13) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND13_CA_BC_CONFIG);
		} else {	//option == mCurrentBandData
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_LTE_BAND13_CA_BC_CONFIG);
		}

		if (res!=null) {
			return Integer.parseInt(res);		
		} else {
			return 0;
		}
	}
    */
    
    
    /*
     * send_efs()
     */
    
    public boolean send_lte_bandpref(int option, long maskData) {
		if (option == mCurrentUsingBand) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND_CAPABILITY, Long.toString(maskData));			
		} else if (option == mCurrentBandData) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND_PREFERENCE, Long.toString(maskData));			
		} else if (option == mCurrentBandData_extn) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VZW_LTE_BAND_65_128, Long.toString(maskData));			
		} else {
			Log.e(TAG,"[LA] send_lte_bandpref ERROR = " + option);			
		}
	    return false;
	}
    
    /*
    public boolean sendBandData(int maskData) {
		mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND_PREFERENCE, Integer.toString(maskData));
		return false;
	}
    public boolean sendBandData(long maskData) {
		mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND_PREFERENCE, Long.toString(maskData));
		return false;
	}
    public boolean sendBandData_extn(long maskData) {
		mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_VZW_LTE_BAND_65_128, Long.toString(maskData));
		return false;
	}
	*/    

    /*
    public boolean sendRoamData(int maskData) {
		mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_ROAMING_ALLOWED_LTE_BAND, Integer.toString(maskData));
		return false;
	}
	*/
    /*    
    public boolean sendCA2Data(int maskData) {
		mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND2_CA_BC_CONFIG, Integer.toString(maskData));
		return false;
	}
    
    public boolean sendCA4Data(int maskData) {
    	mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND4_CA_BC_CONFIG, Integer.toString(maskData));
		return false;
	}    
    
    public boolean sendCA5Data(int maskData) {
    	mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND5_CA_BC_CONFIG, Integer.toString(maskData));
		return false;
	}
    
    public boolean sendCA13Data(int maskData) {
    	mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_LTE_BAND13_CA_BC_CONFIG, Integer.toString(maskData));    	
		return false;
	}
	*/
    
}

