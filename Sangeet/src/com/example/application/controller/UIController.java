/**
 * 
 */
package com.example.application.controller;

import android.content.Intent;

import com.example.application.defines.AppConstants;
import com.example.application.factory.ViewFactory;
import com.example.framework.controller.IController;

/**
 * UIController.java
 * 
 * The UIController Class, which helps for add and remove the AbstractView from
 * the Device Screen This Class gives transition effect for adding and removing
 * the AbstractView or any other View(Provided by native platforms)
 * 
 * 
 */

public class UIController implements IController {

	/**
	 * private instance of UIControllers for singleton Design Pattern
	 */
	private static UIController instance;

	private UIController() {

	}

	public static UIController getInstance() {
		if (instance == null) {
			// creating new instance of application controller
			instance = new UIController();
		}
		return instance;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		// create the stack to hold AbstractViews reference in the application

	}

	public void pushScreen(int screenId) {
		Intent intent = new Intent(ApplicationController.getInstance()
				.getApplication().getApplicationContext(), ViewFactory
				.getInstance().getActivityClass(screenId));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ApplicationController.getInstance().getApplication()
				.startActivity(intent);
	}

	/**
	 * Launches the My Location screen.
	 */
	public void launchMyLocationScreen(int screenId, Object locType) {
		Intent intent = new Intent(ApplicationController.getInstance()
				.getApplication().getApplicationContext(), ViewFactory
				.getInstance().getActivityClass(screenId));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(AppConstants.KEY_LOCATION_TYPE, (String) locType);
		ApplicationController.getInstance().getApplication()
				.startActivity(intent);
	}

	public void popScreen() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void enable() {

	}

	@Override
	public void disable() {

	}

	@Override
	public void hideNotify() {

	}

	@Override
	public void backKeyPressed() {

	}

	@Override
	public void showNotify() {

	}

	@Override
	public void onDeviceOrientationChanged(int mode) {

	}

}
