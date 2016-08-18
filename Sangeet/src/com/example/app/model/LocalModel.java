package com.example.app.model;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.net.ConnectivityManager;

import com.example.application.controller.ApplicationController;
import com.example.framework.model.IModel;
import com.example.framework.view.AbstractView;
import com.motion.dao.AudioDao;
import com.motion.dao.LoginDao;

/**
 * LocalModel.java The LocalModel Class , which store the information in cache
 * (runtime memory,RMS, sqllite,preferences) This class can use the platfrom
 * dependent features.
 * 
 * 
 */

public class LocalModel implements IModel {

	/**
	 * The location model instance to be used to get current lat long at any
	 * time.
	 */

	private ArrayList<AudioDao> songsListArrayList;

	private static LocalModel localModel;
	private ArrayList<LoginDao> arrayListLoginLocalModel;
	private MediaPlayer mediaPlayer;
	private MediaPlayer videoMediaPlayer;

	public LocalModel() {
	}

	@Override
	public void initialize() {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void informView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(AbstractView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unRegister(AbstractView view) {
		// TODO Auto-generated method stub

	}

	/**
	 * create singleton class to get only one object trough out the project.
	 * 
	 * @return
	 */

	public static LocalModel getInstance() {

		if (localModel == null) {

			localModel = new LocalModel();

		}

		return localModel;
	}

	/**
	 * create the signleton media player class to get only one object overall
	 * the class.
	 * 
	 * @return
	 */

	public MediaPlayer getMediaPlayeInstance() {

		if (mediaPlayer == null) {

			mediaPlayer = new MediaPlayer();

		}

		return mediaPlayer;

	}

	public MediaPlayer getVideoMediaPlayeInstance() {

		if (videoMediaPlayer == null) {

			videoMediaPlayer = new MediaPlayer();

		}

		return videoMediaPlayer;

	}

	public boolean isInternetAvailable() {
		boolean isNetworkConnectionAvailable = false;

		ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationController
				.getInstance().getApplication()
				.getSystemService("connectivity");

		isNetworkConnectionAvailable = (connectivityManager
				.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager
				.getActiveNetworkInfo().isConnected());

		return isNetworkConnectionAvailable;
	}

	public ArrayList<AudioDao> getSongsListArrayList() {
		return songsListArrayList;
	}

	public void setSongsListArrayList(ArrayList<AudioDao> songsListArrayList) {
		this.songsListArrayList = songsListArrayList;
	}

	/**
	 * this arrayList is use to send ArrayList<LoginDao> login response data to
	 * EditProfile screen
	 * 
	 * @return
	 */
	public ArrayList<LoginDao> getArrayListLoginLocalModel() {
		return arrayListLoginLocalModel;
	}

	/**
	 * this array list set the login response data to ArrayList<LoginDao>
	 * 
	 * @param arrayListLoginLocalModel
	 */
	public void setArrayListLoginLocalModel(
			ArrayList<LoginDao> arrayListLoginLocalModel) {
		this.arrayListLoginLocalModel = arrayListLoginLocalModel;
	}

}
