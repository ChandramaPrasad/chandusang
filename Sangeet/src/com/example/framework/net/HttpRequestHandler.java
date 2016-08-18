package com.example.framework.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpConnection;

import android.content.Context;

/**
 * HttpRequestHandler.java The class to implement the logic for sending and
 * receiving data from server
 * 
 * 
 * 
 */

public class HttpRequestHandler implements INetworkHandler {

	/**
	 * Constant for communicating with server with standard HTTP GET Method
	 */
	public static final String HTTP_GET = "GET";

	/**
	 * Constant for communicating with server with standard HTTP POST Method
	 */
	public static final String HTTP_POST = "POST";

	/**
	 * Constant for communicating with server with standard HTTP DELETE Method
	 */

	public static final String HTTP_DELETE = "DELETE";
	/**
	 * Constant for communicating with server with standard HTTP PUT Method
	 */

	public static final String HTTP_PUT = "PUT";

	/**
	 * Hashtable to save headers parameters
	 */
	protected Hashtable<Object, Object> hashtable = new Hashtable<Object, Object>();

	/**
	 * The URL to connect to the server
	 */
	protected String serviceURL = null;

	/**
	 * Method type to establish HTTP Connection Default is GET Method
	 */
	protected String methodType = HTTP_GET;

	/**
	 * Cancel Request Variable to handle connection cancellation
	 */
	protected boolean cancelRequest = false;

	/**
	 * INetworkListener Reference for managing error and data communication
	 */
	protected INetworkListener listener;

	/**
	 * Variable to set connecting message
	 */
	protected String connectingMessage = "Connecting...";

	/**
	 * Variable to set loading data message
	 */
	protected String loadingMessage = "Loading...";

	/**
	 * Variable to set processing message
	 */
	protected String processingMessage = "Please wait...";

	/**
	 * HttpConnection reference to connect to the server
	 */
	protected HttpURLConnection connection;

	/**
	 * InputStream reference to read data from server
	 */
	protected InputStream inputStream = null;

	/**
	 * Count to handle Redirection code 302
	 */
	protected int redirectionCount = 0;
	/**
	 * Possible MAX Redirection
	 */
	public static final int MAX_REDIERCTION = 2;
	/**
	 * Request Count to handle request if any error occur for connection
	 */
	protected int requestCount = 0;
	/**
	 * Max Request Count, If there is an error to communicate with server this
	 * class will retry the connection till MAX_REQUEST
	 */
	public static final int MAX_REQUEST = 5;
	/**
	 * Maximum buffer size for reading data in one chunk
	 */
	public static final int MAX_BUFFER = 1024 * 16;// in KB

	// Below added by Gaurav

	/**
	 * maximum time should be taken to connect to the server
	 */
	public final int CONNECTION_TIMEOUT = 10000; // 10 sec

	/**
	 * maximum time should be taken to read from server
	 */

	public final int READ_TIMEOUT = 20000; // 20 sec

	/**
	 * context needed to check Internet connection from here
	 */
	public Context context;

	/**
	 * string keeps the record of type of request called
	 */
	public String requestType;

	/**
	 * To set Method Type for HTTP Request
	 * 
	 * @param methodType
	 *            HttpRequestHandler.POST or HttpRequestHandler.GET
	 */

	private final String wifiExcpetion = "Network is not available.";
	private final String cancelException = "Request has been cancelled.";
	private final String serverException = "Server is temporary unavaialbe.";
	private final String unknownHostException = "Unable to connect to server.";

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getMethodType() {
		return methodType;
	}

	/**
	 * To set headers parameter before creating connection
	 * 
	 * @param key
	 *            To set key for header
	 * @param value
	 *            To set value for given key in header
	 */
	public void setHeaders(String key, String value) {
		hashtable.put(key, value);
	}

	public String getHeaders() {
		return hashtable.toString();
	}

	/**
	 * Cancel Server Communication Request
	 */
	public void cancelRequest() {
		this.cancelRequest = true;
		closeConnection();
	}

	/**
	 * Server url to request
	 * 
	 * @param url
	 */
	public void setServiceURL(String url) {
		serviceURL = url;
	}

