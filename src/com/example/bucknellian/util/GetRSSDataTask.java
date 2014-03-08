package com.example.bucknellian.util;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bucknellian.data.RssItem;

public class GetRSSDataTask extends AsyncTask<String, Void, Void> {

	private String icon;
	private RssReader rssReader;
	private RssItemAdapter<RssItem> adapter;
	private List<RssItem> rssItems;

	public GetRSSDataTask(List<RssItem> rssItems, RssItemAdapter<RssItem> adapter, String icon) {
		super();
		this.icon = icon;
		this.adapter = adapter;
		this.rssItems = rssItems;
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected Void doInBackground(String... urls) {

		// setTabs();

		// Create a list adapter
		this.rssReader = new RssReader(urls[0], this.icon,
				this.rssItems, this);
		// Debug the task thread name
		Log.d("RssReader", Thread.currentThread().getName());

		try {
			// Create RSS reader

			// Parse RSS, get items
			rssReader.getItems();

		} catch (Exception e) {
			Log.e("RssReader", e.getMessage());
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

	}
}