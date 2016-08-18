package com.motion.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.motion.dao.VideoDao;
import com.motion.sangeet.R;

public class RomanceCustomAdapter extends BaseAdapter {

	private Context context;

	/**
	 * constructor to initialise the current objects.
	 * 
	 * @param context
	 * @param videoDaoArrayList
	 */
	private ArrayList<VideoDao> videoDaoArrayList;

	public RomanceCustomAdapter(Context context,
			ArrayList<VideoDao> videoDaoArrayList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.videoDaoArrayList = videoDaoArrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videoDaoArrayList.size();
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
					R.layout.custom_grid_layout, null);

		}

		TextView timeTextView = (TextView) convertView
				.findViewById(R.id.timeTextView);
		timeTextView.setText(videoDaoArrayList.get(position).getRating());

		return convertView;
	}
}
