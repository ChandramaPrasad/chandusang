package com.motion.videocontroller;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.model.LocalModel;
import com.motion.actionbar.BaseActivity;
import com.motion.adapter.CustomGridAdapter;
import com.motion.sangeet.LoginActivity;
import com.motion.sangeet.R;
import com.motion.util.UserSessionManager;

public class VideoPlayerActivity extends BaseActivity implements
		SurfaceHolder.Callback, MediaPlayer.OnPreparedListener,
		VideoControllerView.MediaPlayerControl, OnInfoListener,
		OnErrorListener, OnCompletionListener, OnBufferingUpdateListener {

	SurfaceView videoSurface;
	MediaPlayer player;
	VideoControllerView controller;
	private ProgressDialog mProgressDialog;
	private GridView videoGridView;
	private CustomGridAdapter customGridAdapter;
	private boolean mFullScreen = true;
	private TextView titleTextView;
	private LinearLayout likesLayout;
	private UserSessionManager sessionManager;
	private ImageView playButton;
	private ImageView videoImagePreview;
	// private ProgressBar spinnerProgressBar;
	private ProgressBar progressbar;
	private String videourl;
	WakeLock wakeLock;
	KeyguardLock keyguardLock;
	private String videoUrlName;

	private int mProgressStatus = 0;
	private TextView addtoplaylistTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		// getLayoutInflater().inflate(R.layout.activity_video_player,
		// contenFrameLayout);

		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "INFO");
		wakeLock.acquire();

		KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		keyguardLock = km.newKeyguardLock("name");
		keyguardLock.disableKeyguard();

		videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
		SurfaceHolder videoHolder = videoSurface.getHolder();
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		addtoplaylistTextView = (TextView) findViewById(R.id.addtoplaylistTextView);
		videoGridView = (GridView) findViewById(R.id.videoGridView);
		likesLayout = (LinearLayout) findViewById(R.id.likesLayout);
		playButton = (ImageView) findViewById(R.id.playButton);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);
		videoImagePreview = (ImageView) findViewById(R.id.videoImagePreview);
		sessionManager = new UserSessionManager(getApplicationContext());
		customGridAdapter = new CustomGridAdapter(this);
		mProgressDialog = new ProgressDialog(this);

		// instantiate it within the onCreate method
		mProgressDialog.setMessage("Downloading..");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);

		videourl = getIntent().getStringExtra("videopath");
		System.out.println("Videourl>>>" + videourl);

		videoHolder.addCallback(this);

		player = LocalModel.getInstance().getVideoMediaPlayeInstance();
		controller = new VideoControllerView(this);

		videoGridView.setAdapter(customGridAdapter);

		player.setOnPreparedListener(this);
		player.setOnInfoListener(this);
		player.setOnErrorListener(this);
		player.setOnCompletionListener(this);
		player.setOnBufferingUpdateListener(this);
		// / videoUrlName=getIntent().getStringExtra("videoUrlName");
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// videoImagePreview.setVisibility(View.GONE);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						playButton.setVisibility(View.GONE);
					}
				});

				// play();
				new PlayerUrl().execute(videourl);

			}
		});

		final DownloadTask downloadTask = new DownloadTask(
				VideoPlayerActivity.this);
		downloadTask
				.execute("http://motionpixeltech.com/androidlogin/meenakshidixitinterview.mp4");

		mProgressDialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						downloadTask.cancel(true);
					}
				});

		/**
		 * set like dislike if user login only otherwise asking for use login.
		 */
		likesLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean userstatus = sessionManager.isUserLoggedIn();
				System.out.println("User staus from like>>" + userstatus);

				if (userstatus == false) {

					Intent intent = new Intent(VideoPlayerActivity.this,
							LoginActivity.class);
					startActivity(intent);

				} else {
					Toast.makeText(getApplicationContext(),
							"you successfully like", Toast.LENGTH_SHORT).show();
				}

			}
		});
		/**
		 * set song to playlist if and only if user login to application.
		 */
		addtoplaylistTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean userstatus = sessionManager.isUserLoggedIn();
				System.out.println("User staus from playlist>>" + userstatus);

				if (userstatus == false) {

					Intent intent = new Intent(VideoPlayerActivity.this,
							LoginActivity.class);
					startActivity(intent);

				} else {
					// Toast.makeText(getApplicationContext(),
					// "you successfully add to playlist",
					// Toast.LENGTH_SHORT).show();

					new DownloadFile().execute("");
				}

			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		controller.show();
		return false;
	}

	// Implement SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		// player.setDisplay(holder);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);

		// player.prepareAsync();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		player.pause();
		super.onPause();

	}

	// End SurfaceHolder.Callback

	// Implement MediaPlayer.OnPreparedListener

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// player.reset();
		super.onStop();
		// if (player != null && player.isPlaying()) {
		//
		// player.stop();
		//
		// }

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		controller.setMediaPlayer(this);
		controller
				.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
		progressbar.setVisibility(View.GONE);
	}

	// End MediaPlayer.OnPreparedListener

	// Implement VideoMediaController.MediaPlayerControl
	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {

		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return player.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void seekTo(int i) {
		player.seekTo(i);
	}

	@Override
	public void start() {
		player.start();

	}

	@Override
	public boolean isFullScreen() {
		if (mFullScreen) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		player.start();

		if (!player.isPlaying()) {

			videoImagePreview.setVisibility(View.VISIBLE);
			playButton.setVisibility(View.VISIBLE);

		}

	}

	@Override
	public void toggleFullScreen() {

		setFullScreen(isFullScreen());

	}

	// End VideoMediaController.MediaPlayerControl

	private void setFullScreen(boolean fullScreen) {
		// TODO Auto-generated method stub

		fullScreen = false;

		if (mFullScreen)

		{
			Log.v("FullScreen",
					"-----------Set full screen SCREEN_ORIENTATION_LANDSCAPE------------");
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			titleTextView.setVisibility(View.GONE);
			actionBar.hide();
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			int width = displaymetrics.widthPixels;
			android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurface
					.getLayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;
			params.setMargins(0, 0, 0, 0);
			// set icon is full screen
			mFullScreen = fullScreen;
		} else {
			Log.v("FullScreen",
					"-----------Set small screen SCREEN_ORIENTATION_PORTRAIT------------");
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			titleTextView.setVisibility(View.VISIBLE);
			actionBar.show();
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			final FrameLayout mFrame = (FrameLayout) findViewById(R.id.videoSurfaceContainer);
			// int height = displaymetrics.heightPixels;
			int height = mFrame.getHeight();// get height Frame Container video
			int width = displaymetrics.widthPixels;
			android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurface
					.getLayoutParams();
			params.width = width;
			params.height = height;
			params.setMargins(0, 0, 0, 0);
			// set icon is small screen
			mFullScreen = !fullScreen;
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			titleTextView.setVisibility(View.GONE);
			actionBar.hide();
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
			// setContentView(R.layout.view_lang);
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			int width = displaymetrics.widthPixels;
			android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurface
					.getLayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;// -80 for android
														// controls
			params.setMargins(0, 0, 0, 0);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
			titleTextView.setVisibility(View.VISIBLE);
			actionBar.show();
			// setContentView(R.layout.view);
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			int width = displaymetrics.widthPixels;
			android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurface
					.getLayoutParams();
			params.width = width;
			params.height = height / 3;
			params.setMargins(0, 0, 0, 0);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if (player != null && player.isPlaying()) {

			player.stop();

		}
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
			progressbar.setVisibility(View.VISIBLE);

		if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
			progressbar.setVisibility(View.GONE);

		return false;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED)
			mp.reset();

		else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN)
			mp.reset();
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		player.reset();
		playButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		// controller.mProgress.setSecondaryProgress(percent);
		if (percent == 100) {
			progressbar.setVisibility(View.GONE);
		} else if (percent < controller.mProgress.getMax()) {
			progressbar.setSecondaryProgress(percent);
			progressbar.setSecondaryProgress(percent / 100);

		} else
			progressbar.setVisibility(View.VISIBLE);

	}

	/**
	 * This asyntask use to play video when user click on play button.
	 * 
	 * @author Admin
	 * 
	 */
	private class PlayerUrl extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			playButton.setVisibility(View.GONE);
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String videourl = params[0];

			try {
				// progressbar.setVisibility(View.VISIBLE);
				player.setAudioStreamType(AudioManager.STREAM_MUSIC);

				player.setDataSource(VideoPlayerActivity.this,
						Uri.parse(videourl));
				// java.net.URLDecoder.decode(videoUrlName , "UTF-8")));
				player.prepare();

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.start();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressbar.setVisibility(View.GONE);
		}
	}

	private class DownloadFile extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create progress dialog
			mProgressDialog = new ProgressDialog(VideoPlayerActivity.this);
			// Set your progress dialog Title
			mProgressDialog.setTitle("Progress Bar Tutorial");
			// Set your progress dialog Message
			mProgressDialog.setMessage("Downloading, Please Wait!");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// Show progress dialog
			mProgressDialog.show();
			mProgressStatus = 0;
		}

		@Override
		protected String doInBackground(String... Url) {
			try {
				URL url = new URL(Url[0]);
				URLConnection connection = url.openConnection();
				connection.connect();

				// Detect the file lenghth
				int fileLength = connection.getContentLength();

				// Locate storage location
				String filepath = Environment.getExternalStorageDirectory()
						.getPath();

				// Download the file
				InputStream input = new BufferedInputStream(url.openStream());

				// Save the downloaded file
				OutputStream output = new FileOutputStream(filepath + "/"
						+ "sangeet.mp4");

				byte data[] = new byte[50024];
				long total = 0;
				int count;

				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress

					publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
					/** Sleeps this thread for 100ms */
					Thread.sleep(100);
				}

				// Close connection
				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				// Error Log
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			// Update the progress dialog
			mProgressDialog.setProgress(progress[0]);
			// Dismiss the progress dialog
			// mProgressDialog.dismiss();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mProgressDialog.dismiss();
		}
	}

	private class DownloadTask extends AsyncTask<String, Integer, String> {

		private Context context;
		private PowerManager.WakeLock mWakeLock;

		public DownloadTask(Context context) {
			this.context = context;
		}

		@Override
		protected String doInBackground(String... sUrl) {
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			try {
				URL url = new URL(sUrl[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();

				// expect HTTP 200 OK, so we don't mistakenly save error report
				// instead of the file
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return "Server returned HTTP "
							+ connection.getResponseCode() + " "
							+ connection.getResponseMessage();
				}

				// this will be useful to display download percentage
				// might be -1: server did not report the length
				int fileLength = connection.getContentLength();

				// download the file
				input = connection.getInputStream();
				output = new FileOutputStream("/sdcard/song/mysong.mp4");

				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return null;
					}
					total += count;
					// publishing the progress....
					if (fileLength > 0) // only if total length is known
						publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
			} catch (Exception e) {
				return e.toString();
			} finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				} catch (IOException ignored) {
				}

				if (connection != null)
					connection.disconnect();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// take CPU lock to prevent CPU from going off if the user
			// presses the power button during download
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					getClass().getName());
			mWakeLock.acquire();
			mProgressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			// if we get here, length is known, now set indeterminate to false
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			mWakeLock.release();
			mProgressDialog.dismiss();
			if (result != null)
				Toast.makeText(context, "Download error: " + result,
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT)
						.show();
		}
	}

}
