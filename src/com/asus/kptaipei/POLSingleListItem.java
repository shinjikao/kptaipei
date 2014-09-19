package com.asus.kptaipei;

import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

public class POLSingleListItem extends Fragment {
	private static String TAG = "KP";

	private TextView tv_title;
	private TextView tv_content;
	private WebView myWebView;

	HashMap<String, String> _retVal;

	public POLSingleListItem(HashMap<String, String> retVal) {
		_retVal = retVal;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.d(TAG, "SingleListItem");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.politics_single_list_item_view,
				container, false);

		TextView tv_title = (TextView) rootView.findViewById(R.id.Single_Title);
		String title = _retVal.get("title");

		tv_title.setText(title);

		TextView tv_content = (TextView) rootView
				.findViewById(R.id.Single_Content);
		String content =stripHtml( _retVal.get("content"));
		String y_content = _retVal.get("content").replace("src=\"//", "src=\"http://");
				
		tv_content.setText(content);
		tv_content.setMovementMethod(new ScrollingMovementMethod());

		myWebView = (WebView) rootView.findViewById(R.id.youtube);

		
		 Log.d(TAG,content);
		String youtubeTag = "<html><body>"
				+ y_content.substring(y_content.indexOf("<iframe"),
						y_content.indexOf("</iframe>") + 9) + "</body></html>";

		//Log.d(TAG, youtubeTag);
		myWebView.setPadding(0, 0, 0, 0);
		myWebView.setInitialScale(getScale());
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setPluginsEnabled(true);
		myWebView.setEnabled(true);
		
		myWebView.loadData(youtubeTag, "text/html", "utf-8");

		return rootView;

	}
	private int getScale(){
	    Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
	    int width = display.getWidth(); 
	    Double val = new Double(width)/new Double(560);
	    val = val * 100d;
	    return val.intValue();
	}
	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}
}
