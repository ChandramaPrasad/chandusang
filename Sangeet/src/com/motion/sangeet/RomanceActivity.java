package com.motion.sangeet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import com.motion.adapter.RomanceCustomAdapter;

public class RomanceActivity extends FragmentActivity {

	private GridView romanceGridView;
	private RomanceCustomAdapter roamceCutomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_romance);

		intialiseViews();
		setAdapterToGridVeiw();
	}

	/**
	 * set the adapter to gridview after fetching data from the server.
	 */
	private void setAdapterToGridVeiw() {
		// TODO Auto-generated method stub

		romanceGridView.setAdapter(roamceCutomAdapter);

	}

	/**
	 * This method use to initialise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		romanceGridView = (GridView) findViewById(R.id.romanceGridView);
//		roamceCutomAdapter = new RomanceCustomAdapter(this);

	}
}
