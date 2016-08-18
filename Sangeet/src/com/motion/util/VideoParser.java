package com.motion.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableLayout.LayoutParams;

import com.motion.dao.VideoDao;
import com.motion.net.ServiceHandler;

/**
 * Async task class to get json by making HTTP call
 */
public class VideoParser extends
		AsyncTask<String, Integer, ArrayList<VideoDao>> {

	Context context;
	private ProgressDialog progressDialog;
	private ArrayList<VideoDao> videoDaoArrayList;

	public VideoParser(Context context) {
		this.context = context;
		videoDaoArrayList = new ArrayList<VideoDao>();
		progressDialog = new ProgressDialog(context);
	}

	public VideoParser() {
		super();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(true);
		progressDialog.show();
		progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall); 
		//Window window = progressDialog.getWindow();
		//window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	protected ArrayList<VideoDao> doInBackground(String... params) {
		// Creating service handler class instance

		String url = params[0].toString();

		ServiceHandler sh = new ServiceHandler();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

		if (jsonStr != null) {

			try {
				JSONObject jsonObj = new JSONObject(jsonStr);

				JSONArray videoDeatils = jsonObj.getJSONArray("ftp_uploads");

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

					videoDaoArrayList.add(mostViewDai);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return videoDaoArrayList;
	}

	protected void onPostExecute(ArrayList<VideoDao> result) {
		super.onPostExecute(result);

		if (progressDialog!=null && progressDialog.isShowing()) {

			progressDialog.dismiss();

		}

	}

}
