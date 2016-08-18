package com.motion.sangeet;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.motion.actionbar.BaseActivity;
import com.motion.util.Util;

public class FeedBackActivity extends BaseActivity {

	private ImageView sendImageView;
	private EditText messageEditText;
	private String feedbackMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLayoutInflater().inflate(R.layout.activity_feed_back,
				contenFrameLayout);

		intialiseViews();
		sendImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// calling asyntaks to sending mail as feedback to company email
				// id
				if (messageEditText.getText().length() == 0) {

					Toast.makeText(getApplicationContext(),
							"Enter message to send feedback",
							Toast.LENGTH_SHORT).show();

				} else if (!Util.isNetworkAvailable(getApplicationContext())) {

					messageEditText.setText("");

					Toast.makeText(getApplicationContext(),
							"Connect internet to send feedback",
							Toast.LENGTH_SHORT).show();

				} else {

					new sendEmailAsyn().execute();
					feedbackMessage = messageEditText.getText().toString();

				}

			}
		});
	}

	/**
	 * This method use to initialise the views.
	 */
	private void intialiseViews() {
		// TODO Auto-generated method stub

		sendImageView = (ImageView) findViewById(R.id.sendMailImageView);
		messageEditText = (EditText) findViewById(R.id.messageEditText);

	}

	// This method use to send email to user.
	private class sendEmailAsyn extends AsyncTask<Void, Void, Void> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(FeedBackActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			// Get the session object
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"greeturfriend66@gmail.com", "greet1234");// change
							// accordingly
						}
					});

			// compose message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(
						"greeturfriend66@gmail.com", "Greet"));// change
				// accordingly
				// message.addRecipient(Message.RecipientType.TO,
				// new InternetAddress(receiverMail, receiverName));
				message.setRecipients(Message.RecipientType.TO,
						"cprasad362@gmail.com");
				message.setSubject("FEEEDBACK");
				message.setText(feedbackMessage);

				// send message
				Transport.send(message);

				System.out.println("message sent successfully");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.cancel();

			messageEditText.setText("");

		}

	}

}
