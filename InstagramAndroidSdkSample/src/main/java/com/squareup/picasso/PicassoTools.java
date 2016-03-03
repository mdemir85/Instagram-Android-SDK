package com.squareup.picasso;

public class PicassoTools {

	public static void clearCache(Picasso picasso) {
		picasso.cache.clear();
	}

	public static int getCacheSize(Picasso picasso) {
		return picasso.cache.size();
	}
}