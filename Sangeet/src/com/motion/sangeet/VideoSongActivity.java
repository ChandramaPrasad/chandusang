package com.motion.sangeet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.motion.adapter.VideoSongAdapter;

public class VideoSongActivity extends Activity {

	private GridView videoSongsGridView;
	private VideoSongAdapter videoSongAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_song);

		intialiseViews();
		setAdapterToGridView();
	}

	/**
	 * This method use to set adapter to gridviews.
	 */
	private void setAdapterToGridView() {
		// TODO Auto-generated method stub

		videoSongsGridView.setAdapter(videoSongAdapter);

	}

	/**
	 * This method use to initialise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		videoSongsGridView = (GridView) findViewById(R.id.videosongsGridView);
		videoSongAdapter = new VideoSongAdapter(this);

	}
}
