package com.example.bucknellian;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.bucknellian.data.RssItem;
import com.example.bucknellian.util.GetRSSDataTask;
import com.example.bucknellian.util.RssItemAdapter;
import com.example.bucknellian.util.RssItemsDataSource;
import com.example.bucknellian.views.newsFragment;

public class MainActivity extends Activity {

	private MainActivity local;
	private List<RssItem> rssItems;
	private RssItemAdapter<RssItem> adapter;
	public RssItemsDataSource rssItemsDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		local = this;
		this.rssItems = new ArrayList<RssItem>();
		this.adapter = new RssItemAdapter<RssItem>(local,
				R.layout.rss_row_view, rssItems);
		
		this.rssItemsDataSource = new RssItemsDataSource(this);
		this.rssItemsDataSource.open();
		
		// Get references to the Fragments
		FragmentManager fm = getFragmentManager();
		// find the fragment
		newsFragment bucknellianNewsFragment = (newsFragment) fm
				.findFragmentById(R.id.BucknellianNewsFragment);
		// set listItems variable in bucknellianNewsFragment for local
		// reference
		bucknellianNewsFragment.setListItems(rssItems);
		// set adapter
		bucknellianNewsFragment.setListAdapter(adapter);
		
		loadMainScreen();
		
		if (rssItemsDataSource.isDatabaseEmpty()){
			Log.e("Read New Rss", "Read New Rss");
			GetRSSDataTask bucknellianTask = new GetRSSDataTask(this.rssItems, this.adapter, "Bucknellian.jpg", local, null);
			bucknellianTask.execute("http://bucknellian.net/category/news/feed/");
			
			
			GetRSSDataTask campusVinylTask = new GetRSSDataTask(this.rssItems, this.adapter, "CampusVinyl.jpg", local, this.rssItemsDataSource);
			campusVinylTask.execute("http://feeds.feedburner.com/CampusVinyl");
		}
		else{
			Log.e("Read Old Rss", "Read Old Rss");
			List<RssItem> oldItems = rssItemsDataSource.getAllRssItems();
			for (RssItem item: oldItems){
				this.rssItems.add(item);
				adapter.notifyDataSetChanged();
			}
			
		}

	}


	@Override
	protected void onResume(){
		rssItemsDataSource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		rssItemsDataSource.close();
		super.onPause();
	}
	
	private void loadProgressBar() {
		ViewGroup mainView = (ViewGroup) findViewById(R.id.mainView);
		mainView.setVisibility(View.INVISIBLE);

		View progressBar = (View) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.VISIBLE);
	}

	private void loadMainScreen() {
		View progressBar = (View) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);

		ViewGroup mainView = (ViewGroup) findViewById(R.id.mainView);
		mainView.setVisibility(View.VISIBLE);
	}

	private void setTabs() {
		final ActionBar actionBar = getActionBar();
		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Create a tab listener that is called when the user changes tabs.

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				// show the given tab
			}

			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// hide the given tab
			}

			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// probably ignore this event
			}
		};

		actionBar.addTab(actionBar.newTab().setText("Bucknellian")
				.setTabListener(tabListener));

		actionBar.addTab(actionBar.newTab().setText("Something else")
				.setTabListener(tabListener));

		actionBar.addTab(actionBar.newTab().setText("Something else")
				.setTabListener(tabListener));
	}

}
