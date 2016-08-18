package com.example.framework.view;

public interface AbstractView {
	/**
	 * To initialize the View or Screen
	 * 
	 */
	public abstract void initialize();

	/**
	 * To reinitialize the View or Screen. This is for some cases when user
	 * implement freeResource to optimize memory issue.
	 */
	public abstract void reInitialize();

	/**
	 * To free only View releated stuff.
	 * 
	 */
	public abstract void freeResource();

	/**
	 * Destory View and Model Data for the given Screen.
	 * 
	 */
	public abstract void destroy();

	/**
	 * Enable function get called when screen displays again to the user
	 * 
	 */
	public abstract void enable();

	/**
	 * Disable function get called when screen is not visible to user.
	 */

	public abstract void disable();

	/**
	 * To handle Application hide functionality if application get interrupt by
	 * Device.
	 * 
	 * @return TODO
	 * 
	 */
	public abstract boolean hideNotify();

	/**
	 * This method is only invoked when the underlying canvas for the form is
	 * shown this method isn't called for form based events and is generally
	 * usable for suspend/resume based behavior
	 * 
	 * @return TODO
	 */
	public abstract boolean showNotify();

	/**
	 * To handle back key event in all screen
	 * 
	 * @return TODO
	 */
	public abstract boolean backKeyPressed();

	/**
	 * To update a view
	 */
	public abstract void update();

	/**
	 * to notify that device orientation change when device mode change
	 * landscape to portrait or wise versa
	 * 
	 * @param mode
	 *            mode 0 for portait mode 1 for landscape
	 */
	public abstract void onDeviceOrientationChanged(int mode);
}
