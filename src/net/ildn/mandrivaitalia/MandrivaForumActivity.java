package net.ildn.mandrivaitalia;

import java.util.ArrayList;

import net.ildn.DataRetriever;
import net.ildn.GlobalMenu;
import net.ildn.NewsAdapter;
import net.ildn.NewsItemRow;
import net.ildn.fedorait.R;
import net.ildn.fedorait.WebContent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MandrivaForumActivity extends GlobalMenu {

	private DataRetriever dataRetriever;
	private static final String LOG_ID = "mandrakeitalia.org - ForumActivity";
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(LOG_ID, "Richiamato onListItemClick()");
		Intent showme = new Intent(getApplicationContext(), WebContent.class);
		showme.setData(Uri.parse(messages.get(position).getLink()
				.toExternalForm()));
		showme.putExtra("description", messages.get(position).getDescription());
		showme.putExtra("fonte", this.getString(R.string.intestazionemandriva));
		showme.putExtra("baseurl", messages.get(position).getLink().toString());
		startActivity(showme);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(LOG_ID, "Richiamato onCreate()");

		setContentView(R.layout.layoutnews);
		n_adapter = new NewsAdapter(this, R.layout.feedforumrow,
				new ArrayList<NewsItemRow>(),getString(R.string.intestazionemandriva));
		setListAdapter(this.n_adapter);
		String urlFeed = this.getString(R.string.mandrivafeedforum);
		dataRetriever = new DataRetriever(urlFeed, this);
		showProgressDialog();
		dataRetriever.start();
		
	}

}
