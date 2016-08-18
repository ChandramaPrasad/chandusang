package com.motion.sangeet;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app.model.LocalModel;
import com.motion.actionbar.BaseActivity;
import com.motion.adapter.SongsAdapter;
import com.motion.audiocontroller.AudioControllerActivity;
import com.motion.dao.AudioDao;
import com.motion.net.ServiceHandler;
import com.motion.util.Util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SongsActivity extends BaseActivity {

	private ListView songsListView;
	private ProgressDialog progressDialog;
	private String url = "http://motionpixeltech.com/Devotional/get_songs.php?album=";
	private ArrayList<AudioDao> songsList;
	private SongsAdapter songsAdapter;
	private String albumName;

	// Songs list

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.activity_songs, contenFrameLayout);
		intialiseViews();

		albumName = getIntent().getStringExtra("albumname");
		url += albumName;
		/**
		 * click on item to play songs.
		 */

		/**
		 * send request to get the songs.
		 */
		System.out.println("URL>>>" + url);
		String actulurl = url.replaceAll(" ", "%20");
		if (Util.isNetworkAvailable(getApplicationContext()) && actulurl != null) {
			new GetSongs().execute(actulurl);

		}

		songsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				String songName = LocalModel.getInstance().getSongsListArrayList().get(position).getPathUrl();
				Toast.makeText(getApplicationContext(), "path : " + songName, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SongsActivity.this, AudioControllerActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);

			}
		});

	}

	/**
	 * This method use to initialise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		songsListView = (ListView) findViewById(R.id.songsListView);
		progressDialog = new ProgressDialog(this);
		songsList = new ArrayList<AudioDao>();

	}

	/**
	 * Async task class to get json by making HTTP call
	 */
	private class GetSongs extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog.setMessage("Please wait..");
			progressDialog.setCancelable(true);
			progressDialog.show();

		}

		protected String doInBackground(String... arg) {
			// Creating service handler class instance

			String url = arg[0];
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			if (jsonStr != null) {

				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					JSONArray videoDeatils = jsonObj.getJSONArray("songs");

					for (int i = 0; i < videoDeatils.length(); i++) {

						AudioDao mostViewDai = new AudioDao();
						JSONObject c = videoDeatils.getJSONObject(i);
						mostViewDai.setSong(c.getString("names"));
						mostViewDai.setDuration(c.getString("duration"));
						mostViewDai.setSinger(c.getString("Singers"));
						mostViewDai.setPathUrl(c.getString("path"));
						songsList.add(mostViewDai);

					}

					LocalModel.getInstance().setSongsListArrayList(songsList);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (progressDialog.isShowing()) {

				progressDialog.dismiss();

			}

			if (!songsList.isEmpty()) {

				songsAdapter = new SongsAdapter(getApplicationContext(),
						LocalModel.getInstance().getSongsListArrayList());

				songsListView.setAdapter(songsAdapter);
			}

		}
	}
}
