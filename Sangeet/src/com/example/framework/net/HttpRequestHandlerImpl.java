package com.example.framework.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/***
 * handle request setd to server
 * 
 * 
 */
public class HttpRequestHandlerImpl extends HttpRequestHandler {

	/**
	 * Class Constructor
	 * 
	 * @param url
	 *            Url to hit server
	 */
	public HttpRequestHandlerImpl(String url, Context contextt) {
		serviceURL = url;
		context = contextt;

	}

	/**
	 * This function is related to set specific headers for CMS backend api.
	 * these headers are common so its good to have these headers in one
	 * function.
	 */

	/**
	 * Overridden Excecute Method
	 * 
	 * @return Data in byte array
	 * @throws com.application.exception.ApplicationException
	 */
	public byte[] execute() {
		try {
			return super.execute();
		} catch (Exception exception) {

			if (exception instanceof SocketTimeoutException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof ServerException) {

				// Log.writeLog("ServerException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}
				return exception.getMessage().getBytes();

			} else if (exception instanceof CancelException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();

			} else if (exception instanceof ConnectException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();

			} else if (exception instanceof WifiMobileDataException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof UnknownHostException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof IOException) {
				// Log.writeLog("IOException >> "+ exception.getMessage());
				// if unable to connect to server
				// System.out.println( "IOException 111  =  " +
				// exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();

			} else if (exception instanceof Exception) {
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();

			}
		}

		return null;
	}

	/**
	 * Overridden Execute Method
	 * 
	 * @param requestData
	 *            Data to be post to the server
	 * @return
	 * @throws com.application.exception.ApplicationException
	 */
	public byte[] execute(String requestData) {

		try {
			// execute super method of execute
			return super.execute(requestData);
		} catch (Exception exception) {

			if (exception instanceof SocketTimeoutException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof ServerException) {

				// Log.writeLog("ServerException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();

				// throw new
				// ApplicationException(ErrorMessageCodes.ERROR_SERVER_DOWN);
			} else if (exception instanceof CancelException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof ConnectException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof WifiMobileDataException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof UnknownHostException) {
				// Log.writeLog("CancelException >> "+ exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof IOException) {
				// Log.writeLog("IOException >> "+ exception.getMessage());
				// if unable to connect to server
				// System.out.println( "IOException 111  =  " +
				// exception.getMessage());
				exception.printStackTrace();

				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			} else if (exception instanceof Exception) {

				exception.printStackTrace();

				System.out.println("getmessage()" + exception.getMessage());
				// check if exception is already in json form or not if not then
				// we will make it
				if (exception.getMessage() == null
						|| !exception.getMessage().startsWith("{")) {

					return covertErrorStringInJsonForm(
							exception.toString() + "" + exception.getMessage())
							.getBytes();
				}

				return exception.getMessage().getBytes();
			}

		}
		return null;
	}

	public String getHttpHeaders() {
		return this.hashtable.toString();
	}

	/*
	 * method to convert normal exception msg to json form with key as "error"..
	 * added by gaurav
	 */

	public String covertErrorStringInJsonForm(String errorString) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("result", JSONObject.NULL);
			jsonObject.put("error_code", 10000);
			jsonObject.put("error_message", errorString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// checking whether the exception msg contains " symbol inside
		// or not if it contains then we suppose to convert it to
		// space otherwise json won't be created
		if (errorString.contains("\"")) {
			// replacing " by space
			errorString = errorString.replaceAll("\"", " ");
		}

		return jsonObject.toString();
	}

}
