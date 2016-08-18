package com.motion.sangeet;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.motion.actionbar.BaseActivity;
import com.motion.adapter.VideoPlayListAdapter;
import com.motion.dao.VideoPlayListDao;
import com.motion.net.ServiceHandler;

public class VideoPlayListActivity extends BaseActivity {

	private ListView listViewDetailsVideoPlayList;
	private VideoPlayListAdapter videoPlayListAdapter;
	private ArrayList<VideoPlayListDao> videoPlayListArray;
	private ProgressDialog progressDialog;
	private final static String url = " mm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.video_play_list_layout, contenFrameLayout);
		iniComponent();
		setListAdapter();
		// new VideoSongList().execute();
//		listViewDetailsVideoPlayList.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
//				intent.putExtra("position", position);
//				startActivity(intent);
//			}
//		});
	}

	private void setListAdapter() {
		// TODO Auto-generated method stub
		videoPlayListAdapter = new VideoPlayListAdapter(this, videoPlayListArray);
		listViewDetailsVideoPlayList.setAdapter(videoPlayListAdapter);
	}

	private void iniComponent() {
		// TODO Auto-generated method stub
		listViewDetailsVideoPlayList = (ListView) findViewById(R.id.listViewDetailsVideoPlayList);
		// progressDialog=new ProgressDialog(this);
		videoPlayListArray = new ArrayList<VideoPlayListDao>();

		VideoPlayListDao videoPlayListDao = new VideoPlayListDao();
		videoPlayListDao.setVideoName("mar java mit java");
		videoPlayListDao
				.setVideoDescription("In this song hero and heroin are Imran Hasmi and Nisha and singer is nhi pta");
		videoPlayListDao.setVideoThumbnailImage("hi");
		videoPlayListArray.add(videoPlayListDao);
	}

	private class VideoSongList extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.setMessage("Please wait....");
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			String strUrl = sh.makeServiceCall(url, ServiceHandler.GET);
			if (strUrl != null) {
				try {
					JSONObject jsonObject = new JSONObject(strUrl);
					JSONArray jsonArray = jsonObject.getJSONArray("");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObjecArray = jsonArray.getJSONObject(i);
						VideoPlayListDao videoPlayListDao = new VideoPlayListDao();
						videoPlayListDao.setVideoName(jsonObjecArray.getString(""));
						videoPlayListDao.setVideoDescription(jsonObjecArray.getString(""));
						videoPlayListDao.setVideoThumbnailImage(jsonObjecArray.getString(""));
						/**
						 * use this for get image from server and set that image on imageView
						 */
//						String str1 = jsonObjecArray.getString("");
//						byte[] b = str1.getBytes();
//						byte[] ba2 = Base64.decode(str1 ,Base64.DEFAULT);
//						Bitmap bitmap = BitmapFactory.decodeByteArray(ba2, 0, ba2.length);
						//Drawable d = new BitmapDrawable(getResources(),bitmap);

						videoPlayListArray.add(videoPlayListDao);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (progressDialog.isShowing())
				progressDialog.dismiss();

			if (!videoPlayListArray.isEmpty()) {
				videoPlayListAdapter = new VideoPlayListAdapter(getApplicationContext(), videoPlayListArray);
				listViewDetailsVideoPlayList.setAdapter(videoPlayListAdapter);
			}
		}

	}
}
