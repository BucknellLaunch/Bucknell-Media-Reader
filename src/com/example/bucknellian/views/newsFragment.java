package com.example.bucknellian.views;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bucknellian.data.RssItem;

public class newsFragment extends ListFragment implements OnRefreshListener {
	// listItems and activity are used for calling new activities
	List<RssItem> listItems;
	Activity activity;

	private PullToRefreshLayout pullToRefreshLayout;

	public void setListItems(List<RssItem> l) {
		listItems = l;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		// We need to create a PullToRefreshLayout manually
		pullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

		// We can now setup the PullToRefreshLayout
		ActionBarPullToRefresh
				.from(getActivity())
				// We need to insert the PullToRefreshLayout into the Fragment's
				// ViewGroup
				.insertLayoutInto(viewGroup)
				// We need to mark the ListView and it's Empty View as pullable
				// This is because they are not direct children of the ViewGroup
				.theseChildrenArePullable(getListView(),
						getListView().getEmptyView()).listener(this)
				.setup(pullToRefreshLayout);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

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

		/*
		 * Intent i = new Intent(Intent.ACTION_VIEW); // We have to set data for
		 * our new Intent
		 * i.setData(Uri.parse(listItems.get(position).getLink())); // And start
		 * activity with our Intent activity.startActivity(i);
		 */

		Intent i = new Intent(activity, BlogView.class);
		i.putExtra("url", listItems.get(position).getLink());
		startActivity(i);
	}

	@Override
	public void onRefreshStarted(View view) {
		Log.e("Pull Working", "Pull Working");
		pullToRefreshLayout.setRefreshComplete();
	}

}
