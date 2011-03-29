package net.ildn;

import net.ildn.fedorait.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserCredential {
	
	private String prefsValue;
	private Context cx;
	private static final String SEED="!tHiSiSmyS4r3ds3cr3tz";
	private static final String LOG_ID = "ildn - UserCredential";
		
	/*
	 * Class for read/wirte User Credential (username/password) from 
	 * sharedpreference. These Credential are read/write with 
	 * SimpleCrypto interface 
	 */

	public UserCredential(Context cx) {
		this.cx=cx;
	}

	/*
	 * use prefs from sharedPreference for access to user account name/password
	 */
	public String getPrefs(String prefs) {
		SharedPreferences settings = cx.getSharedPreferences(cx.getString(R.string.ildnPreference), 0);
		String sp = settings.getString(prefs, "");
		try {
			if (sp!=null && !sp.equalsIgnoreCase("")) {
				String decifrato = SimpleCrypto.decrypt(SEED, sp);
				Log.i(LOG_ID, prefs +" decrypt is " + decifrato);
				this.prefsValue = decifrato;
			}
			else 
				this.prefsValue = "";
		}
		catch (Exception e) {
			Log.i(LOG_ID, e.getMessage());
			return "";
		}
		return this.prefsValue;
	}
	
	/*
	 * prefs  is name of preference to store in sharedPreferences
	 * secret is the value that prefs has 
	 * use prefs for store secret in sharedPreferences for the user account username/password 
	 * in crypt form
	 */
	public boolean setPrefs(String prefs,String secret) {
		SharedPreferences settings = cx.getSharedPreferences(cx.getString(R.string.ildnPreference), 0);
		SharedPreferences.Editor editor = settings.edit();
		
		try {
			if (secret != null) {
				String cifrato = SimpleCrypto.encrypt(SEED, secret);
				Log.i(LOG_ID, secret + " crypt is " + cifrato);
				editor.putString(prefs,cifrato);
				editor.commit();
			}
		}
		catch (Exception e) {
			Log.i(LOG_ID, e.getMessage());
			return false;
		}
		
		return true;
	}
}
