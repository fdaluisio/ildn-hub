package net.ildn.feed;

import java.util.List;

public interface FeedParser {
	List<Message> parse();
}