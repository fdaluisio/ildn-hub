package net.ildn.fedorait;

import net.ildn.Authentication;
import net.ildn.OtherActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class fedorait extends TabActivity {
	
	private static final String LOG_ID = "Fedora-it.org - fedoraitActivity";
	private int statusAuth = Authentication.NOT_ACCESS;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_ID, "Richiamato onCreate()");
		setContentView(R.layout.main);
		Resources res = getResources(); 
		TextView tv = new TextView(this);
		
		/*
		 * Login code
		 */
		SharedPreferences settings = getSharedPreferences(getString(R.string.ildnPreference), MODE_PRIVATE);
		String portalelogin = settings.getString("portalelogin", "nessuno");
		Authentication auth = new Authentication(this);		
		if (portalelogin.equalsIgnoreCase(getString(R.string.intestazionefedora))) {
			statusAuth = auth.login();
			Log.i(LOG_ID,"return auth status: "+ statusAuth);			
		}
		tv = (TextView)findViewById(R.id.testatina);
		if (statusAuth == Authentication.ACCESS) {
			tv.setText(auth.getUsername()+ "@" + getResources().getString(R.string.intestazionefedora));
		}
		else 
			tv.setText(getResources().getString(R.string.intestazionefedora));
		tv.setBackgroundResource(R.color.fedora);
		
		LinearLayout l = new LinearLayout(this);
		l = (LinearLayout)findViewById(R.id.sfondo);
		l.setBackgroundResource(R.color.fedora);
				
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Reusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, NewsActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("news")
				.setIndicator("News", res.getDrawable(R.drawable.ic_tab_news))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, ForumActivity.class);
		spec = tabHost
				.newTabSpec("forum")
				.setIndicator("Forum", res.getDrawable(R.drawable.ic_tab_forum))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, BlogActivity.class);
		spec = tabHost.newTabSpec("blog")
				.setIndicator("Blog", res.getDrawable(R.drawable.ic_tab_blog))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, GuideActivity.class);
		spec = tabHost
				.newTabSpec("guide")
				.setIndicator("Guide", res.getDrawable(R.drawable.ic_tab_guide))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, OtherActivity.class);
		intent.putExtra("fonte", this.getString(R.string.intestazionefedora));
		spec = tabHost.newTabSpec("other")
				.setIndicator("ILDN", res.getDrawable(R.drawable.ic_tab_other))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onStop()
	 */
	@Override
	protected void onStop() {
		Log.i(LOG_ID, "Richiamato onStop()");
		super.onStop();
	}

}
