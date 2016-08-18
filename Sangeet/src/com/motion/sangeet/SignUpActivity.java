package com.motion.sangeet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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

import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.application.defines.AppConstants;
import com.motion.net.ServiceHandler;
import com.motion.util.Util;

public class SignUpActivity extends Activity implements OnClickListener {

	/**
	 * declare the variable here.
	 */

	private ImageView imageViewPhotoSignUp;
	private ImageView buttonImageViewSubmitSignUp;
	private EditText editTextNameSignUp;
	private EditText usernameeditext;
	private EditText editTextPasswordSignUp;
	private EditText editTextEmailSignUp;
	private EditText editTextMobileSignUp;
	private EditText editTextCitySignUp;
	private EditText editTextAboutSignUp;
	private EditText editTextCountrySignUp;
	private EditText editTextStatusSignUp;
	private RadioGroup radioGroupSignUp;
	private RadioButton radioButton;
	private boolean emailStatus;
	private String selectRadioTextString = "";
	private Boolean nameBoolean;
	private String attachmentFile;
	private int columnIndex;
	private Uri URI = null;
	ProgressDialog progressDiaolog;
	private Bitmap bitmap;
	private String userid;
	private String name;
	private String gender;
	private String password;
	private String email;
	private String mobile;
	private String city;
	private String country;
	private static final int PICK_FROM_GALLERY = 101;
	final int PIC_CROP = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_layout);
		initComp();
		registerEvent();

		imageViewPhotoSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				selectImage();
			}
		});
	}

	/**
	 * this method use to perform the action on component
	 */
	private void registerEvent() {

		radioGroupSignUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				int selectedId = group.getCheckedRadioButtonId();
				switch (checkedId) {
				case R.id.radioButtonMaleSignUp:
					radioButton = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButton.getText().toString();

					break;

				case R.id.radioButtonFemaleSignUp:
					radioButton = (RadioButton) findViewById(selectedId);
					selectRadioTextString = radioButton.getText().toString();

					break;
				}
			}
		});

		/**
		 * here use setOnFocusChangeListener to check username is already exists
		 * or not
		 */
		usernameeditext.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				String username = null;
				if (!hasFocus)
					username = usernameeditext.getText().toString();
				if (username != null)
					// call asynctask to check username present or not
					new SignUpUserCheck().execute(username);

			}
		});

		buttonImageViewSubmitSignUp.setOnClickListener(this);

	}

	/**
	 * this method use to initialise view component
	 */
	private void initComp() {
		// TODO Auto-generated method stub
		progressDiaolog = new ProgressDialog(SignUpActivity.this);
		editTextEmailSignUp = (EditText) findViewById(R.id.editTextEmailSignUp);
		editTextNameSignUp = (EditText) findViewById(R.id.editTextNameSignUp);
		editTextPasswordSignUp = (EditText) findViewById(R.id.editTextPasswordSignUp);
		usernameeditext = (EditText) findViewById(R.id.usernameEditText);
		editTextAboutSignUp = (EditText) findViewById(R.id.editTextAboutSignUp);
		editTextMobileSignUp = (EditText) findViewById(R.id.editTextMobileSignUp);
		editTextCitySignUp = (EditText) findViewById(R.id.editTextCitySignUp);
		editTextCountrySignUp = (EditText) findViewById(R.id.editTextCountrySignUp);

		editTextStatusSignUp = (EditText) findViewById(R.id.editTextStatusSignUp);
		imageViewPhotoSignUp = (ImageView) findViewById(R.id.imageViewPhotoSignUp);
		buttonImageViewSubmitSignUp = (ImageView) findViewById(R.id.buttonImageViewSubmitSignUp);

		radioGroupSignUp = (RadioGroup) findViewById(R.id.radioGroupSignUp);

	}

	/**
	 * this is override method use to insert data into database after click
	 * submit button
	 */
	@Override
	public void onClick(View v) {
		if (v == buttonImageViewSubmitSignUp) {
			registerUser();

		}

	}

	private void registerUser() {
		// check email is valid or not using isValidEmail which is a static
		// method present in Util class
		emailStatus = Util.isValidEmail(editTextEmailSignUp.getText().toString());
		// same as email

		// this mehtod check field contains number or not
		nameBoolean = Util.isDigit(editTextNameSignUp);
		boolean cityBoolean = Util.isDigit(editTextCitySignUp);
		boolean countryBoolean = Util.isDigit(editTextCountrySignUp);
		// below code use for some validate on EditText
		editTextNameSignUp.setError(null);
		if (editTextNameSignUp.getText().toString().length() == 0) {
			editTextNameSignUp.setError("Please enter name");

		} else if (!nameBoolean) {
			editTextNameSignUp.setError("Name should not contain number ");
			editTextNameSignUp.setText("");
		} else if (usernameeditext.getText().toString().length() == 0) {
			usernameeditext.setError("Please enter user name");
		} else if (selectRadioTextString.length() == 0) {
			Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_LONG).show();
		} else if (editTextPasswordSignUp.getText().toString().length() == 0) {
			editTextPasswordSignUp.setError("please enter password");
		}

		else if (editTextEmailSignUp.getText().toString().length() == 0) {
			editTextEmailSignUp.setError("Please enter email ID");
		} else if (!emailStatus) {
			System.out.println("emails status>>>" + emailStatus);
			editTextEmailSignUp.setError("Please enter valid email ID");
			editTextEmailSignUp.setText("");
		} else if (editTextMobileSignUp.getText().toString().length() == 0) {
			editTextMobileSignUp.setError("Please enter mobile number");
		} else if (editTextMobileSignUp.getText().toString().length() != 10) {
			editTextMobileSignUp.setError("Please enter valid 10 digit number");
			editTextMobileSignUp.setText("");
		} else if (editTextCitySignUp.getText().toString().length() == 0) {
			editTextCitySignUp.setError("Please enter city name");
		} else if (!cityBoolean) {
			editTextCitySignUp.setError("City should not contain number ");
			editTextCitySignUp.setText("");
		} else if (editTextCountrySignUp.getText().toString().length() == 0) {
			editTextCountrySignUp.setError("Please enter country name");
		} else if (!countryBoolean) {
			editTextCountrySignUp.setError("Country should not contain number ");
			editTextCountrySignUp.setText("");
		} else {

			String aboutme = null;
			String message = null;

			/**
			 * provide the validation to check weather to text is valid or not
			 */
			if (editTextStatusSignUp.getText().toString().length() == 0) {

				message = "Status message";

			} else {
				message = editTextStatusSignUp.getText().toString();
			}

			if (editTextAboutSignUp.getText().toString().length() == 0) {

				aboutme = "About Me";
			} else {
				aboutme = editTextAboutSignUp.getText().toString();
			}

			name = editTextNameSignUp.getText().toString().trim().toLowerCase();
			userid = usernameeditext.getText().toString().trim().toLowerCase();
			gender = selectRadioTextString.toString().trim().toLowerCase();
			password = editTextPasswordSignUp.getText().toString().trim().toLowerCase();
			email = editTextEmailSignUp.getText().toString().trim().toLowerCase();
			mobile = editTextMobileSignUp.getText().toString().trim().toLowerCase();

			city = editTextCitySignUp.getText().toString().trim().toLowerCase();
			country = editTextCountrySignUp.getText().toString().trim().toLowerCase();

			new SignUpTesting().execute(name, userid, gender, password, email, mobile, aboutme, message, city, country);

		}
	}

	/**
	 * This asyntask use to sign up for new user.
	 * 
	 * @author Admin
	 * 
	 */
	private class SignUpTesting extends AsyncTask<String, Integer, Double> {

		// private URI uri;
		private String url;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDiaolog.setMessage("Please wait....");
			progressDiaolog.setCancelable(false);
			progressDiaolog.show();
		}

		@Override
		protected Double doInBackground(String... params) throws ArrayIndexOutOfBoundsException {
			// TODO Auto-generated method stub
			postSend(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8],
					params[9]);
			return null;
		}

		@Override
		protected void onPostExecute(Double result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progressDiaolog != null && progressDiaolog.isShowing())
				progressDiaolog.dismiss();

			if (bitmap != null && usernameeditext.getText().toString().length() != 0) {

				String user = usernameeditext.getText().toString();
				new ImageUploadTask().execute(user);

			} else {
				resetUserDetails();

				Intent intent = new Intent(SignUpActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}

		}

		private void postSend(String name, String userid, String gender, String password, String email, String mobile,
				String aboutme, String message, String city, String country) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			try {
				url = "http://motionpixeltech.com/androidlogin/index.php?";
				url += "name=" + name + "&username=" + userid + "&gender=" + gender + "&paswd=" + password + "&email="
						+ email + "&mobile=" + mobile + "&aboutme=" + aboutme + "&mesage=" + message + "&city=" + city
						+ "&country=" + country;
				// uri = new URI(url.replace(" ", "%20"));
				System.out.println("url>>" + url);

				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);

				HttpResponse responses = httpClient.execute(httpGet);

				// is = entity.getContent();

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
			progressDialog = new ProgressDialog(SignUpActivity.this);
			progressDialog.setMessage("Loading..");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) throws ArrayIndexOutOfBoundsException {
			// TODO Auto-generated method stub
			String response = postData(params[0]);

			return response;
		}

		private String postData(String userid) {
			// TODO Auto-generated method stub

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(AppConstants.IMAGE_UPLOAD);

			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// check weather bitmap null or not if not null then proceed for the
			// upload on server.
			if (bitmap != null) {

				bitmap.compress(CompressFormat.JPEG, 10, bos);

				byte[] data = bos.toByteArray();
				Bitmap screen = BitmapFactory.decodeByteArray(data, 0, data.length);
				float oHeight = screen.getHeight();
				float oWidth = screen.getWidth();
				float aspectRatio = oWidth / oHeight;
				int newHeight = 0;
				int newWidth = 0;
				newHeight = 450;
				newWidth = (int) (newHeight * aspectRatio);
				screen = Bitmap.createScaledBitmap(screen, newWidth, newHeight, true);
				// entity.addPart("photoId", new
				// StringBody(getIntent().getStringExtra("photoId")));
				// entity.addPart("returnformat", new StringBody("json"));
				entity.addPart("myFile", new ByteArrayBody(data, userid + ".jpg"));
				try {
					// entity.addPart("photoCaption", new StringBody(caption));
					httpPost.setEntity(entity);
					HttpResponse response = httpClient.execute(httpPost, localContext);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

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

				resetUserDetails();
				Intent intent = new Intent(SignUpActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();

				progressDialog.dismiss();

			}

		}
	}

	/**
	 * This method use to upload the image.
	 */
	private void selectImage() {

		try {

			Intent intent = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");

			startActivityForResult(Intent.createChooser(intent, "Complete action using"),

					PICK_FROM_GALLERY);

		} catch (Exception e) {
			// TODO: handle exception
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

				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
				bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
				imageViewPhotoSignUp.setVisibility(View.VISIBLE);
				imageViewPhotoSignUp.setImageBitmap(bitmap);

				cursor.close();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_LONG).show();
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

				imageViewPhotoSignUp.setVisibility(View.VISIBLE);
				imageViewPhotoSignUp.setImageBitmap(selectedBitmap);
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
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	/**
	 * Method use to reset all feilds after user successfully registration .
	 */
	public void resetUserDetails() {
		editTextNameSignUp.setText("");
		editTextNameSignUp.requestFocus();
		usernameeditext.setText("");
		editTextPasswordSignUp.setText("");
		editTextEmailSignUp.setText("");
		editTextMobileSignUp.setText("");
		editTextCitySignUp.setText("");
		editTextCountrySignUp.setText("");
		editTextAboutSignUp.setText("");
		editTextStatusSignUp.setText("");
		radioGroupSignUp.check(0);
		/**
		 * reset the image when user select image to set as user profile
		 * otherwise this block of code will not execute.
		 */
		if (bitmap != null) {

			imageViewPhotoSignUp.setImageBitmap(null);
			imageViewPhotoSignUp.setImageResource(R.drawable.dummyprofilepic);
		}
	}

	/**
	 * this asynctask will check username already exists or not,if exists it
	 * clear the username edit text untill you will not write unique username into username edittext
	 * 
	 * @author Work Station
	 *
	 */
	private class SignUpUserCheck extends AsyncTask<String, Integer, String> {
		private ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
		private String url;
		private String username1;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Please wait...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) throws ArrayIndexOutOfBoundsException {
			//take response from postSend() and return to onPostExecute() after comletion of doInBackground().
			return postSend(params[0]);
			
		}

		@Override
		protected void onPostExecute(String result) {
			// DO Auto-generated method stub
			super.onPostExecute(result);
			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			//here checking return reponse is Exists or not for the purpose of unique username
			if (result.equals("Exists")) {
				usernameeditext.setText("");
				Toast.makeText(getApplicationContext(), "this user already exist", Toast.LENGTH_SHORT).show();
			}
		}

		private String postSend(String username) {
			// DO Auto-generated method stub
			try {
				url = "http://motionpixeltech.com/androidlogin/checkuser.php?";
				url += "username=" + username;
				System.out.println("url>>>>>>" + url);
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				ServiceHandler serviceHandler = new ServiceHandler();
				String strUrl = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
				JSONObject jsonObject = new JSONObject(strUrl);

				JSONArray jsonArray = jsonObject.getJSONArray("new_users");

				JSONObject jsObjectArray = jsonArray.getJSONObject(0);
				// System.out.println("status>>>>>>>>>>>>>>>>>>>>>>" + status);

				username1 = jsObjectArray.getString("username");
				

			} catch (ClassCastException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return username1;
		}
	}

}
