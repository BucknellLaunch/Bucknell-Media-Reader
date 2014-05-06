package fragments;

import java.util.List;

import models.RssItem;
import models.SortedArrayList;

import sync.GetRSSDataTask;
import sync.RssUpdateChecker;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import views.BlogView;
import adapters.RssItemAdapter;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bucknellian.R;

import database.RssItemsDataSource;


public class newsFragment extends ListFragment implements OnRefreshListener {
	private PullToRefreshLayout pullToRefreshLayout;
	private SortedArrayList<RssItem> rssItems;
	private RssItemAdapter<RssItem> adapter;
	public RssItemsDataSource rssItemsDataSource;


	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		this.rssItems = new SortedArrayList<RssItem>();
		this.adapter = new RssItemAdapter<RssItem>(getActivity(),
				R.layout.rss_row_view, rssItems);

		this.rssItemsDataSource = new RssItemsDataSource(getActivity());
		this.rssItemsDataSource.open();

		this.setListAdapter(adapter);

		// if there is something in the database, display them first.
		if (!rssItemsDataSource.isDatabaseEmpty()) {
			Log.e("Read Old Rss", "Read Old Rss");
			List<RssItem> oldItems = rssItemsDataSource.getAllRssItems();
			for (RssItem item : oldItems) {
				// need to change this line
				this.rssItems.insertSorted(item);
				adapter.notifyDataSetChanged();
			}
			
			updateRss();
		} else {
			// add new RSS
			Log.e("Read New Rss", "Read New Rss");
			GetRSSDataTask bucknellianTask = new GetRSSDataTask(this.rssItems,
					this.adapter, "Bucknellian.jpg", getActivity(), null);
			bucknellianTask.execute("http://bucknellian.net/category/news/feed/");

			GetRSSDataTask campusVinylTask = new GetRSSDataTask(this.rssItems,
					this.adapter, "CampusVinyl.jpg", getActivity(),
					this.rssItemsDataSource);
			campusVinylTask.execute("http://feeds.feedburner.com/CampusVinyl");
		}
	}

	public void updateRss() {
		RssUpdateChecker bucknellChecker = new RssUpdateChecker(
				"http://bucknellian.net/category/news/feed/",
				this.rssItems, "Bucknellian.jpg", this.adapter,
				this.rssItemsDataSource);
		bucknellChecker.execute();
		
		RssUpdateChecker campusVinylChecker = new RssUpdateChecker(
				"http://feeds.feedburner.com/CampusVinyl",
				this.rssItems, "CampusVinyl.jpg", this.adapter,
				this.rssItemsDataSource);
		campusVinylChecker.execute();
	}

	@Override
	public void onResume() {
		rssItemsDataSource.open();
		super.onResume();
	}

	@Override
	public void onPause() {
		rssItemsDataSource.close();
		super.onPause();
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

		Intent i = new Intent(getActivity(), BlogView.class);
		i.putExtra("url", rssItems.get(position).getLink());
		startActivity(i);
	}

	@Override
	public void onRefreshStarted(View view) {
		updateRss();
		pullToRefreshLayout.setRefreshComplete();
	}

}
