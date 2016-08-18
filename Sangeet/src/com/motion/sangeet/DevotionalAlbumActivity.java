package com.motion.sangeet;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.motion.actionbar.BaseActivity;
import com.motion.adapter.DevotionalAlbumAdapter;
import com.motion.dao.DevotionalSongDao;
import com.motion.net.ServiceHandler;

public class DevotionalAlbumActivity extends BaseActivity {

	private GridView devotionalGridview;
	private ArrayList<DevotionalSongDao> devotionalSongDaosArrayList;
	Context context;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.activity_devotional_album,
				contenFrameLayout);
		intialiseViews();

		/**
		 * Load the images from remote server.
		 */
		new GetAlbumDetails()
				.execute("http://motionpixeltech.com/ftp/audio/sangeet_album.php");

		devotionalGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				String albumname = devotionalSongDaosArrayList.get(position)
						.getAlbumName().toString();
				Toast.makeText(getApplicationContext(), " " + albumname,
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(DevotionalAlbumActivity.this,
						SongsActivity.class);
				intent.putExtra("albumname", albumname);
				startActivity(intent);

			}
		});

	}

	/**
	 * This method use to initialie the views here.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub
		devotionalGridview = (GridView) findViewById(R.id.devotionalAlbumGridview);
		devotionalSongDaosArrayList = new ArrayList<DevotionalSongDao>();
		progressDialog = new ProgressDialog(this);
		context = this;

	}

	/**
	 * Async task class to get json by making HTTP call
	 */
	public class GetAlbumDetails extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog.setMessage("Please wait..");
			progressDialog.setCancelable(true);
			progressDialog.show();

		}

		protected String doInBackground(String... params) {
			// Creating service handler class instance

			String url = params[0].toString();
			ServiceHandler handler = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = handler.makeServiceCall(url, ServiceHandler.GET);

			if (jsonStr != null) {

				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					JSONArray videoDeatils = jsonObj.getJSONArray("album");

					for (int i = 0; i < videoDeatils.length(); i++) {

						DevotionalSongDao albumDao = new DevotionalSongDao();
						JSONObject c = videoDeatils.getJSONObject(i);
						albumDao.setAlbumName(c.getString("albumname"));
						albumDao.setThumnails(c.getString("thumbnails"));

						devotionalSongDaosArrayList.add(albumDao);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (progressDialog.isShowing()) {

				progressDialog.dismiss();

			}

			devotionalGridview.setAdapter(new DevotionalAlbumAdapter(
					DevotionalAlbumActivity.this, devotionalSongDaosArrayList));

		}
	}
}
