package sync;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import database.RssItemsDataSource;
import models.RssItem;
import models.SortedArrayList;
import adapters.RssItemAdapter;
import android.app.Activity;
import android.os.AsyncTask;


public class NewRssAdapter extends AsyncTask<String, Void, Void> {

	private String icon;
	private RssItemAdapter<RssItem> adapter;
	private SortedArrayList<RssItem> rssItems;
	private Activity activity;
	private RssItemsDataSource rssItemsDataSource;
	private String url;

	public NewRssAdapter() {
		super();
	}

	@Override
	protected void onPreExecute() {

	}
	
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public void setRssItems(SortedArrayList<RssItem> rssItems){
		this.rssItems = rssItems;
	}
	
	public void setListAdapter(RssItemAdapter<RssItem> adapter){
		this.adapter = adapter;
	}
	
	public void setBaseActivity(Activity activity){
		this.activity = activity;
	}
	
	public void setDataSource(RssItemsDataSource rssItemsDataSource){
		this.rssItemsDataSource = rssItemsDataSource;
	}
	
	public void setUrl(String url){
		this.url = url;
	}

	
	public void getItems() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		SAXParser saxParser = factory.newSAXParser();
		RssParseHandler handler = new RssParseHandler(this.icon, this.rssItems,
				this, this.activity);
		saxParser.parse(url, handler);
	}

	@Override
	protected Void doInBackground(String... urls) {
		try {
			getItems();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void publicPublishProgress(){
		publishProgress();
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		this.adapter.notifyDataSetChanged();
	}
	@Override
	protected void onPostExecute(Void result) {
		if (this.rssItemsDataSource != null)
			this.rssItemsDataSource.addRssItems(this.rssItems);
	}
}