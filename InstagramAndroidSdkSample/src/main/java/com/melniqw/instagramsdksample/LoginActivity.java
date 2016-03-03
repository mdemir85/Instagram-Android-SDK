package com.melniqw.instagramsdksample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.melniqw.androidinstagramsdksample.R;
import com.melniqw.instagramsdk.Api;
import com.melniqw.instagramsdk.Auth;
import com.melniqw.instagramsdksample.instagram.InstagramApp;

@SuppressWarnings("deprecation")
public final class LoginActivity extends AppCompatActivity {
	private static final String TAG = LoginActivity.class.getName();

	private WebView _webView;
	private LinearLayout _loadProgressLayout;
	private ProgressBar _loadProgressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		setTitle(getString(R.string.authorize));
//		try {
//			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		} catch(NullPointerException e) {
//			e.printStackTrace();
//		}
		initWebView();
		initProgressView();
	}

	private void initWebView() {
		_webView = (WebView)findViewById(R.id.webView);
		_webView.getSettings().setJavaScriptEnabled(true);
		_webView.clearCache(true);
		_webView.setWebViewClient(new InstagramWebViewClient());
		_webView.setWebChromeClient(new InstagramWebChromeClient());

		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		_webView.loadUrl(Auth.getCodeRequest(InstagramApp.CLIENT_ID, InstagramApp.REDIRECT_URL));
	}

	private void initProgressView() {
		_loadProgressLayout = (LinearLayout)findViewById(R.id.loadProgressLayout);
		_loadProgressLayout.setVisibility(View.GONE);
		_loadProgressBar = (ProgressBar)findViewById(R.id.loadProgressBar);
	}

	private class InstagramWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(url.startsWith(InstagramApp.REDIRECT_URL)) {
				final Intent intent = new Intent();
				if (url.contains("code")) {
					String temp[] = url.split("=");
					final String code = temp[1];
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								final String accessToken = Api.authorize(InstagramApp.CLIENT_ID, InstagramApp.CLIENT_SECRET, InstagramApp.REDIRECT_URL, code);
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										intent.putExtra("access_token", accessToken);
										setResult(Activity.RESULT_OK, intent);
										finish();
									}
								});
							} catch(final Exception e) {
								e.printStackTrace();
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										intent.putExtra("error", e.getMessage());
										setResult(Activity.RESULT_OK, intent);
										finish();
									}
								});
							}

						}
					}).start();
				} else if (url.contains("error")) {
					String temp[] = url.split("=");
					String error = temp[temp.length - 1];
					intent.putExtra("error", error);
					setResult(Activity.RESULT_OK, intent);
					finish();
				}
				return true;
			}
			return false;
		}


		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			_loadProgressLayout.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			_loadProgressLayout.setVisibility(View.GONE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			_loadProgressLayout.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(),
							getString(R.string.page_load_error),
							Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.putExtra("error", description);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	private class InstagramWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			_loadProgressBar.setProgress(newProgress);
		}
	}
}
