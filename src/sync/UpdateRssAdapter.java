package sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import models.RssItem;
import models.SortedArrayList;

import org.xml.sax.SAXException;

import android.util.Log;


public class UpdateRssAdapter extends RssAdapter {
	private Date latestLocalDate;

	// an inner Handler class to handle RSS data.
	private class RssUpdateHandler extends RssHandler {

		public RssUpdateHandler() {
			super();
			this.rssItems = new SortedArrayList<RssItem>();
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
	}

	
	public void setupLatestLocalDate(){
		try {
			if (rssItems.size() == 0)
				return;
			RssItem item = rssItems.get(0);
			Log.e("Date of the first element", item.getPubDate());
			this.latestLocalDate = item.getPubDateObject();
		} catch (Exception e) {
			Log.e("No Items in rssItems", "No Items in rssItems");
			e.printStackTrace();
		}
	}

	@Override
	protected ArrayList<RssItem> doInBackground(Void... params) {

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
					this.rssItems.insertSorted(item);
				}
				this.adapter.notifyDataSetChanged();
			}
		} else {
			Log.e("Nothing to update", "Nothing to update");
		}
	}
}
