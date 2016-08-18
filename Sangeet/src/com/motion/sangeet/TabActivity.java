package com.motion.sangeet;

import android.annotation.SuppressLint;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.motion.actionbar.BaseActivity;

public class TabActivity extends BaseActivity {

	private LocalActivityManager mLocalActivityManager;
	private TabHost tabHost;
	private TabHost.TabSpec spec;
	private Intent intent;
	private static ImageView catImageView;
	private HorizontalScrollView horizontalScrollbar;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getLayoutInflater()
				.inflate(R.layout.tab_main_layout, contenFrameLayout);

//		horizontalScrollbar = (HorizontalScrollView) findViewById(R.id.horizontalScrollbar);

//		horizontalScrollbar.setHorizontalScrollBarEnabled(false);

		tabHost = (TabHost) findViewById(R.id.tabHost);
		mLocalActivityManager = new LocalActivityManager(this, false);

		tabHost.setup(mLocalActivityManager);
		mLocalActivityManager.dispatchCreate(savedInstanceState);

		// set up your tabs here. It's easy to just do seperate activities for
		// each tab, and link them in here.
		View tabView = createTabView(this, R.drawable.newarrival_f);
		intent = new Intent().setClass(this, NewArrivalActivity.class);
		spec = tabHost.newTabSpec("tagname1").setIndicator(tabView)
				.setContent(intent);
		tabHost.addTab(spec);

		View tabView1 = createTabView(this, R.drawable.mostviewed_f);
		Intent intent5 = new Intent().setClass(this, MostViewActivity.class);
		spec = tabHost.newTabSpec("tagname2").setIndicator(tabView1)
				.setContent(intent5);
		tabHost.addTab(spec);

		View tabView2 = createTabView(this, R.drawable.birthdayspcl_f);
		Intent intent1 = new Intent().setClass(this,
				SpecialBirthdayActivity.class);
		spec = tabHost.newTabSpec("tagname2").setIndicator(tabView2)
				.setContent(intent1);
		tabHost.addTab(spec);

		View tabView3 = createTabView(this, R.drawable.videosongs_f);
		Intent intent2 = new Intent().setClass(this, VideoSongActivity.class);
		spec = tabHost.newTabSpec("tagname2").setIndicator(tabView3)
				.setContent(intent2);
		tabHost.addTab(spec);

		View tabView4 = createTabView(this, R.drawable.comedy_f);
		Intent intent3 = new Intent().setClass(this, ComedyActivity.class);
		spec = tabHost.newTabSpec("tagname2").setIndicator(tabView4)
				.setContent(intent3);
		tabHost.addTab(spec);

		View tabView5 = createTabView(this, R.drawable.romance_f);
		Intent intent4 = new Intent().setClass(this, RomanceActivity.class);
		spec = tabHost.newTabSpec("tagname2").setIndicator(tabView5)
				.setContent(intent4);
		tabHost.addTab(spec);

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			if (i == 0)
				tabHost.getTabWidget().getChildAt(i)
						.setBackgroundColor(Color.parseColor("#873a72"));

			else
				tabHost.getTabWidget().getChildAt(i)
						.setBackgroundColor(Color.parseColor("#992169"));
		}

		/**
		 * This method use to change the backgroung color of the tab when user
		 * click.
		 */
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub

				for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
					tabHost.getTabWidget().getChildAt(i)
							.setBackgroundColor(Color.parseColor("#992169"));
				}

				tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
						.setBackgroundColor(Color.parseColor("#873a72"));

			}
		});

	}

	@SuppressLint("NewApi")
	private static View createTabView(Context context, int drawable) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.custom_tab_layout, null, false);
		catImageView = (ImageView) view.findViewById(R.id.catImageView);
		catImageView
				.setBackground(context.getResources().getDrawable(drawable));
		return view;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLocalActivityManager.dispatchPause(isFinishing()); // you have to
															// manually dispatch
															// the pause msg
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLocalActivityManager.dispatchResume(); // you have to manually dispatch
												// the resume msg
												// /**
		// * Here the trick to change the android tab text color as white when
		// * load this activity and it will work only on onresume method.
		// */
		// for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
		//
		// tv.setTextColor(Color.parseColor("#ffffff"));
		// }

	}
}
