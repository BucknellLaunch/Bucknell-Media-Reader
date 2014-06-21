package fragments;

import java.util.List;

import models.RssItem;
import models.SortedArrayList;
import sync.RssAdapter;
import sync.UpdateRssAdapter;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import views.BlogView;
import adapters.RssItemAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bucknellian.R;

import database.RssItemsDataSource;


public class NewsFragment extends ListFragment implements OnRefreshListener {
	private PullToRefreshLayout pullToRefreshLayout;
	private SortedArrayList<RssItem> rssItems;
	public SortedArrayList<RssItem> getRssItems() {
		return rssItems;
	}

	private RssItemAdapter<RssItem> adapter;
	
	public RssItemAdapter<RssItem> getAdapter() {
		return adapter;
	}

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
			showSplashScreen();
			
			// add new RSS
			Log.e("Read New Rss", "Read New Rss");
			RssAdapter newBucknellRssAdapter = new RssAdapter();
			newBucknellRssAdapter.setIcon("Bucknellian.jpg");
			newBucknellRssAdapter.setListAdapter(adapter);
			newBucknellRssAdapter.setRssItems(rssItems);
			newBucknellRssAdapter.setDataSource(rssItemsDataSource);
			newBucknellRssAdapter.setUrl("http://bucknellian.net/category/news/feed/");			
			newBucknellRssAdapter.execute();

			RssAdapter newCampusVinylRssAdapter = new RssAdapter();
			newCampusVinylRssAdapter.setIcon("CampusVinyl.jpg");
			newCampusVinylRssAdapter.setListAdapter(adapter);
			newCampusVinylRssAdapter.setRssItems(rssItems);
			newCampusVinylRssAdapter.setDataSource(rssItemsDataSource);
			newCampusVinylRssAdapter.setUrl("http://feeds.feedburner.com/CampusVinyl");
			newCampusVinylRssAdapter.setActivity(getActivity());
			newCampusVinylRssAdapter.execute();
		}
	}

	private void showSplashScreen() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		Fragment splashScreen = new SplashScreenFragment();
		fragmentTransaction.add(android.R.id.content, splashScreen, "SplashScreen");
		fragmentTransaction.commit();
	}

	public void updateRss() {
		UpdateRssAdapter bucknellChecker = new UpdateRssAdapter();
		bucknellChecker.setIcon("Bucknellian.jpg");
		bucknellChecker.setListAdapter(adapter);
		bucknellChecker.setRssItems(rssItems);
		bucknellChecker.setDataSource(rssItemsDataSource);
		bucknellChecker.setUrl("http://bucknellian.net/category/news/feed/");		
		bucknellChecker.setupLatestLocalDate();
		bucknellChecker.execute();
		
		
		UpdateRssAdapter campusVinylChecker = new UpdateRssAdapter();
		campusVinylChecker.setIcon("CampusVinyl.jpg");
		campusVinylChecker.setListAdapter(adapter);
		campusVinylChecker.setRssItems(rssItems);
		campusVinylChecker.setDataSource(rssItemsDataSource);
		campusVinylChecker.setUrl("http://feeds.feedburner.com/CampusVinyl");		
		campusVinylChecker.setupLatestLocalDate();
		campusVinylChecker.setPullToRefeshLayout(pullToRefreshLayout);
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

		/*
		Intent i = new Intent(getActivity(), BlogView.class);
		i.putExtra("url", rssItems.get(position).getLink());
		startActivity(i);
		*/
		
		Bundle bundle = new Bundle();
		bundle.putString("url", rssItems.get(position).getLink());
		
		BlogViewFragment blogViewFragment = new BlogViewFragment();
		blogViewFragment.setArguments(bundle);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(android.R.id.content, blogViewFragment);
		fragmentTransaction.commit();
	}

	@Override
	public void onRefreshStarted(View view) {
		updateRss();
	}

}
