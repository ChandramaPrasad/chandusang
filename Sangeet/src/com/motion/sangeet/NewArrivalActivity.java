package com.motion.sangeet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import com.motion.adapter.NewArraivalCustomAdapter;

public class NewArrivalActivity extends FragmentActivity {

	private GridView newArrivalGridView;
	private NewArraivalCustomAdapter arraivalCustomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_arrival);

		intialiseViews();
		setAdapterToGridView();

	}

	/**
	 * This method use to set adapter to gridview.
	 */
	private void setAdapterToGridView() {
		// TODO Auto-generated method stub

		newArrivalGridView.setAdapter(arraivalCustomAdapter);

	}

	/**
	 * THis method use to initialise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		newArrivalGridView = (GridView) findViewById(R.id.newarrivalGridView);

		arraivalCustomAdapter = new NewArraivalCustomAdapter(this);

	}
}
