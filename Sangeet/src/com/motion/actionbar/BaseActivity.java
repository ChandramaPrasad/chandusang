package com.motion.actionbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.controller.ApplicationController;
import com.example.application.defines.AppDefines;
import com.example.framework.net.NetworkUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.motion.adapter.CustomGridAdapter;
import com.motion.adapter.MostViewCustomAdapter;
import com.motion.adapter.NavigationDraverAdapter;
import com.motion.adapter.RomanceCustomAdapter;
import com.motion.dao.VideoDao;
import com.motion.sangeet.EditProfileActivity;
import com.motion.sangeet.FeedBackActivity;
import com.motion.sangeet.LoginActivity;
import com.motion.sangeet.MakeYourMoodMenuActivity;
import com.motion.sangeet.MakeYourMoodPlayList;
import com.motion.sangeet.R;
import com.motion.sangeet.SearchViewActivity;
import com.motion.sangeet.SignUpActivity;
import com.motion.sangeet.TabActivity;
import com.motion.sangeet.VideoPlayListActivity;
import com.motion.util.ImageTrans_roundedcorner;
import com.motion.util.UserSessionManager;
import com.motion.util.VideoParser;
import com.motion.videocontroller.VideoPlayerActivity;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class BaseActivity extends ActionBarActivity implements
		ObservableScrollViewCallbacks, DrawerLayout.DrawerListener {

	private ImageView tamilImageButton;
	private ImageView devotionalImageButton;
	private ImageView kanadaImageButton;
	private ListView drawer_listView;
	protected ActionBar actionBar;
	protected ImageView actionImageView;
	protected FrameLayout contenFrameLayout;
	protected DrawerLayout drawerLayout;
	private ObservableGridView obsergridView;
	Fragment videofragement;

	private EditText editTextSearch;
	private ImageView searchImageView;
	private ImageView teluguImageButton;
	private TableLayout menuTableLayout;
	private ObservableScrollView scroll;
	private ImageView imageViewSearchTitle;
	private RomanceCustomAdapter romanceCustomAdapter;

	public static final String[] items = { "Playlist", "Make your mood",
			"Settings", "Share", "Rate your app", "Feedback", "Edit profile",
			"About us", "Logout" };
	private ImageView newarrivalImageView;
	private ImageView mostviewImageView;
	private ImageView roamceImageView;
	private ImageView birthspecImageView;
	private ImageView videoSongImageView;
	private ImageView comedyspecialImageView;
	private ImageView malaylamImageButton;
	private ImageView bhojpuriImageButton;

	private TextView teleguTextView;
	private TextView tamilTextView;
	private TextView kannadaTextView;
	private TextView malyalamTextView;
	private TextView bhojpuriTextView;
	private LinearLayout languageLayout;
	private String categories = null;
	private String language = null;
	private String status;
	private boolean birthdaylanguageStatus = false;

	private ArrayList<VideoDao> videoDaoArrayList;
	private String url = "http://motionpixeltech.com/ftp/sangeet.php";
	private ProgressDialog progressDialog;
	private RelativeLayout loginRelativLayout;

	private MostViewCustomAdapter mostViewCustomAdapter;
	private UserSessionManager sessionManager;
	private boolean isUserLogin;
	private HashMap<String, String> userdetails;
	private String picusername;
	private String username;
	private ImageView userpicImageView;
	private TextView loginNameTextView;
	private TextView messageTextView;
	private String link = "http://motionpixeltech.com/androidlogin/images/";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_layout);
		// THis method use to initialise the views.
		//
		// if (!Util.isNetworkAvailable(getApplicationContext())) {
		//
		// startActivity(new Intent(
		// android.provider.Settings.ACTION_SETTINGS));
		//
		// }

		initViews();

		// method to set the adapter to listview.
		setAdapterToListView();

		setCustomTitleBar(R.layout.custom_actionbar_layout);

		registerEvents();

		updateUsernameProfilePicture();

		/**
		 * When user click on the button it will open search window.
		 */
		searchImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(BaseActivity.this,
						SearchViewActivity.class);
				startActivity(intent);

			}
		});

		/**
		 * Here check weather user login or not?
		 */
		loginRelativLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boolean userstatus = sessionManager.isUserLoggedIn();
				System.out.println("user status from base activity>>"
						+ userstatus);

				if (userstatus == false) {

					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(BaseActivity.this,
									LoginActivity.class);
							startActivity(intent);

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
				}

			}
		});
		/***
		 * when user click on this button it will open the actionbar to user
		 * select the option from menu
		 */
		actionImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
					drawerLayout.closeDrawers();

				} else {
					drawerLayout.bringToFront();
					drawerLayout.requestLayout();

					drawerLayout.openDrawer(Gravity.LEFT);

				}
			}
		});

		/**
		 * set the listener to gridview to get the item base on their position.
		 */
		obsergridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (getStatus() != null && getStatus().equals("newarrival")) {

					String videopath = "http://motionpixeltech.com/telugucomedy/"
							+ videoDaoArrayList.get(position).getVideo_name()
									.toString();

					Intent intent = new Intent(BaseActivity.this,
							VideoPlayerActivity.class);
					intent.putExtra("videopath", videopath);
					startActivity(intent);

				} else if (getStatus() != null && getStatus().equals("romance")) {

					String rating = videoDaoArrayList.get(position).getRating()
							.toString();
					Toast.makeText(getApplicationContext(), "Rating:" + rating,
							Toast.LENGTH_SHORT).show();

				}

			}
		});

		/**
		 * WHen user click on the devotional button he/she will redirect to
		 * devotional screen.
		 */
		devotionalImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ApplicationController.getInstance().handleEvent(
						AppDefines.EVENT_ID_DEVOTIONAL_ALBUM_SCREEN);

			}
		});

		/**
		 * when user click on telgu button then it will open the birthday
		 * special screen
		 */
		teluguImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getApplicationContext(),
						MakeYourMoodPlayList.class);
				startActivity(intent);

			}
		});

		tamilImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),
						TabActivity.class));
			}
		});
		kanadaImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						VideoPlayerActivity.class));

				// get an instance of FragmentTransaction from your Activity

			}
		});

		bhojpuriImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		malaylamImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		teleguTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				language = teleguTextView.getText().toString();

				if (getStatus() != null && getStatus().equals("newarrival")) {

					Toast.makeText(BaseActivity.this, "telegu new arrival",
							Toast.LENGTH_LONG).show();
					try {

						VideoParser parser = new VideoParser(BaseActivity.this);

						String videourl = "http://motionpixeltech.com/ftp/sangeet.php";
						videoDaoArrayList = parser.execute(videourl).get();
						mostViewCustomAdapter = new MostViewCustomAdapter(
								BaseActivity.this, videoDaoArrayList);
						obsergridView.setAdapter(mostViewCustomAdapter);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (getStatus() != null && getStatus().equals("romance")) {
					Toast.makeText(BaseActivity.this, "telegu romance",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("videosongs")) {

					Toast.makeText(BaseActivity.this, "telegu videosongs",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null
						&& getStatus().equals("mostview")) {

					Toast.makeText(BaseActivity.this, "telegu mostview",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("comedyspcl")) {

					Toast.makeText(BaseActivity.this, "telegu comedyspcl",
							Toast.LENGTH_LONG).show();

				}

			}
		});

		tamilTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				language = tamilTextView.getText().toString();

				if (getStatus() != null && getStatus().equals("newarrival")) {

					Toast.makeText(BaseActivity.this, "Tamil new arrival",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null && getStatus().equals("romance")) {
					Toast.makeText(BaseActivity.this, "tamil romance",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("videosongs")) {

					Toast.makeText(BaseActivity.this, "tamil videosongs",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null
						&& getStatus().equals("mostview")) {

					Toast.makeText(BaseActivity.this, "tamil mostview",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("comedyspcl")) {

					Toast.makeText(BaseActivity.this, "tamil comedyspcl",
							Toast.LENGTH_LONG).show();

				}

			}
		});

		malyalamTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				language = teleguTextView.getText().toString();

				if (getStatus() != null && getStatus().equals("newarrival")) {

					Toast.makeText(BaseActivity.this, "telegu new arrival",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null && getStatus().equals("romance")) {
					Toast.makeText(BaseActivity.this, "telegu romance",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("videosongs")) {

					Toast.makeText(BaseActivity.this, "telegu videosongs",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null
						&& getStatus().equals("mostview")) {

					Toast.makeText(BaseActivity.this, "telegu mostview",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("comedyspcl")) {

					Toast.makeText(BaseActivity.this, "telegu comedyspcl",
							Toast.LENGTH_LONG).show();

				}

			}
		});

		bhojpuriTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				language = teleguTextView.getText().toString();

				if (getStatus() != null && getStatus().equals("newarrival")) {

					Toast.makeText(BaseActivity.this, "bhojpur new arrival",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null && getStatus().equals("romance")) {
					Toast.makeText(BaseActivity.this, "bhojpur romance",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("videosongs")) {

					Toast.makeText(BaseActivity.this, "bhojpur videosongs",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null
						&& getStatus().equals("mostview")) {

					Toast.makeText(BaseActivity.this, "bhojpur mostview",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("comedyspcl")) {

					Toast.makeText(BaseActivity.this, "bhojpur comedyspcl",
							Toast.LENGTH_LONG).show();

				}

			}
		});

		kannadaTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				language = teleguTextView.getText().toString();

				if (getStatus() != null && getStatus().equals("newarrival")) {

					Toast.makeText(BaseActivity.this, "Kannada new arrival",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null && getStatus().equals("romance")) {
					Toast.makeText(BaseActivity.this, "Kannada romance",
							Toast.LENGTH_LONG).show();

					try {

						VideoParser parser = new VideoParser(BaseActivity.this);

						if (!videoDaoArrayList.isEmpty()) {
							videoDaoArrayList.clear();

						}
						videoDaoArrayList = parser.execute(url).get();
						romanceCustomAdapter = new RomanceCustomAdapter(
								BaseActivity.this, videoDaoArrayList);
						obsergridView.setAdapter(romanceCustomAdapter);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (getStatus() != null
						&& getStatus().equals("videosongs")) {

					Toast.makeText(BaseActivity.this, "Kannada videosongs",
							Toast.LENGTH_LONG).show();

				} else if (getStatus() != null
						&& getStatus().equals("mostview")) {

					Toast.makeText(BaseActivity.this, "Kannada mostview",
							Toast.LENGTH_LONG).show();
				} else if (getStatus() != null
						&& getStatus().equals("comedyspcl")) {

					Toast.makeText(BaseActivity.this, "Kannada comedyspcl",
							Toast.LENGTH_LONG).show();

				}

			}
		});
		newarrivalImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				birthdaylanguageStatus = false;
				languageLayout.setVisibility(View.VISIBLE);
				setStatus("");
				setStatus("newarrival");

			}
		});

		roamceImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				birthdaylanguageStatus = false;
				languageLayout.setVisibility(View.VISIBLE);

				setStatus("");
				setStatus("romance");

			}
		});

		videoSongImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				birthdaylanguageStatus = false;
				languageLayout.setVisibility(View.VISIBLE);

				setStatus("");
				setStatus("videosongs");

			}
		});

		birthspecImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				languageLayout.setVisibility(View.GONE);
				birthdaylanguageStatus = true;
				// TODO Auto-generated method stub

			}
		});

		mostviewImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				birthdaylanguageStatus = false;
				languageLayout.setVisibility(View.VISIBLE);
				setStatus("");
				setStatus("mostview");

			}
		});

		comedyspecialImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				birthdaylanguageStatus = false;
				languageLayout.setVisibility(View.VISIBLE);
				setStatus("");
				setStatus("comedyspcl");

			}
		});
		/**
		 * click on item to select from drawer
		 */
		drawer_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(BaseActivity.this,
									VideoPlayListActivity.class);
							startActivity(intent);

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;
				case 1:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(BaseActivity.this,
									MakeYourMoodMenuActivity.class);
							startActivity(intent);

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;

				case 5:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(BaseActivity.this,
									FeedBackActivity.class);
							startActivity(intent);

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;

				case 2:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(BaseActivity.this,
									SignUpActivity.class);
							startActivity(intent);

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;
				case 7:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// Intent intent = new Intent(BaseActivity.this,
							// AudioControllerActivity.class);
							// startActivity(intent);

						}
					}, 250);
				case 8:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {

							// logout user when no need more login.
							sessionManager.logoutUser();
							Picasso.with(getApplicationContext()).invalidate(
									link);
							userpicImageView
									.setImageResource(R.drawable.profilepic);
							messageTextView.setVisibility(View.VISIBLE);
							loginNameTextView.setText("Welcome Guest");

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;
				case 6:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {

							boolean userstatus = sessionManager
									.isUserLoggedIn();

							if (userstatus == false) {

								Toast.makeText(getApplicationContext(),
										"Login to edit profile",
										Toast.LENGTH_SHORT).show();

							} else {
								Intent intent = new Intent(BaseActivity.this,
										EditProfileActivity.class);
								startActivity(intent);

							}

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;

				case 4:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// Intent intent = new Intent(BaseActivity.this,
							// LoginActivity.class);
							// startActivity(intent);

						}
					}, 250);

					drawerLayout.closeDrawer(Gravity.LEFT);
					break;
				}

			}
		});
	}

	/**
	 * This method use to register the events
	 */
	private void registerEvents() {
		// TODO Auto-generated method stub

		obsergridView.setScrollViewCallbacks(this);
		drawerLayout.setDrawerListener(this);

	}

	/**
	 * This method created to set adapter to listview.
	 */
	private void setAdapterToListView() {
		// TODO Auto-generated method stub

		NavigationDraverAdapter draverAdapter = new NavigationDraverAdapter(
				this, items);
		drawer_listView.setAdapter(draverAdapter);

	}

	/**
	 * create the method to initialise the views.
	 */
	private void initViews() {
		// TODO Auto-generated method stub

		progressDialog = new ProgressDialog(this);

		userdetails = new HashMap<String, String>();
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		userpicImageView = (ImageView) findViewById(R.id.userpicImageView);
		loginNameTextView = (TextView) findViewById(R.id.loginNameTextView);

		messageTextView = (TextView) findViewById(R.id.messagetextView);
		sessionManager = new UserSessionManager(getApplicationContext());
		loginRelativLayout = (RelativeLayout) findViewById(R.id.image_view);

		drawer_listView = (ListView) findViewById(R.id.drawer_listView);
		contenFrameLayout = (FrameLayout) findViewById(R.id.contentFrameLayout);
		// gridView = (GridView) findViewById(R.id.gridView);
		obsergridView = (ObservableGridView) findViewById(R.id.obsergridView);

		obsergridView.setAdapter(new CustomGridAdapter(this));

		videoDaoArrayList = new ArrayList<VideoDao>();

		actionBar = getSupportActionBar();

		devotionalImageButton = (ImageView) findViewById(R.id.devotionalImageButton);
		teluguImageButton = (ImageView) findViewById(R.id.teluguImageButton);
		tamilImageButton = (ImageView) findViewById(R.id.tamilImageButton);
		kanadaImageButton = (ImageView) findViewById(R.id.kanadaImageButton);
		malaylamImageButton = (ImageView) findViewById(R.id.malaylamImageButton);
		bhojpuriImageButton = (ImageView) findViewById(R.id.bhojpuriImageButton);

		menuTableLayout = (TableLayout) findViewById(R.id.menuTableLayout);
		roamceImageView = (ImageView) findViewById(R.id.roamceImageView);
		birthspecImageView = (ImageView) findViewById(R.id.birthspecImageView);
		videoSongImageView = (ImageView) findViewById(R.id.videoSongImageView);
		comedyspecialImageView = (ImageView) findViewById(R.id.comedyspecialImageView);

		languageLayout = (LinearLayout) findViewById(R.id.languageLayout);

		/**
		 * categories imageview mapping ids
		 */
		newarrivalImageView = (ImageView) findViewById(R.id.newarrivalImageView);
		mostviewImageView = (ImageView) findViewById(R.id.mostviewImageView);

		teleguTextView = (TextView) findViewById(R.id.telguTextView);
		tamilTextView = (TextView) findViewById(R.id.tamilTextView);
		kannadaTextView = (TextView) findViewById(R.id.kannadaTextView);
		malyalamTextView = (TextView) findViewById(R.id.malyalamTextView);
		bhojpuriTextView = (TextView) findViewById(R.id.bhojpuriTextView);
	}

	/**
	 * Vi This method use to set the custom action bar.
	 * 
	 * @param customActionbarLayout
	 */
	private void setCustomTitleBar(int customActionbarLayout) {

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);

		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#7c4272")));
		actionBar.setCustomView(customActionbarLayout);
		// getSupportActionBar().setElevation(0);

		actionImageView = (ImageView) actionBar.getCustomView().findViewById(
				R.id.actionImageView);

		editTextSearch = (EditText) actionBar.getCustomView().findViewById(
				R.id.editTextSearch);
		searchImageView = (ImageView) actionBar.getCustomView().findViewById(
				R.id.searchImageView);
		imageViewSearchTitle = (ImageView) actionBar.getCustomView()
				.findViewById(R.id.imageViewSearchTitle);

		imageViewSearchTitle.setVisibility(View.VISIBLE);
		searchImageView.setVisibility(View.VISIBLE);
		editTextSearch.setVisibility(View.GONE);
	}

	@Override
	public void onDownMotionEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollChanged(int arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {
		// TODO Auto-generated method stub

		if (scrollState == ScrollState.UP) {

			menuTableLayout.setVisibility(View.GONE);

			/**
			 * change the status of the language to get weather selected or not
			 */
			if (!birthdaylanguageStatus == true) {

				languageLayout.setVisibility(View.VISIBLE);
			}

		} else if (scrollState == ScrollState.DOWN) {
			menuTableLayout.setVisibility(View.VISIBLE);
			languageLayout.setVisibility(View.GONE);

		}

	}

	/**
	 * setter and getter use to check weather
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	public void updateUsernameProfilePicture() {

		/**
		 * Check weather user login or not if user login then set username and
		 * profile picture else keep as default.
		 */

		isUserLogin = sessionManager.isUserLoggedIn();

		if (isUserLogin == false) {

			loginNameTextView.setText("Welcome guest");
			messageTextView.setVisibility(View.VISIBLE);
			// userpicImageView
			// .setImageDrawable(getDrawable(R.drawable.profilepic));

		} else {

			messageTextView.setVisibility(View.GONE);
			userdetails = sessionManager.getUserDetails();
			picusername = userdetails.get("username");
			username = userdetails.get("name");

			link += picusername + ".jpg";

			// This line use to remove the cache of the image and load the new
			// image
			// when user load from the edit screen.

			System.out.println("username>>>" + username);
			System.out.println("picturename" + picusername);

			loginNameTextView.setText("" + username);

			System.out.println("Image link>>" + link);

			/**
			 * This is to load the image from image url.
			 */
			Picasso.with(this).invalidate(link);
			Picasso.with(this)
					.load(link)
					.networkPolicy(
							NetworkUtils.isNetworkAvailable(this) ? NetworkPolicy.NO_CACHE
									: NetworkPolicy.OFFLINE).resize(100, 100)
					.transform(new ImageTrans_roundedcorner()).centerCrop()
					.into(userpicImageView);
		}

	}

}
