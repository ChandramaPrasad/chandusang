package com.motion.sangeet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.application.defines.AppConstants;
import com.example.framework.net.NetworkUtils;
import com.motion.actionbar.BaseActivity;
import com.motion.dao.LoginDao;
import com.motion.net.ServiceHandler;
import com.motion.util.ImageTrans_roundedcorner;
import com.motion.util.UserSessionManager;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends BaseActivity implements
		OnClickListener {

	private static final String REGISTER_URL = "http://motionpixeltech.com/ftp/users/register.php";
	private ImageView imageViewUploadEditProfile;
	private EditText editTextNameEditProfile;
	private EditText editTextEmaiEditProfile;
	private EditText editTextPasswordEditProfile;
	private EditText editTextMobileEditProfile;
	private EditText editTextStatusEditProfile;
	private EditText editTextAboutMeEditProfile;
	private static final int PICK_FROM_GALLERY = 101;
	final int PIC_CROP = 1;
	private ArrayList<LoginDao> arrayListEditProfile;
	private ImageView imageViewSubmitEditProfile;
	private String nameUpdate;
	private String passwordUpdate;
	private String emailUpdate;
	private String mobileUpdate;
	private String statusUpdate;
	private String aboutUpdate;
	private UserSessionManager sessionManager;
	private boolean isUserLogin;
	private HashMap<String, String> userdetails;
	private String name;
	private String email;
	private String mobile;
	private String statusmessage;
	private String aboutme;
	private String password;
	private String idsignup;
	private String urlid;
	private Bitmap bitmap;
	private String picusername;
	private String attachmentFile;
	private int columnIndex;
	private String link = "http://motionpixeltech.com/androidlogin/images/";
	private Uri URI = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.edit_profile_layout,
				contenFrameLayout);
		initialiseComponent();

		/**
		 * THis varibale first checek weather user login or not if login then
		 * retrive value of current user other dont open edit profile activity.
		 */
		isUserLogin = sessionManager.isUserLoggedIn();

		if (isUserLogin == true) {

			userdetails = sessionManager.getUserDetails();

			idsignup = userdetails.get("idsignup");
			picusername = userdetails.get("username");

			urlid = "http://motionpixeltech.com/androidlogin/index.php?";
			urlid += "idsignup=" + idsignup;

			System.out.println("userid>>>" + urlid);

			link += picusername + ".jpg";

			// This line use to remove the cache of the image and load the new
			// image
			// when user load from the edit screen.

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
					.into(imageViewUploadEditProfile);

			new GetUserDetails().execute();

		}

		/**
		 * Here user will select image to update on server.
		 */
		imageViewUploadEditProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				selectImage();

			}
		});

	}

	private void setEditProfileData() {

		// arrayListEditProfile ArrayList<LoginDao> taking data from LocalModel
		// class

		editTextNameEditProfile.setText(name);
		editTextEmaiEditProfile.setText(email);
		editTextPasswordEditProfile.setText(password);
		editTextMobileEditProfile.setText(mobile);
		editTextStatusEditProfile.setText(statusmessage);
		editTextAboutMeEditProfile.setText(aboutme);
		nameUpdate = editTextNameEditProfile.getText().toString();
		emailUpdate = editTextEmaiEditProfile.getText().toString();
		passwordUpdate = editTextPasswordEditProfile.getText().toString();
		mobileUpdate = editTextMobileEditProfile.getText().toString();
		statusUpdate = editTextStatusEditProfile.getText().toString();
		aboutUpdate = editTextAboutMeEditProfile.getText().toString();

	}

	/**
	 * this method use to initialise view component
	 */
	private void initialiseComponent() {
		// TODO Auto-generated method stub

		arrayListEditProfile = new ArrayList<LoginDao>();
		userdetails = new HashMap<String, String>();
		sessionManager = new UserSessionManager(getApplicationContext());
		imageViewUploadEditProfile = (ImageView) findViewById(R.id.imageViewUploadEditProfile);
		editTextNameEditProfile = (EditText) findViewById(R.id.editTextNameEditProfile);
		editTextEmaiEditProfile = (EditText) findViewById(R.id.editTextEmaiEditProfile);
		editTextPasswordEditProfile = (EditText) findViewById(R.id.editTextPasswordEditProfile);
		editTextMobileEditProfile = (EditText) findViewById(R.id.editTextMobileEditProfile);
		editTextStatusEditProfile = (EditText) findViewById(R.id.editTextStatusEditProfile);
		editTextAboutMeEditProfile = (EditText) findViewById(R.id.editTextAboutMeEditProfile);
		imageViewSubmitEditProfile = (ImageView) findViewById(R.id.imageViewSubmitButton);
		imageViewSubmitEditProfile.setOnClickListener(this);
	}

	/**
	 * to override this method for performing submit operation means to
	 * communicate RegisterUserClass for saving the data
	 */
	@Override
	public void onClick(View v) {
		if (v == imageViewSubmitEditProfile) {
			registerUser();

			if (bitmap != null && !TextUtils.isEmpty(picusername)) {

				new ImageUploadTask().execute(picusername);

			}
		}

	}

	private void registerUser() {
		editTextNameEditProfile.setError(null);
		if (editTextNameEditProfile.getText().toString().length() == 0)
			editTextNameEditProfile.setError("Please enter name");
		else if (editTextEmaiEditProfile.getText().toString().length() == 0)
			editTextEmaiEditProfile.setError("Please enter email ID");
		else if (editTextPasswordEditProfile.getText().toString().length() == 0)
			editTextPasswordEditProfile.setError("Please enter password");
		else if (editTextMobileEditProfile.getText().toString().length() == 0)
			editTextMobileEditProfile.setError("Please enter mobile number");
		else if (editTextMobileEditProfile.getText().toString().length() != 10) {
			editTextMobileEditProfile
					.setError("Please enter valid 10 digit number");
			editTextMobileEditProfile.setText("");
		} else {

			String name = editTextNameEditProfile.getText().toString().trim()
					.toLowerCase();
			String email = editTextEmaiEditProfile.getText().toString().trim()
					.toLowerCase();
			String password = editTextPasswordEditProfile.getText().toString()
					.trim().toLowerCase();
			String mobile = editTextMobileEditProfile.getText().toString()
					.trim().toLowerCase();
			String status = editTextStatusEditProfile.getText().toString()
					.trim().toLowerCase();
			String about = editTextAboutMeEditProfile.getText().toString()
					.trim().toLowerCase();
			new EditProfileSendData().execute(idsignup, name, password, email,
					mobile, about, status);
		}

	}

	/**
	 * This method use to update the user profile data.
	 * 
	 * @author Admin
	 * 
	 */
	private class EditProfileSendData extends
			AsyncTask<String, Integer, Double> {

		private ProgressDialog progressDialog = new ProgressDialog(
				EditProfileActivity.this);
		private String url;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog.setMessage("Please wait");
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Double doInBackground(String... params) {

			postSend(params[0], params[1], params[2], params[3], params[4],
					params[5], params[6]);
			return null;
		}

		@Override
		protected void onPostExecute(Double result) {

			super.onPostExecute(result);

			if (!nameUpdate
					.equals(editTextNameEditProfile.getText().toString())
					|| !emailUpdate.equals(editTextEmaiEditProfile.getText()
							.toString())
					|| !mobileUpdate.equals(editTextMobileEditProfile.getText()
							.toString())
					|| !statusUpdate.equals(editTextStatusEditProfile.getText()
							.toString())
					|| !aboutUpdate.equals(editTextAboutMeEditProfile.getText()
							.toString())) {
				Toast.makeText(getApplicationContext(),
						"Profile updated successfully ", Toast.LENGTH_LONG)
						.show();

				Intent intent = new Intent(EditProfileActivity.this,
						BaseActivity.class);
				startActivity(intent);
				finish();

			} else if (!passwordUpdate.equals(editTextPasswordEditProfile
					.getText().toString())) {

				Toast.makeText(getApplicationContext(),
						"Profile updated successfully ", Toast.LENGTH_LONG)
						.show();

			} else {
				Toast.makeText(getApplicationContext(),
						"trying to update same profile", Toast.LENGTH_SHORT)
						.show();
			}

			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

		}

		private void postSend(String idsignup, String name, String password,
				String email, String mobile, String about, String status) {
			try {
				url = "http://motionpixeltech.com/androidlogin/index.php?";
				url += "idsignup=" + idsignup + "&name=" + name + "&paswd="
						+ password + "&email=" + email + "&mobile=" + mobile
						+ "&aboutme=" + about + "&mesage=" + status;
				System.out.println("url>>>>>>>>>>>>>>>>>" + url);
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassCastException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * This method use to update the user profile data.
	 * 
	 * @author Admin
	 * 
	 */
	private class GetUserDetails extends AsyncTask<String, Integer, Double> {

		private ProgressDialog progressDialog = new ProgressDialog(
				EditProfileActivity.this);
		private String url;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog.setMessage("Please wait");
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Double doInBackground(String... params) {

			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(urlid, ServiceHandler.GET);

			if (jsonStr != null) {

				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					JSONArray videoDeatils = jsonObj.getJSONArray("new_users");

					JSONObject c = videoDeatils.getJSONObject(0);

					name = (c.getString("name"));
					email = (c.getString("email"));
					password = (c.getString("paswd"));
					mobile = (c.getString("mobile"));
					statusmessage = (c.getString("status message"));
					aboutme = (c.getString("about me"));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setEditProfileData();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Double result) {

			super.onPostExecute(result);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

		}

	}

	/**
	 * This method use to upload the image.
	 */
	private void selectImage() {

		try {

			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");

			startActivityForResult(
					Intent.createChooser(intent, "Complete action using"),

					PICK_FROM_GALLERY);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * This Asyntaks use to load image when user will login.
	 * 
	 * @author Admin
	 * 
	 */
	class ImageUploadTask extends AsyncTask<String, String, String> {
		private String sResponse;
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(EditProfileActivity.this);
			progressDialog.setMessage("Loading..");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
				throws ArrayIndexOutOfBoundsException {
			// TODO Auto-generated method stub
			String response = postData(params[0]);

			return response;
		}

		private String postData(String userid) {
			// TODO Auto-generated method stub

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(AppConstants.IMAGE_UPLOAD);

			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// check weather bitmap null or not if not null then proceed for the
			// upload on server.
			if (bitmap != null) {

				bitmap.compress(CompressFormat.JPEG, 10, bos);

				byte[] data = bos.toByteArray();
				Bitmap screen = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				float oHeight = screen.getHeight();
				float oWidth = screen.getWidth();
				float aspectRatio = oWidth / oHeight;
				int newHeight = 0;
				int newWidth = 0;
				newHeight = 450;
				newWidth = (int) (newHeight * aspectRatio);
				screen = Bitmap.createScaledBitmap(screen, newWidth, newHeight,
						true);
				// entity.addPart("photoId", new
				// StringBody(getIntent().getStringExtra("photoId")));
				// entity.addPart("returnformat", new StringBody("json"));
				entity.addPart("myFile", new ByteArrayBody(data, userid
						+ ".jpg"));
				try {
					// entity.addPart("photoCaption", new StringBody(caption));
					httpPost.setEntity(entity);
					HttpResponse response = httpClient.execute(httpPost,
							localContext);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));

					String sResponse = reader.readLine();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return sResponse;

		}

		@Override
		protected void onPostExecute(String sResponse) {

			if (progressDialog != null && progressDialog.isShowing()) {

				progressDialog.dismiss();

			}

		}
	}

	/**
	 * after selecting image will check weather image upload properly or not if
	 * yes then upload otherwise not upload
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {

			try {
				// OI FILE Manager
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				attachmentFile = cursor.getString(columnIndex);
				URI = Uri.parse("file://" + attachmentFile);
				// first check uri is null or not if not null then call
				// method
				// to crop the image.

				if (URI != null) {
					performCrop(URI);

				}
				bitmap = MediaStore.Images.Media.getBitmap(
						this.getContentResolver(), selectedImage);
				imageViewUploadEditProfile.setImageBitmap(bitmap);

				cursor.close();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Internal error",
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}

		}
		// This will crop the image according to select image by user.
		if (requestCode == PIC_CROP) {
			if (data != null) {
				// get the returned data
				Bundle extras = data.getExtras();
				// get the cropped bitmap
				Bitmap selectedBitmap = extras.getParcelable("data");

				imageViewUploadEditProfile.setImageBitmap(selectedBitmap);
			}
		}
	}

	/**
	 * Method use to perform the pic crop opration when user want to upload crop
	 * picture on the server.
	 * 
	 * @param picUri
	 */
	private void performCrop(Uri picUri) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 0);
			cropIntent.putExtra("aspectY", 0);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 200);
			cropIntent.putExtra("outputY", 150);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast
					.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
