package com.asus.kptaipei;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asus.kptaipei.adapter.CustomLstAdapter;
import com.asus.kptaipei.app.AppController;

public class Politics extends Fragment {

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
	public static String tag_youtubeId = "youtubeId";

	// Hashmap for ListView
	public ArrayList<HashMap<String, String>> politicsList;
	private ListView listView;
	private CustomLstAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// get json data
		urlPolitics = getResources().getString(R.string.urlPolitics);
		urlRealMan = getResources().getString(R.string.urlRealMan);
		urlNews = getResources().getString(R.string.urlNews);

		politicsList = new ArrayList<HashMap<String, String>>();
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);

		makeJsonObjectRequest();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_politics, container, false);

		return rootView;
	}

	private void makeJsonObjectRequest() {
		showDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				urlPolitics, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						 Log.d(TAG, "myTag=" + response.toString());
						try {

							Log.d(TAG, String.valueOf(response.length()));
							String isScccuess = response
									.getString(tag_isSuccess);

							JSONArray data = response.getJSONArray("data");
							String idd = "";
							String title = "";
							String post_date = "";
							String url = "";
							String author = "";
							String category_name = "";
							String content = "";

							String youtubeId = "";
							// Parsing json
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

								Document docOfContent = Jsoup.parse(content);
								Element element1 = docOfContent.select("h3[class=special-h-tag]").first();
								Element element2 = docOfContent.select("div[class]").first();

								if (element1 != null && element2 != null)
								{
									String removeStr1 = element1.toString();
									String removeStr2 = element2.toString();
									content = content.replace(removeStr1, "").replace(removeStr2, "");
								}

								

								HashMap<String, String> retVal = new HashMap<String, String>();

								retVal.put(tag_id, idd);
								retVal.put(tag_title, title);
								retVal.put(tag_post_date, post_date);
								retVal.put(tag_url, url);
								retVal.put(tag_author, author);
								retVal.put(tag_url, url);
								retVal.put(tag_content, content);
								retVal.put(tag_category_name, category_name);
								retVal.put(tag_youtubeId, getYoutbeId(content));

								politicsList.add(retVal);
							}

							listView = (ListView) getActivity().findViewById(R.id.list);
							adapter = new CustomLstAdapter(getActivity(), politicsList);
							listView.setAdapter(adapter);

							// listening to single list item on click
							listView.setOnItemClickListener(new OnItemClickListener() {
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {

									// to single page
									Fragment fragment = null;
									fragment = new SingleListItem_Politics(politicsList.get(position));
									if (fragment != null) {
										FragmentManager fragmentManager = getFragmentManager();
										fragmentManager
												.beginTransaction()
												.replace(R.id.frame_container,
														fragment).commit();

									} else {
										Log.e(TAG, "Error in create fragment");
									}

								}
							});

						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getActivity(),
									"Error " + e.getMessage(),
									Toast.LENGTH_LONG).show();
							Log.d(TAG, "Error=" + e.getMessage());
						}

						hideDialog();
						adapter.notifyDataSetChanged();
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

	public String getYoutbeId(String content)
	{
		String yId = "";

		Document doc = Jsoup.parse(content);
		String src = doc.select("iframe").attr("src");
		yId = src.substring(src.lastIndexOf("/") + 1);

		return yId;

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
