package com.asus.kptaipei;

import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.TextView;

public class SingleListItem_Politics extends Fragment {
	private static String TAG = "KP";

	private TextView tv_title;
	private TextView tv_content;
	private WebView myWebView;
	private WebView myWebViewContent;

	HashMap<String, String> _retVal;

	public SingleListItem_Politics(HashMap<String, String> retVal) {
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

		View rootView = inflater.inflate(R.layout.politics_single_list_item_view, container, false);
		// TextView tv_content = (TextView)
		// rootView.findViewById(R.id.Single_Content);
		String html = _retVal.get("content").replace("src=\"//", "src=\"http://");

		// Spanned sp = Html.fromHtml(_retVal.get("content"));

		// tv_content.setText(sp);
		// tv_content.setMovementMethod(new ScrollingMovementMethod());

		myWebViewContent = (WebView) rootView.findViewById(R.id.plolitics_content);

		WebSettings ws = myWebViewContent.getSettings();
		ws.getPluginState();
		ws.setPluginState(PluginState.ON);
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);

		ws.setAllowFileAccess(true);
		//ws.setAllowFileAccessFromFileURLs(true);
		myWebViewContent.setInitialScale(getScale());
		myWebViewContent.getSettings().setJavaScriptEnabled(true);
		myWebViewContent.setEnabled(true);
		html = html.trim().replace(html, " <body style='margin:0; padding: 0;'>" + html + "</body>");

		Log.i(TAG, "html= " + html);
		myWebViewContent.setWebChromeClient(new WebChromeClient() {});
		
		myWebViewContent.loadData(html, "text/html; charset=utf-8", "UTF-8");

		// if(y_content.contains("<iframe" ) && y_content.contains("</iframe>")
		// )
		// {
		// youtubeTag = "<html><body>" +
		// y_content.substring(y_content.indexOf("<iframe"),
		// y_content.indexOf("</iframe>") + 9)+ "</body></html>";
		// s }

		return rootView;

	}

	private int getScale() {
		Display display = ((WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		Double val = new Double(width) / new Double(560);
		val = val * 100d;
		return val.intValue();
	}

	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}
}
