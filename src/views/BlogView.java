package views;

import com.example.bucknellian.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class BlogView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog_view);
		WebView myWebView = (WebView) findViewById(R.id.blog_webview);

		Bundle extras = getIntent().getExtras();
		String url = "";
		if (extras != null) {
			url = extras.getString("url");
		}
		myWebView.loadUrl(url);
	}

}
