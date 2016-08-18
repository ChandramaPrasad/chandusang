/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.framework.net;

/**
 * ServerException.java The Class to throw exception for server communication
 * 
 */
public class ServerException extends Exception {
	/**
	 * Unique error code to handle exception
	 */
	int errorCode;

	/**
	 * Unique error code to handle exception
	 */
	public ServerException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Message to be access after exception
	 */
	public ServerException(String s) {
		super(s);
	}

	/**
	 * Constructor
	 * 
	 * @param errorCode
	 *            Unique error code to handle exception
	 */
	public ServerException(int errorCode) {
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
