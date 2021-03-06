package net.ildn.feed;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.Log;

public class Message implements Comparable<Message>,Cloneable {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	static SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"EEEEE, d MMM yyyy");

	static SimpleDateFormat preFORMATTER = new SimpleDateFormat(
			"EEE, d MMM yyyy kk:mm:ss z", Locale.US);

	private String title = " - ";
	private URL link;
	private String description = " - ";
	private Date date;
	private String creator = " - ";

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		if (creator != null)
			this.creator = creator.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}

	// getters and setters omitted for brevity
	public URL getLink() {
		return link;
	}

	public void setLink(String link) {
		try {
			this.link = new URL(link);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public String getDate() {
		return FORMATTER.format(this.date);
		// return FORMATTER.format(this.date);
	}

	public void setDate(String date) {
		// pad the date if necessary
		while (!date.endsWith("00")) {
			date += "0";
		}
		// una errata formattazione della data porta a mostrare una lista di
		// contenuti vuota allora
		// forzo la data a quella del device nel catch
		//Log.i("Fedora-it.org - Message",	"feed news pubdate fornita dal server:" + date.toString());
		try {
			this.date = preFORMATTER.parse(date.trim());
			//Log.i("Fedora-it.org - Message", "feed news pubdate preFormattata:"+ this.date);
		} catch (ParseException ex) {
			Log.e("Fedora-it.org - Message",
					"error preFORMATTER data from server");
			this.date = new Date();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ");
		sb.append(title);
		sb.append('\n');
		sb.append("Date: ");
		sb.append(this.getDate());
		sb.append('\n');
		sb.append("Link: ");
		sb.append(link);
		sb.append('\n');
		sb.append("Description: ");
		sb.append(description);
		sb.append('\n');
		sb.append("Creator: ");
		sb.append(creator);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public int compareTo(Message another) {
		if (another == null)
			return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}
}
