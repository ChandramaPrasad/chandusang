package com.motion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.motion.sangeet.R;
/**
 * this is make for birthday
 * @author Work Station
 *
 */

public class BirthdaySpecialCustomGridAdapter extends BaseAdapter {

	private Context context;

	public BirthdaySpecialCustomGridAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
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

			convertView = LayoutInflater.from(context).inflate(R.layout.custom_grid_birthday__layout, null);

		}

		return convertView;
	}

}
