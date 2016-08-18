package com.example.framework.controller;

/**
 * IController.java
 * 
 * An Interface which should be implemented by Controller in application.
 * 
 */
public interface IController {

	/**
	 * To initialize the Views or Screens, Models
	 * 
	 */
	public abstract void initialize();

	/**
	 * Destroy View and Model Data for the given Screen.
	 * 
	 */
	public abstract void destroy();

	/**
	 * This function get called whenever AbstractView redisplay on device screen
	 * 
	 */
	public abstract void enable();

	/**
	 * This function get called whenever AbstractView overlapped by another
	 * AbstractView
	 */

	public abstract void disable();

	/**
	 * To notify the AbstarctView, that there is an interruption. So
	 * AbstractView can handle all the required condition before going
	 * application in background.
	 * 
	 */
	public abstract void hideNotify();

	/**
	 * To handle back key event in all screen
	 */
	public abstract void backKeyPressed();

	/**
	 * To notify the AbstractView that application will be visible on the screen
	 * after calling this method.
	 */
	public abstract void showNotify();

	/**
	 * to notify that device orientation change when device mode change
	 * landscape to portrait or wise versa
	 * 
	 * @param mode
	 *            mode 0 for portait mode 1 for landscape
	 */
	public void onDeviceOrientationChanged(int mode);

}
