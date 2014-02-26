package com.example.bucknellian.views;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.example.bucknellian.BlogView;
import com.example.bucknellian.data.RssItem;

public class newsFragment extends ListFragment {
	// listItems and activity are used for calling new activities
	List<RssItem> listItems;
	Activity activity;

	public void setListItems(List<RssItem> l) {
		listItems = l;
	}

	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		activity = a;
	}

	// create a new activity when items are clicked
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// We create an Intent which is going to display data
		
/*		 Intent i = new Intent(Intent.ACTION_VIEW); // We have to set data for our new Intent
		 i.setData(Uri.parse(listItems.get(position).getLink())); // And start activity with our Intent activity.startActivity(i);
		 */

		Intent i = new Intent(activity, BlogView.class);
		i.putExtra("url", listItems.get(position).getLink());
		startActivity(i);
	}
}
