package net.ildn.suseitalia;

import net.ildn.Authentication;
import net.ildn.OtherActivity;
import net.ildn.fedorait.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class SuseItalia extends TabActivity {

	private static final String LOG_ID = "suseitalia.org - debianitaliaActivity";
	private int statusAuth = Authentication.NOT_ACCESS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(LOG_ID, "Richiamato onCreate()");
		setContentView(R.layout.main);
		Resources res = getResources();
		TextView tv = new TextView(this);
		tv = (TextView)findViewById(R.id.testatina);
		
		/*
		 * Login code
		 */
		SharedPreferences settings = getSharedPreferences(getString(R.string.ildnPreference), MODE_PRIVATE);
		String portalelogin = settings.getString("portalelogin", "nessuno");
		Authentication auth = new Authentication(this);		
		if (portalelogin.equalsIgnoreCase(getString(R.string.intestazionesuse))) {
			statusAuth = auth.login();
			Log.i(LOG_ID,"return auth status: "+ statusAuth);			
		}		
		tv = (TextView)findViewById(R.id.testatina);
		if (statusAuth == Authentication.ACCESS) {
			tv.setText(auth.getUsername()+ "@" + getResources().getString(R.string.intestazionesuse));
		}
		else 
			tv.setText(getResources().getString(R.string.intestazionesuse));
		tv.setBackgroundResource(R.color.suse);
		
		LinearLayout l = new LinearLayout(this);
		l = (LinearLayout)findViewById(R.id.sfondo);
		l.setBackgroundResource(R.color.suse);
		
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Reusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, SuseNewsActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("news")
				.setIndicator("News", res.getDrawable(R.drawable.ic_tab_news))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, SuseForumActivity.class);
		spec = tabHost
				.newTabSpec("forum")
				.setIndicator("Forum", res.getDrawable(R.drawable.ic_tab_forum))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, SuseBlogActivity.class);
		spec = tabHost.newTabSpec("blog")
				.setIndicator("Blog", res.getDrawable(R.drawable.ic_tab_blog))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, SuseGuideActivity.class);
		spec = tabHost
				.newTabSpec("guide")
				.setIndicator("Guide", res.getDrawable(R.drawable.ic_tab_guide))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, OtherActivity.class);
		intent.putExtra("fonte", this.getString(R.string.intestazionesuse));
		spec = tabHost.newTabSpec("other")
				.setIndicator("ILDN", res.getDrawable(R.drawable.ic_tab_other))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

}
