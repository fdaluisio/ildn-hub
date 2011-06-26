package net.ildn;

import net.ildn.fedorait.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.rosaloves.bitlyj.Url;
import static com.rosaloves.bitlyj.Bitly.*;


public class WebContent extends Activity {

	WebView viewer;
	final Activity activity = this;
	static final int PROGRESS_DIALOG = 0;
	public static final String PREFS_NAME = "ildnPreference";
	private String fonte = "nothing";
	private String baseurl = "";
	private String titlecontent = "";
	
	private static final String LOG_ID ="ILDN-Hub - WebContent";;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.webview);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);

		Intent launchingIntent = getIntent();
		String content = launchingIntent.getData().toString();
		// Imposto sorgente del contenuto web
		fonte = launchingIntent.getExtras().getString("fonte");
		titlecontent = launchingIntent.getExtras().getString("titlecontent");
		Log.i(LOG_ID, "uri activity: " + content);
		this.baseurl = launchingIntent.getExtras().getString("baseurl");
		Log.i(LOG_ID, "baseurl from intent: " + this.baseurl);
		Uri baseurl = Uri.parse(this.baseurl);
		Log.i(LOG_ID,"Host: " + baseurl.getHost());

		
		/*
		 * Carico impostazioni personalizzate per i plugin flash e js
		 */
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		viewer = (WebView) findViewById(R.id.webview1);
		viewer.setWebViewClient(new HelloWebViewClient());
		viewer.getSettings().setJavaScriptEnabled(
				settings.getBoolean("js", true));
		viewer.getSettings().setPluginsEnabled(
				settings.getBoolean("flash", false));
		// Enable multi-touch if ROM supports
		viewer.getSettings().setSupportZoom(true);
		viewer.getSettings().setBuiltInZoomControls(true);
		viewer.getSettings().setAllowFileAccess(true);
		viewer.getSettings().setUseWideViewPort(true);
		
		activity.setTitle(fonte);

		
		// Load Description
		viewer.loadDataWithBaseURL(baseurl.toString(),
				launchingIntent.getExtras().getString("description"),
				"text/html", "UTF-8", null);

		viewer.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (view.getUrl()!= null && !view.getUrl().toString().equalsIgnoreCase("about:blank")) {
					Log.i(fonte + " - WebContent","onProgressChanged: " + view.getUrl().toString());
					activity.setTitle(Uri.parse(view.getUrl()).getHost().toString());
				}
					
				else 
					activity.setTitle(fonte);
				activity.setProgress(progress * 100);
				if (progress == 100)
					setProgressBarIndeterminateVisibility(false); // Hide
																	// progress
																	// circle
																	// when page
																	// loaded
				
			}
		});

	}

	private class HelloWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Toast.makeText(activity, "Oops...! " + description,
					Toast.LENGTH_SHORT).show();
			Log.i(fonte + " - WebContent", "onReceivedError: errorCode="
					+ errorCode + " description=" + description + " for url="
					+ failingUrl);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Log.i(fonte + " - WebContent", "onKeyDown "+ event.getKeyCode());
		
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			//Log.i(fonte + " - WebContent", "onKeyDown "+ event.getKeyCode() + " now call onBackPassed");
			onBackPressed();
			return true;
		}
/*		
		if ((keyCode == KeyEvent.KEYCODE_BACK) && viewer.canGoBack()) {
			viewer.goBack();
			return true;
		}	
*/
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * Before the dialog is created
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog dialog = ProgressDialog.show(activity, fonte,
					getResources().getString(R.string.loading), true);
			return dialog;

		default:
			return null;
		}
	}

	/**
	 * @return the fonte
	 */
	public String getFonte() {
		return fonte;
	}

	/**
	 * @param fonte
	 *            the fonte to set
	 */
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public void share(String subject,String text) {
		 final Intent intent = new Intent(Intent.ACTION_SEND);

		 intent.setType("text/plain");
		 intent.putExtra(Intent.EXTRA_SUBJECT, titlecontent);
		 Url bitlyurl = null;
		 try {
			 bitlyurl = as("vasheek", "R_011c063dcec95586aaeb5f7ef7b1c8b0").call(shorten(baseurl));
			 
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		 if (bitlyurl != null) {
			 intent.putExtra(Intent.EXTRA_TEXT, titlecontent + " - " + bitlyurl.getShortUrl());
		 }
		 else 
			 intent.putExtra(Intent.EXTRA_TEXT, titlecontent + " - " + baseurl);
		 startActivity(Intent.createChooser(intent, getString(R.string.share)));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.webcontentmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {

		case R.id.sharemenu:
			share("ILDN-Hub", "ILDN-Hub text to share");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
			
	@Override
	protected void onStop() {
		Log.i(fonte + " - WebContent", "onStop ");
		super.onStop();
	}

	
}
