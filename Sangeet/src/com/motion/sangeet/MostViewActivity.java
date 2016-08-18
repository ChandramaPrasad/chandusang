package com.motion.sangeet;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.motion.adapter.MostViewCustomAdapter;
import com.motion.dao.VideoDao;
import com.motion.net.ServiceHandler;

public class MostViewActivity extends FragmentActivity {

	private ArrayList<VideoDao> mostViewDaoArrayList;
	private String url = "http://motionpixeltech.com/Ftp/sangeet.php";
	private GridView mostViewGridView;
	private ProgressDialog progressDialog;

	private MostViewCustomAdapter mostViewCustomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_most_view);

		intialiseViews();
		setAdapterToGridView();

		/**
		 * send request on server to get all videos from the server to show the
		 * user to play the videos.
		 */
		new GetContacts().execute();

		/**
		 * When user click on the button it will send video name to next
		 * activity that is call Plaer activity.
		 */
		mostViewGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				String videoname = mostViewDaoArrayList.get(position)
						.getVideo_name().toString();
				String video = "http://motionpixeltech.com/Ftp/uploads/"
						+ videoname;

				// Intent intent = new Intent(MostViewActivity.this,
				// PlayerActivity.class);
				// intent.putExtra("videoname", video);
				// startActivity(intent);

			}
		});

	}

	/**
	 * This method use to set adapter to GridVeiw.
	 */
	private void setAdapterToGridView() {
		// TODO Auto-generated method stub

		mostViewGridView.setAdapter(mostViewCustomAdapter);

		System.out.println("Main adapter set>>>");

	}

	/**
	 * This method use to initialise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		mostViewDaoArrayList = new ArrayList<VideoDao>();
		mostViewGridView = (GridView) findViewById(R.id.mostViewGridView);
		progressDialog = new ProgressDialog(this);

		mostViewCustomAdapter = new MostViewCustomAdapter(this,
				mostViewDaoArrayList);

	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog.setMessage("Please wait..");
			progressDialog.setCancelable(true);
			progressDialog.show();

		}

		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			if (jsonStr != null) {

				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					JSONArray videoDeatils = jsonObj
							.getJSONArray("ftp_uploads");

					for (int i = 0; i < videoDeatils.length(); i++) {

						VideoDao mostViewDai = new VideoDao();
						JSONObject c = videoDeatils.getJSONObject(i);
						mostViewDai.setVideo_name(c.getString("video_name"));
						mostViewDai.setDescription(c.getString("description"));
						mostViewDai.setDuration(c.getString("duration"));
						mostViewDai.setLikes(c.getString("likes"));
						mostViewDai.setRating(c.getString("rating"));
						mostViewDai.setThumbnails(c.getString("thumbnails"));
						mostViewDai.setViews(c.getString("views"));

						mostViewDaoArrayList.add(mostViewDai);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (progressDialog.isShowing()) {

				progressDialog.dismiss();

			}

			if (!mostViewDaoArrayList.isEmpty()) {

				mostViewCustomAdapter = new MostViewCustomAdapter(
						getApplicationContext(), mostViewDaoArrayList);

				mostViewGridView.setAdapter(mostViewCustomAdapter);
				System.out.println("Adapter set>>>" + mostViewDaoArrayList);

			}

		}
	}
}
