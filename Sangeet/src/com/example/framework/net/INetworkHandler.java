package com.example.framework.net;

import java.net.HttpURLConnection;

/**
 * INetworkHandler.java An Interface for implementing all the required network
 * protocol
 * 
 */
public interface INetworkHandler {

	/**
	 * Initializing necessary data
	 */
	public void initialize();

	/**
	 * To Destroy Other data.
	 */
	public void destroy();

	/**
	 * To Open Connection
	 */
	public HttpURLConnection openConnection() throws Exception;

	/**
	 * To Close Connection
	 */
	public void closeConnection();
}
