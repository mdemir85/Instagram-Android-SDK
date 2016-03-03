package com.melniqw.instagramsdksample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.melniqw.instagramsdk.Media;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaListAdapter extends BaseAdapter {
	private static final String TAG = MediaListAdapter.class.getName();

	private Context _context;
	private ArrayList<Media> _medias;
	private int _width;
	private int _height;

	public MediaListAdapter(Context context) {
		_context = context;
	}
	
	public void setMedias(ArrayList<Media> medias) {
		_medias = medias;
		notifyDataSetChanged();
	}
	
	public void setLayoutParam(int width, int height) {
		_width = width;
		_height = height;
	}
	
	@Override
	public int getCount() {
		return (_medias == null) ? 0 : _medias.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ImageView imageView;
		if(convertView == null) {
			imageView = new ImageView(_context);
			imageView.setLayoutParams(new GridView.LayoutParams(_width, _height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(0, 0, 0, 0);
			imageView.setTag(_medias.get(position));
		} else {
			imageView = (ImageView) convertView;
//			Media media = (Media)imageView.getTag();

		}
		String imageUrl = _medias.get(position).image.lowResolution.url;
		Picasso.with(_context).load(imageUrl).into(imageView);
		return imageView;
	}
}