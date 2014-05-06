package sync;


import database.RssItemsDataSource;
import models.RssItem;
import models.SortedArrayList;
import adapters.RssItemAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;


public class GetRSSDataTask extends AsyncTask<String, Void, Void> {

	private String icon;
	private RssReader rssReader;
	private RssItemAdapter<RssItem> adapter;
	private SortedArrayList<RssItem> rssItems;
	private Activity activity;
	private RssItemsDataSource rssItemsDataSource;

	public GetRSSDataTask(SortedArrayList<RssItem> rssItems, RssItemAdapter<RssItem> adapter, String icon, Activity activity, RssItemsDataSource rssItemsDataSource) {
		super();
		this.icon = icon;
		this.adapter = adapter;
		this.rssItems = rssItems;
		this.activity = activity;
		this.rssItemsDataSource = rssItemsDataSource;
		
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected Void doInBackground(String... urls) {

		// setTabs();

		// Create a list adapter
		this.rssReader = new RssReader(urls[0], this.icon,
				this.rssItems, this,this.activity);
		// Debug the task thread name
		Log.d("RssReader", Thread.currentThread().getName());

		try {
			// Parse RSS, get items
			rssReader.getItems();

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