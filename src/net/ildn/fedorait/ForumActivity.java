package net.ildn.fedorait;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ForumActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Fedora-it.org - ForumActivity", "Richiamato onCreate()");
		TextView textview = new TextView(this);
		textview.setText("This is the Forum tab");
		setContentView(textview);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("Fedora-it.org - ForumActivity", "Richiamato onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("Fedora-it.org - ForumActivity", "Richiamato onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("Fedora-it.org - ForumActivity", "Richiamato onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("Fedora-it.org - ForumActivity", "Richiamato onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("Fedora-it.org - ForumActivity", "Richiamato onDestroy()");
	}

}
