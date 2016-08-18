package com.example.app.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.application.controller.ApplicationController;
import com.example.application.defines.AppConstants;
import com.example.framework.model.BaseModel;
import com.example.framework.net.HttpRequestHandlerImpl;
import com.motion.dao.AudioDao;

public class AudioSongModel extends BaseModel {

	private LocalModel localModel;
	private ArrayList<AudioDao> songArrayListDao = null;

	public AudioSongModel() {
		super();

		localModel = ApplicationController.getInstance().getModelFacade()
				.getLocalModel();
		songArrayListDao = new ArrayList<AudioDao>();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
		startAsyncTask();
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub

		String url = AppConstants.URL_SONGS;
		httpRequestHandlerImpl = new HttpRequestHandlerImpl(url,
				ApplicationController.getInstance().getApplication()
						.getApplicationContext());
		httpRequestHandlerImpl.setMethodType(HttpRequestHandlerImpl.HTTP_GET);

		System.out.println("URL: " + url);

	}

	@Override
	public Object doInBackground(String... params) {
		// TODO Auto-generated method stub
		byte[] responseByteArray = httpRequestHandlerImpl.execute();
		String responseData = new String(responseByteArray);
		return responseData;
	}

	@Override
	public void onPostExecute(Object jsonResult) {
		// TODO Auto-generated method stub

		String response = jsonResult.toString();
		System.out.println("onPostExecut Result Songs:" + response);
		parseJsonData(response);
		informView();

	}

	/**
	 * This method use to parse the data to respective fields..
	 * 
	 * @param jsonResult
	 * @return
	 */

	private void parseJsonData(String jsonResult) {

		try {

			JSONObject jsonObj = new JSONObject(jsonResult);

			JSONArray videoDeatils = jsonObj.getJSONArray("tbl_songs");

			for (int i = 0; i < videoDeatils.length(); i++) {

				AudioDao mostViewDai = new AudioDao();
				JSONObject c = videoDeatils.getJSONObject(i);
				mostViewDai
						.setSong("http://motionpixeltech.com/ftp/audio/songs/"
								+ c.getString("Song"));
				mostViewDai.setDuration(c.getString("duration"));

				songArrayListDao.add(mostViewDai);

			}

			// set list of data into localmodel.
			LocalModel.getInstance().setSongsListArrayList(songArrayListDao);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
