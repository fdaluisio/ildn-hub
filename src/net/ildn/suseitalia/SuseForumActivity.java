package net.ildn.suseitalia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SuseForumActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("suseitalia.org - ForumActivity", "Richiamato onCreate()");
		TextView textview = new TextView(this);
		textview.setText("This is the Forum tab");
		setContentView(textview);
	}

}
