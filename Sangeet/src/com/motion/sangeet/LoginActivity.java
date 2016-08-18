package com.motion.sangeet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.motion.actionbar.BaseActivity;
import com.motion.dao.LoginDao;
import com.motion.net.ServiceHandler;
import com.motion.util.UserSessionManager;

public class LoginActivity extends Activity {
	private EditText editTextUsernameLogin;
	private EditText editTextPasswordLogin;
	private ImageView buttonImageViewLoginLogin;
	private ImageView buttonImageViewSignUpLogin;
	private ArrayList<LoginDao> arraLoginDao;
	String username;
	String password;
	String idsingup;
	String statusmsg;
	String aboutme;
	String name;
	String emailid;
	String mobile;
	private UserSessionManager sessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		initViewSComponent();

		registerEvent();
	}

	/**
	 * this method use for registering the event on component
	 */
	private void registerEvent() {
		// TODO Auto-generated method stub
		buttonImageViewLoginLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v == buttonImageViewLoginLogin)
					registerUser();
			}
		});
		buttonImageViewSignUpLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginActivity.this,
						SignUpActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * this method use to initialise the component(views)
	 */
	private void initViewSComponent() {
		// TODO Auto-generated method stub
		arraLoginDao = new ArrayList<LoginDao>();

		editTextUsernameLogin = (EditText) findViewById(R.id.editTextUsernameLogin);
		editTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);

		sessionManager = new UserSessionManager(getApplicationContext());

		buttonImageViewSignUpLogin = (ImageView) findViewById(R.id.buttonImageViewSignUpLogin);

		buttonImageViewLoginLogin = (ImageView) findViewById(R.id.buttonImageViewLoginLogin);

	}

	private void registerUser() {
		// TODO Auto-generated method stub
		editTextUsernameLogin.setError(null);
		// editTextPasswordLogin.setError(null);
		if (editTextUsernameLogin.getText().toString().length() == 0) {
			// editTextUsernameLogin.requestFocus();
			editTextUsernameLogin.setError("Please enter username");
		}

		else if (editTextPasswordLogin.getText().toString().length() == 0) {
			// editTextPasswordLogin.requestFocus();
			editTextPasswordLogin.setError("Please enter password");

		} else {
			String userid = editTextUsernameLogin.getText().toString().trim()
					.toLowerCase();
			String password = editTextPasswordLogin.getText().toString().trim()
					.toLowerCase();
			new LoginTesting().execute(userid, password);

		}

	}

	private class LoginTesting extends AsyncTask<String, Integer, Double> {
		private ProgressDialog progressDialog = new ProgressDialog(
				LoginActivity.this);
		private String url;
		private String idsignup;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Please wait...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Double doInBackground(String... params)
				throws ArrayIndexOutOfBoundsException {

			postSend(params[0], params[1]);
			return null;
		}

		@Override
		protected void onPostExecute(Double result) {
			// DO Auto-generated method stub
			super.onPostExecute(result);
			if (progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
			if (idsignup != null && idsignup.equals("null")) {
				Toast.makeText(getApplicationContext(), "Invalid credential",
						Toast.LENGTH_LONG).show();
				editTextPasswordLogin.setText("");
				editTextUsernameLogin.setText("");
				editTextUsernameLogin.requestFocus();
			}

			else {
				Toast.makeText(getApplicationContext(), "Login Successfully",
						Toast.LENGTH_SHORT).show();

				sessionManager.createUserLoginSession(username, password,
						idsingup, name);
				Intent intent = new Intent(LoginActivity.this,
						BaseActivity.class);
				startActivity(intent);
				finish();
			}
		}

		private void postSend(String userid, String paswd) {
			// DO Auto-generated method stub
			try {
				url = "http://motionpixeltech.com/androidlogin/index.php?";
				url += "username=" + userid + "&paswd=" + paswd;
				System.out.println("url>>>>>>" + url);
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				ServiceHandler serviceHandler = new ServiceHandler();
				String strUrl = serviceHandler.makeServiceCall(url,
						ServiceHandler.GET);
				JSONObject jsonObject = new JSONObject(strUrl);

				JSONArray jsonArray = jsonObject.getJSONArray("new_users");

				JSONObject jsObjectArray = jsonArray.getJSONObject(0);

				// System.out.println("status>>>>>>>>>>>>>>>>>>>>>>" + status);
				idsignup = jsObjectArray.getString("idsignup");
				if (!idsignup.equals("null")) {
					// LoginDao loginDao = new LoginDao();
					idsingup = jsObjectArray.getString("idsignup");
					password = jsObjectArray.getString("paswd");
					statusmsg = jsObjectArray.getString("status message");
					aboutme = jsObjectArray.getString("about me");
					username = jsObjectArray.getString("Username");
					name = jsObjectArray.getString("name");
					emailid = jsObjectArray.getString("email");
					mobile = jsObjectArray.getString("mobile");
					// arraLoginDao.add(loginDao);

				}
				// System.out.println("Array Login Value" + arraLoginDao);
				// if (arraLoginDao != null) {
				// LocalModel.getInstance().setArrayListLoginLocalModel(
				// arraLoginDao);
				// }

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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
