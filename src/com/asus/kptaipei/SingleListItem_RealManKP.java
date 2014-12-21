package com.asus.kptaipei;

import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class SingleListItem_RealManKP extends Fragment {
	private static String TAG = "KP";


	private WebView myWebViewContent;
	private String html ="";
	HashMap<String, String> _retVal;

	public SingleListItem_RealManKP(HashMap<String, String> retVal) {
		_retVal = retVal;

	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.single_list_item_view_realman,container, false);

		myWebViewContent = (WebView) rootView.findViewById(R.id.realman_content);
		SettingWebView();

		return rootView;

	}

	

	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}

	private int getScale() {
		Display display = ((WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		Double val = new Double(width) / new Double(560);
		val = val * 100d;
		return val.intValue();
	}

	
	
	private void SettingWebView()
	{
		WebSettings ws = myWebViewContent.getSettings();
		ws.getPluginState();
		ws.setPluginState(PluginState.ON);
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setAllowFileAccess(true);
		myWebViewContent.setInitialScale(getScale());
		ws.setTextSize(WebSettings.TextSize.LARGER);
		myWebViewContent.getSettings().setJavaScriptEnabled(true);
		myWebViewContent.setEnabled(true);
		html = _retVal.get("content").replace("src=\"//", "src=\"http://");
		html = html.trim().replace(html, " <body style='margin:0; padding: 0;'>" + html + "</body>");
		myWebViewContent.setWebChromeClient(new WebChromeClient() {});
		myWebViewContent.loadData(html, "text/html; charset=utf-8", "UTF-8");
	}
	
	
}
