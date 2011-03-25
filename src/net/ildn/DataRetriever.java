package net.ildn;

import java.util.ArrayList;
import java.util.List;

import net.ildn.feed.AndroidSaxFeedParser;
import net.ildn.feed.FeedParser;
import net.ildn.feed.Message;
import android.util.Log;

public class DataRetriever extends Thread {

	private String urlFeed;
	private GlobalMenu activity;

	private static final String LOG_ID = "ILDN Hub - DataRetriever";

	public DataRetriever(String urlFeed, GlobalMenu activity) {
		this.urlFeed = urlFeed;
		this.activity = activity;
	}

	public String getUrlFeed() {
		return urlFeed;
	}

	public void setUrlFeed(String urlFeed) {
		this.urlFeed = urlFeed;
	}

	@Override
	public void run() {
		List<Message> messages = getContent();
		activity.updateContents(messages);
	}

	private List<Message> getContent() {
		List<Message> messages = new ArrayList<Message>();
		try {
			Log.i(LOG_ID, "ParserType = ANDROID_SAX ");
			FeedParser parser = new AndroidSaxFeedParser(urlFeed);
			long start = System.currentTimeMillis();
			messages = parser.parse();
			long duration = System.currentTimeMillis() - start;
			Log.i(LOG_ID, "Parser duration=" + duration);
		} catch (Throwable t) {
			Log.e(LOG_ID, t.getMessage(), t);
		}
		return messages;
	}
}
