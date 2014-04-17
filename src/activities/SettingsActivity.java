/**
 * 
 */
package activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import fragments.SettingsFragment;

/**
 * @author ll024
 * 
 */
public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}

}
