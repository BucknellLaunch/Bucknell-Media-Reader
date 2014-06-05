/**
 * 
 */
package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.bucknellian.R;

/**
 * @author ll024
 * 
 */
public class BlogViewFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.blog_view, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		WebView myWebView = (WebView) getView().findViewById(R.id.blog_webview);

		String url = getArguments().getString("url");
		myWebView.loadUrl(url);
	}
}
