/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.framework.net;

/**
 * CancelException.java The Class to handle Cancellation of network request of
 * any other communication in thread
 * 
 */
public class CancelException extends Exception {
	/**
	 * Unique error code to handle exception
	 */
	int errorCode;

	/**
	 * Constructor
	 */
	public CancelException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Message to be access after exception
	 */
	public CancelException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param errorCode
	 *            Unique error code to handle exception
	 */
	public CancelException(int errorCode) {
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
