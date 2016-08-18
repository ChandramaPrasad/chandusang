package com.motion.audiocontroller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.app.model.LocalModel;
import com.motion.actionbar.BaseActivity;
import com.motion.sangeet.R;

public class AudioControllerActivity extends BaseActivity implements
		OnCompletionListener, SeekBar.OnSeekBarChangeListener {

	private SeekBar audioSeekbar;
	private ImageView playButton;
	private ImageView nextButton;
	private ImageView previousButton;
	private TextView songsDurationTextView;
	private TextView totalSongDurationTextView;
	// Handler to update UI timer, progress bar etc,.
	private Handler mHandler = new Handler();;
	private Utilities utils;
	private int currentSongIndex = 0;
	private MediaPlayer mediaPlayer;
	private final Handler handler = new Handler();
	private int position;
	private TextView textViewMovingTitle;
	private ProgressBar songProgressBar;
	String duration;
	long totalDuration;

	int time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.audio_controller_layout,
				contenFrameLayout);

		intialiseVeiws();
		registerEvent();

		Intent mIntent = getIntent();
		position = mIntent.getIntExtra("position", 0);

		/**
		 * checking condition if already mediaplayer is playing or not if
		 * already playing then stop it first and play selected song by end
		 * user.
		 */
		if (mediaPlayer.isPlaying()) {

			mediaPlayer.stop();

		}
		/**
		 * Method use to play the songs which is select by user.
		 */
		playSong(position);

		// new PlayAudioSong().execute(position);

		/**
		 * when user click on the play button it will play audio or pause audio
		 */
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// check for already playing
				if (mediaPlayer.isPlaying()) {
					if (mediaPlayer != null) {
						mediaPlayer.pause();
						// Changing button image to play button
						playButton.setImageResource(R.drawable.play_but);

					}
				} else {
					// Resume song
					if (mediaPlayer != null) {
						mediaPlayer.start();

						// Changing button image to pause button
						playButton.setImageResource(R.drawable.pause);

					}
				}

			}
		});

		/**
		 * when user click on the next button it will go to next songs
		 */
		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// check if next song is there or not
				if (currentSongIndex < (LocalModel.getInstance()
						.getSongsListArrayList().size() - 1)) {
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1;
					setSongDuration(currentSongIndex);
				} else {
					// play first song
					playSong(0);
					currentSongIndex = 0;
					setSongDuration(currentSongIndex);
				}

			}
		});

		/**
		 * when user click on this button it will go to previous songs.
		 */
		previousButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (currentSongIndex > 0) {
					playSong(currentSongIndex - 1);
					currentSongIndex = currentSongIndex - 1;
					setSongDuration(currentSongIndex);
					System.out.println("currentSongIndex" + currentSongIndex);
				} else {
					// play last song
					// playSong(LocalModel.getInstance().getSongsListArrayList()
					// .size() - 1);
					// currentSongIndex = LocalModel.getInstance()
					// .getSongsListArrayList().size() - 1;
					//
					// System.out.println("currentSongIndex" +
					// currentSongIndex);
					// setSongDuration(currentSongIndex);

					playSong(0);
					currentSongIndex = 0;
					setSongDuration(currentSongIndex);
				}

			}
		});
	}

	private void registerEvent() {
		// TODO Auto-generated method stub

		audioSeekbar.setOnSeekBarChangeListener(this);

		mediaPlayer.setOnCompletionListener(this);

	}

	/**
	 * This method use to initialise the views
	 */
	private void intialiseVeiws() {
		// TODO Auto-generated method stub
		// songProgressBar = (ProgressBar) findViewById(R.id.songProgressBar);

		audioSeekbar = (SeekBar) findViewById(R.id.audioSeekbar);
		playButton = (ImageView) findViewById(R.id.imageViewAudioPlay);
		nextButton = (ImageView) findViewById(R.id.imageViewAudioNext);
		previousButton = (ImageView) findViewById(R.id.imageViewAudioPrevious);
		songsDurationTextView = (TextView) findViewById(R.id.songsDurationTextView);
		totalSongDurationTextView = (TextView) findViewById(R.id.songsTotalDurationTextView);
		// get singleton class instance to play song without inturupt.
		mediaPlayer = LocalModel.getInstance().getMediaPlayeInstance();

		textViewMovingTitle = (TextView) findViewById(R.id.textViewMovingTitle);
		// textViewMovingTitle.setSelected(true);
		textViewMovingTitle.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.move));

		audioSeekbar.setMax(mediaPlayer.getDuration());
		utils = new Utilities();

	}

	/**
	 * Function to play a song
	 * 
	 * @param i
	 */

	public void playSong(int i) {
		// Play song
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(LocalModel.getInstance()
					.getSongsListArrayList().get(i).getPathUrl());

			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.prepare();
			mediaPlayer.start();

			// Displaying Song title
			// String songTitle = songsList.get(songIndex).get("songTitle");
			// songTitleLabel.setText(songTitle);

			// Changing Button Image to pause image
			playButton.setImageResource(R.drawable.pause);

			// set Progress bar values
			audioSeekbar.setProgress(mediaPlayer.getCurrentPosition());
			audioSeekbar.setMax(100);

			// Updating progress bar

			// This line use to convert time into milliseconds from given time
			// on
			// the server.
			String[] data = LocalModel.getInstance().getSongsListArrayList()
					.get(i).getDuration().trim().split(":");
			;
			int minutes = Integer.parseInt(data[0]);
			int seconds = Integer.parseInt(data[1]);

			time = seconds + 60 * minutes;
			updateProgressBar();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * THis method use to update the progress bar base on songs playing
	 */

	private void updateProgressBar() { // TODO Auto-generated method stub
		handler.postDelayed(mUpdateTimeTask, 100);

	}

	/**
	 * Background Runnable thread
	 */

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {

			totalDuration = TimeUnit.MILLISECONDS.convert(time,
					TimeUnit.SECONDS);
			long currentDuration = mediaPlayer.getCurrentPosition();

			// Displaying Total Duration time
			totalSongDurationTextView.setText(""
					+ utils.milliSecondsToTimer(totalDuration));
			// Displaying time completed // playing
			songsDurationTextView.setText(" "
					+ utils.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (utils.getProgressPercentage(currentDuration,
					totalDuration));
			audioSeekbar.setProgress(progress);

			// Running this thread after 100 milliseconds
			handler.postDelayed(this, 100);
		}
	};

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) { // TODO
		// Auto-generated
		// method
		// stub

	}

	public void onStartTrackingTouch(SeekBar seekBar) { // TODO
		// Auto-generated method stub // remove message Handler from updating
		// progressbar
		mHandler.removeCallbacks(mUpdateTimeTask);

	}

	public void onStopTrackingTouch(SeekBar seekBar) { // TODO
		// Auto-generated method stub

		mHandler.removeCallbacks(mUpdateTimeTask);
		// Typecasting long datatype to int .
		int totalduration = (int) totalDuration;
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalduration);

		// forward or backward to certain seconds
		mediaPlayer.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub

		if (currentSongIndex < (LocalModel.getInstance()
				.getSongsListArrayList().size() - 1)) {
			playSong(currentSongIndex + 1);
			currentSongIndex = currentSongIndex + 1;
			setSongDuration(currentSongIndex);

		} else {
			// play first song
			playSong(0);
			currentSongIndex = 0;
			setSongDuration(currentSongIndex);
		}

	}

	private void setSongDuration(int position) {

		String[] data = LocalModel.getInstance().getSongsListArrayList()
				.get(position).getDuration().trim().split(":");

		int minutes = Integer.parseInt(data[0]);
		int seconds = Integer.parseInt(data[1]);

		time = seconds + 60 * minutes;
		totalSongDurationTextView.setText(""
				+ utils.milliSecondsToTimer(TimeUnit.MILLISECONDS.convert(time,
						TimeUnit.SECONDS)));

	}

	/**
	 * this AsyncTask used to show the progressbar/progressdialog when song
	 * loading after click on next/previous button of player button
	 * 
	 * @author Work Station calling this method from playSong()
	 */

	/**
	 * this AsyncTask used to show the progressbar/progressdialog when song
	 * loading after click on next/previous button of player button
	 * 
	 * @author Work Station calling this method from playSong()
	 */

	class PlayAudioSong extends AsyncTask<Integer, Integer, Void> {

		// ProgressDialog progressDialog = new
		// ProgressDialog(AudioControllerActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// progressDialog.setMessage("Loading...");
			// progressDialog.setCancelable(false);
			// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// progressDialog.show();
			songProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int i = params[0];
			// int song = i.intValue();
			try {
				mediaPlayer.reset();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setDataSource(LocalModel.getInstance()
						.getSongsListArrayList().get(i).getPathUrl());
				mediaPlayer.prepare();
				mediaPlayer.start();

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (songProgressBar != null) {
				songProgressBar.setVisibility(View.GONE);
			}
		}

	}

}
