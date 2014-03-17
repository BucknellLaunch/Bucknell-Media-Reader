package com.example.bucknellian;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.bucknellian.util.RssItemsDataSource;
import com.example.bucknellian.views.newsFragment;

public class MainActivity extends Activity{

	public RssItemsDataSource rssItemsDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Get references to the Fragments
		FragmentManager fm = getFragmentManager();
		// find the fragment
		newsFragment bucknellianNewsFragment = (newsFragment) fm
				.findFragmentById(R.id.BucknellianNewsFragment);
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
