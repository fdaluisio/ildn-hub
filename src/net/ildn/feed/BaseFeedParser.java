package net.ildn.feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.util.ByteArrayBuffer;

public abstract class BaseFeedParser implements FeedParser {

	// names of the XML tags
	static final String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final String DESCRIPTION = "description";
	static final String LINK = "link";
	static final String TITLE = "title";
	static final String ITEM = "item";
	static final String CREATOR = "creator";

	protected static HashMap<String,String> nameSpacesHash=new HashMap<String, String>();
	
	private final URL feedUrl;

	protected BaseFeedParser(String feedUrl) {
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getXml() {
		InputStream stream = null;
		try {
			byte[] tmp = new byte[1024];
			stream = getInputStream();
			ByteArrayBuffer buff = new ByteArrayBuffer(0);
			int n;
			while ((n = stream.read(tmp)) != -1) {
				buff.append(tmp, 0, n);
			}
			String xml = new String(buff.toByteArray(), "UTF-8");
			return xml.trim();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (Exception e) {
			}
		}
	}
	
	protected String getElementNamespace(String elementName){
		String result="";
		if (nameSpacesHash.get(elementName)!=null){
			result=nameSpacesHash.get(elementName);
		}
		return result;
	}
	
}