package com.motion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.motion.sangeet.R;

public class NavigationDraverAdapter extends BaseAdapter {

	private Context context;
	private String item[];

	public NavigationDraverAdapter(Context context, String[] items) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.item = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.length;
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

			convertView = LayoutInflater.from(context).inflate(R.layout.navigation_draver_layout, null);

		}

		TextView nameTextview = (TextView) convertView.findViewById(R.id.nameTextView);
		nameTextview.setText(item[position]);
		return convertView;
	}

}
