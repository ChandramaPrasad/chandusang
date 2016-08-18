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

public class MostViewCustomAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<VideoDao> mostViewDaoArrayList;

	public MostViewCustomAdapter(Context context,
			ArrayList<VideoDao> mostViewDaoArrayList) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.mostViewDaoArrayList = mostViewDaoArrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mostViewDaoArrayList.size();
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

		final TextView videourlTextView = (TextView) convertView
				.findViewById(R.id.videourlTextView);

		videourlTextView.setText("http://motionpixeltech.com/telugucomedy/"
				+ mostViewDaoArrayList.get(position).getVideo_name());

		TextView nameTextView = (TextView) convertView
				.findViewById(R.id.nameTextView);

		nameTextView.setText(mostViewDaoArrayList.get(position).getVideo_name()
				.toString());

		TextView timeTextView = (TextView) convertView
				.findViewById(R.id.timeTextView);

		timeTextView.setText(mostViewDaoArrayList.get(position).getDuration()
				.toString());

		TextView viewTextView = (TextView) convertView
				.findViewById(R.id.viewTextView);

		viewTextView.setText(mostViewDaoArrayList.get(position).getViews());

		return convertView;
	}
}
