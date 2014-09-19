package com.asus.kptaipei;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asus.kptaipei.app.AppController;

public class Home extends Fragment {

	private static String TAG = "KP";

	private static String urlPolitics = "";
	private static String urlRealMan = "";
	private static String urlNews = "";

	private String page = "";
	private String page_size = "";

	// Progress dialog
	private ProgressDialog pDialog;
	private TextView txtResponse;
	private String jsonResponse;

	public static String tag_isSuccess = "isSuccess";
	public static String tag_id = "id";
	public static String tag_title = "title";
	public static String tag_post_date = "post_date";
	public static String tag_author = "author";
	public static String tag_last_modify = "last_modify";
	public static String tag_url = "url";
	public static String tag_category_name = "category_name";
	public static String tag_category_id = "category_id";
	public static String tag_content = "content";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Home");

		// get json data
		urlPolitics = getResources().getString(R.string.urlPolitics);
		urlRealMan = getResources().getString(R.string.urlRealMan);
		urlNews = getResources().getString(R.string.urlNews);

		Toast.makeText(getActivity(), "Home", Toast.LENGTH_LONG).show();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(MainActivity.TAG, "home onCreateView");

		View rootView = inflater.inflate(R.layout.home, container, false);

		return rootView;
	}

	private void showDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

}
