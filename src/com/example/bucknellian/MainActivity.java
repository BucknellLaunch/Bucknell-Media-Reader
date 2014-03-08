package com.example.bucknellian;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.bucknellian.data.RssItem;
import com.example.bucknellian.util.GetRSSDataTask;
import com.example.bucknellian.util.RssItemAdapter;
import com.example.bucknellian.util.RssReader;
import com.example.bucknellian.views.newsFragment;

public class MainActivity extends Activity {

	private MainActivity local;
	private List<RssItem> rssItems;
	RssItemAdapter<RssItem> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		local = this;
		this.rssItems = new ArrayList<RssItem>();

		this.adapter = new RssItemAdapter<RssItem>(local,
				R.layout.rss_row_view, rssItems);

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

		GetRSSDataTask task = new GetRSSDataTask(this.rssItems, this.adapter, "Bucknellian.jpg");
		task.execute("http://bucknellian.net/category/news/feed/");

		Log.d("RssReader", Thread.currentThread().getName());
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
