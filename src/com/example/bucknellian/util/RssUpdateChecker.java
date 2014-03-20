package com.example.bucknellian.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bucknellian.data.RssItem;
import com.example.bucknellian.data.SortedArrayList;

public class RssUpdateChecker extends AsyncTask<String, Void, Void> {
	private String url;
	private Date latestLocalDate;
	private Date latestRemoteDate;
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz");

	// an inner Handler class to handle RSS data.
	private class RssUpdateHandler extends DefaultHandler {
		private int numOfItemParsed;
		private boolean parsingPubDate;

		public RssUpdateHandler() {
			this.numOfItemParsed = 0;
			this.parsingPubDate = false;
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (this.numOfItemParsed > 0)
				return;
			if ("pubDate".equals(qName))
				this.parsingPubDate = true;

		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

			if (this.numOfItemParsed > 0)
				return;

			if ("item".equals(qName))
				this.numOfItemParsed++;

		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (this.numOfItemParsed > 0)
				return;

			if (this.parsingPubDate == true) {
				try {
					// set the newestRemoteDate to be the date of the first item
					// in the remote site
					latestRemoteDate = sdf.parse(new String(ch, start, length));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				this.parsingPubDate = false;
			}

		}
	}

	public RssUpdateChecker(String url, SortedArrayList<RssItem> rssItems) {
		this.url = url;
		try {
			RssItem item = rssItems.get(0);
			// set latestLocalDate to the date of the first item in the rssItem
			// list, which is the newest
			this.latestLocalDate = item.getPubDateObject();
		} catch (Exception e) {
			Log.e("No Items in rssItems", "No Items in rssItems");
			e.printStackTrace();
		}

	}

	@Override
	protected Void doInBackground(String... params) {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			RssUpdateHandler handler = new RssUpdateHandler();
			saxParser.parse(this.url, handler);
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
	protected void onPostExecute(Void result) {
		try{
			if (this.latestLocalDate.compareTo(this.latestRemoteDate) < 0){
				// need to update the list
			}
		} catch (Exception e){
			Log.e("Not success", "Not success");
			e.printStackTrace();
		}
	}
}
