package net.ildn.debianitalia;

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

public class DebianItalia extends TabActivity {

	private static final String LOG_ID = "DebianItalia.org - debianitaliaActivity";
	private int statusAuth = Authentication.NOT_ACCESS;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(LOG_ID, "Richiamato onCreate()");
		setContentView(R.layout.main);
		Resources res = getResources(); // Resource object to get Drawables
		
		
		TextView tv = new TextView(this);
		tv = (TextView)findViewById(R.id.testatina);

		/*
		 * Login code
		 */
		
		SharedPreferences settings = getSharedPreferences(getString(R.string.ildnPreference), MODE_PRIVATE);
		String portaledefault = settings.getString("portaledefault", "");
		Authentication auth = new Authentication(this);		
		if (portaledefault.equalsIgnoreCase(getString(R.string.intestazionedebian))) {
			statusAuth = auth.login();
			Log.i(LOG_ID,"return auth status: "+ statusAuth);			
		}
		
		tv = (TextView)findViewById(R.id.testatina);
		if (statusAuth == Authentication.ACCESS) {
			tv.setText(auth.getUsername()+ "@" + getResources().getString(R.string.intestazionedebian));
		}
		else 
			tv.setText(getResources().getString(R.string.intestazionedebian));

		
		
		tv.setBackgroundResource(R.color.debian);
		
		LinearLayout l = new LinearLayout(this);
		l = (LinearLayout)findViewById(R.id.sfondo);
		l.setBackgroundResource(R.color.debian);
				
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Reusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, DebianNewsActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("news")
				.setIndicator("News", res.getDrawable(R.drawable.ic_tab_news))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, DebianForumActivity.class);
		spec = tabHost
				.newTabSpec("forum")
				.setIndicator("Forum", res.getDrawable(R.drawable.ic_tab_forum))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, DebianBlogActivity.class);
		spec = tabHost.newTabSpec("blog")
				.setIndicator("Blog", res.getDrawable(R.drawable.ic_tab_blog))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, DebianGuideActivity.class);
		spec = tabHost
				.newTabSpec("guide")
				.setIndicator("Guide", res.getDrawable(R.drawable.ic_tab_guide))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, OtherActivity.class);
		intent.putExtra("fonte", this.getString(R.string.intestazionedebian));
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
