/**
 * 
 */
package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author ll024
 *
 *	Manages the collection of fragments for each RSS list item.
 */
public class RssCollectionPagerAdapter extends FragmentStatePagerAdapter {
	
	public RssCollectionPagerAdapter(android.support.v4.app.FragmentManager fm){
		super(fm);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
