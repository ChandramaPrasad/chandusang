package com.motion.sangeet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.motion.actionbar.BaseActivity;

public class MakeYourMoodMenuActivity extends BaseActivity implements OnClickListener {
	private ImageView searchImageView;
	private EditText editTextSearch;
	private ImageView smileImageView;
	private ImageView sadImageView;
	private ImageView loveImageView;
	private ImageView actionImageView;
	private ImageView hotFillingImageView;
	private ImageView emotionSelectImageView;
	private ImageView imageViewSearchTitle;
	private CheckBox selectImageCheckBox;
	private String radioButtonText;
	private RelativeLayout relativeLayoutCircularImageView;
	private RadioGroup radioGroup;
	private Bitmap bitmap;
	private RadioButton radioButtonState;
	private String selectRadioTextString;
	private String url = "http://farm4.static.flickr.com/3442/3310644753_5607eb96a4.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * this method replace frameLayout of main avtivity to
		 * make_your_mood_menu_layout
		 */
		getLayoutInflater().inflate(R.layout.make_your_mood_menu_layout, contenFrameLayout);
		initialiseViews();
		
		/**
		 * this method use to select particular radio button
		 */
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				int selectedId = group.getCheckedRadioButtonId();
				switch (checkedId) {
				case R.id.teluguRadioButton:
					radioButtonState = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButtonState.getText().toString();

					break;

				case R.id.tamilRadioButton:
					radioButtonState = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButtonState.getText().toString();

					break;

				case R.id.kanadaRadioButton:
					radioButtonState = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButtonState.getText().toString();

					break;

				case R.id.malayalamRadioButton:
					radioButtonState = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButtonState.getText().toString();

					break;

				case R.id.bhojpuriRadioButton:
					radioButtonState = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButtonState.getText().toString();

					break;
				}

			}
		});

	}

	private void initialiseViews() {
		
		/**
		 * here we getting the action bar instance from BaseActivity
		 */
		ActionBar actionBar = getSupportActionBar();
		searchImageView = (ImageView) actionBar.getCustomView().findViewById(R.id.searchImageView);
		editTextSearch = (EditText) actionBar.getCustomView().findViewById(R.id.editTextSearch);
		imageViewSearchTitle=(ImageView) actionBar.getCustomView().findViewById(R.id.imageViewSearchTitle);
		searchImageView.setVisibility(View.VISIBLE);	
		searchImageView.setVisibility(View.VISIBLE);
		editTextSearch.setVisibility(View.GONE);
		
		radioGroup = (RadioGroup) findViewById(R.id.stateRadioGroup);
		smileImageView = (ImageView) findViewById(R.id.imageViewSmile);
		sadImageView = (ImageView) findViewById(R.id.imageViewSad);
		actionImageView = (ImageView) findViewById(R.id.imageViewAction);
		loveImageView = (ImageView) findViewById(R.id.imageViewLove);
		hotFillingImageView = (ImageView) findViewById(R.id.imageViewHotFeeling);
		emotionSelectImageView = (ImageView) findViewById(R.id.imageViewEmotionSelect);

		smileImageView.setOnClickListener(this);
		sadImageView.setOnClickListener(this);
		actionImageView.setOnClickListener(this);
		loveImageView.setOnClickListener(this);
		hotFillingImageView.setOnClickListener(this);
		emotionSelectImageView.setOnClickListener(this);
		// this check box is used to enter into MakeYoorMoodPlayList with Bitmap
		// image
		selectImageCheckBox = (CheckBox) findViewById(R.id.selectImageCheckBox);
		selectImageCheckBox.setOnClickListener(new OnClickListener() {
			/**
			 * this method transfer the image and radio text to
			 * MakeYourMoodPlayList after click on check box
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (selectImageCheckBox.isChecked()) {
					if (emotionSelectImageView != null && selectRadioTextString != null) {
						//emotionSelectImageView.buildDrawingCache();
						//bitmap = emotionSelectImageView.getDrawingCache();
						Intent intent = new Intent(getApplicationContext(), MakeYourMoodPlayList.class);
						intent.putExtra("BitmapImage", bitmap);
						intent.putExtra("RadioStateTextString", selectRadioTextString);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "Please select radio button and circle image",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	/**
	 * This method use to select the particular image which is given in circle
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageViewSmile:
			emotionSelectImageView.setImageResource(R.drawable.smile_b);
			//smileImageView.setImageResource(R.drawable.smile_f);
			smileImageView.buildDrawingCache();
			bitmap = smileImageView.getDrawingCache();
			// show first image of imageView
//			sadImageView.setImageResource(R.drawable.sad);
//			loveImageView.setImageResource(R.drawable.love);
//			actionImageView.setImageResource(R.drawable.action);
//			hotFillingImageView.setImageResource(R.drawable.hotfeeling);
			break;

		case R.id.imageViewSad:
			//sadImageView.setImageResource(R.drawable.sad_f);
			sadImageView.buildDrawingCache();
			bitmap = sadImageView.getDrawingCache();
			emotionSelectImageView.setImageResource(R.drawable.sad_b);
			// show first image of imageView
//			loveImageView.setImageResource(R.drawable.love);
//			actionImageView.setImageResource(R.drawable.action);
//			hotFillingImageView.setImageResource(R.drawable.hotfeeling);
//			smileImageView.setImageResource(R.drawable.smile);
			break;
		case R.id.imageViewLove:
			//loveImageView.setImageResource(R.drawable.love_f);
			loveImageView.buildDrawingCache();
			bitmap = loveImageView.getDrawingCache();
			emotionSelectImageView.setImageResource(R.drawable.love_b);
			// show first image of imageView
//			sadImageView.setImageResource(R.drawable.sad);
//			actionImageView.setImageResource(R.drawable.action);
//			hotFillingImageView.setImageResource(R.drawable.hotfeeling);
//			smileImageView.setImageResource(R.drawable.smile);
			break;
		case R.id.imageViewAction:
			//actionImageView.setImageResource(R.drawable.action_f);
			actionImageView.buildDrawingCache();
			bitmap = actionImageView.getDrawingCache();
			emotionSelectImageView.setImageResource(R.drawable.action_b);
			// show first image of imageView
//			sadImageView.setImageResource(R.drawable.sad);
//			loveImageView.setImageResource(R.drawable.love);
//			hotFillingImageView.setImageResource(R.drawable.hotfeeling);
//			smileImageView.setImageResource(R.drawable.smile);
			break;
		case R.id.imageViewHotFeeling:
			//hotFillingImageView.setImageResource(R.drawable.hotfeeling_f);
			hotFillingImageView.buildDrawingCache();
			bitmap = hotFillingImageView.getDrawingCache();
			emotionSelectImageView.setImageResource(R.drawable.hotfeeling_b);
			// show first image of imageView
//			sadImageView.setImageResource(R.drawable.sad);
//			loveImageView.setImageResource(R.drawable.love);
//			actionImageView.setImageResource(R.drawable.action);
//			smileImageView.setImageResource(R.drawable.smile);
			break;

		}
	}

}
