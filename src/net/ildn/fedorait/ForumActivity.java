package net.ildn.fedorait;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ForumActivity extends Activity {
//public class ForumActivity extends GlobalMenu {	
	//private DataRetriever dataRetriever;
	private static final String LOG_ID = "Fedora-it.org - ForumActivity";
/*	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(LOG_ID, "Richiamato onListItemClick()");
		Intent showme = new Intent(getApplicationContext(), WebContent.class);
		showme.setData(Uri.parse(messages.get(position).getLink()
				.toExternalForm()));
		showme.putExtra("description", messages.get(position).getDescription());
		showme.putExtra("fonte", this.getString(R.string.intestazionefedora));
		showme.putExtra("baseurl", messages.get(position).getLink().toString());
		startActivity(showme);
	}
*/
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_ID, "Richiamato onCreate()");
		
		TextView textview = new TextView(this);
		textview.setText("This is the Forum tab");
		setContentView(textview);
	
	/*	
		n_adapter = new NewsAdapter(this, R.layout.feedforumrow,
				new ArrayList<NewsItemRow>(),getString(R.string.intestazionefedora));
		setListAdapter(this.n_adapter);
		String urlFeed = this.getString(R.string.fedorafeedforum);
		dataRetriever = new DataRetriever(urlFeed, this);
		showProgressDialog();
		dataRetriever.start();
	*/	
	}

}
