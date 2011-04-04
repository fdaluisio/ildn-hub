package net.ildn.feed;

import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

public class AndroidSaxFeedParser extends BaseFeedParser {

	static final String RSS = "rss";
	
	private static final String LOG_ID = "ILDN Hub - AndroidSaxFeedParser";

	
	static{
		try{
		nameSpacesHash.put(CREATOR, "http://purl.org/dc/elements/1.1/");
		}catch (Throwable e){
			Log.e(LOG_ID,"Error in intializing: "+e.toString(),e);
		}
	}
	

	public AndroidSaxFeedParser(String feedUrl) {
		super(feedUrl);
	}

	public List<Message> parse(){
		final Message currentMessage = new Message();
		RootElement root = new RootElement(RSS);
		final List<Message> messages = new ArrayList<Message>();
		Element channel = root.getChild(CHANNEL);
		Element item = channel.getChild(ITEM);
		item.setEndElementListener(new EndElementListener() {
			public void end() {
				try{
				messages.add((Message)currentMessage.clone());
				}catch(Throwable e){
					Log.e(LOG_ID,"Enable to add message "+currentMessage.toString()+": "+e.toString(),e);
					throw new RuntimeException(e);
				}
			}
		});
		item.getChild(getElementNamespace(TITLE),TITLE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setTitle(body);
					}
				});
		item.getChild(getElementNamespace(LINK),LINK).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setLink(body);
					}
				});
		item.getChild(getElementNamespace(DESCRIPTION),DESCRIPTION).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setDescription(body);
					}
				});
		item.getChild(getElementNamespace(PUB_DATE),PUB_DATE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setDate(body);
					}
				});
		item.getChild(getElementNamespace(CREATOR),CREATOR).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setCreator(body);
					}
				});
		try {
			Xml.parse(this.getXml(), root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return messages;
	}
}
