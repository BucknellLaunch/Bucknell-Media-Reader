package sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import models.RssItem;
import models.RssLatestDate;
import models.SortedArrayList;

import org.xml.sax.SAXException;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import android.util.Log;

public class UpdateRssAdapter extends RssAdapter {
	private PullToRefreshLayout pullToRefreshLayout;

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
					latestDate = RssLatestDate.getInstance(activity);
					if (latestDate.get().compareTo(remoteDate) < 0) {
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

	public void setPullToRefeshLayout(PullToRefreshLayout pullToRefreshLayout) {
		this.pullToRefreshLayout = pullToRefreshLayout;
	}


	@Override
	protected SortedArrayList<RssItem> doInBackground(Void... params) {

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
	protected void onPostExecute(SortedArrayList<RssItem> result) {
		if (result != null) {
			if ((this.rssItemsDataSource != null) && result.size() != 0) {
				this.rssItemsDataSource.addRssItems(result);

				for (RssItem item : result) {
					this.rssItems.insertSorted(item);
				}
				this.adapter.notifyDataSetChanged();
			}
			
			updateLatestDate();
		} else {
			Log.e("Nothing to update", "Nothing to update");
		}
		if (pullToRefreshLayout != null) {
			pullToRefreshLayout.setRefreshComplete();
			Log.e("Finish pullToRefresh", "Finish pullToRefresh");
		}
		Log.e("finish", "finish");
	}
}
