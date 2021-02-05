package com.lge.la;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;

import com.android.lge.lgsvcitems.*;
import com.qualcomm.qcrilhook.*;
import com.qualcomm.qcrilmsgtunnel.*;



import android.os.Handler;



public class ModePref extends Activity implements RadioGroup.OnCheckedChangeListener {

    public static final int MODE_CDMAEVDOAUTOMATIC 	 = 0;
    public static final int MODE_1XONLY  	 = 3;
    public static final int MODE_HDRONLY  	 = 2;
    
    public static final int MODE_GSMWCDMAAUTOMATICONLY  	 = 4;
    public static final int MODE_GSMONLY  	 = 5;
    public static final int MODE_WCDMAONLY  	 = 6;
    
    public static final int MODE_LTEONLY  	 = 7;
    public static final int MODE_GLOBAL  	 = 8;
    
   
    //private LgSvcCmd mLgSvcCmd;
	//private Handler mLgSvcItemsHandler;
	//private Runnable lgScvItemsRunnable;
    
    RadioGroup Radio_Group;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//== ==//
		//mLgSvcCmd = LgSvcCmd.getInstance(getApplicationContext());
		//mLgSvcItemsHandler = new Handler();
		//lgScvItemsRunnable = new Runnable() { 
		//	@Override
		//	public void run() {
		//		recreate();
		//	}
		//};
		//if (mLgSvcCmd.getIQcrilMsgTunnelServiceStatus() == false) { mLgSvcItemsHandler.postDelayed(lgScvItemsRunnable , 100); }
		//== ==//
		
		setContentView(R.layout.mode_pref);
        
        this.Radio_Group = (RadioGroup)findViewById(R.id.Rradiogroup);         
        Radio_Group.setOnCheckedChangeListener(this);
        
        
        setModePrefToDialog();
    }
    
    public void onCheckedChanged(RadioGroup group, int id) {
    	
    	//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            	
    	switch(id) {
        case R.id.RCDMAEVDOAutomatic:
        	setModePref(Long.toString(MODE_CDMAEVDOAUTOMATIC));
//        	p.setSummary("Hybrid Mode");
            break;

        case R.id.R1xonly:
        	setModePref(Long.toString(MODE_1XONLY));
//        	p.setSummary("1x Only");
            break;
        
        case R.id.Rhdronly:
        	setModePref(Long.toString(MODE_HDRONLY));
//        	p.setSummary("HDR Only");
        	break;

        case R.id.RGSMWCDMAAutomatic:
        	setModePref(Long.toString(MODE_GSMWCDMAAUTOMATICONLY));
//        	p.setSummary("HDR Only");
        	break;
        case R.id.RGSMonly:        	
        	setModePref(Long.toString(MODE_GSMONLY));
//        	p.setSummary("LTE Only");
        	break;
        case R.id.RWCDMAonly:        	
        	setModePref(Long.toString(MODE_WCDMAONLY));
//        	p.setSummary("LTE Only");
        	break;
        	
        	
        case R.id.RLTEonly:        	
        	setModePref(Long.toString(MODE_LTEONLY));
//        	p.setSummary("LTE Only");
        	break;
        
        case R.id.RGlobal:        	
        	setModePref(Long.toString(MODE_GLOBAL));
//        	p.setSummary("LTE Only");
        	break;

        default:
            finish();
            break;
        }
    	
    }

    private void setModePrefToDialog() {
        RadioButton cb;
        int setting;
        boolean set =true;

        setting = (int)queryModePref();
        switch (setting) {
        case MODE_CDMAEVDOAUTOMATIC:
        	cb = (RadioButton)findViewById(R.id.RCDMAEVDOAutomatic);
            cb.setChecked(set);
        	break;
        case MODE_HDRONLY:
        	cb = (RadioButton)findViewById(R.id.R1xonly);
            cb.setChecked(set);
        	break;
        case MODE_1XONLY:
        	cb = (RadioButton)findViewById(R.id.Rhdronly);
            cb.setChecked(set);
        	break;

        case MODE_GSMWCDMAAUTOMATICONLY:
        	cb = (RadioButton)findViewById(R.id.RGSMWCDMAAutomatic);
            cb.setChecked(set);
        	break;
        case MODE_GSMONLY:
        	cb = (RadioButton)findViewById(R.id.RGSMonly);
        	cb.setChecked(set);
        	break;
        case MODE_WCDMAONLY:
        	cb = (RadioButton)findViewById(R.id.RWCDMAonly);
        	cb.setChecked(set);
        	break;
        	
        case MODE_LTEONLY:
        	cb = (RadioButton)findViewById(R.id.RLTEonly);
        	cb.setChecked(set);
        	break;
        
        case MODE_GLOBAL:
        	cb = (RadioButton)findViewById(R.id.RGlobal);
        	cb.setChecked(set);
        	break;
       	
        default:
        	break;
        }
    }

    private void setModePref(String setting) {
    	int retVal = 1;    	
    	String str; 
    	str = "Unknown Return Value";
    	
    	//mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE, setting);   	
   //retVal = mLgSvcCmd.setCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE, setting);
    	//LgSvcCmd.onSendOprtMode(1);    	
    	
    	switch (retVal) {
        case 0:
        	str = "Write Success";
        	break;
        case 1:
        	str = "Write Fail";
        	break;        
        default:
        	str = "Unknown Return Value";
        	break;
        }
    	
    	//Toast.makeText(getApplicationContext(),Integer.toString(retVal), Toast.LENGTH_LONG).show();
    	Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    private long queryModePref() {
        String tmp = "1";
        int setting;

    //tmp = mLgSvcCmd.getCmdValue(LgSvcCmdIds.CMD_MODE_PREFERENCE);
        
        try {
            setting = (int)Long.parseLong(tmp, 16);
        }
        catch(NullPointerException ie) {
            setting = 0;
        }
        catch(NumberFormatException nfe) {
            setting = 0;
        }

        return setting;
    }

}

