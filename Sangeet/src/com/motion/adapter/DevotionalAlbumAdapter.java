package com.motion.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.motion.dao.AlbumDao;
import com.motion.dao.DevotionalSongDao;
import com.motion.sangeet.R;
import com.squareup.picasso.Picasso;

public class DevotionalAlbumAdapter extends BaseAdapter {

	private Context context;
	private ImageView songsImageView;
	private ArrayList<DevotionalSongDao> albumDaoArrayList;

	// TODO Auto-generated constructor stub
	public DevotionalAlbumAdapter(Context context,
			ArrayList<DevotionalSongDao> albumDaoArrayList) {
		super();
		this.context = context;
		this.albumDaoArrayList = albumDaoArrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return albumDaoArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.devotional_album_apater_item, null);

		}
		songsImageView = (ImageView) convertView
				.findViewById(R.id.songsImageView);
		TextView albumnameTextVie = (TextView) convertView
				.findViewById(R.id.albumnameTextVie);

		loadProfileImage(albumDaoArrayList.get(position).getThumnails()
				.toString().trim());

		albumnameTextVie.setText(albumDaoArrayList.get(position).getAlbumName()
				.toString());
		songsImageView = (ImageView) convertView
				.findViewById(R.id.songsImageView);
		// Picasso.with(context).load("http://motionpixeltech.com/Devotional/albumimages/"+arrayListDao.get(position).getThumnails()).into(songsImageView);
		loadProfileImage(albumDaoArrayList.get(position).getThumnails());
		return convertView;
	}

	/**
	 * This method use to load the image from the server.
	 * 
	 * @param currentUsername
	 */
	protected void loadProfileImage(String currentUsername) {
		String result = null;
		String getimage = "http://motionpixeltech.com/Devotional/albumimages/";
		getimage += currentUsername;

		if (getimage != null) {

			// Here clear the path to set the image again tap on user
			// picture.

			result = getimage.replaceAll(" ", "%20");
			System.out.println("ImagePath>>" + result);
			Picasso.with(context).load((result)).into(songsImageView);
			result = "";

		}

	}
}