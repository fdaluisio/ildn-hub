package net.ildn.mageiaitalia;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import net.ildn.DataRetriever;
import net.ildn.GlobalMenu;
import net.ildn.NewsAdapter;
import net.ildn.NewsItemRow;
import net.ildn.WebContent;
import net.ildn.fedorait.R;

public class MageiaGuideActivity extends GlobalMenu {

	private DataRetriever dataRetriever;
	private static final String LOG_ID = "Mageiaitalia.org - GuideActivity";

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(LOG_ID, "Richiamato onListItemClick()");
		Intent showme = new Intent(getApplicationContext(), WebContent.class);
		showme.setData(Uri.parse(messages.get(position).getLink()
				.toExternalForm()));
		showme.putExtra("description", messages.get(position).getDescription());
		showme.putExtra("fonte", this.getString(R.string.intestazionemageia));
		showme.putExtra("baseurl", messages.get(position).getLink().toString());
		showme.putExtra("titlecontent", messages.get(position).getTitle());
		startActivity(showme);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_ID, "Richiamato onCreate()");
		setContentView(R.layout.layoutnews);
		n_adapter = new NewsAdapter(this, R.layout.feedguiderow,
				new ArrayList<NewsItemRow>(),getString(R.string.intestazionemageia));
		setListAdapter(this.n_adapter);
		String urlFeed = this.getString(R.string.mageiafeedguide);
		dataRetriever = new DataRetriever(urlFeed, this);
		showProgressDialog();
		dataRetriever.start();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(LOG_ID, "Richiamato onStart()");
	}

}
