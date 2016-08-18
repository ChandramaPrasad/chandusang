package com.motion.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.motion.dao.VideoPlayListDao;
import com.motion.sangeet.R;

public class VideoPlayListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<VideoPlayListDao> videoPlayListArray;

	public VideoPlayListAdapter(Context context, ArrayList<VideoPlayListDao> videoPlayListArray) {
		super();
		this.context = context;
		this.videoPlayListArray = videoPlayListArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videoPlayListArray.size();
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
		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.video_play_list_adapter_layout, null);

		ImageView imageViewPlayList = (ImageView) convertView.findViewById(R.id.imageViewPlayList);
		imageViewPlayList.setImageResource(R.drawable.boy);

		TextView textViewVideoNameVideoPlayList = (TextView) convertView
				.findViewById(R.id.textViewVideoNameVideoPlayList);
		textViewVideoNameVideoPlayList.setText(videoPlayListArray.get(position).getVideoName().toString());

		TextView textViewDescriptionVideoPlayList = (TextView) convertView
				.findViewById(R.id.textViewDescriptionVideoPlayList);
		textViewDescriptionVideoPlayList.setText(videoPlayListArray.get(position).getVideoDescription().toString());
		return convertView;
	}

}
