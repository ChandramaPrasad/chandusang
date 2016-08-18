package com.motion.sangeet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import com.motion.adapter.ComedyCustomAdapter;

public class ComedyActivity extends FragmentActivity {

	private GridView comedyGridVeiw;
	private ComedyCustomAdapter comedyCustomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comedy);

		intialiseViews();
		setAdapterToGridView();
	}

	/**
	 * This method use to set the gridview adapter.
	 */
	private void setAdapterToGridView() {
		// TODO Auto-generated method stub

		comedyGridVeiw.setAdapter(comedyCustomAdapter);

	}

	/***
	 * This method use to initialise the views,
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		comedyGridVeiw = (GridView) findViewById(R.id.comedyGridView);

		comedyCustomAdapter = new ComedyCustomAdapter(this);
	}
}
