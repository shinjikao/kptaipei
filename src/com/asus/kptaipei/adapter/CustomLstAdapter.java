package com.asus.kptaipei.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.asus.kptaipei.MainActivity;
import com.asus.kptaipei.R;
import com.asus.kptaipei.app.AppController;

public class CustomLstAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String,String>> politicsItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomLstAdapter(Activity activity, ArrayList<HashMap<String,String>> politics) {
		this.activity = activity;
		this.politicsItems = politics;
	}

	@Override
	
	public int getCount() {
		return politicsItems.size();
	}

	@Override
	public Object getItem(int location) {

		return politicsItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null )
			convertView = inflater.inflate(R.layout.list_item_politics ,null);
		
		if(imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.pthumbnail);
		
		TextView title = (TextView) convertView.findViewById(R.id.ptitle);
		TextView author =(TextView) convertView.findViewById(R.id.pauthor);
		TextView postDate = (TextView) convertView.findViewById(R.id.ppostDate);
		
		
		
		HashMap<String,String> p = politicsItems.get(position);		
		
		String sTitle = p.get("title");
		String sAuthor = p.get("author");
		String sDate = p.get("post_date");
		
		thumbNail.setImageUrl( "http://img.youtube.com/vi/"+p.get("youtubeId") +"/0.jpg", imageLoader);
		title.setText(sTitle.replace( p.get("category_name"), ""));
		author.setText(sAuthor);
		postDate.setText(sDate.subSequence(0,sDate.indexOf("T")));
		
		return convertView;

	}

}
