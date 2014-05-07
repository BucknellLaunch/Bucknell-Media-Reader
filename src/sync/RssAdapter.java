/**
 * 
 */
package sync;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import database.RssItemsDataSource;

import models.RssItem;
import models.SortedArrayList;
import adapters.RssItemAdapter;
import android.os.AsyncTask;

/**
 * @author Li Li
 * 
 */
public class RssAdapter extends AsyncTask<String, Void, ArrayList<RssItem>> {

	protected String icon;
	protected String url;
	protected RssItemAdapter<RssItem> adapter;
	protected RssItemsDataSource rssItemsDataSource;
	protected SortedArrayList<RssItem> rssItems;

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setRssItems(SortedArrayList<RssItem> rssItems) {
		this.rssItems = rssItems;
	}

	public void setListAdapter(RssItemAdapter<RssItem> adapter) {
		this.adapter = adapter;
	}

	public void setDataSource(RssItemsDataSource rssItemsDataSource) {
		this.rssItemsDataSource = rssItemsDataSource;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	protected ArrayList<RssItem> doInBackground(String... urls) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			RssHandler handler = new RssHandler();
			handler.setRssItems(rssItems);
			handler.setIcon(icon);
			saxParser.parse(url, handler);
			return handler.getItems();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<RssItem> result) {
		if (result != null) {
			if (this.rssItemsDataSource != null)
				this.rssItemsDataSource.addRssItems(this.rssItems);
		}
		adapter.notifyDataSetChanged();
	}

}
