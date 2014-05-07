package sync;


import java.util.List;

import models.RssItem;
import models.SortedArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;


public class RssParseHandler extends DefaultHandler {

	private SortedArrayList<RssItem> rssItems;
	// We have a local reference to an object which is constructed while parser
	// is working on an item tag
	// Used to reference item while parsing
	private RssItem currentItem;
	// We have two indicators which are used to differentiate whether a tag
	// title or link is being processed by the parser
	// Parsing title indicator
	private boolean parsingTitle;
	// Parsing link indicator
	private boolean parsingLink;
	// Parsing lastBuildDate indicator
	private boolean parsingPubDate;
	// Parsing category indicator
	private boolean parsingCategory;
	
	private String icon;
	private NewRssAdapter task;
	private Activity activity;
	
	public RssParseHandler(String icon, SortedArrayList<RssItem> rssItems,
			NewRssAdapter task, Activity activity) {
		this.rssItems = rssItems;
		this.task = task;
		this.icon = icon;
		this.activity = activity;
	}

	// We have an access method which returns a list of items that are read from
	// the RSS feed. This method will be called when parsing is done.
	public List<RssItem> getItems() {
		return rssItems;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("item".equals(qName)) {
			currentItem = new RssItem();
		} else if ("title".equals(qName)) {
			parsingTitle = true;
		} else if ("link".equals(qName)) {
			parsingLink = true;
		} else if ("pubDate".equals(qName)) {
			parsingPubDate = true;
		} else if ("category".equals(qName)){
			parsingCategory = true;
		}
	}

	// The EndElement method adds the current RssItem to the list when a closing
	// item tag is processed. It sets appropriate indicators to false - when
	// title and link closing tags are processed
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("item".equals(qName)) {
			currentItem.setIcon(icon);
			RssItem newItem = new RssItem(currentItem);

			this.activity.runOnUiThread(new RssUpdateRunnable(newItem) {
				public void run() {
					rssItems.insertSorted(this.item);
					task.publicPublishProgress();
				}
			});
			currentItem = null;
		} else if ("title".equals(qName)) {
			parsingTitle = false;
		} else if ("link".equals(qName)) {
			parsingLink = false;
		} else if ("pubDate".equals(qName)) {
			parsingPubDate = false;
		} else if ("category".equals(qName)){
			parsingCategory = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (parsingTitle) {
			if (currentItem != null)
				currentItem.setTitle(new String(ch, start, length));
		} else if (parsingLink) {
			if (currentItem != null) {
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
		} else if (parsingPubDate) {
			if (currentItem != null) {
				currentItem.setPubDate(new String(ch, start, length));
				parsingPubDate = false;
			}
		} else if (parsingCategory){
			if (currentItem != null){
				currentItem.setCategory(new String(ch, start, length));
				parsingCategory = false;
			}
		}
	}

}
