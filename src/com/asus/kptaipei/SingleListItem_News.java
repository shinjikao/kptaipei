package com.asus.kptaipei;

import java.util.HashMap;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SingleListItem_News extends Fragment implements ImageGetter{
	private static String TAG = "KP";

	private TextView tv_title;
	private TextView tv_content;
	private TextView tv_image_news;

	
	HashMap<String, String> _retVal;

	public SingleListItem_News(HashMap<String, String> retVal) {
		_retVal = retVal;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "News Single Item Class");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.single_list_item_view_news,container, false);

		tv_title = (TextView) rootView.findViewById(R.id.Single_Title);
		String title = _retVal.get("title");

		tv_title.setText(title);

		tv_content = (TextView) rootView.findViewById(R.id.Single_Content);
		
		tv_image_news = (TextView) rootView.findViewById(R.id.Single_Item_Image);
//		String content = stripHtml(_retVal.get("content"));

		
		//Spanned content = Html.fromHtml(_retVal.get("content"));
		
		
		//Log.d(TAG, "content =" + content);

		
		
		
		
		
		
		//Document doc = Jsoup.parse(newsList.get(0).get("content"));
		//Element img = doc.body().getElementsByClass("alignnone wp-image-4153 size-full").attr("img");
		//String img = doc.body().getElementsByClass("caption").html();

		//Log.d(TAG, src);
		
		
		tv_image_news.setText(Html.fromHtml(_retVal.get("content"), new ImageGetter() {                 
            @Override
            public Drawable getDrawable(String source) {
                String path =  source;

                Drawable bmp = Drawable.createFromPath(path);
                bmp.setBounds(0, 0, bmp.getIntrinsicWidth(), bmp.getIntrinsicHeight());

                return bmp;
            }
        }, null));
		tv_content.setMovementMethod(new ScrollingMovementMethod());

		return rootView;

	}

	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}

	@Override
	public Drawable getDrawable(String source) {
		 // TODO Auto-generated method stub
        int id = 0;

        if(source.equals("addbutton.png")){
          // id = R.drawable.addbutton;
        }

        if(source.equals("tu1.png")){
           // id = R.drawable.tu1;
        }
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        return d;
		
	}
}
