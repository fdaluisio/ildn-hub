package net.ildn;

import net.ildn.fedorait.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SexySplash extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000;
	private String portaledefault = "";

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.sexysplash);

		/*
		 * Loading SharedPreferences per il portale di default
		 */

		SharedPreferences settings = getSharedPreferences(
				this.getString(R.string.ildnPreference), 0);
		portaledefault = settings.getString("portaledefault",
				this.getString(R.string.intestazionefedora));

		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				/* Create an Intent that will start the Menu-Activity. */
				Intent mainIntent;
				if (portaledefault
						.equalsIgnoreCase(getString(R.string.intestazionedebian))) {
					mainIntent = new Intent(getString(R.string.portaledebian));
				} else if (portaledefault
						.equalsIgnoreCase(getString(R.string.intestazionefedora))) {
					mainIntent = new Intent(getString(R.string.portalefedora));
				} else if (portaledefault
						.equalsIgnoreCase(getString(R.string.intestazionesuse))) {
					mainIntent = new Intent(getString(R.string.portalesuse));
				} else if (portaledefault
						.equalsIgnoreCase(getString(R.string.intestazionemandriva))) {
					mainIntent = new Intent(getString(R.string.portalemandriva));
				}
				// Aggiungere qui altri portali
				else
					mainIntent = new Intent(getString(R.string.portalefedora));

				SexySplash.this.startActivity(mainIntent);
				SexySplash.this.finish();

			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
