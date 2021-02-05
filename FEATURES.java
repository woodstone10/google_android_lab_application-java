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

public class FEATURES extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	SharedPreferences prefs;
	private ListPreference p;
	
	private static final String TAG = "LabApplication";


    //Masking bit
    //13   12  11  10  09  08 07 06 05 04 03 02 01 00
    //1000 800 400 200 100 80 40 20 10 08 04 02 01 00
    
    private CheckBoxPreference mFeaturesCheckBoxPreference;
 	private PreferenceScreen mFeaturesPreferenceScreen;
 	
 	/*
 	 * Read Option
 	 */
 	private final int mReadOption = 1; //Common value
	//FGI
	private final int mFGIReadOption_fgi = 1; //fgi
	private final int mFGIReadOption_fgi_rel9 = 2; //fgi_rel9
	private final int mFGIReadOption_fgi_r10 = 3; //fgi_r10
	private final int mFGIReadOption_fgi_tdd = 4; //fgi_tdd
	private final int mFGIReadOption_fgi_tdd_rel9 = 5; //fgi_tdd_rel9
	private final int mFGIReadOption_fgi_tdd_r10 = 6; //fgi_tdd_r10
	//Cap R11
	private final int mCapR11ReadOption_fdd = 1; //fdd_cap_r11
	private final int mCapR11ReadOption_tdd = 2; //tdd_cap_r11
 	
	

	
	//Common masking value
	private long bit32_Masking_Value[] = {
		0x80000000L,  //bit32
		0x40000000, //bit31
		0x20000000, //bit30
		0x10000000, //bit29
		0x8000000, //bit28
		0x4000000, //bit27
		0x2000000, //bit26
		0x1000000, //bit25
		0x800000, //bit24 
		0x400000, //bit23
		0x200000, //bit22
		0x100000, //bit21
		0x80000, //bit20   
		0x40000, //bit19
		0x20000, //bit18
		0x10000, //bit17
		0x8000,	//bit16	
		0x4000, //bit15
		0x2000, //bit14
		0x1000, //bit13
		0x0800,	//bit12	
		0x0400, //bit11
		0x0200, //bit10
		0x0100, //bit9
		0x0080,	//bit8	 
		0x0040, //bit7
		0x0020, //bit6
		0x0010, //bit5
		0x0008,	//bit4	
		0x0004, //bit3
		0x0002, //bit2
		0x0001 //bit1
	};
	
	
	
	/*
	 * FGI FDD
	 */
    //fgi    
	private int mFGIKeyCnt = 32;	
	private String FGI_Keys[] = {
		"key_fgi_bit_001",	
		"key_fgi_bit_002",
		"key_fgi_bit_003",
		"key_fgi_bit_004",
		"key_fgi_bit_005",			
		"key_fgi_bit_006",
		"key_fgi_bit_007",	
		"key_fgi_bit_008",		
		"key_fgi_bit_009",	
		"key_fgi_bit_010",
		"key_fgi_bit_011",
		"key_fgi_bit_012",
		"key_fgi_bit_013",
		"key_fgi_bit_014",
		"key_fgi_bit_015",			
		"key_fgi_bit_016",
		"key_fgi_bit_017",	
		"key_fgi_bit_018",		
		"key_fgi_bit_019",	
		"key_fgi_bit_020",
		"key_fgi_bit_021",
		"key_fgi_bit_022",
		"key_fgi_bit_023",
		"key_fgi_bit_024",
		"key_fgi_bit_025",			
		"key_fgi_bit_026",
		"key_fgi_bit_027",	
		"key_fgi_bit_028",		
		"key_fgi_bit_029",	
		"key_fgi_bit_030",
		"key_fgi_bit_031",
		"key_fgi_bit_032"
	};
	private boolean Using_FGI[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true, //bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true, //bit8	 
		true, //bit9
		true, //bit10
		true, //bit11
		true, //bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true, //bit16	
		true, //bit17
		true, //bit18
		true, //bit19
		true, //bit20   
		true, //bit21
		true, //bit22
		true, //bit23
		true, //bit24 
		true, //bit25
		true, //bit26
		true, //bit27
		true, //bit28
		true, //bit29
		true, //bit30
		true, //bit31
		true  //bit32	
	};
	
	//fgi_rel9
	private int mFGIRel9KeyCnt = 9;	
	private String FGIRel9_Keys[] = {
		"key_fgi_rel9_bit_033",	
		"key_fgi_rel9_bit_034",
		"key_fgi_rel9_bit_035",
		"key_fgi_rel9_bit_036",
		"key_fgi_rel9_bit_037",			
		"key_fgi_rel9_bit_038",
		"key_fgi_rel9_bit_039",	
		"key_fgi_rel9_bit_040",		
		"key_fgi_rel9_bit_041"
	};
	private boolean Using_FGIRel9[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true, //bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true, //bit8	 
		true //bit9
		/*
		true, //bit10
		true, //bit11
		true, //bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true, //bit16	
		true, //bit17
		true, //bit18
		true, //bit19
		true, //bit20   
		true, //bit21
		true, //bit22
		true, //bit23
		true, //bit24 
		true, //bit25
		true, //bit26
		true, //bit27
		true, //bit28
		true, //bit29
		true, //bit30
		true, //bit31
		true  //bit32	
		*/
	};
	
	//fgi_r10
	private int mFGIR10KeyCnt = 16;	
	private String FGIR10_Keys[] = {
		"key_fgi_r10_bit_101",	
		"key_fgi_r10_bit_102",
		"key_fgi_r10_bit_103",
		"key_fgi_r10_bit_104",
		"key_fgi_r10_bit_105",			
		"key_fgi_r10_bit_106",
		"key_fgi_r10_bit_107",	
		"key_fgi_r10_bit_108",		
		"key_fgi_r10_bit_109",	
		"key_fgi_r10_bit_110",
		"key_fgi_r10_bit_111",
		"key_fgi_r10_bit_112",
		"key_fgi_r10_bit_113",
		"key_fgi_r10_bit_114",
		"key_fgi_r10_bit_115",			
		"key_fgi_r10_bit_116"
	};
	private boolean Using_FGIR10[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true, //bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true, //bit8	 
		true, //bit9
		true, //bit10
		true, //bit11
		true, //bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true //bit16	
		//true, //bit17
		//true, //bit18
		//true, //bit19
		//true,	//bit20   
		//true, //bit21
		//true, //bit22
		//true, //bit23
		//true,	//bit24 
		//true //bit25
		//true, //bit26
		//true, //bit27
		//true,	//bit28
		//true, //bit29
		//true, //bit30
		//true, //bit31
		//true  //bit32	
	};
	
	
	/*
	 * fgi tdd   
	 */
	private int mFGITDDKeyCnt = 32;	
	private String FGITDD_Keys[] = {
		"key_fgi_tdd_bit_001",	
		"key_fgi_tdd_bit_002",
		"key_fgi_tdd_bit_003",
		"key_fgi_tdd_bit_004",
		"key_fgi_tdd_bit_005",			
		"key_fgi_tdd_bit_006",
		"key_fgi_tdd_bit_007",	
		"key_fgi_tdd_bit_008",		
		"key_fgi_tdd_bit_009",	
		"key_fgi_tdd_bit_010",
		"key_fgi_tdd_bit_011",
		"key_fgi_tdd_bit_012",
		"key_fgi_tdd_bit_013",
		"key_fgi_tdd_bit_014",
		"key_fgi_tdd_bit_015",			
		"key_fgi_tdd_bit_016",
		"key_fgi_tdd_bit_017",	
		"key_fgi_tdd_bit_018",		
		"key_fgi_tdd_bit_019",	
		"key_fgi_tdd_bit_020",
		"key_fgi_tdd_bit_021",
		"key_fgi_tdd_bit_022",
		"key_fgi_tdd_bit_023",
		"key_fgi_tdd_bit_024",
		"key_fgi_tdd_bit_025",			
		"key_fgi_tdd_bit_026",
		"key_fgi_tdd_bit_027",	
		"key_fgi_tdd_bit_028",		
		"key_fgi_tdd_bit_029",	
		"key_fgi_tdd_bit_030",
		"key_fgi_tdd_bit_031",
		"key_fgi_tdd_bit_032"
	};
	private boolean Using_FGITDD[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true, //bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true, //bit8	 
		true, //bit9
		true, //bit10
		true, //bit11
		true, //bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true, //bit16	
		true, //bit17
		true, //bit18
		true, //bit19
		true, //bit20   
		true, //bit21
		true, //bit22
		true, //bit23
		true, //bit24 
		true, //bit25
		true, //bit26
		true, //bit27
		true, //bit28
		true, //bit29
		true, //bit30
		true, //bit31
		true  //bit32	
	};
	
	private int mFGITDDRel9KeyCnt = 9;	
	private String FGITDDRel9_Keys[] = {
		"key_fgi_tdd_rel9_bit_033",	
		"key_fgi_tdd_rel9_bit_034",
		"key_fgi_tdd_rel9_bit_035",
		"key_fgi_tdd_rel9_bit_036",
		"key_fgi_tdd_rel9_bit_037",			
		"key_fgi_tdd_rel9_bit_038",
		"key_fgi_tdd_rel9_bit_039",	
		"key_fgi_tdd_rel9_bit_040",		
		"key_fgi_tdd_rel9_bit_041"
	};
	private boolean Using_FGITDDRel9[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true, //bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true, //bit8	 
		true //bit9
		/*
		true, //bit10
		true, //bit11
		true, //bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true, //bit16	
		true, //bit17
		true, //bit18
		true, //bit19
		true, //bit20   
		true, //bit21
		true, //bit22
		true, //bit23
		true, //bit24 
		true, //bit25
		true, //bit26
		true, //bit27
		true, //bit28
		true, //bit29
		true, //bit30
		true, //bit31
		true  //bit32	
		*/
	};
	
	private int mFGITDDR10KeyCnt = 16;	
	private String FGITDDR10_Keys[] = {
		"key_fgi_tdd_r10_bit_101",	
		"key_fgi_tdd_r10_bit_102",
		"key_fgi_tdd_r10_bit_103",
		"key_fgi_tdd_r10_bit_104",
		"key_fgi_tdd_r10_bit_105",			
		"key_fgi_tdd_r10_bit_106",
		"key_fgi_tdd_r10_bit_107",	
		"key_fgi_tdd_r10_bit_108",		
		"key_fgi_tdd_r10_bit_109",	
		"key_fgi_tdd_r10_bit_110",
		"key_fgi_tdd_r10_bit_111",
		"key_fgi_tdd_r10_bit_112",
		"key_fgi_tdd_r10_bit_113",
		"key_fgi_tdd_r10_bit_114",
		"key_fgi_tdd_r10_bit_115",			
		"key_fgi_tdd_r10_bit_116"
	};
	private boolean Using_FGITDDR10[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true, //bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true, //bit8	 
		true, //bit9
		true, //bit10
		true, //bit11
		true, //bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true //bit16	
		//true, //bit17
		//true, //bit18
		//true, //bit19
		//true,	//bit20   
		//true, //bit21
		//true, //bit22
		//true, //bit23
		//true,	//bit24 
		//true //bit25
		//true, //bit26
		//true, //bit27
		//true,	//bit28
		//true, //bit29
		//true, //bit30
		//true, //bit31
		//true  //bit32	
	};
		
	
	/*
	 * CAP R11
	 */
	//
    //fdd_cap_r11
    //
	private int mFDDCapR11KeyCnt = 25;
	private String FDDCapR11_Keys[] = {
		"key_fdd_cap_r11_bit_1",	
		"key_fdd_cap_r11_bit_2",
		"key_fdd_cap_r11_bit_3",
		"key_fdd_cap_r11_bit_4",
		"key_fdd_cap_r11_bit_5",			
		"key_fdd_cap_r11_bit_6",
		"key_fdd_cap_r11_bit_7",	
		"key_fdd_cap_r11_bit_8",
		"key_fdd_cap_r11_bit_9",	
		"key_fdd_cap_r11_bit_10",
		"key_fdd_cap_r11_bit_11",
		"key_fdd_cap_r11_bit_12",
		"key_fdd_cap_r11_bit_13",
		"key_fdd_cap_r11_bit_14",
		"key_fdd_cap_r11_bit_15",			
		"key_fdd_cap_r11_bit_16",
		"key_fdd_cap_r11_bit_17",	
		"key_fdd_cap_r11_bit_18",
		"key_fdd_cap_r11_bit_19",	
		"key_fdd_cap_r11_bit_20",
		"key_fdd_cap_r11_bit_21",	
		"key_fdd_cap_r11_bit_22",
		"key_fdd_cap_r11_bit_23",
		"key_fdd_cap_r11_bit_24",
		"key_fdd_cap_r11_bit_25"
	};
	private boolean Using_FDDCapR11[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true,	//bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true,	//bit8	 
		true, //bit9
		true, //bit10
		true, //bit11
		true,	//bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true,	//bit16	
		true, //bit17
		true, //bit18
		true, //bit19
		true,	//bit20   
		true, //bit21
		true, //bit22
		true, //bit23
		true,	//bit24 
		true //bit25
		//true, //bit26
		//true, //bit27
		//true,	//bit28
		//true, //bit29
		//true, //bit30
		//true, //bit31
		//true  //bit32	
	};
	
	//tdd_cap_r11
    //
	private int mTDDCapR11KeyCnt = 25;
	private String TDDCapR11_Keys[] = {
		"key_tdd_cap_r11_bit_1",	
		"key_tdd_cap_r11_bit_2",
		"key_tdd_cap_r11_bit_3",
		"key_tdd_cap_r11_bit_4",
		"key_tdd_cap_r11_bit_5",			
		"key_tdd_cap_r11_bit_6",
		"key_tdd_cap_r11_bit_7",	
		"key_tdd_cap_r11_bit_8",
		"key_tdd_cap_r11_bit_9",	
		"key_tdd_cap_r11_bit_10",
		"key_tdd_cap_r11_bit_11",
		"key_tdd_cap_r11_bit_12",
		"key_tdd_cap_r11_bit_13",
		"key_tdd_cap_r11_bit_14",
		"key_tdd_cap_r11_bit_15",			
		"key_tdd_cap_r11_bit_16",
		"key_tdd_cap_r11_bit_17",	
		"key_tdd_cap_r11_bit_18",
		"key_tdd_cap_r11_bit_19",	
		"key_tdd_cap_r11_bit_20",
		"key_tdd_cap_r11_bit_21",	
		"key_tdd_cap_r11_bit_22",
		"key_tdd_cap_r11_bit_23",
		"key_tdd_cap_r11_bit_24",
		"key_tdd_cap_r11_bit_25"
	};
	private boolean Using_TDDCapR11[] = {
		true, //bit1
		true, //bit2
		true, //bit3
		true,	//bit4	
		true, //bit5
		true, //bit6
		true, //bit7
		true,	//bit8	 
		true, //bit9
		true, //bit10
		true, //bit11
		true,	//bit12	 
		true, //bit13
		true, //bit14
		true, //bit15
		true,	//bit16	
		true, //bit17
		true, //bit18
		true, //bit19
		true,	//bit20   
		true, //bit21
		true, //bit22
		true, //bit23
		true,	//bit24 
		true //bit25
		//true, //bit26
		//true, //bit27
		//true,	//bit28
		//true, //bit29
		//true, //bit30
		//true, //bit31
		//true  //bit32	
	};
	

	
	private LgSvcCmd mLgSvcCmd;
	private Handler mLgSvcItemsHandler;
	private Runnable lgScvItemsRunnable;
	
	private Phone mPhone;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      

        addPreferencesFromResource(R.layout.features);  //Check box
        //addPreferencesFromResource(R.layout.fdd_cap_r11);
        //addPreferencesFromResource(R.layout.fgi);
        //addPreferencesFromResource(R.layout.fgi_rel9);
        //addPreferencesFromResource(R.layout.fgi_r10);
        //addPreferencesFromResource(R.layout.reboot);
        
        mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());   
        
        System.out.println("onCreate"); 
        
        Log.v(TAG,"[LA] " + "FEATURES.java");
        
        /* Check box */
		//1. Read Current Using Band Data through LGRIL
		//2. Calculating masking value, and Change Using_Band data
		//3. Change layout about focus   
        setUsing_fgi();        
        setUsing_cap_r11();
        
        /* checkbox check read NV/EFS data */
		setInitValue();
		
        /* listening checkbox change */
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
    public void setUsing_fgi() {
    	/*
    	 * FGI FDD
    	 */
    	//FGI
    	long val_fgi = read_fgi(mFGIReadOption_fgi);     	
    	Log.d(TAG, "[LA] fgi (hex) = " + Long.toHexString(val_fgi));
		for ( int i = 0; i < mFGIKeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_fgi & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fgi Keys[i] : " + FGI_Keys[i] + " enable");
				Using_FGI[i] = ( bit32_Masking_Value[i]==(val_fgi & bit32_Masking_Value[i]) );
			}
		}
		for ( int i = 0; i < mFGIKeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGI_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FGI[i]);
		}
		
		//FGI Rel9
		long val_fgi_rel9 = read_fgi(mFGIReadOption_fgi_rel9);     	
    	Log.d(TAG, "[LA] fgi_rel9 (hex) = " + Long.toHexString(val_fgi_rel9));
		for ( int i = 0; i < mFGIRel9KeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_fgi_rel9 & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fgi_rel9 Keys[i] : " + FGIRel9_Keys[i] + " enable");
				Using_FGIRel9[i] = ( bit32_Masking_Value[i]==(val_fgi_rel9 & bit32_Masking_Value[i]) );
			}
		}
		for ( int i = 0; i < mFGIRel9KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGIRel9_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FGIRel9[i]);
		}
		
		//FIG R10
    	long val_r10 = read_fgi(mFGIReadOption_fgi_r10);     	
    	Log.d(TAG, "[LA] fgi_r10 (hex) = " + Long.toHexString(val_r10));
		for ( int i = 0; i < mFGIR10KeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_r10 & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fgi_r10 Keys[i] : " + FGIR10_Keys[i] + " enable");
				Using_FGIR10[i] = ( bit32_Masking_Value[i]==(val_r10 & bit32_Masking_Value[i]) );
			}
			else{
				//Log.e(TAG, "[LA] bit32_Masking_Value Keys[i] : " + bit32_Masking_Value[i]);
				//Log.e(TAG, "[LA] fgi_r10 Keys[i] : " + FGIR10_Keys[i] + " disable");				
			}
		}
		for ( int i = 0; i < mFGIR10KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGIR10_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FGIR10[i]);
		}
		
		/*
		 * FGI TDD
		 */
		//FGI
    	long val_fgi_tdd = read_fgi(mFGIReadOption_fgi_tdd);     	
    	Log.d(TAG, "[LA] fgi_tdd (hex) = " + Long.toHexString(val_fgi_tdd));
		for ( int i = 0; i < mFGITDDKeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_fgi_tdd & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fgi_tdd Keys[i] : " + FGITDD_Keys[i] + " enable");
				Using_FGITDD[i] = ( bit32_Masking_Value[i]==(val_fgi_tdd & bit32_Masking_Value[i]) );
			}
		}
		for ( int i = 0; i < mFGITDDKeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDD_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FGITDD[i]);
		}
		
		//FGI Rel9
		long val_fgi_tdd_rel9 = read_fgi(mFGIReadOption_fgi_tdd_rel9);     	
    	Log.d(TAG, "[LA] fgi_tdd_rel9 (hex) = " + Long.toHexString(val_fgi_tdd_rel9));
		for ( int i = 0; i < mFGITDDRel9KeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_fgi_tdd_rel9 & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fgi_tdd_rel9 Keys[i] : " + FGITDDRel9_Keys[i] + " enable");
				Using_FGITDDRel9[i] = ( bit32_Masking_Value[i]==(val_fgi_tdd_rel9 & bit32_Masking_Value[i]) );
			}
		}
		for ( int i = 0; i < mFGITDDRel9KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDDRel9_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FGITDDRel9[i]);
		}
		
		//FIG R10
    	long val_fgi_tdd_r10 = read_fgi(mFGIReadOption_fgi_tdd_r10);     	
    	Log.d(TAG, "[LA] fgi_tdd_r10 (hex) = " + Long.toHexString(val_fgi_tdd_r10));
		for ( int i = 0; i < mFGITDDR10KeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_fgi_tdd_r10 & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fgi_tdd_r10 Keys[i] : " + FGITDDR10_Keys[i] + " enable");
				Using_FGITDDR10[i] = ( bit32_Masking_Value[i]==(val_fgi_tdd_r10 & bit32_Masking_Value[i]) );
			}
		}
		for ( int i = 0; i < mFGITDDR10KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDDR10_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FGITDDR10[i]);
		}
	}
       
    
    public void setUsing_cap_r11() {
    	/*
    	 * fdd_cap_r11
    	 */
    	long val_fdd_cap_r11 = read_cap_r11(mCapR11ReadOption_fdd);        	
    	Log.d(TAG, "[LA] fdd_cap_r11 (hex) = " + Long.toHexString(val_fdd_cap_r11));
		for ( int i = 0; i < mFDDCapR11KeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_fdd_cap_r11 & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] fdd_cap_r11 Keys[i] : " + FDDCapR11_Keys[i] + " enable");
				Using_FDDCapR11[i] = ( bit32_Masking_Value[i]==(val_fdd_cap_r11 & bit32_Masking_Value[i]) );
			}
		}		
		/*
		for ( int i = 0; i < mFDDCapR11KeyCnt; i++ ) {
			mFDDCapR11CheckBoxPreference = (CheckBoxPreference) findPreference (FDDCapR11_Keys[i]);
			mFDDCapR11CheckBoxPreference.setEnabled(Using_FDDCapR11[i]);
		}
		*/
		for ( int i = 0; i < mFDDCapR11KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FDDCapR11_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_FDDCapR11[i]);
		}
		
		/*
		 * tdd_cap_r11
		 */
    	long val_tdd_cap_r11 = read_cap_r11(mCapR11ReadOption_tdd);        	
    	Log.d(TAG, "[LA] tdd_cap_r11 (hex) = " + Long.toHexString(val_tdd_cap_r11));
		for ( int i = 0; i < mTDDCapR11KeyCnt; i++ ) {
			if(bit32_Masking_Value[i]==(val_tdd_cap_r11 & bit32_Masking_Value[i])){
				Log.w(TAG, "[LA] tdd_cap_r11 Keys[i] : " + TDDCapR11_Keys[i] + " enable");
				Using_TDDCapR11[i] = ( bit32_Masking_Value[i]==(val_tdd_cap_r11 & bit32_Masking_Value[i]) );
			}
		}		
		for ( int i = 0; i < mTDDCapR11KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (TDDCapR11_Keys[i]);
			mFeaturesCheckBoxPreference.setEnabled(Using_TDDCapR11[i]);
		}
	}
    
    
        
    /*
     * Setting from read value    
     */
    public void setInitValue() {
		/*
		 * FGI FDD
		 */
		long val_fgi = read_fgi(mFGIReadOption_fgi); 
	    for ( int i = 0; i < mFGIKeyCnt; i++ ) {
	    	mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGI_Keys[i]);  			  
	    	mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fgi & bit32_Masking_Value[i]) );
		} 
	    
	    long val_fgi_rel9 = read_fgi(mFGIReadOption_fgi_rel9); 
	    for ( int i = 0; i < mFGIRel9KeyCnt; i++ ) {
	    	mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGIRel9_Keys[i]);  			  
	    	mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fgi_rel9 & bit32_Masking_Value[i]) );
		} 
	    
	    long val_fgi_r10 = read_fgi(mFGIReadOption_fgi_r10); 
	    for ( int i = 0; i < mFGIR10KeyCnt; i++ ) {
	    	mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGIR10_Keys[i]);  			  
	    	mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fgi_r10 & bit32_Masking_Value[i]) );
		} 
	    
	    /*
		 * FGI TDD
		 */
		long val_fgi_tdd = read_fgi(mFGIReadOption_fgi_tdd); 
	    for ( int i = 0; i < mFGITDDKeyCnt; i++ ) {
	    	mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDD_Keys[i]);  			  
	    	mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fgi_tdd & bit32_Masking_Value[i]) );
		} 
	    
	    long val_fgi_tdd_rel9 = read_fgi(mFGIReadOption_fgi_tdd_rel9); 
	    for ( int i = 0; i < mFGITDDRel9KeyCnt; i++ ) {
	    	mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDDRel9_Keys[i]);  			  
	    	mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fgi_tdd_rel9 & bit32_Masking_Value[i]) );
		} 
	    
	    long val_fgi_tdd_r10 = read_fgi(mFGIReadOption_fgi_tdd_r10); 
	    for ( int i = 0; i < mFGITDDR10KeyCnt; i++ ) {
	    	mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDDR10_Keys[i]);  			  
	    	mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fgi_tdd_r10 & bit32_Masking_Value[i]) );
		} 
		
	    /*
	     * CAP R11
	     */
		long val_fdd_cap_r11 = read_cap_r11(mCapR11ReadOption_fdd); 
		for ( int i = 0; i < mFDDCapR11KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FDDCapR11_Keys[i]);
			mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_fdd_cap_r11 & bit32_Masking_Value[i]) );
		}
		
		long val_tdd_cap_r11 = read_cap_r11(mCapR11ReadOption_tdd); 
		for ( int i = 0; i < mTDDCapR11KeyCnt; i++ ) {
			mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (TDDCapR11_Keys[i]);
			mFeaturesCheckBoxPreference.setChecked( bit32_Masking_Value[i]==(val_tdd_cap_r11 & bit32_Masking_Value[i]) );
		}
    }
    
    
    /*
     * Change Listener
     */
    public void registerChangeListener() {    	
    	mFeaturesPreferenceScreen = (PreferenceScreen) findPreference ("features_save_and_reboot");
    	mFeaturesPreferenceScreen.setOnPreferenceClickListener(new SaveEventListener());  
    }  
    
    
    /*
     * Write value
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
  		
 	} 	  
    private class SaveEventListener implements OnPreferenceClickListener {
		public boolean onPreferenceClick (Preference preference) {

			
			/*
			 * FGI FDD
			 */
			long result_fgi_Value = 0;
			for ( int i = 0; i < mFGIKeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGI_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fgi_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fgi = " + result_fgi_Value);
			send_fgi(mFGIReadOption_fgi, result_fgi_Value);
			
			long result_fgi_rel9_Value = 0;
			for ( int i = 0; i < mFGIRel9KeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGIRel9_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fgi_rel9_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fgi_rel9 = " + result_fgi_rel9_Value);
			send_fgi(mFGIReadOption_fgi_rel9, result_fgi_rel9_Value);
			
			long result_fgi_r10_Value = 0;
			for ( int i = 0; i < mFGIR10KeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGIR10_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fgi_r10_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fgi_r10 = " + result_fgi_r10_Value);
			send_fgi(mFGIReadOption_fgi_r10, result_fgi_r10_Value);
			
			
			/*
			 * FGI TDD
			 */
			long result_fgi_tdd_Value = 0;
			for ( int i = 0; i < mFGITDDKeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDD_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fgi_tdd_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fgi_tdd = " + result_fgi_tdd_Value);
			send_fgi(mFGIReadOption_fgi_tdd, result_fgi_tdd_Value);
			
			long result_fgi_tdd_rel9_Value = 0;
			for ( int i = 0; i < mFGITDDRel9KeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDDRel9_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fgi_tdd_rel9_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fgi_tdd_rel9 = " + result_fgi_tdd_rel9_Value);
			send_fgi(mFGIReadOption_fgi_tdd_rel9, result_fgi_tdd_rel9_Value);
			
			long result_fgi_tdd_r10_Value = 0;
			for ( int i = 0; i < mFGITDDR10KeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FGITDDR10_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fgi_tdd_r10_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fgi_tdd_r10 = " + result_fgi_tdd_r10_Value);
			send_fgi(mFGIReadOption_fgi_tdd_r10, result_fgi_tdd_r10_Value);
						
			
			/*
			 * CAP R11
			 */
			long result_fdd_cap_r11_Value = 0;
			for ( int i = 0; i < mFDDCapR11KeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (FDDCapR11_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_fdd_cap_r11_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] fdd_cap_r11 = " + result_fdd_cap_r11_Value);
			send_cap_r11(mCapR11ReadOption_fdd, result_fdd_cap_r11_Value);
			
			long result_tdd_cap_r11_Value = 0;
			for ( int i = 0; i < mTDDCapR11KeyCnt; i++ ) {
				mFeaturesCheckBoxPreference = (CheckBoxPreference) findPreference (TDDCapR11_Keys[i]);
				if (mFeaturesCheckBoxPreference.isChecked()) {
					result_tdd_cap_r11_Value |= bit32_Masking_Value[i];
				}
			}			
			Log.e(TAG,"[LA] tdd_cap_r11 = " + result_tdd_cap_r11_Value);
			send_cap_r11(mCapR11ReadOption_tdd, result_tdd_cap_r11_Value);
							
			
			/*
			 * Reboot the device
			 */
			Toast.makeText(FEATURES.this, "Set Value and Reboot", Toast.LENGTH_SHORT ).show();
			ResetThread resetThread = new ResetThread(FEATURES.this, 3000);
	        resetThread.start();
			
			return false;
		}
	}
    
    
    /*
     * read_efs()    
     */
    public long read_fgi(int option) {
		String res;
		
		//return 0; //for test only
		
	    if (option == mFGIReadOption_fgi) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_FGI);
			//Log.d(TAG,"[LA] Read EFS fgi (dec) = " + res);
		} else if (option == mFGIReadOption_fgi_rel9) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_FGI_R9);
			//Log.d(TAG,"[LA] Read EFS fgi_rel9 (dec) = " + res);
		} else if (option == mFGIReadOption_fgi_r10) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_FGI_R10);
			//Log.d(TAG,"[LA] Read EFS fgi_r10 (dec) = " + res);
		} else if (option == mFGIReadOption_fgi_tdd) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_TDD_FGI);
			//Log.d(TAG,"[LA] Read EFS fgi_tdd (dec) = " + res);
		} else if (option == mFGIReadOption_fgi_tdd_rel9) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_TDD_FGI_9);
			//Log.d(TAG,"[LA] Read EFS fgi_tdd_rel9 (dec) = " + res);
		} else if (option == mFGIReadOption_fgi_tdd_r10) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_TDD_FGI_10);
			//Log.d(TAG,"[LA] Read EFS fgi_tdd_r10 (dec) = " + res);
		} else {
			Log.e(TAG,"[LA] Read EFS fgi ERROR option = " + option);
			res = null;
		}

		if (res!=null) {
			//return Integer.parseInt(res);			
			return Long.parseLong(res);
			//return BigInteger.valueOf(res); //not working
			//return BigInteger.parseBigInteger(res); //not working
		} else {
			return 0;
		}

	}
    
    public long read_cap_r11(int option) {
		String res;
		
	    if (option == mCapR11ReadOption_fdd) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_FDD_CAP_RL11);
			//Log.d(TAG,"[LA] Read EFS fdd_cap_r11 (dec) = " + res);
		} else if (option == mCapR11ReadOption_tdd) {
			res = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_TDD_CAP_R11);
			//Log.d(TAG,"[LA] Read EFS tdd_cap_r11 (dec) = " + res);
		} else {
			Log.e(TAG,"[LA] Read EFS cap_r11 ERROR option = " + option);
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
     * send_efs()
     */
    public boolean send_fgi(int option, long maskData) {
	    if (option == mFGIReadOption_fgi) {
	    	mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_FGI, Long.toString(maskData));			
		} else if (option == mFGIReadOption_fgi_rel9) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_FGI_R9, Long.toString(maskData));			
		} else if (option == mFGIReadOption_fgi_r10) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_FGI_R10, Long.toString(maskData));			
		} else if (option == mFGIReadOption_fgi_tdd) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_TDD_FGI, Long.toString(maskData));			
		} else if (option == mFGIReadOption_fgi_tdd_rel9) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_TDD_FGI_9, Long.toString(maskData));			
		} else if (option == mFGIReadOption_fgi_tdd_r10) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_TDD_FGI_10, Long.toString(maskData));			
		} else {
			Log.e(TAG,"[LA] send_fgi ERROR = " + option);			
		}
	    return false;
	}
      
    public boolean send_cap_r11(int option, long maskData) {
		if (option == mCapR11ReadOption_fdd) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_FDD_CAP_RL11, Long.toString(maskData));		
		} else if (option == mCapR11ReadOption_tdd) {
			mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_TDD_CAP_R11, Long.toString(maskData));		
		} else {
			Log.e(TAG,"[LA] send_cap_r11 ERROR = " + option);			
		}
	    return false;
	}
  

}

