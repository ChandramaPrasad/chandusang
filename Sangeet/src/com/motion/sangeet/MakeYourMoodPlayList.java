package com.motion.sangeet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.motion.actionbar.BaseActivity;
import com.motion.adapter.MostViewCustomAdapter;
import com.motion.adapter.VideoPlayListAdapter;
import com.motion.dao.VideoDao;
import com.motion.util.VideoParser;
import com.motion.videocontroller.VideoPlayerActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.motion.actionbar.BaseActivity;
import com.motion.adapter.MakeYourMoodPlayListAdapter;

public class MakeYourMoodPlayList extends BaseActivity {
	private GridView gridView;
	private Bundle bundle;
	private ProgressDialog progressDialog;
	private TextView makeYourMoodPlayListStateTextView;
	private ImageView makeYourMoodPlayListImageView;
	private ImageView searchImageView;
	private EditText editTextSearch;
	private ImageView imageViewSearchTitle;
	private ArrayList<VideoDao> videoDaoArrayList;
	private VideoPlayListAdapter videoPlayListAdapter;
	private final String url = "http://motionpixeltech.com/ftp/sangeet.php";
	private MostViewCustomAdapter mostViewCustomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLayoutInflater().inflate(R.layout.make_your_mood_play_list_layout, contenFrameLayout);
		initComponent();
		setGridView();
		sendDataToPlayerActivity();
	}

	private void sendDataToPlayerActivity() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String videoUrlName="http://motionpixeltech.com/telugucomedy/"+videoDaoArrayList.get(position).getVideo_name().toString();
				Intent intent = new Intent(MakeYourMoodPlayList.this, VideoPlayerActivity.class);
				intent.putExtra("videoUrlName",videoUrlName );
				startActivity(intent);
			}
		});
	}

	private void setGridView() {
		// TODO Auto-generated method stub
		gridView = (GridView) findViewById(R.id.mood_gridView);
		// gridView.setAdapter(new MakeYourMoodPlayListAdapter(this));

		// Bitmap bitmap = (Bitmap)
		// this.getIntent().getParcelableExtra("BitmapImage");
		// makeYourMoodPlayListImageView.setImageBitmap(bitmap);
		VideoParser parser = new VideoParser(MakeYourMoodPlayList.this);

		try {
			videoDaoArrayList = parser.execute(url).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mostViewCustomAdapter = new MostViewCustomAdapter(MakeYourMoodPlayList.this, videoDaoArrayList);
		gridView.setAdapter(mostViewCustomAdapter);
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		/**
		 * here we getting the action bar instance from BaseActivity
		 */
		ActionBar actionBar = getSupportActionBar();
		searchImageView = (ImageView) actionBar.getCustomView().findViewById(R.id.searchImageView);
		editTextSearch = (EditText) actionBar.getCustomView().findViewById(R.id.editTextSearch);
		imageViewSearchTitle = (ImageView) actionBar.getCustomView().findViewById(R.id.imageViewSearchTitle);
		searchImageView.setVisibility(View.VISIBLE);
		searchImageView.setVisibility(View.VISIBLE);
		editTextSearch.setVisibility(View.GONE);
		progressDialog = new ProgressDialog(this);
		makeYourMoodPlayListStateTextView = (TextView) findViewById(R.id.makeYourMoodPlayListStateTextView);
		makeYourMoodPlayListImageView = (ImageView) findViewById(R.id.makeYourMoodPlayListImageView);
		// Bundle extras = getIntent().getExtras();
		// String value;
		// if (extras != null) {
		// value = extras.getString("RadioStateTextString");
		// makeYourMoodPlayListStateTextView.setText(value);
		// }
	}

}
