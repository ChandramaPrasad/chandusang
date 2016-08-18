/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.framework.net;

/**
 * INetworkListener An Interface to inform about Network communication
 * 
 */
public interface INetworkListener {

	/**
	 * To set Progress value in percentage
	 */
	public void setProgress(int value);

	/**
	 * To set Loading Message
	 */
	public void setLoadingMessage(String loadingMessage);

	/**
	 * This function get called if application received meaningful data from
	 * server
	 * 
	 * @param data
	 *            byte array
	 */
	public void onDataRecieved(byte data[]);

	/**
	 * This function get called if there is server or we get unwanted data from
	 * server
	 * 
	 * @param data
	 */
	public void onErrorRecieved(byte data[]);

	/**
	 * This function get called if file is downloaded
	 * 
	 * @param filePath
	 *            The path of the file which is downloaded
	 */
	public void onFileDownloadComplete(String filePath);

	/**
	 * This function get called if file is downloaded
	 * 
	 * @param filePath
	 *            The path of the file which is downloaded
	 * @param object
	 *            The reference of an object for notification
	 */
	public void onFileDownloadComplete(String filePath, Object object);

	/**
	 * This function get called if there is not sufficient space in
	 * device/memory card
	 */
	public void onMemoryExceed();

}
