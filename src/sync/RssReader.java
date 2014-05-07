package sync;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import models.RssItem;
import models.SortedArrayList;

import android.app.Activity;


public class RssReader {

	private String rssUrl;
	private String icon;
	private SortedArrayList<RssItem> rssItems;
	private NewRssAdapter task;

	private Activity activity;

	public RssReader(String rssUrl, String icon, SortedArrayList<RssItem> rssItems,
			NewRssAdapter task, Activity activity) {
		this.rssUrl = rssUrl;
		this.icon = icon;
		this.task = task;
		this.rssItems = rssItems;
		this.activity = activity;

	}

	public void getItems() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		SAXParser saxParser = factory.newSAXParser();
		RssParseHandler handler = new RssParseHandler(this.icon, this.rssItems,
				this.task, this.activity);
		saxParser.parse(rssUrl, handler);
	}

}
