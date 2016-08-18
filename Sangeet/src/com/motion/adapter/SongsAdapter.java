package com.motion.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.motion.dao.AudioDao;
import com.motion.sangeet.R;

public class SongsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<AudioDao> songsList;

	public SongsAdapter(Context context, ArrayList<AudioDao> songsList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.songsList = songsList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return songsList.size();
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
					R.layout.songs_item_adapter_layout, null);

		}

		TextView songNameTextView = (TextView) convertView
				.findViewById(R.id.songNameTextView);

		songNameTextView.setText(songsList.get(position).getSong().toString());

		TextView singerTextView = (TextView) convertView
				.findViewById(R.id.singerTextView);
		singerTextView.setText(songsList.get(position).getSinger().toString());

		return convertView;
	}
}
