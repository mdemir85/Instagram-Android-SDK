/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alexey <menliqw> Melnikov.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

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