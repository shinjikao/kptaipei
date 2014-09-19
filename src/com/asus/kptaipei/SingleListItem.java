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

public class SingleListItem extends Fragment {
	private static String TAG = "KP";

	private TextView tv_title;
	private TextView tv_content;
	

	HashMap<String, String> _retVal;

	public SingleListItem(HashMap<String, String> retVal) {
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

		View rootView = inflater.inflate(R.layout.rkp_single_list_item_view,
				container, false);

		tv_title = (TextView) rootView.findViewById(R.id.Single_Title);
		String title = _retVal.get("title");

		tv_title.setText(title);

		tv_content = (TextView) rootView.findViewById(R.id.Single_Content);
		String content = stripHtml(_retVal.get("content"));

		Log.d(TAG, "content =" + content);

		tv_content.setText(content);
		tv_content.setMovementMethod(new ScrollingMovementMethod());

		return rootView;

	}

	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}
}
