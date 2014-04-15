package sync;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import models.RssItem;
import models.SortedArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import data.RssItemsDataSource;

import adapters.RssItemAdapter;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;


public class RssUpdateChecker extends
		AsyncTask<String, Void, ArrayList<RssItem>> {
	private String url;
	private Date latestLocalDate;
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz");

	private String icon;
	private SortedArrayList<RssItem> localItems;
	private RssItemAdapter<RssItem> adapter;
	private RssItemsDataSource rssItemsDataSource;

	// an inner Handler class to handle RSS data.
	private class RssUpdateHandler extends DefaultHandler {

		private ArrayList<RssItem> rssItems;
		// We have a local reference to an object which is constructed while
		// parser
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

		public RssUpdateHandler() {
			super();
			this.rssItems = new ArrayList<RssItem>();
		}

		public ArrayList<RssItem> getItems() {
			return this.rssItems;
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
			} else if ("category".equals(qName)) {
				parsingCategory = true;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if ("item".equals(qName)) {
				Date remoteDate = currentItem.getPubDateObject();
				try {
					if (latestLocalDate.compareTo(remoteDate) < 0) {
						currentItem.setIcon(icon);
						this.rssItems.add(currentItem);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if ("title".equals(qName)) {
				parsingTitle = false;
			} else if ("link".equals(qName)) {
				parsingLink = false;
			} else if ("pubDate".equals(qName)) {
				parsingPubDate = false;
			} else if ("category".equals(qName)) {
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
			} else if (parsingCategory) {
				if (currentItem != null) {
					currentItem.setCategory(new String(ch, start, length));
					parsingCategory = false;
				}
			}
		}
	}

	public RssUpdateChecker(String url, SortedArrayList<RssItem> rssItems,
			String icon, RssItemAdapter<RssItem> adapter,
			RssItemsDataSource rssItemsDataSource) {
		this.url = url;
		this.icon = icon;
		this.localItems = rssItems;
		this.adapter = adapter;
		this.rssItemsDataSource = rssItemsDataSource;
		// set latestLocalDate to the date of the first item in the rssItem
		// list, which is the newest
		try {
			RssItem item = rssItems.get(0);
			Log.e("Date of the first element", item.getPubDate());
			this.latestLocalDate = item.getPubDateObject();
		} catch (Exception e) {
			Log.e("No Items in rssItems", "No Items in rssItems");
			e.printStackTrace();
		}
	}

	@Override
	protected ArrayList<RssItem> doInBackground(String... params) {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			RssUpdateHandler handler = new RssUpdateHandler();
			saxParser.parse(this.url, handler);
			return handler.getItems();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(ArrayList<RssItem> result) {
		if (result != null) {
			if ((this.rssItemsDataSource != null) && result.size() != 0) {
				this.rssItemsDataSource.addRssItems(result);

				for (RssItem item : result) {
					this.localItems.insertSorted(item);
				}
				this.adapter.notifyDataSetChanged();
			}
		} else {
			Log.e("Nothing to update", "Nothing to update");
		}
	}
}
