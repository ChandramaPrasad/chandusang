/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.framework.net;

/**
 * WifiMobileDataException.java The Class to handle wifi/mobile data
 * un-availability sort of exception
 */
public class WifiMobileDataException extends Exception {
	/**
	 * Unique error code to handle exception
	 */
	int errorCode;

	/**
	 * Constructor
	 */
	public WifiMobileDataException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Message to be access after exception
	 */
	public WifiMobileDataException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param errorCode
	 *            Unique error code to handle exception
	 */
	public WifiMobileDataException(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * The function to get Error Code Value
	 * 
	 * @return Error Code value
	 */
	public int getCode() {
		return errorCode;
	}

}
