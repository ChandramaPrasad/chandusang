package com.motion.sangeet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import com.motion.adapter.BirthdayCustomAdapter;

public class SpecialBirthdayActivity extends FragmentActivity {

	private GridView birthdayGridView;

	private BirthdayCustomAdapter birthdayCustomAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_special_birthday);

		intialiseViews();
		setAdapterToGridView();
	}

	/**
	 * THis method use to set adapter to gridviews.
	 */
	private void setAdapterToGridView() {
		// TODO Auto-generated method stub
		birthdayGridView.setAdapter(birthdayCustomAdapter);

	}

	/**
	 * THis method use to initalise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		birthdayGridView = (GridView) findViewById(R.id.birthdayGridView);
		birthdayCustomAdapter = new BirthdayCustomAdapter(this);

	}
}