	/**
	 * To Destroy Other data.
	 */
	public void destroy() {

		closeConnection(); // added by ashish
		if (hashtable != null) {
			hashtable.clear();
		}
		hashtable = null;
		serviceURL = null;

		listener = null;
		connectingMessage = null;
		loadingMessage = null;
		processingMessage = null;
		connection = null;
		inputStream = null;
	}

	/**
	 * Remove headers
	 */
	public void removeHeaders() {
		hashtable.clear();
	}

	/**
	 * To Open a new connection to server
	 * 
	 * @return HttpConnection reference
	 * @throws java.lang.Exception
	 * @see HttpConnection
	 */
	public HttpURLConnection openConnection() throws Exception {

		URL url = new URL(serviceURL);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();

		try {

			hashtable.put("User-Agent", System.getProperty("http.agent"));
			Enumeration enumeration = hashtable.keys();
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				urlConnection.setRequestProperty(key, hashtable.get(key)
						.toString());
			}
			urlConnection.setRequestMethod(methodType);

		} catch (Exception e) {

		}
		return urlConnection;

	}

	/**
	 * Recieve Data from server
	 * 
	 * @return Response from server in form of ByteArray
	 * @throws java.lang.Exception
	 */
	public byte[] execute() throws Exception {

		// ByteArrayOutputStream References
		ByteArrayOutputStream baos = null;

		try {

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			// opening connection
			connection = openConnection();
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);

			if (cancelRequest) {
				throw new CancelException(cancelException);
			}
			if (listener != null) {
				listener.setLoadingMessage(connectingMessage);
			}
			connection.connect();
			// getting response code to handle error
			int responseCode = connection.getResponseCode();

			if (cancelRequest) {
				throw new CancelException(cancelException);
			}

			int byteWritten = 0;
			int length = 0;
			// responseCode = 500;
			switch (responseCode) {
			// status 406 means resource is only capable of generating content
			// not acceptable according to the Accept headers sent in the
			// request. addded by gaurav for delete mix
			case 406:
				return "{}".toString().getBytes();
			case 200:
				break;
			case 301:
				if (redirectionCount < MAX_REDIERCTION) {
					redirectionCount++;
					setServiceURL(connection.getHeaderField("Location"));
					closeConnection(connection, null, inputStream, baos);
					return execute();
				}
			case 302:
				if (redirectionCount < MAX_REDIERCTION) {
					redirectionCount++;
					setServiceURL(connection.getHeaderField("Location"));
					closeConnection(connection, null, inputStream, baos);
					return execute();
				}
				break;
			default: {

				String str = connection.getHeaderField("Content-Encoding");

				// data is zipped we need to use gzipinputstream
				if (str != null && str.compareTo("gzip") != -1) {
					inputStream = new GZIPInputStream(
							connection.getInputStream());
				} else {
					inputStream = connection.getInputStream();
				}

				baos = new ByteArrayOutputStream();
				byte[] buff = new byte[MAX_BUFFER];

				while ((length = inputStream.read(buff)) != -1) {
					baos.write(buff, 0, length);
				}

				// checking whether network available or not
				if (!NetworkUtils.isNetworkAvailable(context)) {

					throw new WifiMobileDataException(wifiExcpetion);
				}

				String errorData = new String(baos.toByteArray());

				// if data is not in json format we need to throw the exception
				if (errorData.trim().charAt(0) == '{') {

					throw new ServerException(errorData);

				} else {

					throw new ServerException(serverException);
				}

			}

			}
			if (listener != null) {
				listener.setLoadingMessage(loadingMessage);
			}
			// opening input stream
			String str = connection.getHeaderField("Content-Encoding");
			//
			// data is zipped we need to use gzipinputstream
			if (str != null && str.compareTo("gzip") != -1) {
				inputStream = new GZIPInputStream(connection.getInputStream());
				String contentLength = connection
						.getHeaderField("Content-Length");
			} else {
				inputStream = connection.getInputStream();
			}
			baos = new ByteArrayOutputStream();
			byte[] buff = new byte[MAX_BUFFER];

			while ((length = inputStream.read(buff)) != -1) {

				baos.write(buff, 0, length);
				byteWritten += length;
			}

			if (listener != null) {
				listener.setLoadingMessage(processingMessage);
			}
			// Tools.printLog("in execute >> " + cancelRequest);
			if (cancelRequest) {
				throw new CancelException(cancelException);
			}

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}
			byte data[] = baos.toByteArray();

			if (listener != null) {
				listener.onDataRecieved(data);
			}

			return baos.toByteArray();
		} catch (CancelException cancelException) {

			throw cancelException;
		} catch (ServerException serverException) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute();
			} else {
				requestCount = 0;
			}

			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			throw serverException;
		} catch (WifiMobileDataException wifiMobileDataException) {

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute();
			} else {
				requestCount = 0;
			}

			throw wifiMobileDataException;
		} catch (UnknownHostException exception) {

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute();
			} else {
				requestCount = 0;
			}

			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			// occurs when there is internet connention but no data left
			throw new UnknownHostException(unknownHostException);
		} catch (ConnectException exception) {

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute();
			} else {
				requestCount = 0;
			}

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}
			// need to again make the object to set string as
			// "connection timeout"
			// occurs when there is internet connention but no data left
			throw new UnknownHostException(unknownHostException);

		} catch (SocketTimeoutException exception) {

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute();
			} else {
				requestCount = 0;
			}

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			throw exception;
		} catch (IOException ioe) {

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}
			// Tools.showExceptionLog("ioe; " + ioe.getMessage(),e);

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);
				return execute();
			} else {
				requestCount = 0;
				if (cancelRequest) {
					throw new CancelException(cancelException);
				} else {
					throw ioe;
				}
			}

		} catch (Exception ex) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute();
			} else {
				requestCount = 0;
			}

			if (cancelRequest) {
				throw new CancelException(cancelException);
			} else {
				throw ex;
			}
		} finally {
			closeConnection(connection, null, inputStream, baos);

		}

	}

	/**
	 * Recieve Data from server
	 * 
	 * @return Response from server in form of ByteArray
	 * @throws java.lang.Exception
	 */
	public byte[] execute(String data) throws Exception {

		ByteArrayOutputStream baos = null;

		try {

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			// opening connection
			connection = openConnection();
			connection.setConnectTimeout(CONNECTION_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);
			// set content length
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(data.getBytes().length));

			connection.setDoInput(true);
			connection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());

			wr.write(data.toString().getBytes());

			wr.flush();
			wr.close();

			if (cancelRequest) {
				throw new CancelException(cancelException);
			}
			if (listener != null) {
				listener.setLoadingMessage(connectingMessage);
			}
			connection.connect();
			// getting response code to handle error
			int responseCode = connection.getResponseCode();

			if (cancelRequest) {
				throw new CancelException(cancelException);
			}

			int byteWritten = 0;
			int length = 0;
			// responseCode = 500;
			switch (responseCode) {
			// 201 added by gaurav as it's needed, 201 indicates successfully
			// something created..added for create mix
			case 201:
				break;
			case 200:
				break;
			case 301:
				if (redirectionCount < MAX_REDIERCTION) {
					redirectionCount++;
					setServiceURL(connection.getHeaderField("Location"));
					closeConnection(connection, null, inputStream, baos);
					return execute(data);
				}
			case 302:
				if (redirectionCount < MAX_REDIERCTION) {
					redirectionCount++;
					setServiceURL(connection.getHeaderField("Location"));
					closeConnection(connection, null, inputStream, baos);
					return execute(data);
				}
				break;
			default: {

				String str = connection.getHeaderField("Content-Encoding");

				// data is zipped we need to use gzipinputstream
				if (str != null && str.compareTo("gzip") != -1) {

					inputStream = new GZIPInputStream(
							connection.getInputStream());

				} else {
					inputStream = connection.getInputStream();
				}

				baos = new ByteArrayOutputStream();
				byte[] buff = new byte[MAX_BUFFER];

				while ((length = inputStream.read(buff)) != -1) {
					baos.write(buff, 0, length);
				}

				// checking whether network available or not
				if (!NetworkUtils.isNetworkAvailable(context)) {

					throw new WifiMobileDataException(wifiExcpetion);
				}

				String errorData = new String(baos.toByteArray());

				// if data is not in json format we need to throw the exception
				if (errorData.trim().charAt(0) == '{') {

					throw new ServerException(errorData);
				} else {
					throw new ServerException(serverException);
				}

			}

			}
			if (listener != null) {
				listener.setLoadingMessage(loadingMessage);
			}
			// opening input stream
			String str = connection.getHeaderField("Content-Encoding");

			if (str != null && str.compareTo("gzip") != -1) {

				inputStream = new GZIPInputStream(connection.getInputStream());

			} else {
				inputStream = connection.getInputStream();

			}
			baos = new ByteArrayOutputStream();
			byte[] buff = new byte[MAX_BUFFER];

			while ((length = inputStream.read(buff)) != -1) {

				baos.write(buff, 0, length);
				byteWritten += length;
			}

			if (listener != null) {
				listener.setLoadingMessage(processingMessage);
			}
			// Tools.printLog("in execute >> " + cancelRequest);
			if (cancelRequest) {
				throw new CancelException(wifiExcpetion);
			}

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			byte response[] = baos.toByteArray();

			if (listener != null) {
				listener.onDataRecieved(response);
			}

			return response;
		} catch (CancelException cancelException) {

			throw cancelException;
		} catch (ServerException serverException) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute(data);
			} else {
				requestCount = 0;
			}

			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			throw serverException;
		} catch (WifiMobileDataException wifiMobileDataException) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute(data);
			} else {
				requestCount = 0;
			}

			throw wifiMobileDataException;
		} catch (UnknownHostException exception) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute(data);
			} else {
				requestCount = 0;
			}

			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			// occurs when there is internet connention but no data left
			throw exception;
		} catch (ConnectException exception) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute(data);
			} else {
				requestCount = 0;
			}

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}

			// need to again make the object to set string as
			// "connection timeout"
			// occurs when there is internet connention but no data left
			throw exception;

		} catch (SocketTimeoutException exception) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute(data);
			} else {
				requestCount = 0;
			}

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}
			// need to again make the object to set string as
			// "connection timeout"

			throw exception;
		} catch (IOException ioe) {

			// hitting api again in the case of next song error

			// if (requestCount < MAX_REQUEST) {
			// requestCount++;
			// closeConnection(connection, null, inputStream, baos);
			//
			// return execute(data);
			// } else {
			// requestCount = 0;
			// }

			// checking whether network available or not
			if (!NetworkUtils.isNetworkAvailable(context)) {

				throw new WifiMobileDataException(wifiExcpetion);
			}
			if (cancelRequest) {
				throw new CancelException(cancelException);
			} else {
				throw ioe;
			}

		} catch (Exception ex) {

			// hitting api again in the case of next song error

			if (requestCount < MAX_REQUEST) {
				requestCount++;
				closeConnection(connection, null, inputStream, baos);

				return execute(data);
			} else {
				requestCount = 0;
			}

			if (cancelRequest) {
				throw new CancelException(cancelException);
			} else {
				throw ex;
			}
		} finally {
			closeConnection(connection, null, inputStream, baos);
		}

	}

	/**
	 * Close All Open Connection
	 * 
	 * @param connection
	 *            HttpConnection Reference
	 * @param outputStream
	 *            OutputStream Reference
	 * @param inputStream
	 *            InputStream Reference
	 * @param baos
	 *            ByteArrayOutputStream Reference
	 */
	protected void closeConnection(HttpURLConnection connection,
			OutputStream outputStream, InputStream inputStream,
			ByteArrayOutputStream baos) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException ioe) {

			} // Ignored
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException ioe) {

			} // Ignored
		}

		if (connection != null) {

			connection.disconnect();
			// Ignored
		}
		if (baos != null) {
			try {
				baos.close();
			} catch (IOException ioe) {

			} // Ignored
		}

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		if (connection != null) {

			connection.disconnect();
			// Ignored
		}
	}

}
