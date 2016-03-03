package com.melniqw.instagramsdksample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melniqw.androidinstagramsdksample.R;
import com.melniqw.instagramsdk.Api;
import com.melniqw.instagramsdk.Media;
import com.melniqw.instagramsdk.UserInfo;
import com.melniqw.instagramsdksample.instagram.InstagramSession;
import com.squareup.picasso.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getName();

	private ProgressBar _mediaProgressBar;
	private GridView _mediaGridView;

	private MediaListAdapter _mediaListAdapter;

	private static final int LOGIN_ACTIVITY_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InstagramSession.createInstance(getApplicationContext());
		if(InstagramSession.getInstance().isActive()){
			setContentView(R.layout.activity_user);
			_mediaProgressBar = (ProgressBar) findViewById(R.id.mediaProgressBar);
			_mediaGridView = (GridView) findViewById(R.id.mediaGridView);
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = (int) Math.ceil((double) dm.widthPixels / 3) - 30;
			int height = width;
			_mediaListAdapter = new MediaListAdapter(getApplicationContext());
			_mediaListAdapter.setLayoutParam(width, height);
			_mediaGridView.setAdapter(_mediaListAdapter);

			final TextView userFullnameTextView = (TextView)findViewById(R.id.userFullnameTextView);
			final TextView userNameTextView = (TextView)findViewById(R.id.userNameTextView);
			final ImageView userImageView = (ImageView)findViewById(R.id.userImageView);
			Picasso.with(getApplicationContext()).load(R.drawable.ic_user).transform(new CircleTransformation()).into(userImageView);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final UserInfo userInfo = Api.getSelfInfo(InstagramSession.getInstance().getAccessToken());
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								userFullnameTextView.setText(userInfo.user.fullName);
								userNameTextView.setText(userInfo.user.username);
								Picasso.with(getApplicationContext()).load(userInfo.user.profilePicture).transform(new CircleTransformation()).into(userImageView);
							}
						});
						final ArrayList<Media> medias = Api.getSelfFeed(30, null, null, InstagramSession.getInstance().getAccessToken());
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_mediaProgressBar.setVisibility(View.GONE);
								_mediaListAdapter.setMedias(medias);
							}
						});
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		} else {
			setContentView(R.layout.activity_main);
			((Button) findViewById(R.id.loginButton)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startLoginActivity();
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_settings) {
			return true;
		} else if(id == R.id.action_logout) {
			InstagramSession.getInstance().drop();
			startActivity(new Intent(MainActivity.this, MainActivity.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startLoginActivity() {
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivityForResult(intent, LOGIN_ACTIVITY_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data == null || resultCode != RESULT_OK)
			return;
		switch(requestCode) {
			case LOGIN_ACTIVITY_CODE:
				processLoginActivityResult(data);
				break;
		}
	}

	private void processLoginActivityResult(Intent data) {
		String error = data.getStringExtra("error");
		if(error == null) {
			InstagramSession.getInstance().setAccessToken(data.getStringExtra("access_token"));
			startActivity(new Intent(MainActivity.this, MainActivity.class));
			finish();
		}
	}
}