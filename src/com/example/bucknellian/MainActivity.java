package com.example.bucknellian;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bucknellian.data.RssItem;
import com.example.bucknellian.util.RssReader;
import com.example.bucknellian.views.newsFragment;

public class MainActivity extends Activity {

	private MainActivity local;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		local = this;

		GetRSSDataTask task = new GetRSSDataTask();

		task.execute("http://bucknellian.blogs.bucknell.edu/feed/");

		Log.d("RssReader", Thread.currentThread().getName());

	}

	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
		@Override
		protected void onPreExecute() {
			loadProgressBar();

		}

		@Override
		protected List<RssItem> doInBackground(String... urls) {

			// Debug the task thread name
			Log.d("RssReader", Thread.currentThread().getName());

			try {
				// Create RSS reader
				RssReader rssReader = new RssReader(urls[0]);

				// Parse RSS, get items
				return rssReader.getItems();

			} catch (Exception e) {
				Log.e("RssReader", e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<RssItem> result) {

			loadMainScreen();

			// Create a list adapter
			ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,
					R.layout.paperpad_list, result);

			// Get references to the Fragments
			FragmentManager fm = getFragmentManager();
			// find the fragment
			newsFragment bucknellianNewsFragment = (newsFragment) fm
					.findFragmentById(R.id.BucknellianNewsFragment);
			// set listItems variable in bucknellianNewsFragment for local
			// reference
			bucknellianNewsFragment.setListItems(result);
			// set adapter
			bucknellianNewsFragment.setListAdapter(adapter);

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
	}
}
