package com.asus.kptaipei;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

public class News extends ListFragment {

	private static String TAG = "KP";

	public static String urlPolitics = "";
	public static String urlRealMan = "";
	public static String urlNews = "";
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

	// Hashmap for ListView
	public ArrayList<HashMap<String, String>> newsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(MainActivity.TAG, "onCreateView[News]");

		// get json data
		urlNews = getResources().getString(R.string.urlNews);

		newsList = new ArrayList<HashMap<String, String>>();
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		makeJsonObjectRequest();

		View rootView = inflater.inflate(R.layout.fragment_news, container, false);
		return rootView;
	}

	private void makeJsonObjectRequest() {
		showDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				urlNews, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// Log.d(TAG, response.toString());
						try {

							String isScccuess = response.getString(tag_isSuccess);

							Log.d(TAG, urlNews);

							JSONArray data = response.getJSONArray("data");
							String idd = "";
							String title = "";
							String post_date = "";
							String url = "";
							String author = "";
							String category_name = "";
							String content = "";

							for (int i = 0; i < data.length(); i++) {

								JSONObject _politics = (JSONObject) data.get(i);
								idd = _politics.getString("id");
								title = _politics.getString("title");
								post_date = _politics.getString("post_date");
								url = _politics.getString("url");
								author = _politics.getString("author");
								category_name = _politics
										.getString("category_name");
								content = _politics.getString("content");

								HashMap<String, String> retVal = new HashMap<String, String>();

								retVal.put(tag_id, idd);
								retVal.put(tag_title, title);
								retVal.put(tag_post_date, post_date);
								retVal.put(tag_url, url);
								retVal.put(tag_author, author);
								retVal.put(tag_url, url);
								retVal.put(tag_content, content);
								retVal.put(tag_category_name, category_name);

								newsList.add(retVal);

							}

							ListAdapter adapter = new SimpleAdapter(getActivity(), newsList,
									R.layout.list_item_news,
									new String[] { tag_title, tag_post_date, tag_category_name, tag_url },
									new int[] { R.id.title, R.id.post_date, R.id.category, R.id.url });
							setListAdapter(adapter);

							ListView lv = getListView();

							// listening to single list item on click
							lv.setOnItemClickListener(new OnItemClickListener() {
								public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

									// to single page
									Fragment fragment = null;
									fragment = new SingleListItem_News(newsList.get(position));
									if (fragment != null) {
										FragmentManager fragmentManager = getFragmentManager();
										fragmentManager
												.beginTransaction()
												.replace(R.id.frame_container, fragment)
												.addToBackStack(null)
												.commit();

									} else {
										Log.e(TAG, "Error in create fragment");
									}

								}
							});

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getActivity(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
						}

						hideDialog();
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error " + error.getMessage());
						Toast.makeText(getActivity(), error.getMessage(),
								Toast.LENGTH_SHORT).show();

						hideDialog();
					}

				});
		AppController.getInstance().addToRequestQueue(jsonObjReq);

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
